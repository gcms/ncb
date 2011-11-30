package cvm.ncb.oem.pe;

import cvm.model.CVM_Debug;
import cvm.ncb.csm.CommServiceManager;
import cvm.ncb.ks.CommFWCapabilitySet;
import cvm.ncb.ks.ConIDMappingTable;
import cvm.ncb.ks.Connection;
import cvm.ncb.manager.NCBCall;
import cvm.ncb.manager.NCBCallQueue;
import cvm.ncb.oem.policy.Attribute;
import cvm.ncb.oem.policy.Feature;
import cvm.ncb.oem.policy.Framework;
import cvm.ncb.oem.policy.PolicyManager;
import cvm.ncb.repository.loader.GlobalConstant;
import cvm.ncb.tpm.CommFWResource;
import cvm.ncb.tpm.CommFWTouch;
import cvm.ncb.tpm.CommTPManager;
import edu.fiu.strg.ACSTF.resource.AbstractResource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.TreeSet;

public class PolicyEvalManager extends AbstractResource implements Runnable {
    private static Log log = LogFactory.getLog(PolicyEvalManager.class);

    private static PolicyEvalManager instance = null;
    private NCBCallQueue m_callQueue = null;
    private CommServiceManager csMgr = null;
    private PolicyManager pm = null;
    private CommTPManager scManager = null;
    private boolean useACF = false;
    private String testConnId = "";
    private boolean testFWChanged = false;
    private int testCount = 0;
    private boolean testFlag = false;
    private boolean resultFlag = false;


    /**
     * Singleton implementation.
     *
     * @return instance of NCBCallQueue
     */
    public static PolicyEvalManager getInstance() {
        if (instance == null)
            instance = new PolicyEvalManager();
        return instance;
    }

    private PolicyEvalManager() {
        m_callQueue = NCBCallQueue.getInstance();
        csMgr = CommServiceManager.getInstance();
        pm = new PolicyManager();
        useAutonomicFramework();
        Thread t = new Thread(this);
        t.start();
    }

    private void useAutonomicFramework() {
        CommFWResource cr = new CommFWResource(csMgr.getCommObjectList());
        try {
            scManager = new CommTPManager<CommFWTouch>("CVM_SC_MGR",
                    this.getClass().getResource("../../tpm/CVMSelfConfig.xml").toURI(), cr);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        useACF = false;
        //scManager = new CommFWTestpoint(cr);
    }

    public void run() {
        if (useACF == true)
            scManager.manage();

        while (true) {
            if (m_callQueue.hasNext()) {
                NCBCall m_call = getNextCommand();
                log.debug("Next command on queue: " + m_call);
                evaluateCall(m_call);
                //executeCommand(m_call);
            } else {
                //CVM_Debug.getInstance().printDebugMessage("Empty");
            }
            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    /**
     * get the next NCB request from the call queue
     */
    private NCBCall getNextCommand() {
        return m_callQueue.next();
    }

    /**
     * evaluating the call against the frameworks to
     * find the appropriate framework
     */
    private void evaluateCall(NCBCall m_call) {
        log.debug("Evaluating: " + m_call);
        boolean fwChanged = false;
        if (m_call.getCallName().equals("failedFramework")) {
            Iterator<Connection> itr = ConIDMappingTable.getInstance().getAllConnections();
            while (itr.hasNext()) {
                Connection con = itr.next();
                if (con.containsComObj(m_call.sessionID())) { // framework name
                    Object[] obj = {con.getConId(), con.getDefaultMedium()};
                    m_callQueue.add(con.getConId(), 0, "enableMedium", con.getDefaultMedium(), obj);
                    CVM_Debug.getInstance().printDebugMessage("PolicyEvalManager : Queuing NCB sendMedia called with connectionID:\"" +
                            con.getConId() + "\" and MediumName:\"" + con.getDefaultMedium() + "\".");
                }
            }
        } else if (m_call.getCallName().equals("sendSchema")) {
            csMgr.sendSchema((String) m_call.getParams()[0], (String) m_call.getParams()[1],
                    (String) m_call.getParams()[2], (String) m_call.getParams()[3],
                    (String) m_call.getParams()[4]);
            //executeCSMCommand(m_call);
            return;
        } else if (m_call.getCallName().equals("loginAll")) {
            csMgr.loginAll();
            //executeCSMCommand(m_call);
            return;
        } else if (m_call.getCallName().equals("logoutAll")) {
            csMgr.logoutAll();
            //executeCSMCommand(m_call);
            return;
        } else if (m_call.getCallName().equals("createSession")) {
            if (scManager.hasConnection(m_call.sessionID())) {
                scManager.getConnection(m_call.sessionID()).offerNCBCall(m_call);
            } else {
                scManager.updateConTable(m_call.sessionID());
                scManager.getConnection(m_call.sessionID()).offerNCBCall(m_call);
            }
        } else if (!scManager.hasConnection(m_call.sessionID())) {
            // connection ID does not exist!
            return;
        } else if (m_call.getCallName().contains("Medium")) {
            scManager.getConnection(m_call.sessionID()).offerNCBCall(m_call);
            Object reqType = null;
            if (m_call.getMedium().equalsIgnoreCase("AUDIO"))
                reqType = GlobalConstant.RequestedType.Audio;
            else reqType = GlobalConstant.RequestedType.Video;
            TreeSet<Framework> fwSet = pm.getFrameworkSet(CommFWCapabilitySet
                    .getInstance().getAllFrameworks(),
                    (GlobalConstant.RequestedType) reqType,
                    GlobalConstant.OperationType.request,
                    scManager.getConnection(m_call.sessionID())
                            .getNumOfUsers());//.iterator().next().getFrameworkName();
            fwChanged = scManager.updateConTable(m_call.sessionID(), m_call.getMedium(),
                    fwSet.iterator().next().getFrameworkName());

            this.testFWChanged = fwChanged;
            this.testCount++;
            this.testFlag = (fwChanged && this.testCount == 2);
            CVM_Debug.getInstance().printDebugMessage("FwSet contains: " + fwSet + " FwName: " + fwSet.iterator().next().getFrameworkName() + " fwChanged: " + fwChanged);
            if (fwChanged) {
                generateChangeCalls(scManager.getConnection(m_call.sessionID()));
            }
//	    	if (!this.testFlag) {
            executeAllCommands(scManager.getConnection(m_call.sessionID()));
/*	    	}
	    	else {
	    		System.out.println("Testing, Testing, 123");
				cvm.ncb.test.FWSwitchTestDriver.getInstance().use(
					new cvm.ncb.handlers.event.CallExecuted_Event(
							scManager.getConnection(m_call.sessionID())));
				while(this.testFlag);
				if (this.resultFlag) { 
					executeAllCommands(scManager.getConnection(m_call.sessionID())); 
				}
				else {
					System.out.println("TESTING FAILED!!!!");
				}	
	    	}
*/
        }
        //else if(m_call.getCallName().startsWith("disable")){
        //	scManager.getConnection(m_call.sessionID()).offerNCBCall(m_call);
        //}
        else scManager.getConnection(m_call.sessionID()).offerNCBCall(m_call);
        //executeCSMCommand(m_call);
    }

    /**
     * executing the call
     */
    private void executeCSMCommand(NCBCall m_call) {
        log.debug("Executing call: " + m_call);
        csMgr.executeCall(m_call.sessionID(), m_call.getCallName(),
                m_call.getMedium(), m_call.getParams());
    }

    private void executeAllCommands(Connection con) {
        for (Object fwName : scManager.getResetFWTableIterator()) {
            if (con.containsComObj((String) fwName)) {
                // destroy connection
                scManager.removeConnection(con.getConId());
                // remove from FWResetTable
                scManager.removeFWFromResetFWTable((String) fwName);
            }
        }
        while (con.peekCall() != null) {
            executeCSMCommand(con.pollCall());
        }
    }

    private void generateChangeCalls(Connection con) {
        CVM_Debug.getInstance().printDebugMessage("Generating new call for framework "
                + con.getComObj(con.getDeactivatedMedia().get(0)));
        Object[] conIDs = {con.getConId()};
        for (String medium : con.getDeactivatedMedia()) {
            if (medium == null) continue;
            CVM_Debug.getInstance().printDebugMessage("Generating" +
                    " destroy call for " + medium);
            con.offerNCBCall(new NCBCall(con.getConId(), 0,
                    "destroySession", medium, conIDs));
        }
        con.offerNCBCall(new NCBCall(con.getConId(), 0, "createSession",
                con.getDefaultMedium(), conIDs));
        for (String party : con.getParticipants()) {
            Object[] params = {con.getConId(), party};
            con.offerNCBCall(new NCBCall(con.getConId(), 1, "addAParticipant",
                    con.getDefaultMedium(), params));
        }

    }

    private TreeSet<Framework> getAllFrameworks() {
        TreeSet<Framework> hs = new TreeSet<Framework>();

        // Frameworks that are available
        Framework fw1 = new Framework("Skype");
        Feature fw1feat1 = new Feature("Audio");
        fw1feat1.addAttribute(new Attribute("Enabled", "true"));
        fw1feat1.addAttribute(new Attribute("NumberOfUsers", "4"));

        Feature fw1feat2 = new Feature("Video");
        fw1feat2.addAttribute(new Attribute("Enabled", "true"));
        fw1feat2.addAttribute(new Attribute("NumberOfUsers", "2"));
        fw1feat2.addAttribute(new Attribute("onlineStatus.Enabled", "true"));

        fw1.addFeature(fw1feat1);
        fw1.addFeature(fw1feat2);

        Framework fw2 = new Framework("Smack");
        Feature fw2feat1 = new Feature("Audio");
        fw2feat1.addAttribute(new Attribute("Enabled", "true"));
        fw2feat1.addAttribute(new Attribute("NumberOfUsers", "6"));

        fw2.addFeature(fw2feat1);

        Feature fw2feat2 = new Feature("Video");
        fw2feat2.addAttribute(new Attribute("Enabled", "true"));
        fw2feat2.addAttribute(new Attribute("NumberOfUsers", "1"));
        fw2feat2.addAttribute(new Attribute("onlineStatus.Enabled", "true"));
        fw2.addFeature(fw2feat2);

        // Available for NCB
        hs.add(fw1);
        hs.add(fw2);

        return hs;
    }

    public String getTestConnId() {
        return testConnId;
    }

    public void setTestConnId(String testConnId) {
        this.testConnId = testConnId;
    }

    public boolean getTestFWChanged() {
        return testFWChanged;
    }

    public void setTestFWChanged(boolean testFWChanged) {
        this.testFWChanged = testFWChanged;
    }

    public int getTestCount() {
        return testCount;
    }

    public void setTestCount(int testCount) {
        this.testCount = testCount;
    }

    public boolean getTestFlag() {
        return testFlag;
    }

    public void setTestFlag(boolean testFlag) {
        this.testFlag = testFlag;
    }

    public boolean getResultFlag() {
        return resultFlag;
    }

    public void setResultFlag(boolean resultFlag) {
        this.resultFlag = resultFlag;
    }

}
