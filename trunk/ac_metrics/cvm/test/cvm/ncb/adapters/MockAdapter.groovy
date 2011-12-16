package cvm.ncb.adapters

import cvm.ncb.handlers.exception.LoginException

/**
 * Created by IntelliJ IDEA.
 * User: Gustavo Sousa
 * Date: 08/12/11
 * Time: 19:52
 * To change this template use File | Settings | File Templates.
 */
class MockAdapter extends NCBBridgeBase {
    private List calls = []
    private boolean hasFailed = false

    public static MockAdapter instance
    boolean loginFail

    public MockAdapter() {
        if (getClass().equals(MockAdapter))
            instance = this
    }

    public List getCalls() {
        calls.clone()
    }

    public void markFailed() {
        hasFailed = true
    }

    String getCapability() {
        calls.push("getCapability()")
    }

    void login() {
        calls.push("login()")
        if (loginFail)
            throw new LoginException();
    }

    void logout() {
        calls.push("logout()")
    }

    void restartService() {
        calls.push("restartService()")
    }

    void createSession(String sessionID) {
        calls.push("createSession($sessionID)")
    }

    void destroySession(String sessionID) {
        calls.push("destroySession($sessionID)")
    }

    void addParticipant(String sID, String participantID) {
        calls.push("addParticipant($sID, $participantID)")
    }

    void removeParticipant(String sID, String participant) {
         calls.push("removeParticipant($sID, $participant)")
    }

    boolean isLoggedIn(String userName) {
        return false  //To change body of implemented methods use File | Settings | File Templates.
    }

    boolean isSessionCreated(String sID) {
        return false  //To change body of implemented methods use File | Settings | File Templates.
    }

    void sendSchema(String schema, String participantID) {
         calls.push("sendSchema($schema, $participantID)")
    }

    void enableMedium(String connectionID, String mediumName) {
         calls.push("enableMedium($connectionID, $mediumName)")
    }

    void enableMediumReceiver(String connectionID, String mediumName) {
        calls.push("enableMediumReceiver($connectionID, $mediumName)")
    }

    void disableMedium(String connectionID, String mediumName) {
        calls.push("disableMedium($connectionID, $mediumName)")
    }

    boolean hasMediumFailed(String sessID, String medium_type) {
        hasFailed
    }

    String getFWName() {
        "Mock"
    }

    void loginShouldFail() {
        loginFail = true
    }
}

class Mock2Adapter extends MockAdapter {
    public static Mock2Adapter instance

    public Mock2Adapter() {
        if (getClass().equals(Mock2Adapter))
            instance = this
    }

    public String getFWName() {
        "Mock2"
    }

    boolean hasMediumFailed(String sessID, String medium_type) {
        false
    }
}
