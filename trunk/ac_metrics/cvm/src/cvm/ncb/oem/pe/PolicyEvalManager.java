package cvm.ncb.oem.pe;

import cvm.ncb.csm.BridgeCall;
import cvm.ncb.csm.CommServiceManager;
import cvm.ncb.handlers.NCBExceptionHandler;
import cvm.ncb.handlers.exception.LoginException;
import cvm.ncb.ks.CommFWCapabilitySet;
import cvm.ncb.ks.ConIDMappingTable;
import cvm.ncb.ks.Connection;
import cvm.ncb.oem.policy.Framework;
import cvm.ncb.oem.policy.PolicyManager;
import cvm.ncb.repository.loader.GlobalConstant;
import cvm.ncb.tpm.CommFWResource;
import cvm.ncb.tpm.CommTPManager;
import edu.fiu.strg.ACSTF.resource.AbstractResource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class PolicyEvalManager extends AbstractResource implements Runnable {
    private static Log log = LogFactory.getLog(PolicyEvalManager.class);

    private NCBCallQueue m_callQueue = null;
    private CommServiceManager csMgr = null;
    private PolicyManager pm = null;
    private CommTPManager scManager = null;
    private boolean useACF = true;
    private boolean run = false;

    private CommFWResource commFWResource;

    private List<NCBCallHandler> callHandlers;
    private NCBExceptionHandler m_xhXHandler;


    public PolicyEvalManager(NCBExceptionHandler exceptionHandler) {
        m_xhXHandler = exceptionHandler;
        csMgr = new CommServiceManager(exceptionHandler);

        m_callQueue = new NCBCallQueue();
        pm = new PolicyManager();

        commFWResource = new CommFWResource(m_callQueue,  new ConIDMappingTable(), csMgr);
        scManager = createTouchPointManager(commFWResource);

        callHandlers = new ArrayList<NCBCallHandler>();
    }

    public void registerHandler(NCBCallHandler handler) {
        callHandlers.add(handler);
    }

    public void start() {
        run = true;
        Thread t = new Thread(this);
        t.start();
    }

    public void stop() {
        run = false;
    }

    private CommTPManager createTouchPointManager(CommFWResource cr) {
        try {
            return new CommTPManager("CVM_SC_MGR",
                    this.getClass().getResource("../../tpm/CVMSelfConfig.xml").toURI(), cr);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void run() {
        if (useACF)
            scManager.manage();

        while (run) {
            if (m_callQueue.hasNext()) {
                NCBCall m_call = m_callQueue.next();
                log.debug("Next command on queue: " + m_call);
                evaluateCall(m_call);
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
     * evaluating the call against the frameworks to
     * find the appropriate framework
     */
    private void evaluateCall(NCBCall m_call) {
        log.debug("Evaluating: " + m_call);
        for (NCBCallHandler callHandler : callHandlers) {
            if (callHandler.canHandle(m_call)) {
                callHandler.handle(m_call, commFWResource, this);
                break;
            }
        }
    }

    public Framework findNewFramework(GlobalConstant.RequestedType reqType, GlobalConstant.OperationType opType, int numOfUsers) {
        TreeSet<Framework> allFrameworks = CommFWCapabilitySet.getInstance().getAvailableFrameworks();
        log.debug("All frameworks: " + allFrameworks);

        TreeSet<Framework> fwSet = pm.getFrameworkSet(allFrameworks, reqType, opType, numOfUsers);
        log.debug("Reduced set: " + fwSet);

        return fwSet.iterator().next();
    }

    /**
     * executing the call
     */
    private void executeCSMCommand(Connection con, BridgeCall m_call) {
        log.debug("Executing call: " + m_call);
        log.debug(m_call.getName() + "/" + m_call.getMedium() + "/" + m_call.getFwName() + "/" + con.getComObj(m_call.getMedium()) + "/" + con.getPreviousComObj(m_call.getMedium()));
        try {
            csMgr.executeCall(con, m_call);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (InvocationTargetException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IllegalAccessException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public void executeAllCommands(Connection con) {
        restartService(con);

        while (con.peekCall() != null) {
            executeCSMCommand(con, con.pollCall());
        }
    }

    private void restartService(Connection con) {
        for (String fwName : scManager.getResetFWTableIterator()) {
            assert false;
            if (con.containsComObj(fwName)) {
                // destroy connection
                commFWResource.getConIDMappingTable().remove(con.getConId());
                // remove from FWResetTable
                scManager.removeFWFromResetFWTable(fwName);
            }
        }
    }

    public NCBCallQueue getCallQueue() {
        return m_callQueue;
    }

    public void handleException(LoginException e) {
        m_xhXHandler.handleException(e);
    }
}
