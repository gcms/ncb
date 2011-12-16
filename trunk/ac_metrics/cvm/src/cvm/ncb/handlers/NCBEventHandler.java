package cvm.ncb.handlers;

import cvm.model.CVM_Debug;
import cvm.model.Handles_Event;
import cvm.model.Uses_Listener;
//import cvm.ucm.handlers.UCMEventObjectManager;

/**
 * This class listens for and handles possible events coming 
 * from NCB. Events not handled are notified to UCM for handling.
 * 
 * @author Frank Hernandez
 * 
 */

public class NCBEventHandler implements Uses_Listener
{
	private static NCBEventHandler instance = null;
	//private UCMEventObjectManager m_ucmNotifier = null;
	/**
	 * The NCBEventhandler constructor register itself with the event source, 
	 * which will fire the events. 
	 */
	private NCBEventHandler(NCBEventObjectManager notifier)
	{
		notifier.addUpListener(this);
	}
	
	/**
	 * This method will handle all the events coming from NCB 
	 *
	 * @param e the event that will be handled by the handler class
	 */
	private synchronized void handleEvent(Handles_Event e)
	{
		
		/*
		 * For now all there are no Specific events 
		 * that NCB handles so the overlying layer UCM
		 * is notified.
		 */
		CVM_Debug.getInstance().printDebugMessage("NCB EventHandler: Firing UCM Event");
		//m_ucmNotifier.fireUpEventUCM(e);
		
		/*
		else
		{
			//if the event is not handled by NCB forward to UCM.
			m_ucmNotifier.fireUpEventUCM(e);
		}
		*/
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
