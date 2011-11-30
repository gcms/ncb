package cvm.ncb.drivers;

import cvm.model.CVM_Debug;
import cvm.model.Handles_Event;
import cvm.model.Uses_Listener;
import cvm.ncb.handlers.NCBEventObjectManager;
import cvm.ncb.handlers.event.SchemaReceived_Event;
import cvm.ncb.manager.NCB_M_Facade;

import java.util.Scanner;

public class NCBDriver implements Uses_Listener{
	private String cvmUname = "Andrew";
	private int DELAY = 20000;
	NCBEventObjectManager eomgr = null;
	private NCBDriver(){
		eomgr = NCBEventObjectManager.Instance();
		eomgr.addUpListener(this);
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
	    NCB_M_Facade m_facade = NCB_M_Facade.getInstance();
		NCBDriver driver = new NCBDriver();
		
		m_facade.login("Andrew", "password");
		m_facade.createSession("101");

		driver.twoWay(m_facade, "AUDIO");
	}
	
	public void twoWay(NCB_M_Facade m_facade, String medium){
		m_facade.sendSchema("101 ","Andrew"," Yali","101 "+medium+" Yali Andrew");
		m_facade.addParty("101", "Yali");
		m_facade.enableMedium("101", medium);
	}

	public void threeWay(NCB_M_Facade m_facade, String medium){
		m_facade.sendSchema("101 ","Andrew"," Yali","101 "+medium+" Yali Tariq Andrew");
		m_facade.sendSchema("101 ","Andrew"," Tariq","101 "+medium+" Yali Tariq Andrew");
		m_facade.addParty("101", "Yali");
		m_facade.addParty("101", "Tariq");
		m_facade.enableMedium("101", medium);
	}

	public void fourWay(NCB_M_Facade m_facade, String medium){
		m_facade.sendSchema("101 ","Andrew"," Yali","101 "+medium+" Yali Tariq Yingbo Andrew");
		m_facade.sendSchema("101 ","Andrew"," Tariq","101 "+medium+" Yali Tariq Yingbo Andrew");
		m_facade.sendSchema("101 ","Andrew"," Yingbo","101 "+medium+" Yali Tariq Yingbo Andrew");
		m_facade.addParty("101", "Yali");
		m_facade.addParty("101", "Tariq");
		m_facade.addParty("101", "Yingbo");
		m_facade.enableMedium("101", medium);
	}

	public void fiveWay(NCB_M_Facade m_facade, String medium){
		m_facade.sendSchema("101 ","Andrew"," Yali","101 "+medium+" Yali Tariq Yingbo Karl Andrew");
		m_facade.sendSchema("101 ","Andrew"," Tariq","101 "+medium+" Yali Tariq Yingbo Karl Andrew");
		m_facade.sendSchema("101 ","Andrew"," Yingbo","101 "+medium+" Yali Tariq Yingbo Karl Andrew");
		m_facade.sendSchema("101 ","Andrew"," Karl","101 "+medium+" Yali Tariq Yingbo Karl Andrew");
		m_facade.addParty("101", "Yali");
		m_facade.addParty("101", "Tariq");
		m_facade.addParty("101", "Yingbo");
		m_facade.addParty("101", "Karl");
		m_facade.enableMedium("101", medium);
	}

	public void twoToThreeWay(NCB_M_Facade m_facade, String medium){
		twoWay(m_facade, medium);
		waitForAWhile(DELAY);
		m_facade.sendSchema("101 ","Andrew"," Yali","101 "+medium+" Yali Tariq Andrew");
		m_facade.sendSchema("101 ","Andrew"," Tariq","101 "+medium+" Yali Tariq Andrew");
		m_facade.addParty("101", "Yali");
		m_facade.addParty("101", "Tariq");
		m_facade.enableMedium("101", medium);
	}

	public void threeToFourWay(NCB_M_Facade m_facade, String medium){
		threeWay(m_facade, medium);
		waitForAWhile(DELAY);
		m_facade.sendSchema("101 ","Andrew"," Yali","101 "+medium+" Yali Tariq Yingbo Andrew");
		m_facade.sendSchema("101 ","Andrew"," Tariq","101 "+medium+" Yali Tariq Yingbo Andrew");
		m_facade.sendSchema("101 ","Andrew"," Yingbo","101 "+medium+" Yali Tariq Yingbo Andrew");
		m_facade.addParty("101", "Yali");
		m_facade.addParty("101", "Tariq");
		m_facade.addParty("101", "Yingbo");
		m_facade.enableMedium("101", medium);
	}

	public void fourToFiveWay(NCB_M_Facade m_facade, String medium){
		fourWay(m_facade, medium);
		waitForAWhile(DELAY);
		m_facade.sendSchema("101 ","Andrew"," Yali","101 "+medium+" Yali Tariq Yingbo Karl Andrew");
		m_facade.sendSchema("101 ","Andrew"," Tariq","101 "+medium+" Yali Tariq Yingbo Karl Andrew");
		m_facade.sendSchema("101 ","Andrew"," Yingbo","101 "+medium+" Yali Tariq Yingbo Karl Andrew");
		m_facade.sendSchema("101 ","Andrew"," Karl","101 "+medium+" Yali Tariq Yingbo Karl Andrew");
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

	public void use(Handles_Event event) {
		if (event instanceof SchemaReceived_Event) 
			dealWithSchema(((SchemaReceived_Event)event).getSchema());
	}

	/**
	 * This function is the driver's way of handling a schema received event.
	 * @param schema schema file.
	 */
	private synchronized void dealWithSchema(String schema)
	{
		CVM_Debug.getInstance().printDebugMessage("NCBDriver: dealWithSchema Called."+schema);
		NCB_M_Facade m_facade = NCB_M_Facade.getInstance();
		Scanner sch = new Scanner(schema);
		String sessId = sch.next();
		m_facade.createSession(sessId);
		String medium = sch.next();
		// add participants
		while (sch.hasNext()){
			String name = sch.next();
			// remove yourself from list of participants
			if(name.equalsIgnoreCase(cvmUname)){
				continue;
			}
			m_facade.addParty(sessId, name);
		}
		m_facade.enableMediumReceiver(sessId, medium);
	}

}
