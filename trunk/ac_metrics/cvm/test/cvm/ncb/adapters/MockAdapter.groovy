package cvm.ncb.adapters

import cvm.sb.adapters.Call
import cvm.sb.adapters.EventException
import cvm.sb.adapters.Event

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

    @Call(name = "login", parameters = [])
    void login() {
        calls.push("login()")
        if (loginFail) {
            throw new EventException("LoginFailed")
        }
    }

    @Call(name = "logout", parameters = [])
    void logout() {
        calls.push("logout()")
    }

    @Call(name = "restartService", parameters = [])
    void restartService() {
        calls.push("restartService()")
    }

    @Call(name = "createSession", parameters = [ "session" ])
    void createSession(String session) {
        calls.push("createSession($session)")
    }

    @Call(name = "destroySession", parameters = [ "session" ])
    void destroySession(String session) {
        calls.push("destroySession($session)")
    }

    void addParticipant(String session, String participant) {
        calls.push("addParticipant($session, $participant)")
    }

    void removeParticipant(String sID, String participant) {
        calls.push("removeParticipant($sID, $participant)")
    }


    @Call(name = "sendSchema", parameters = [ "schema", "participant" ])
    void sendSchema(String schema, String participant) {
        calls.push("sendSchema($schema, $participant)")
    }

    @Call(name = "enableMedium", parameters = [ "session", "medium" ])
    void enableMedium(String session, String medium) {
        calls.push("enableMedium($session, $medium)")
    }

    @Call(name = "enableMediumReceiver", parameters = [ "session", "medium" ])
    void enableMediumReceiver(String session, String medium) {
        calls.push("enableMediumReceiver($session, $medium)")
    }

    @Call(name = "disableMedium", parameters = [ "session", "medium" ])
    void disableMedium(String session, String medium) {
        calls.push("disableMedium($session, $medium)")
    }

    @Call(name = "hasMediumFailed", parameters = [ "session", "medium" ])
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

    @Call(name = "hasMediumFailed", parameters = [ "session", "medium" ])
    boolean hasMediumFailed(String session, String medium) {
        false
    }
}
