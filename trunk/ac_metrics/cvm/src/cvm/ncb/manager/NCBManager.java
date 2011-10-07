package cvm.ncb.manager;

import java.lang.reflect.InvocationTargetException;

import cvm.model.CVM_Debug;
import cvm.ncb.UserObject;
import cvm.ncb.UserProfile;
import cvm.ncb.UserSchema;
import cvm.ncb.csm.CommObject;
import cvm.ncb.csm.CommServiceManager;
import cvm.ncb.handlers.NCBEventHandler;
import cvm.ncb.handlers.NCBEventObjectManager;
import cvm.ncb.handlers.NCBExceptionHandler;
import cvm.ncb.handlers.exception.LoginException;
import cvm.ncb.handlers.exception.NoSessionException;
import cvm.ncb.handlers.exception.PartyNotAddedException;
import cvm.ncb.handlers.exception.PartyNotFoundException;

/**
 * Manager of NCB layer.
 * @author Frank Hernandez
 *
 */
public class NCBManager 
{
	private static NCBManager instance = null;
	private CommServiceManager m_csmMan = null;
	private CommObject m_coCommCurr = null;
	private NCBExceptionHandler m_xhXHandler  = null;
	private NCBEventHandler m_ehEHandler = null;
	private NCBEventObjectManager m_ncbNotifier = null;
	private NCBCallQueue m_callQueue = null;
	
	private NCBManager()
	{
		
		m_xhXHandler = NCBExceptionHandler.Instance();
		m_ncbNotifier = NCBEventObjectManager.Instance();
		//m_ehEHandler = NCBEventHandler.Instance();
		m_csmMan = CommServiceManager.getInstance();
		m_coCommCurr = m_csmMan.getCommunicationObject();
		CVM_Debug.getInstance().printDebugMessage("Using "+m_coCommCurr.getNCBBridge().getCapability());
		m_callQueue = NCBCallQueue.getInstance();
		cvm.ncb.oem.pe.PolicyEvalManager.getInstance();
	}
	
	/**
	 * Singleton implementation.
	 * @return instance of NCB_M_Facade
	 */
	public static NCBManager getInstance()
	{
		if(instance==null)
			instance = new NCBManager();
		
		return instance;
		
	}
	/**
	 * Queues command, actual executable command is prefixed with x_
	 */
	public void addParty(String sessionID, String participantID)
	{
		CVM_Debug.getInstance().printDebugMessage("NCBManager :Queuing NCB addParty called for "+sessionID+" adding "+participantID+".");
		//Dont forget to parse the list of participants.
		int count =1;
		Object[] obj = {sessionID,participantID};
		m_callQueue.add(sessionID, count, "addAParticipant", "",obj);
	}

	/**
	 * This function adds the participants specified to the specific session.
	 * If any participant cannot be added a PartyNotAddedException is thrown.
	 * If there is no session a NoSessionException is thrown.
	 * NOTE: Exceptions are caught and handled by the NCB Exception Handler.
	 * When finished NCB is notified via a notifyPartyAddedReply event.
	 * @param sessionID Id of the session to add the participant to.
	 * @param participantID Id of the participants to add. i.g crinsomkairos, crinsomkairos1
	 * @see NCBExceptionHandler, PartyNotAddedException, NoSessionException
	 */
	public void x_addParty(String sessionID, String participantID)
	{
		CVM_Debug.getInstance().printDebugMessage("NCBManager : NCB addParty called for "+sessionID+" adding "+participantID+".");
		try 
		{
			//Dont forget to parse the list of participants.
			m_csmMan.getCommunicationObject("Smack").getNCBBridge().
				addParticipant(sessionID, participantID);
			//m_coCommCurr.getNCBBridge().addParticipant(sessionID, participantID);
			m_ncbNotifier.notifyPartyAddedReply(true);
		} 
		catch (PartyNotAddedException e) 
		{
			m_xhXHandler.handleException(e);
		}
		catch (NoSessionException e)
		{
			m_xhXHandler.handleException(e);
		}
		
		
	}
	
	/**
	 * Queues command, actual executable command is prefixed with x_
	 */
	public void mapConnToSession(String connectionID, String sessionID)
	{
		int count =0;
		Object[] obj = {connectionID, sessionID};
		m_callQueue.add(sessionID, count, "x_mapConnToSession","",obj);
		CVM_Debug.getInstance().printDebugMessage("NCBManager : Queuing NCB mapConnToSession called.");
	}

	/**
	 * This function maps a connection to a session.
	 * If there is no session a NoSessionException is thrown.
	 * NOTE: Exceptions are caught and handled by the NCB Exception Handler.
	 * When finished NCB is notified via a notifyPartyAddedReply event.
	 * @param sessionID Id of the session to add the participant to.
	 * @param connectionID Id of the participants to add. i.g crinsomkairos, crinsomkairos1
	 * @see NCBExceptionHandler, NoSessionException
	 */
	public void x_mapConnToSession(String connectionID, String sessionID)
	{
		CVM_Debug.getInstance().printDebugMessage("NCBManager : NCB mapConnToSession called.");
		m_csmMan.mapConnToSession(connectionID, sessionID);
		m_ncbNotifier.notifyConnectionCreatedReply_Event(true);
	}

	/**
	 * Queues command, actual executable command is prefixed with x_
	 */
	public void createSession(String sessionID)
	{
		int count =0;
		Object[] obj = {sessionID};
		m_callQueue.add(sessionID, count, "createSession","",obj);
		CVM_Debug.getInstance().printDebugMessage("NCBManager : Queuing NCB createSession called.");
	}

	/**
	 * This function creates a session with the specific session ID
	 * @param sessionID ID of the session to create.
	 */
	public void x_createSession(String sessionID)
	{
		CVM_Debug.getInstance().printDebugMessage("NCBManager : NCB createSession called.");
		m_coCommCurr = m_csmMan.getCommunicationObject();
		m_csmMan.getCommunicationObject("Smack").getNCBBridge().createSession(sessionID);

	}
	
	/**
	 * Queues command, actual executable command is prefixed with x_
	 */
	public void isCreatedSession(String sessionID)
	{
		int count =0;
		Object[] obj = {sessionID};
		m_callQueue.add(sessionID, count, "isSessionCreated","",obj);
		CVM_Debug.getInstance().printDebugMessage("NCBManager : Queuing NCB isCreatedSession called.");
		
//		return m_coCommCurr.getNCBBridge().isSessionCreated(sessionID);
	}

	/**
	 * This method returns whether the session was created or not.
	 * @return is session created.
	 */
	public void x_isCreatedSession(String sessionID)
	{
		CVM_Debug.getInstance().printDebugMessage("NCBManager : NCB isCreatedSession called.");
		m_coCommCurr = m_csmMan.getCommunicationObject();
		
		m_ncbNotifier.notifyIsCreatedSessionReply(m_csmMan.getCommunicationObject("Smack").getNCBBridge().
				isSessionCreated(sessionID));
	}
		
	/**
	 * This function must be changed, a single connection can have multiple sessions.
	 * This method will return the sessionId for the given connection.
	 * @param connID Id of the connection uinder which the session should be mapped.
	 * @return session ID Id of the session mapped to the connection.
	 * @deprecated  
	 */
	public String getSessionID(String connID)
	{
		CVM_Debug.getInstance().printDebugMessage("NCBManager : NCB getSession called with connID:\""+connID+"\".");
	
		return "";
	}
	

	/**
	 * Queues command, actual executable command is prefixed with x_
	 */
	public void login(String userName, String password)
	{
		Object[] obj = {userName,password};
		m_callQueue.add(""+0, 0, "loginAll","", null);
		CVM_Debug.getInstance().printDebugMessage("NCBManager : Queuing NCB Login called with userName:\""+userName+"\" and Password:\""+password+"\".");
	}

	/**
	 * This method will attempt login the given user.
	 * @param userName
	 * @param password
	 * @return
	 */
	public void x_login(String userName, String password)
	{
		UserObject usr = new UserObject(userName, password);
		CVM_Debug.getInstance().printDebugMessage("NCBManager : NCB Login called with userName:\""+userName+"\" and Password:\""+password+"\".");
//		m_coCommCurr = m_csmMan.getCommunicationObject();
//		try 
//		{
//			m_coCommCurr.getNCBBridge().login(userName, password);
			m_csmMan.loginAll();
			m_ncbNotifier.notifyLoginReply(createUserProfile(usr,
					retrieveSchemas(userName, password)));
/*		} catch (LoginException e) 
		{
			m_xhXHandler.handleException(e);
		}
*/
		//return usr;
	}
	
	/**
	 * This method returns the schemas for the given user.
	 * @param userName
	 * @param password
	 * @return UserSchema
	 */
	public UserSchema retrieveSchemas(String userName, String password)
	{
		CVM_Debug.getInstance().printDebugMessage("NCBManager : NCB retrieve schema called with userName:\""+userName+"\" and Password:\""+password+"\".");
		return new UserSchema();
	}
	
	/**
	 * This method signals whether the sessin is created or not.
	 * @return
	 * @deprecated
	 */
	public boolean createdSession()
	{
		CVM_Debug.getInstance().printDebugMessage("NCBManager : isSessionCreated----Called");
		return true;
	}
	
	/**
	 * This method generates a user profile for the give
	 * user.
	 * @param usr User object to create the profile.
	 * @param schema Schema to send to the user.
	 * @return a created user profile.
	 */
	public UserProfile createUserProfile(UserObject usr, Object schema)
	{
		CVM_Debug.getInstance().printDebugMessage("NCBManager : NCB createUserProfile called.");
		return new UserProfile(usr, schema);
	}
	
	/**
	 * Queues command, actual executable command is prefixed with x_
	 */
	public void removeParty(String sID, String participant)
	{
		Object[] obj = {sID,participant};
		m_callQueue.add(sID, -1, "removeAParticipant","", obj);
		CVM_Debug.getInstance().printDebugMessage("NCBManager - Queuing Remove Party Called");
	}

	/**
	 * This method adds the list of participants 
	 * to the given session. RIGHT WAY
	 * 
	 * @param sID
	 * @param participant
	 * @return
	 */
	public void x_removeParty(String sID, String participant)
	{
		CVM_Debug.getInstance().printDebugMessage("NCBManager - Remove Party Called");
		m_coCommCurr = m_csmMan.getCommunicationObject();
		try 
		{
			m_csmMan.getCommunicationObject("Smack").getNCBBridge().removeParticipant(sID, participant);
			m_ncbNotifier.notifyPartyRemovedReply_Event(true);

		} 
		catch (PartyNotFoundException e) 
		{
			//m_ncbNotifier.notifyPartyRemovedReply_Event(false);	
			m_xhXHandler.handleException(e);
		
		} catch (NoSessionException e) 
		{
			// TODO Auto-generated catch block
			m_xhXHandler.handleException(e);
			e.printStackTrace();
		}
	}
	
	/**
	 * Queues command, actual executable command is prefixed with x_
	 */
	public void sendSchema(String sID, String senderID, String listReceiver, String control_xcml,String data_xcml )
	{
		Object[] obj = {sID,senderID,listReceiver,control_xcml,data_xcml};
		m_callQueue.add(sID, 0, "sendSchema","", obj);
		CVM_Debug.getInstance().printDebugMessage("NCBManager : Queuing NCB sendSchema called with sID:\""+sID+"\" and senderID:\""+senderID+"\" and receiverID:\""+listReceiver+"\" and Control_XCML:\""+control_xcml+"\" and Data_XCML:\""+data_xcml+"\".");
	}

	/***
	 * This methods send a schema to the given user in the
	 * given session.
	 * 
	 * @param sID
	 * @param senderID
	 * @param listReceiver
	 * @param control_xcml
	 * @param data_xcml
	 * @return
	 */
	public void x_sendSchema(String sID, String senderID, String listReceiver, String control_xcml,String data_xcml )
	{
		CVM_Debug.getInstance().printDebugMessage("NCBManager : NCB sendSchema called with sID:\""+sID+"\" and senderID:\""+senderID+"\" and receiverID:\""+listReceiver+"\" and Control_XCML:\""+control_xcml+"\" and Data_XCML:\""+data_xcml+"\".");
//		m_csmMan.sendSchema(sID, senderID, listReceiver, control_xcml, data_xcml);
		//Currently there is not list of receivers implemented, all happens one at a time.
		if(control_xcml != null && !control_xcml.equals(""))
		{
			m_csmMan.getCommunicationObject("Smack").getNCBBridge().sendSchema(control_xcml, listReceiver);
		
		}
		
		if(data_xcml !=null && !data_xcml.equals("") && !data_xcml.equals("null"))
		{
			m_csmMan.getCommunicationObject("Smack").getNCBBridge().sendSchema(data_xcml, listReceiver);
		}
		CVM_Debug.getInstance().printDebugMessage("NCBManager : NCB sendSchema OVER");
		m_ncbNotifier.notifySendSchemaReply(true);
	}

	/**
	 * Queues command, actual executable command is prefixed with x_
	 */
	public void sendSchema(String sID, String senderID, String listReceiver, Object control_xcml)
	{
		Object[] obj = {sID,senderID,listReceiver,control_xcml, null};
		m_callQueue.add(sID, 0, "sendSchema","", obj);
		CVM_Debug.getInstance().printDebugMessage("NCBManager : Queuing NCB sendSchema called with sID:\""+sID+"\" and senderID:\""+senderID+"\" for receiversIDs:\""+listReceiver+"\".");
	}

	/**
	 * This method will send the schema to all participants 
	 * in the specified connection.
	 * @param sID
	 * @param senderID
	 * @param listReceiver
	 * @param control_xcml
	 * @return
	 */
	public void x_sendSchema(String sID, String senderID, String listReceiver, Object control_xcml)
	{
		CVM_Debug.getInstance().printDebugMessage("NCBManager : NCB sendSchema called with sID:\""+sID+"\" and senderID:\""+senderID+"\" for receiversIDs:\""+listReceiver+"\".");
		m_ncbNotifier.notifySendSchemaReply(true);
	}

	/**
	 * Queues command, actual executable command is prefixed with x_
	 */
	public void sendMedia(String sID, String medium, String mediumURL)
	{
		Object[] obj = {sID,medium,mediumURL};
		m_callQueue.add(sID, 0, "x_sendMedia",medium, obj);
		CVM_Debug.getInstance().printDebugMessage("NCBManager : Queuing NCB sendMedia called with sID:\""+sID+"\" and Medium:\""+medium+"\" and MediumURL:\""+mediumURL+"\".");
	}

	/**
	 * This command will send the specified medium to all 
	 * the participants during the connection.
	 * @param sID
	 * @param medium
	 * @param mediumURL
	 * @return
	 */
	public void x_sendMedia(String sID, String medium, String mediumURL)
	{
		CVM_Debug.getInstance().printDebugMessage("NCBManager : NCB sendMedia called with sID:\""+sID+"\" and Medium:\""+medium+"\" and MediumURL:\""+mediumURL+"\".");
		m_ncbNotifier.notifySendMediaReply(true);
	}

	/**
	 * Queues command, actual executable command is prefixed with x_
	 */
	public void enableMedium(String connectionID, String mediumName)
	{
		Object[] obj = {connectionID, mediumName};
		m_callQueue.add(connectionID, 0, "enableMedium",mediumName, obj);
		CVM_Debug.getInstance().printDebugMessage("NCBManager : Queuing NCB sendMedia called with connectionID:\""+connectionID+"\" and MediumName:\""+mediumName+"\".");
	}

	/**
	 * Queues command, actual executable command is prefixed with x_
	 */
	public void enableMediumReceiver(String connectionID, String mediumName)
	{
		Object[] obj = {connectionID, mediumName};
		m_callQueue.add(connectionID, 0, "enableMediumReceiver",mediumName, obj);
		CVM_Debug.getInstance().printDebugMessage("NCBManager : Queuing NCB sendMedia called with connectionID:\""+connectionID+"\" and MediumName:\""+mediumName+"\".");
	}

	/**
	 * This command will send the specified medium to all 
	 * the participants during the connection.
	 * @param sID
	 * @param medium
	 * @param mediumURL
	 * @return
	 */
	public void x_enableMedium(String connectionID, String mediumName)
	{
		CVM_Debug.getInstance().printDebugMessage("NCBManager : NCB enableMedia called with connectionID:\""+connectionID+"\" and MediumName:\""+mediumName+"\".");
		m_coCommCurr = m_csmMan.getCommunicationObject();
		try 
		{
			m_csmMan.getCommunicationObject("Smack").getNCBBridge().enableMedium(connectionID, mediumName);	
		}
		catch (Exception e) 
		{
			m_xhXHandler.handleException(e);
		}
	}

	/**
	 * Queues command, actual executable command is prefixed with x_
	 */
	public void disableMedium(String connectionID, String mediumName)
	{
		Object[] obj = {connectionID, mediumName};
		m_callQueue.add(connectionID, 0, "disableMedium", mediumName, obj);
		CVM_Debug.getInstance().printDebugMessage("NCBManager : Queuing NCB sendMedia called with connectionID:\""+connectionID+"\" and MediumName:\""+mediumName+".");
	}

	/**
	 * This command will stop sending  the specified medium to all 
	 * the participants during the connection.
	 * @param sID
	 * @param medium
	 * @param mediumURL
	 * @return
	 */
	public void x_disableMedium(String connectionID, String mediumName)
	{
		CVM_Debug.getInstance().printDebugMessage("NCBManager : NCB sendMedia called with connectionID:\""+connectionID+"\" and MediumName:\""+mediumName+".");
		m_coCommCurr = m_csmMan.getCommunicationObject();
		try 
		{
			m_csmMan.getCommunicationObject("Smack").getNCBBridge().disableMedium(connectionID, mediumName);	
		}
		catch (Exception e) 
		{
			m_xhXHandler.handleException(e);
		}
	}

	/**
	 * Queues command, actual executable command is prefixed with x_
	 */
	public void saveSchema(Object schema)
	{
		Object[] obj = {schema};
		m_callQueue.add(""+0, 0, "x_saveSchema","", obj);
		CVM_Debug.getInstance().printDebugMessage("NCBManager : Queuing NCB saveSchema called.");
	}

	/**
	 * Saves teh given schema.
	 * @param schema
	 */
	public void x_saveSchema(Object schema)
	{
		CVM_Debug.getInstance().printDebugMessage("NCBManager : NCB saveSchema called.");
		m_ncbNotifier.notifySaveSchemaReply(true);
	}

	/**
	 * Queues command, actual executable command is prefixed with x_
	 */
	public void logout(String userName)
	{
		Object[] obj = {userName};
		m_callQueue.add(""+0, 0, "x_logout","", obj);
		CVM_Debug.getInstance().printDebugMessage("NCBManager : Queuing NCB logout called with userName:\""+userName+"\".");
	}

	/**
	 * Logs the user out.
	 * We need to also save the schemas and userProfile info as well
	 * @param userName
	 */
	public void x_logout(String userName)
	{
		CVM_Debug.getInstance().printDebugMessage("NCBManager : NCB logout called with userName:\""+userName+"\".");
		m_csmMan.logoutAll();
//		m_coCommCurr = m_csmMan.getCommunicationObject();
//		m_coCommCurr.getNCBBridge().logout(userName);
	}
	
	/**
	 * Queues command, actual executable command is prefixed with x_
	 */
	public void resetNCB()
	{
		m_callQueue.add(""+0, 0, "x_resetNCB","", null);
		CVM_Debug.getInstance().printDebugMessage("NCBManager : Queuing NCB reset called");
	}

	/**
	 * Resets the ncb instnace.
	 */
	public void x_resetNCB()
	{
	
	}
	
	public CommObject getCurComObj() {
		return m_coCommCurr;
	}

}
