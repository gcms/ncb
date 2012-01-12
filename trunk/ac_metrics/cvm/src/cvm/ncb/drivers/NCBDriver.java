package cvm.ncb.drivers;

import cvm.model.CVM_Debug;
import cvm.model.Event;
import cvm.model.UsesEventListener;
import cvm.ncb.csm.ManagedObjectFactory;
import cvm.ncb.handlers.EventManager;
import cvm.ncb.ks.ObjectManager;
import cvm.ncb.manager.NCBManager;
import cvm.ncb.manager.NCB_M_Facade;
import cvm.ncb.oem.policy.Metadata;
import util.FeaturesParser;

import java.util.Scanner;

public class NCBDriver implements UsesEventListener {
	private String cvmUname;
	private int DELAY = 20000;
    private NCBManager manager;

    public NCBDriver(NCBManager ncbManager, String cvmUname) {
        this.manager = ncbManager;
        this.cvmUname = cvmUname;
    }

    /**
	 * @param args
	 */
	public static void main(String[] args) {
        EventManager eventManager = new EventManager();
        ObjectManager om = new ObjectManager(new ManagedObjectFactory(eventManager));
        for (Metadata md : FeaturesParser.createAllFrameworks()) {
            om.addObject(md);
        }
        NCBDriver driver = new NCBDriver(new NCBManager(om, eventManager), "Andrew");
        driver.run();
	}

    public void run() {        
        manager.getEventObjectManager().addUpListener(this);

        manager.login(cvmUname, "password");
        manager.createSession("101");

        twoWay(manager, "AUDIO");
    }

	public void twoWay(NCBManager manager, String medium){
		manager.sendSchema("101 ", "Andrew", " Yali", "101 " + medium + " Yali Andrew", null);
		manager.addParty("101", "Yali");
		manager.enableMedium("101", medium);
	}

	public void threeWay(NCB_M_Facade m_facade, String medium){
		m_facade.sendSchema("101 ","Andrew"," Yali","101 "+medium+" Yali Tariq Andrew", null);
		m_facade.sendSchema("101 ","Andrew"," Tariq","101 "+medium+" Yali Tariq Andrew", null);
		m_facade.addParty("101", "Yali");
		m_facade.addParty("101", "Tariq");
		m_facade.enableMedium("101", medium);
	}

	public void fourWay(NCB_M_Facade m_facade, String medium){
		m_facade.sendSchema("101 ","Andrew"," Yali","101 "+medium+" Yali Tariq Yingbo Andrew", null);
		m_facade.sendSchema("101 ","Andrew"," Tariq","101 "+medium+" Yali Tariq Yingbo Andrew", null);
		m_facade.sendSchema("101 ","Andrew"," Yingbo","101 "+medium+" Yali Tariq Yingbo Andrew", null);
		m_facade.addParty("101", "Yali");
		m_facade.addParty("101", "Tariq");
		m_facade.addParty("101", "Yingbo");
		m_facade.enableMedium("101", medium);
	}

	public void fiveWay(NCB_M_Facade m_facade, String medium){
		m_facade.sendSchema("101 ","Andrew"," Yali","101 "+medium+" Yali Tariq Yingbo Karl Andrew", null);
		m_facade.sendSchema("101 ","Andrew"," Tariq","101 "+medium+" Yali Tariq Yingbo Karl Andrew", null);
		m_facade.sendSchema("101 ","Andrew"," Yingbo","101 "+medium+" Yali Tariq Yingbo Karl Andrew", null);
		m_facade.sendSchema("101 ","Andrew"," Karl","101 "+medium+" Yali Tariq Yingbo Karl Andrew", null);
		m_facade.addParty("101", "Yali");
		m_facade.addParty("101", "Tariq");
		m_facade.addParty("101", "Yingbo");
		m_facade.addParty("101", "Karl");
		m_facade.enableMedium("101", medium);
	}

	public void twoToThreeWay(NCB_M_Facade m_facade, String medium){
		twoWay(m_facade.getManager(), medium);
		waitForAWhile(DELAY);
		m_facade.sendSchema("101 ","Andrew"," Yali","101 "+medium+" Yali Tariq Andrew", null);
		m_facade.sendSchema("101 ","Andrew"," Tariq","101 "+medium+" Yali Tariq Andrew", null);
		m_facade.addParty("101", "Yali");
		m_facade.addParty("101", "Tariq");
		m_facade.enableMedium("101", medium);
	}

	public void threeToFourWay(NCB_M_Facade m_facade, String medium){
		threeWay(m_facade, medium);
		waitForAWhile(DELAY);
		m_facade.sendSchema("101 ","Andrew"," Yali","101 "+medium+" Yali Tariq Yingbo Andrew", null);
		m_facade.sendSchema("101 ","Andrew"," Tariq","101 "+medium+" Yali Tariq Yingbo Andrew", null);
		m_facade.sendSchema("101 ","Andrew"," Yingbo","101 "+medium+" Yali Tariq Yingbo Andrew", null);
		m_facade.addParty("101", "Yali");
		m_facade.addParty("101", "Tariq");
		m_facade.addParty("101", "Yingbo");
		m_facade.enableMedium("101", medium);
	}

	public void fourToFiveWay(NCB_M_Facade m_facade, String medium){
		fourWay(m_facade, medium);
		waitForAWhile(DELAY);
		m_facade.sendSchema("101 ","Andrew"," Yali","101 "+medium+" Yali Tariq Yingbo Karl Andrew", null);
		m_facade.sendSchema("101 ","Andrew"," Tariq","101 "+medium+" Yali Tariq Yingbo Karl Andrew", null);
		m_facade.sendSchema("101 ","Andrew"," Yingbo","101 "+medium+" Yali Tariq Yingbo Karl Andrew", null);
		m_facade.sendSchema("101 ","Andrew"," Karl","101 "+medium+" Yali Tariq Yingbo Karl Andrew", null);
		m_facade.addParty("101", "Yali");
		m_facade.addParty("101", "Tariq");
		m_facade.addParty("101", "Yingbo");
		m_facade.addParty("101", "Karl");
		m_facade.enableMedium("101", medium);
	}

	private void waitForAWhile(int millis){
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Done Sleeping ==============================");
	}

	public void use(Event event) {
		if (event.getName().equals("SchemaReceived"))
			dealWithSchema((String) event.getParam("schema"));
	}

	/**
	 * This function is the driver's way of handling a schema received event.
	 * @param schema schema file.
	 */
	private synchronized void dealWithSchema(String schema)
	{
		CVM_Debug.getInstance().printDebugMessage("NCBDriver: dealWithSchema Called."+schema);
		Scanner sch = new Scanner(schema);
		String sessId = sch.next();
		manager.createSession(sessId);
		String medium = sch.next();
		// add participants
		while (sch.hasNext()){
			String name = sch.next();
			// remove yourself from list of participants
			if(name.equalsIgnoreCase(cvmUname)){
				continue;
			}
			manager.addParty(sessId, name);
		}
		manager.enableMediumReceiver(sessId, medium);
	}

}
