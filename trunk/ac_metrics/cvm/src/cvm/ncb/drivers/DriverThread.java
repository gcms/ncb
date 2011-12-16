package cvm.ncb.drivers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

import cvm.model.CVM_Debug;
import cvm.model.Handles_Event;
import cvm.model.Uses_Listener;
import cvm.ncb.handlers.NCBEventObjectManager;
import cvm.ncb.handlers.event.SchemaReceived_Event;
import cvm.ncb.handlers.exception.PartyNotAddedException;
import cvm.ncb.manager.NCB_M_Facade;

public class DriverThread extends Thread implements Uses_Listener{
    BufferedReader br = null; 
	String request = null;
	String params = null;
	String myUserName = "Andrew";
	boolean done = false;
    NCB_M_Facade m_facade = NCB_M_Facade.getInstance();
	
    public DriverThread(){
    }

    public void run() {
        m_facade.getManager().getEventObjectManager().addUpListener(this);
		m_facade.login("userName", "password");
		m_facade.createSession("101");

		while(!done){	
/*	      try {
		     System.out.print("Enter request: ");
		     br = new BufferedReader(new InputStreamReader(System.in));
		     request = br.readLine();
	         System.out.print("Enter params: ");
		     br = new BufferedReader(new InputStreamReader(System.in));
	         params = br.readLine();
	         if(request!=null && request.equalsIgnoreCase("done")) {
	        	 done = true;
	        	 continue;
	         }
	         if(request==null ) {
	        	 continue;
	         }
	         if(request.equalsIgnoreCase("ap")) m_facade.addParty("100", params);
	         if(request.equalsIgnoreCase("em")) m_facade.enableMedium("100", params);
	         if(request.equalsIgnoreCase("rm")) m_facade.disableMedium("100", params);
	         request = params = null;
	      } catch (IOException ioe) {
	         CVM_Debug.getInstance().printDebugMessage("IO error trying to read your name!");
	         System.exit(1);
	      }
*/	   }

	}
    public void addParty(String cID, String pID) {
	 	m_facade.addParty(cID, pID);
 	}

 	public void enableMedium(String cID, String medium){
	 	m_facade.enableMedium(cID, medium);
 	}

 	public void enableMediumReceiver(String cID, String medium){	
 		m_facade.enableMediumReceiver(cID, medium);
 	}

	private synchronized void handleEvent(Handles_Event e)
	{
		CVM_Debug.getInstance().printDebugMessage("NCB EventHandler: Firing UCM Event");
		if (e instanceof SchemaReceived_Event) {
			Scanner sch = new Scanner(((SchemaReceived_Event)e).getSchema());
			String sessId = sch.next();
			m_facade.createSession(sessId);
			String medium = sch.next();
			// add participants
			while (sch.hasNext()){
				String name = sch.next();
				if (!name.equalsIgnoreCase(myUserName)){
					m_facade.addParty(sessId, name);
				}
			}
		}
	}

	/**
	 * Implmentation of the use method 
	 * from Uses_Listener class
	 */
	public void use(Handles_Event event) 
	{
		this.handleEvent(event);
	}
}
