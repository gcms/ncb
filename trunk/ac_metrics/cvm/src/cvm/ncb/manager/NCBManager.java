package cvm.ncb.manager;

import cvm.model.CVM_Debug;
import cvm.ncb.UserObject;
import cvm.ncb.UserProfile;
import cvm.ncb.UserSchema;
import cvm.ncb.handlers.EventManager;
import cvm.ncb.handlers.ExceptionHandler;
import cvm.ncb.ks.ObjectManager;
import cvm.ncb.oem.pe.ActionSignalHandler;
import cvm.ncb.oem.pe.CallQueue;
import cvm.ncb.oem.pe.PolicyEvalManager;
import cvm.ncb.oem.pe.SignalHandler;
import cvm.ncb.oem.pe.actions.*;

import java.net.URISyntaxException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Manager of NCB layer.
 *
 * @author Frank Hernandez
 */
public class NCBManager {
    private EventManager m_Notifier = null;
    private CallQueue m_callQueue = null;

    public NCBManager(ObjectManager objectManager, EventManager eventManager) {
        m_Notifier = eventManager;
        ExceptionHandler m_xhXHandler = new ExceptionHandler(m_Notifier);
        //NCBEventHandler m_ehEHandler = NCBEventHandler.Instance();


        PolicyEvalManager policyManager = null;
        try {
            policyManager = createPolicyEvalManager(objectManager, m_xhXHandler);
        } catch (URISyntaxException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            throw new RuntimeException(e);
        }

        m_callQueue = policyManager.getCallQueue();
        policyManager.start();
    }

    private PolicyEvalManager createPolicyEvalManager(ObjectManager om, ExceptionHandler m_xhXHandler) throws URISyntaxException {
        PolicyEvalManager policyManager = new PolicyEvalManager("../../tpm/CVMSelfConfig.xml", "../../repository/examples", om, m_xhXHandler);

        ActionInstance reenableMedium = new ReenableMediumAction();
        SignalHandler failedFrameworkHandler = new ActionSignalHandler("failedFramework", reenableMedium);
        policyManager.registerHandler(failedFrameworkHandler);

        ActionInstance sendSchema = new SendSchemaAction();
        SignalHandler sendSchemaHandler = new ActionSignalHandler("sendSchema", sendSchema);
        policyManager.registerHandler(sendSchemaHandler);

        ActionInstance loginAction = new LoginAction();
        SignalHandler loginAllHandler = new ActionSignalHandler("loginAll", loginAction);
        policyManager.registerHandler(loginAllHandler);

        ActionInstance logoutAction = new LogoutAction();
        SignalHandler logoutAllHandler = new ActionSignalHandler("logoutAll", logoutAction);
        policyManager.registerHandler(logoutAllHandler);

        ActionInstance createSession = new CreateSessionAction();
        SignalHandler createSessionHandler = new ActionSignalHandler("createSession", createSession);
        policyManager.registerHandler(createSessionHandler);

        ActionInstance addParticipant = new AddParticipantAction();
        SignalHandler addParticipantHandler = new ActionSignalHandler("addAParticipant", addParticipant);
        policyManager.registerHandler(addParticipantHandler);

        ActionInstance removeParticipant = new RemoveParticipantAction();
        SignalHandler removeParticipantHandler = new ActionSignalHandler("removeAParticipant", removeParticipant);
        policyManager.registerHandler(removeParticipantHandler);

        ActionInstance mediumChange = new MediumAction();
        SignalHandler enableMediumHandler = new ActionSignalHandler("enableMedium", mediumChange);
        policyManager.registerHandler(enableMediumHandler);
        SignalHandler enableMediumReceiverHandler = new ActionSignalHandler("enableMediumReceiver", mediumChange);
        policyManager.registerHandler(enableMediumReceiverHandler);
        SignalHandler disableMediumHandler = new ActionSignalHandler("disableMedium", mediumChange);
        policyManager.registerHandler(disableMediumHandler);

        return policyManager;
    }

    /**
     * Queues command, actual executable command is prefixed with x_
     */
    public void addParty(String sessionID, String participantID) {
        CVM_Debug.getInstance().printDebugMessage("NCBManager :Queuing NCB addParty called for " + sessionID + " adding " + participantID + ".");
        //Dont forget to parse the list of participants.
        Object[] obj = {sessionID, participantID};
        Map<String, Object> params = new LinkedHashMap<String, Object>();
        params.put("session", sessionID);
        params.put("participant", participantID);
        m_callQueue.add("addAParticipant", params);
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
        Object[] obj = {sessionID};
        Map<String, Object> params = new LinkedHashMap<String, Object>();
        params.put("session", sessionID);
        m_callQueue.add("createSession", params);
        CVM_Debug.getInstance().printDebugMessage("NCBManager : Queuing NCB createSession called.");
    }

    /**
     * Queues command, actual executable command is prefixed with x_
     */
    public void isCreatedSession(String sessionID) {
        Object[] obj = {sessionID};
        Map<String, Object> params = new LinkedHashMap<String, Object>();
        params.put("session", sessionID);
        m_callQueue.add("isSessionCreated", params);
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
        Map<String, Object> params = new LinkedHashMap<String, Object>();
        m_callQueue.add("loginAll", params);
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
        Map<String, Object> params = new LinkedHashMap<String, Object>();
        params.put("session", sID);
        params.put("participant", participant);
        m_callQueue.add("removeAParticipant", params);
        CVM_Debug.getInstance().printDebugMessage("NCBManager - Queuing Remove Party Called");
    }

    /**
     * Queues command, actual executable command is prefixed with x_
     */
    public void sendSchema(String sID, String senderID, String listReceiver, String control_xcml, String data_xcml) {
        Object[] obj = {sID, senderID, listReceiver, control_xcml, data_xcml};
        Map<String, Object> params = new LinkedHashMap<String, Object>();
        params.put("session", sID);
        params.put("sender", senderID);
        params.put("receivers", listReceiver);
        params.put("controlSchema", control_xcml);
        params.put("dataSchema", data_xcml);
        m_callQueue.add("sendSchema", params);
        CVM_Debug.getInstance().printDebugMessage("NCBManager : Queuing NCB sendSchema called with sID:\"" + sID + "\" and senderID:\"" + senderID + "\" and receiverID:\"" + listReceiver + "\" and Control_XCML:\"" + control_xcml + "\" and Data_XCML:\"" + data_xcml + "\".");
    }

    /**
     * Queues command, actual executable command is prefixed with x_
     */
    public void sendMedia(String sID, String medium, String mediumURL) {
        Object[] obj = {sID, medium, mediumURL};
        Map<String, Object> params = new LinkedHashMap<String, Object>();
        params.put("session", sID);
        params.put("medium", medium);
        params.put("url", mediumURL);
        m_callQueue.add("sendMedia", params);
        CVM_Debug.getInstance().printDebugMessage("NCBManager : Queuing NCB sendMedia called with sID:\"" + sID + "\" and Medium:\"" + medium + "\" and MediumURL:\"" + mediumURL + "\".");
    }

    /**
     * Queues command, actual executable command is prefixed with x_
     */
    public void enableMedium(String connectionID, String mediumName) {
        Object[] obj = {connectionID, mediumName};
        Map<String, Object> params = new LinkedHashMap<String, Object>();
        params.put("session", connectionID);
        params.put("medium", mediumName);
        m_callQueue.add("enableMedium", params);
        CVM_Debug.getInstance().printDebugMessage("NCBManager : Queuing NCB sendMedia called with connectionID:\"" + connectionID + "\" and MediumName:\"" + mediumName + "\".");
    }

    /**
     * Queues command, actual executable command is prefixed with x_
     */
    public void enableMediumReceiver(String connectionID, String mediumName) {
        Object[] obj = {connectionID, mediumName};
        Map<String, Object> params = new LinkedHashMap<String, Object>();
        params.put("session", connectionID);
        params.put("medium", mediumName);
        m_callQueue.add("enableMediumReceiver", params);
        CVM_Debug.getInstance().printDebugMessage("NCBManager : Queuing NCB sendMedia called with connectionID:\"" + connectionID + "\" and MediumName:\"" + mediumName + "\".");
    }

    /**
     * Queues command, actual executable command is prefixed with x_
     */
    public void disableMedium(String connectionID, String mediumName) {
        Object[] obj = {connectionID, mediumName};
        Map<String, Object> params = new LinkedHashMap<String, Object>();
        params.put("session", connectionID);
        params.put("medium", mediumName);
        m_callQueue.add("disableMedium", params);
        CVM_Debug.getInstance().printDebugMessage("NCBManager : Queuing NCB sendMedia called with connectionID:\"" + connectionID + "\" and MediumName:\"" + mediumName + ".");
    }

    /**
     * Queues command, actual executable command is prefixed with x_
     */
    public void saveSchema(Object schema) {
        Object[] obj = {schema};
        Map<String, Object> params = new LinkedHashMap<String, Object>();
        params.put("schema", schema);
        m_callQueue.add("saveSchema", params);
        CVM_Debug.getInstance().printDebugMessage("NCBManager : Queuing NCB saveSchema called.");
    }

    /**
     * Queues command, actual executable command is prefixed with x_
     */
    public void logout(String userName) {
        Object[] obj = {userName};
        Map<String, Object> params = new LinkedHashMap<String, Object>();
        m_callQueue.add("logoutAll", params);
        CVM_Debug.getInstance().printDebugMessage("NCBManager : Queuing NCB logout called with userName:\"" + userName + "\".");
    }

    /**
     * Queues command, actual executable command is prefixed with x_
     */
    public void resetNCB() {
        Map<String, Object> params = new LinkedHashMap<String, Object>();
        m_callQueue.add("resetNCB", params);
        CVM_Debug.getInstance().printDebugMessage("NCBManager : Queuing NCB reset called");
    }

    public EventManager getEventObjectManager() {
        return m_Notifier;
    }
}
