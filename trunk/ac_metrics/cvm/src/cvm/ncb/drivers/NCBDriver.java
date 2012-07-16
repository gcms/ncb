package cvm.ncb.drivers;

import cvm.facade.NCB_M_Facade;
import cvm.sb.adapters.Manageable;
import cvm.sb.resource.bridge.ManagedResource;
import cvm.sb.resource.ResourceManager;
import cvm.sb.manager.SignalInstance;
import util.CVM_Debug;
import cvm.model.UsesEventListener;
import cvm.ncb.handlers.EventManager;
import cvm.ncb.manager.NCBManager;
import cvm.sb.policy.metadata.Metadata;
import util.FeaturesParser;

import java.util.Scanner;

public class NCBDriver implements UsesEventListener {
    private String cvmUname;
    private int DELAY = 20000;
    private NCBManager manager;


    public NCBDriver(String cvmUname) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        EventManager eventManager = new EventManager();
        eventManager.addUpListener(this);

        ResourceManager om = new ResourceManager();
        for (Metadata md : FeaturesParser.createAllFrameworks()) {
            Manageable manageable = (Manageable) Class.forName("cvm.ncb.adapters." + md.getName() + "Adapter").newInstance();
            om.addObject(new ManagedResource(md, manageable));
        }

//        manager = new NCBManager(om, eventManager);

        this.cvmUname = cvmUname;
    }

    public NCBDriver(NCBManager manager, String cvmUname) {
        this.manager = manager;
        this.cvmUname = cvmUname;
    }

    /**
     * @param args
     */
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, InterruptedException {
        NCBDriver driver = new NCBDriver("Andrew");
        driver.run();

        Thread.sleep(30000);
        driver.manager.logout("Andrew");
        System.exit(0);
    }

    public void run() {
        manager.login(cvmUname, "password");
        manager.createSession("101");

        twoWay(manager, "AUDIO");
    }

    public void twoWay(NCBManager manager, String medium) {
        manager.sendSchema("101 ", " Yali", "101 " + medium + " Yali Andrew", null);
        manager.addParty("101", "Yali");
        manager.enableMedium("101", medium);
    }

    public void threeWay(NCB_M_Facade m_facade, String medium) {
        m_facade.sendSchema("101 ", "Andrew", " Yali", "101 " + medium + " Yali Tariq Andrew", null);
        m_facade.sendSchema("101 ", "Andrew", " Tariq", "101 " + medium + " Yali Tariq Andrew", null);
        m_facade.addParty("101", "Yali");
        m_facade.addParty("101", "Tariq");
        m_facade.enableMedium("101", medium);
    }

    public void fourWay(NCB_M_Facade m_facade, String medium) {
        m_facade.sendSchema("101 ", "Andrew", " Yali", "101 " + medium + " Yali Tariq Yingbo Andrew", null);
        m_facade.sendSchema("101 ", "Andrew", " Tariq", "101 " + medium + " Yali Tariq Yingbo Andrew", null);
        m_facade.sendSchema("101 ", "Andrew", " Yingbo", "101 " + medium + " Yali Tariq Yingbo Andrew", null);
        m_facade.addParty("101", "Yali");
        m_facade.addParty("101", "Tariq");
        m_facade.addParty("101", "Yingbo");
        m_facade.enableMedium("101", medium);
    }

    public void fiveWay(NCB_M_Facade m_facade, String medium) {
        m_facade.sendSchema("101 ", "Andrew", " Yali", "101 " + medium + " Yali Tariq Yingbo Karl Andrew", null);
        m_facade.sendSchema("101 ", "Andrew", " Tariq", "101 " + medium + " Yali Tariq Yingbo Karl Andrew", null);
        m_facade.sendSchema("101 ", "Andrew", " Yingbo", "101 " + medium + " Yali Tariq Yingbo Karl Andrew", null);
        m_facade.sendSchema("101 ", "Andrew", " Karl", "101 " + medium + " Yali Tariq Yingbo Karl Andrew", null);
        m_facade.addParty("101", "Yali");
        m_facade.addParty("101", "Tariq");
        m_facade.addParty("101", "Yingbo");
        m_facade.addParty("101", "Karl");
        m_facade.enableMedium("101", medium);
    }

    public void twoToThreeWay(NCB_M_Facade m_facade, String medium) {
        twoWay(m_facade.getManager(), medium);
        waitForAWhile(DELAY);
        m_facade.sendSchema("101 ", "Andrew", " Yali", "101 " + medium + " Yali Tariq Andrew", null);
        m_facade.sendSchema("101 ", "Andrew", " Tariq", "101 " + medium + " Yali Tariq Andrew", null);
        m_facade.addParty("101", "Yali");
        m_facade.addParty("101", "Tariq");
        m_facade.enableMedium("101", medium);
    }

    public void threeToFourWay(NCB_M_Facade m_facade, String medium) {
        threeWay(m_facade, medium);
        waitForAWhile(DELAY);
        m_facade.sendSchema("101 ", "Andrew", " Yali", "101 " + medium + " Yali Tariq Yingbo Andrew", null);
        m_facade.sendSchema("101 ", "Andrew", " Tariq", "101 " + medium + " Yali Tariq Yingbo Andrew", null);
        m_facade.sendSchema("101 ", "Andrew", " Yingbo", "101 " + medium + " Yali Tariq Yingbo Andrew", null);
        m_facade.addParty("101", "Yali");
        m_facade.addParty("101", "Tariq");
        m_facade.addParty("101", "Yingbo");
        m_facade.enableMedium("101", medium);
    }

    public void fourToFiveWay(NCB_M_Facade m_facade, String medium) {
        fourWay(m_facade, medium);
        waitForAWhile(DELAY);
        m_facade.sendSchema("101 ", "Andrew", " Yali", "101 " + medium + " Yali Tariq Yingbo Karl Andrew", null);
        m_facade.sendSchema("101 ", "Andrew", " Tariq", "101 " + medium + " Yali Tariq Yingbo Karl Andrew", null);
        m_facade.sendSchema("101 ", "Andrew", " Yingbo", "101 " + medium + " Yali Tariq Yingbo Karl Andrew", null);
        m_facade.sendSchema("101 ", "Andrew", " Karl", "101 " + medium + " Yali Tariq Yingbo Karl Andrew", null);
        m_facade.addParty("101", "Yali");
        m_facade.addParty("101", "Tariq");
        m_facade.addParty("101", "Yingbo");
        m_facade.addParty("101", "Karl");
        m_facade.enableMedium("101", medium);
    }

    private void waitForAWhile(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("Done Sleeping ==============================");
    }

    public void use(SignalInstance event) {
        if (event.getName().equals("SchemaReceived"))
            dealWithSchema((String) event.getParams().get("schema"));
    }

    /**
     * This function is the driver's way of handling a schema received event.
     *
     * @param schema schema file.
     */
    private synchronized void dealWithSchema(String schema) {
        CVM_Debug.getInstance().printDebugMessage("NCBDriver: dealWithSchema Called." + schema);
        Scanner sch = new Scanner(schema);
        String sessId = sch.next();
        manager.createSession(sessId);
        String medium = sch.next();
        // add participants
        while (sch.hasNext()) {
            String name = sch.next();
            // remove yourself from list of participants
            if (name.equalsIgnoreCase(cvmUname)) {
                continue;
            }
            manager.addParty(sessId, name);
        }
        manager.enableMediumReceiver(sessId, medium);
    }

}
