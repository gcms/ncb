package cvm.ncb.manager;

import cvm.model.CVM_Debug;
import cvm.ncb.UserObject;
import cvm.ncb.UserProfile;
import cvm.ncb.UserSchema;

/**
 * Facade into the NCB_Manager package.
 * @author Frank Hernandez
 *
 */
public class NCB_M_Facade
{

	private static NCB_M_Facade instance = null;
	private NCBManager m_ncbManager = null;
	private NCB_M_Facade()
	{
		m_ncbManager = new NCBManager();
	}
	
	/**
	 * Singleton implementation.
	 * @return instance of NCB_M_Facade
	 */
	public static NCB_M_Facade getInstance()
	{
		if(instance==null)
			instance = new NCB_M_Facade();
		
		return instance;
		
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
	public void addParty(String sessionID, String participantID)
	{
		CVM_Debug.getInstance().printDebugMessage("NCB_M_Facade : NCB addParty called.");
		m_ncbManager.addParty(sessionID, participantID);
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
//	public void mapConnToSession(String connectionID, String sessionID)
//	{
//		CVM_Debug.getInstance().printDebugMessage("NCB_M_Facade : NCB mapConnToSession called.");
//		m_ncbManager.mapConnToSession(connectionID, sessionID);
//	}
	/**
	 * This function creates a session with the specific session ID
	 * @param sessionID ID of the session to create.
	 */
	public void createSession(String sessionID)
	{
		CVM_Debug.getInstance().printDebugMessage("NCB_M_Facade : NCB createSession called.");
		m_ncbManager.createSession(sessionID);	
	}
	
	/**
	 * This method returns whether the session was created or not.
	 * @return is session created.
	 */
	public boolean isCreatedSession(String sessionID)
	{
		CVM_Debug.getInstance().printDebugMessage("NCB_M_Facade : NCB isCreatedSession called.");
		m_ncbManager.isCreatedSession(sessionID);
		return true;
	}
	
	
	/**
	 * This function needs to be changed, there can be to many session for one connection.
	 * This method will return the sessionId for the given connection.
	 * @param connID Id of the connection uinder which the session should be mapped.
	 * @return session ID Id of the session mapped to the connection.
	 * @deprecated
	 */
	public String getSessionID(String connID)
	{
		CVM_Debug.getInstance().printDebugMessage("NCB_M_Facade : NCB getSession called with connID:\""+connID+"\".");
		m_ncbManager.getSessionID(connID);
		return "";
	}
	

	/**
	 * This method will attempt login the given user.
	 * @param userName
	 * @param password
	 * @return
	 */
	public UserObject login(String userName, String password)
	{
		UserObject usr = new UserObject(userName, password);
		CVM_Debug.getInstance().printDebugMessage("NCB_M_Facade : NCB Login called with userName:\""+userName+"\" and Password:\""+password+"\".");
		m_ncbManager.login(userName, password);
		return usr;
	}
	

	/**
	 * This method returns the schemas for the given user.
	 * @param userName
	 * @param password
	 * @return
	 */
	public UserSchema retrieveSchemas(String userName, String password)
	{
		CVM_Debug.getInstance().printDebugMessage("NCB_M_Facade : NCB retrieve schema called with userName:\""+userName+"\" and Password:\""+password+"\".");
		m_ncbManager.retrieveSchemas(userName, password);
		return new UserSchema();
	}
	
	/**
	 * This method signals whether the session is created or not.
	 * @return
	 * @deprecated
	 */
	public boolean createdSession()
	{
		CVM_Debug.getInstance().printDebugMessage("NCB_M_Facade : isSessionCreated----Called");
		m_ncbManager.createdSession();
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
		CVM_Debug.getInstance().printDebugMessage("NCB_M_Facade : NCB createUserProfile called.");
		m_ncbManager.createUserProfile(usr, schema);
		return new UserProfile(usr, schema);
	}

	
	/**
	 * This method adds the list of participants 
	 * to the given session. RIGHT WAY
	 * 
	 * @param sID
	 * @param participant
	 * @return
	 */
	public void removeParty(String sID, String participant)
	{
		m_ncbManager.removeParty(sID, participant);
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
	public boolean sendSchema(String sID, String senderID, String listReceiver, String control_xcml,String data_xcml )
	{
		CVM_Debug.getInstance().printDebugMessage("NCB_M_Facade : NCB sendSchema called with sID:\""+sID+"\" and senderID:\""+senderID+"\" and receiverID:\""+listReceiver+"\" and Control_XCML:\""+control_xcml+"\" and Data_XCML:\""+data_xcml+"\".");
		m_ncbManager.sendSchema(sID, senderID, listReceiver, control_xcml, data_xcml);
	 return true;
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
	public boolean sendSchema(String sID, String senderID, String listReceiver, Object control_xcml)
	{
		CVM_Debug.getInstance().printDebugMessage("NCB_M_Facade : NCB sendSchema called with sID:\""+sID+"\" and senderID:\""+senderID+"\" for receiversIDs:\""+listReceiver+"\".");
	 m_ncbManager.sendSchema(sID, senderID, listReceiver, control_xcml);
	 return true;
	}
	/**
	 * This command will send the specified medium to all 
	 * the participants during the connection.
	 * @param sID
	 * @param medium
	 * @param mediumURL
	 * @return
	 */
	public boolean sendMedia(String sID, String medium, String mediumURL)
	{
		CVM_Debug.getInstance().printDebugMessage("NCB_M_Facade : NCB sendMedia called with sID:\""+sID+"\" and Medium:\""+medium+"\" and MediumURL:\""+mediumURL+"\".");
		 m_ncbManager.sendMedia(sID, medium, mediumURL);
		 return true;
	}

	/**
	 * Saves teh given schema.
	 * @param schema
	 */
	public boolean saveSchema(Object schema)
	{
		CVM_Debug.getInstance().printDebugMessage("NCB_M_Facade : NCB saveSchema called.");
		m_ncbManager.saveSchema(schema);
		return true;
	}
	
	public void enableMedium(String connectionID, String mediumName)
	{
		m_ncbManager.enableMedium(connectionID, mediumName);
	}
	
	public void enableMediumReceiver(String connectionID, String mediumName)
	{
		m_ncbManager.enableMediumReceiver(connectionID, mediumName);
	}

	/**
	 * This command will stop sending the specified medium to all 
	 * the participants during the connection.
	 * @param sID
	 * @param medium
	 * @param mediumURL
	 * @return
	 */
	public void disableMedium(String connectionID, String mediumName)
	{
		m_ncbManager.disableMedium(connectionID, mediumName);
	}

	/**
	 * Logs the user out.
	 * @param userName
	 */
	public void logout(String userName)
	{
		CVM_Debug.getInstance().printDebugMessage("NCB_M_Facade : NCB logout called with userName:\""+userName+"\".");
		m_ncbManager.logout(userName);
	}
	
	/**
	 * Resets the ncb instnace.
	 *
	 */
	public void resetNCB()
	{
		CVM_Debug.getInstance().printDebugMessage("NCB_M_Facade : NCB resetNCB is called.");
		m_ncbManager.resetNCB();
	}

    public NCBManager getManager() {
        return m_ncbManager;
    }
}
