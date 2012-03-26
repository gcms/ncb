package cvm.ncb.manager;

import cvm.model.CVM_Debug;
import cvm.ncb.csm.EventListener;
import cvm.ncb.handlers.EventManager;
import cvm.ncb.oem.pe.MainManager;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Manager of NCB layer.
 *
 * @author Frank Hernandez
 */
public class NCBManager extends ManagerFacade {
    public NCBManager(MainManager manager) {
        super(manager);
    }

    /**
     * Queues command, actual executable command is prefixed with x_
     */
    public void addParty(String sessionID, String participantID) {
        enqueue("addAParticipant", "session", sessionID, "participant", participantID);
    }

    /**
     * Queues command, actual executable command is prefixed with x_
     */
    public void createSession(String sessionID) {
        enqueue("createSession", "session", sessionID);
    }

    /**
     * Queues command, actual executable command is prefixed with x_
     */
    public void isCreatedSession(String sessionID) {
        Map<String, Object> params = new LinkedHashMap<String, Object>();
        params.put("session", sessionID);
        enqueue("isSessionCreated", params);
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
        enqueue("loginAll");
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
     * Queues command, actual executable command is prefixed with x_
     */
    public void removeParty(String sID, String participant) {
        Map<String, Object> params = new LinkedHashMap<String, Object>();
        params.put("session", sID);
        params.put("participant", participant);
        enqueue("removeAParticipant", params);
    }

    /**
     * Queues command, actual executable command is prefixed with x_
     */
    public void sendSchema(String sID, String listReceiver, String controlXCML, String dataXCML) {
        Map<String, Object> params = new LinkedHashMap<String, Object>();
        params.put("session", sID);
        params.put("receivers", listReceiver);
        params.put("controlSchema", controlXCML);
        params.put("dataSchema", dataXCML);
        enqueue("sendSchema", params);
    }

    /**
     * Queues command, actual executable command is prefixed with x_
     */
    public void sendMedia(String sID, String medium, String mediumURL) {
        Map<String, Object> params = new LinkedHashMap<String, Object>();
        params.put("session", sID);
        params.put("medium", medium);
        params.put("url", mediumURL);
        enqueue("sendMedia", params);
    }

    /**
     * Queues command, actual executable command is prefixed with x_
     */
    public void enableMedium(String connectionID, String mediumName) {
        Map<String, Object> params = new LinkedHashMap<String, Object>();
        params.put("session", connectionID);
        params.put("medium", mediumName);
        enqueue("enableMedium", params);
    }

    /**
     * Queues command, actual executable command is prefixed with x_
     */
    public void enableMediumReceiver(String connectionID, String mediumName) {
        Map<String, Object> params = new LinkedHashMap<String, Object>();
        params.put("session", connectionID);
        params.put("medium", mediumName);
        enqueue("enableMediumReceiver", params);
    }

    /**
     * Queues command, actual executable command is prefixed with x_
     */
    public void disableMedium(String connectionID, String mediumName) {
        Map<String, Object> params = new LinkedHashMap<String, Object>();
        params.put("session", connectionID);
        params.put("medium", mediumName);
        enqueue("disableMedium", params);
    }

    /**
     * Queues command, actual executable command is prefixed with x_
     */
    public void saveSchema(Object schema) {
        Map<String, Object> params = new LinkedHashMap<String, Object>();
        params.put("schema", schema);
        enqueue("saveSchema", params);
    }

    /**
     * Queues command, actual executable command is prefixed with x_
     */
    public void logout(String userName) {
        enqueue("logoutAll");
    }

    /**
     * Queues command, actual executable command is prefixed with x_
     */
    public void resetNCB() {
        enqueue("resetNCB");
    }
}
