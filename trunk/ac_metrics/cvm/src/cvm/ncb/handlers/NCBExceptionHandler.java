package cvm.ncb.handlers;

import cvm.model.CVM_Debug;
import cvm.ncb.handlers.exception.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

//import cvm.ucm.handlers.exception.InvalidScriptException;
//import cvm.ucm.handlers.exception.MacroNotFoundException;

/**
 * This class defines all the exceptions that could be handled 
 * by the handler 
 * 
 *
 * @author Frank Hernandez
 */
public class NCBExceptionHandler 
{
    private static Log log = LogFactory.getLog(NCBExceptionHandler.class);

    //Used for turning exceptions into events.
	private NCBEventObjectManager m_ncbNotifier = null;

	public NCBExceptionHandler(NCBEventObjectManager notifier)
	{
		m_ncbNotifier = notifier;
	}

	/**
	 * Implementation of the Singleton Pattern
	 * @return NCBExceptionHandler instnace
	 */
//	public static NCBExceptionHandler Instance()
//	{
//		if(instance == null)
//			instance = new NCBExceptionHandler();
//
//		return instance;
//	}

	/**
	 * This method will handle all the exceptions
	 * thrown by the NCB layer.
	 * @param e the exception that will be handled by the handler class
	 */
	public void handleException(Exception e)
	{
        log.debug("Handling exception: " + e);
		
		/**
		 * This handle the PartyNotFoundException,
		 * the exception thrown when it is trying to remove a party that is not in the current connection
		 */
		if(e instanceof PartyNotFoundException)
		{
			CVM_Debug.getInstance().printDebugMessage("NCB_Exception: Party not found : "+((PartyNotFoundException)e).getPartyName());
			m_ncbNotifier.notifyPartyRemovedReply_Event(false);
		}
		/**
		 * This handle the NoSessionException,
		 * the exception thrown when it is trying to add a party or remove a party or send data to a connection that has no physical sessions in the NCB layer
		 */
		else if(e instanceof NoSessionException)
		{
			CVM_Debug.getInstance().printDebugMessage("NCB_Exception: No session created for connection: "+((NoSessionException)e).getConnectionID()+". Notifying SE via Event.");
			m_ncbNotifier.notifyNoSessionException_Event(((NoSessionException)e).getConnectionID());
		
		}
		/**
		 * This handle the DataNotFoundException,
		 * the exception thrown when it is trying to send files or forms that could not be found in the file system
		 */
		else if(e instanceof DataNotFoundException)
		{
			CVM_Debug.getInstance().printDebugMessage("NCB_Exception: File or Form not found for transfer : "+((DataNotFoundException)e).getDataName());
			m_ncbNotifier.notifyDataSchemaNotSentException_Event();
		}
		/**
		 * This handles the LoginException
		 * This exception is thrown when the login fails.
		 */
		else if(e instanceof LoginException)
		{
			CVM_Debug.getInstance().printDebugMessage("NCB_Exception: Login Exception. Notifying SE via Event.");
			m_ncbNotifier.notifyLoginExceptionEvent();
		}
		/**
		 * Handles the PartyNotFoundException
		 * This happens when the party could not be found.
		 */
		else if (e instanceof PartyNotAddedException)
		{
			CVM_Debug.getInstance().printDebugMessage("NCB_Exception: Party Not Added Exception. Notifying SE via Event.");
			m_ncbNotifier.notifyPartyNotAddedException_Event(((PartyNotAddedException)e).getPartyName());
			
		}
		/**
		 * Handles ControlSchemanNotFoundException
		 */
		else if (e instanceof ControlSchemaNotSentException)
		{
			CVM_Debug.getInstance().printDebugMessage("NCB_Exception: Control Schema Not Sent Exception. Notifying SE via Event.");
			m_ncbNotifier.notifyControlSchemaNotSentException_Event();
			
		}
		/**
		 * Handles DataSchemaNotSentException
		 */
		else if (e instanceof DataSchemaNotSentException)
		{
			CVM_Debug.getInstance().printDebugMessage("NCB_Exception: Data Schema Not Sent Exception. Notifying SE via Event.");
			m_ncbNotifier.notifyDataSchemaNotSentException_Event();
			
		}
		/**
		 * Handles SchemaNotSavedException
		 */
		else if( e instanceof SchemaNotSavedException)
		{
			CVM_Debug.getInstance().printDebugMessage("NCB_Exception: Schema Not Saved Exception. Notifying SE via Event.");
			m_ncbNotifier.notifySchemaNotSavedException_Event();
			
		}
		/**
		 * Handles any exception that is not handled above.
		 */
		else
		{
			CVM_Debug.getInstance().printDebugMessage("Exception Not Found "+e.toString());
			m_ncbNotifier.notifyUnrecognizedEvent();
		}
		
	
		
	}

    public NCBEventObjectManager getObjectManager() {
        return m_ncbNotifier;
    }
}
