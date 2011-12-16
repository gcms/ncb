package cvm.ncb.manager;

import cvm.model.CVM_Debug;
import cvm.ncb.UserObject;
import cvm.ncb.UserProfile;
import cvm.ncb.UserSchema;
import cvm.ncb.handlers.NCBEventHandler;
import cvm.ncb.handlers.NCBEventObjectManager;
import cvm.ncb.handlers.NCBExceptionHandler;
import cvm.ncb.oem.pe.NCBCallQueue;
import cvm.ncb.oem.pe.PolicyEvalManager;
import cvm.ncb.oem.pe.handlers.*;

/**
 * Manager of NCB layer.
 *
 * @author Frank Hernandez
 */
public class NCBManager {
    private NCBExceptionHandler m_xhXHandler = null;
    private NCBEventHandler m_ehEHandler = null;
    private NCBEventObjectManager m_ncbNotifier = null;
    private NCBCallQueue m_callQueue = null;
    private PolicyEvalManager policyManager;

    public NCBManager() {
        m_ncbNotifier = new NCBEventObjectManager();
        m_xhXHandler = new NCBExceptionHandler(m_ncbNotifier);
        //m_ehEHandler = NCBEventHandler.Instance();

        policyManager = new PolicyEvalManager(m_xhXHandler);
        policyManager.registerHandler(new FailedFrameworkHandler());
        policyManager.registerHandler(new SendSchemaHandler());
        policyManager.registerHandler(new LoginAllHandler());
        policyManager.registerHandler(new LogoutAllHandler());
        policyManager.registerHandler(new CreateSessionHandler());
        policyManager.registerHandler(new MediumHandler());
        policyManager.registerHandler(new DefaultHandler());

        m_callQueue = policyManager.getCallQueue();
        policyManager.start();
    }

    /**
     * Queues command, actual executable command is prefixed with x_
     */
    public void addParty(String sessionID, String participantID) {
        CVM_Debug.getInstance().printDebugMessage("NCBManager :Queuing NCB addParty called for " + sessionID + " adding " + participantID + ".");
        //Dont forget to parse the list of participants.
        int count = 1;
        Object[] obj = {sessionID, participantID};
        m_callQueue.add(sessionID, count, "addAParticipant", "", obj);
    }

    /**
     * Queues command, actual executable command is prefixed with x_
     */
//	public void mapConnToSession(String connectionID, String sessionID)
//	{
//		int count =0;
//		Object[] obj = {connectionID, sessionID};
//		m_callQueue.add(sessionID, count, "mapConnToSession","",obj);
//		CVM_Debug.getInstance().printDebugMessage("NCBManager : Queuing NCB mapConnToSession called.");
//	}

    /**
     * Queues command, actual executable command is prefixed with x_
     */
    public void createSession(String sessionID) {
        int count = 0;
        Object[] obj = {sessionID};
        m_callQueue.add(sessionID, count, "createSession", "", obj);
        CVM_Debug.getInstance().printDebugMessage("NCBManager : Queuing NCB createSession called.");
    }

    /**
     * Queues command, actual executable command is prefixed with x_
     */
    public void isCreatedSession(String sessionID) {
        int count = 0;
        Object[] obj = {sessionID};
        m_callQueue.add(sessionID, count, "isSessionCreated", "", obj);
        CVM_Debug.getInstance().printDebugMessage("NCBManager : Queuing NCB isCreatedSession called.");

//		return m_coCommCurr.getBridge().isSessionCreated(sessionID);
    }

    /**
     * This function must be changed, a single connection can have multiple sessions.
     * This method will return the sessionId for the given connection.
     *
     * @param connID Id of the connection uinder which the session should be mapped.
     * @return session ID Id of the session mapped to the connection.
     * @deprecated
     */
    public String getSessionID(String connID) {
        CVM_Debug.getInstance().printDebugMessage("NCBManager : NCB getSession called with connID:\"" + connID + "\".");

        return "";
    }


    /**
     * Queues command, actual executable command is prefixed with x_
     */
    public void login(String userName, String password) {
        Object[] obj = {userName, password};
        m_callQueue.add("" + 0, 0, "loginAll", "", new Object[]{});
        CVM_Debug.getInstance().printDebugMessage("NCBManager : Queuing NCB Login called with userName:\"" + userName + "\" and Password:\"" + password + "\".");
    }

    /**
     * This method returns the schemas for the given user.
     *
     * @param userName
     * @param password
     * @return UserSchema
     */
    public UserSchema retrieveSchemas(String userName, String password) {
        CVM_Debug.getInstance().printDebugMessage("NCBManager : NCB retrieve schema called with userName:\"" + userName + "\" and Password:\"" + password + "\".");
        return new UserSchema();
    }

    /**
     * This method signals whether the sessin is created or not.
     *
     * @return
     * @deprecated
     */
    public boolean createdSession() {
        CVM_Debug.getInstance().printDebugMessage("NCBManager : isSessionCreated----Called");
        return true;
    }

    /**
     * This method generates a user profile for the give
     * user.
     *
     * @param usr    User object to create the profile.
     * @param schema Schema to send to the user.
     * @return a created user profile.
     */
    public UserProfile createUserProfile(UserObject usr, Object schema) {
        CVM_Debug.getInstance().printDebugMessage("NCBManager : NCB createUserProfile called.");
        return new UserProfile(usr, schema);
    }

    /**
     * Queues command, actual executable command is prefixed with x_
     */
    public void removeParty(String sID, String participant) {
        Object[] obj = {sID, participant};
        m_callQueue.add(sID, -1, "removeAParticipant", "", obj);
        CVM_Debug.getInstance().printDebugMessage("NCBManager - Queuing Remove Party Called");
    }

    /**
     * Queues command, actual executable command is prefixed with x_
     */
    public void sendSchema(String sID, String senderID, String listReceiver, String control_xcml, String data_xcml) {
        Object[] obj = {sID, senderID, listReceiver, control_xcml, data_xcml};
        m_callQueue.add(sID, 0, "sendSchema", "", obj);
        CVM_Debug.getInstance().printDebugMessage("NCBManager : Queuing NCB sendSchema called with sID:\"" + sID + "\" and senderID:\"" + senderID + "\" and receiverID:\"" + listReceiver + "\" and Control_XCML:\"" + control_xcml + "\" and Data_XCML:\"" + data_xcml + "\".");
    }

    /**
     * Queues command, actual executable command is prefixed with x_
     */
    public void sendSchema(String sID, String senderID, String listReceiver, Object control_xcml) {
        Object[] obj = {sID, senderID, listReceiver, control_xcml, null};
        m_callQueue.add(sID, 0, "sendSchema", "", obj);
        CVM_Debug.getInstance().printDebugMessage("NCBManager : Queuing NCB sendSchema called with sID:\"" + sID + "\" and senderID:\"" + senderID + "\" for receiversIDs:\"" + listReceiver + "\".");
    }

    /**
     * Queues command, actual executable command is prefixed with x_
     */
    public void sendMedia(String sID, String medium, String mediumURL) {
        Object[] obj = {sID, medium, mediumURL};
        m_callQueue.add(sID, 0, "sendMedia", medium, obj);
        CVM_Debug.getInstance().printDebugMessage("NCBManager : Queuing NCB sendMedia called with sID:\"" + sID + "\" and Medium:\"" + medium + "\" and MediumURL:\"" + mediumURL + "\".");
    }

    /**
     * Queues command, actual executable command is prefixed with x_
     */
    public void enableMedium(String connectionID, String mediumName) {
        Object[] obj = {connectionID, mediumName};
        m_callQueue.add(connectionID, 0, "enableMedium", mediumName, obj);
        CVM_Debug.getInstance().printDebugMessage("NCBManager : Queuing NCB sendMedia called with connectionID:\"" + connectionID + "\" and MediumName:\"" + mediumName + "\".");
    }

    /**
     * Queues command, actual executable command is prefixed with x_
     */
    public void enableMediumReceiver(String connectionID, String mediumName) {
        Object[] obj = {connectionID, mediumName};
        m_callQueue.add(connectionID, 0, "enableMediumReceiver", mediumName, obj);
        CVM_Debug.getInstance().printDebugMessage("NCBManager : Queuing NCB sendMedia called with connectionID:\"" + connectionID + "\" and MediumName:\"" + mediumName + "\".");
    }

    /**
     * Queues command, actual executable command is prefixed with x_
     */
    public void disableMedium(String connectionID, String mediumName) {
        Object[] obj = {connectionID, mediumName};
        m_callQueue.add(connectionID, 0, "disableMedium", mediumName, obj);
        CVM_Debug.getInstance().printDebugMessage("NCBManager : Queuing NCB sendMedia called with connectionID:\"" + connectionID + "\" and MediumName:\"" + mediumName + ".");
    }

    /**
     * Queues command, actual executable command is prefixed with x_
     */
    public void saveSchema(Object schema) {
        Object[] obj = {schema};
        m_callQueue.add("" + 0, 0, "saveSchema", "", obj);
        CVM_Debug.getInstance().printDebugMessage("NCBManager : Queuing NCB saveSchema called.");
    }

    /**
     * Queues command, actual executable command is prefixed with x_
     */
    public void logout(String userName) {
        Object[] obj = {userName};
        m_callQueue.add("" + 0, 0, "logoutAll", "", new Object[]{});
        CVM_Debug.getInstance().printDebugMessage("NCBManager : Queuing NCB logout called with userName:\"" + userName + "\".");
    }

    /**
     * Queues command, actual executable command is prefixed with x_
     */
    public void resetNCB() {
        m_callQueue.add("" + 0, 0, "resetNCB", "", null);
        CVM_Debug.getInstance().printDebugMessage("NCBManager : Queuing NCB reset called");
    }

    public NCBEventObjectManager getEventObjectManager() {
        return m_ncbNotifier;
    }
}
