package cvm.ncb.adapters

import cvm.ncb.handlers.exception.LoginException


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

    public void markFailed(String medium) {
        notify(new Event("MediumFailed", [medium: medium]))
    }

    public void markFailed() {
        hasFailed = true
    }

    String getCapability() {
        calls.push("getCapability()")
    }

    @Method(name = "login", parameters = [])
    void login() {
        calls.push("login()")
        if (loginFail)
            throw new LoginException();
    }

    @Method(name = "logout", parameters = [])
    void logout() {
        calls.push("logout()")
    }

    @Method(name = "restartService", parameters = [])
    void restartService() {
        calls.push("restartService()")
    }

    @Method(name = "createSession", parameters = [ "session" ])
    void createSession(String session) {
        calls.push("createSession($session)")
    }

    @Method(name = "destroySession", parameters = [ "session" ])
    void destroySession(String session) {
        calls.push("destroySession($session)")
    }

    void addParticipant(String session, String participant) {
        calls.push("addParticipant($session, $participant)")
    }

    void removeParticipant(String sID, String participant) {
        calls.push("removeParticipant($sID, $participant)")
    }


    @Method(name = "sendSchema", parameters = [ "schema", "participant" ])
    void sendSchema(String schema, String participant) {
        calls.push("sendSchema($schema, $participant)")
    }

    @Method(name = "enableMedium", parameters = [ "session", "medium" ])
    void enableMedium(String session, String medium) {
        calls.push("enableMedium($session, $medium)")
    }

    @Method(name = "enableMediumReceiver", parameters = [ "session", "medium" ])
    void enableMediumReceiver(String session, String medium) {
        calls.push("enableMediumReceiver($session, $medium)")
    }

    @Method(name = "disableMedium", parameters = [ "session", "medium" ])
    void disableMedium(String session, String medium) {
        calls.push("disableMedium($session, $medium)")
    }

    @Method(name = "hasMediumFailed", parameters = [ "session", "medium" ])
    boolean hasMediumFailed(String session, String medium) {
        hasFailed
    }

    String getName() {
        "Mock"
    }

    void loginShouldFail() {
        loginFail = true
    }

    void notifySchemaReceivedEvent(String schema) {
        notify(new Event("SchemaReceived", [schema: schema]))
    }
}

class Mock2Adapter extends MockAdapter {
    public static Mock2Adapter instance

    public Mock2Adapter() {
        if (getClass().equals(Mock2Adapter))
            instance = this
    }

    public String getName() {
        "Mock2"
    }

    @Method(name = "hasMediumFailed", parameters = [ "session", "medium" ])
    boolean hasMediumFailed(String session, String medium_) {
        false
    }
}
