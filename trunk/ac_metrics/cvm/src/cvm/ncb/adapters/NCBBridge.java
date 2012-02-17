package cvm.ncb.adapters;

import cvm.ncb.handlers.exception.LoginException;
import cvm.ncb.handlers.exception.NoSessionException;
import cvm.ncb.handlers.exception.PartyNotAddedException;
import cvm.ncb.handlers.exception.PartyNotFoundException;

public interface NCBBridge extends Manageable {
    /**
     * This function logs the user into the communication bridge i.e Skype, GTalk...
     * @return UserObject with the user infromation.
     * @throws cvm.ncb.handlers.exception.LoginException
     */
    @Method(name="login", parameters = {})
    void login() throws LoginException;

    /**
     * Logs the user out of the Bridge.
     */
    @Method(name="logout", parameters = {})
    void logout();

    /**
     * Restarts the adapter.
     *
     */
    @Method(name="restartService", parameters = {})
    void restartService();

    /**
     * This function creates a session in the Networks Bridge
     * for the specified session ID.
     * @param session Id of the session to create.
     */
    @Method(name="createSession", parameters = {"session"})
    void createSession(String session);

    /**
     * This function destroys a session in the Networks Bridge
     * for the specified session ID.
     * @param session Id of the session to create.
     */
    @Method(name="destroySession", parameters = {"session"})
    void destroySession(String session);

    @Method(name="addAParticipant", parameters = {"session", "participant"})
    void addAParticipant(String session, String participant) throws PartyNotAddedException, NoSessionException;

    /**
     * Adds a participant to the session.
     * @param session Id of the session to add.
     * @param participant Id of the participant to add. i.e useranem: crinsomkairos.
     * @throws cvm.ncb.handlers.exception.PartyNotAddedException
     * @throws cvm.ncb.handlers.exception.NoSessionException
     */
    void addParticipant(String session, String participant) throws PartyNotAddedException, NoSessionException;

    @Method(name="removeAParticipant", parameters = {"session", "participant"})
    void removeAParticipant(String session, String participant) throws PartyNotFoundException, NoSessionException;

    /**
     * Removes a participant to the session.
     * @param sID Id of the session to remove.
     * @param participantID Id of the participant to remove. i.e useranem: crinsomkairos.
     * @throws cvm.ncb.handlers.exception.PartyNotFoundException
     * @throws cvm.ncb.handlers.exception.NoSessionException
     */
    void removeParticipant(String sID, String participant) throws PartyNotFoundException, NoSessionException;

    /**
     * This function sends the schema file to the specified user.
     * @param schema Schema File.
     * @param participant Id of the user.
     */
    @Method(name="sendSchema", parameters = {"schema", "participant"})
    void sendSchema(String schema, String participant);

    /**
     * This function is used to enable a communicaciot medium. This will activate the
     * audio in the communication or chat...
     * @param session
     * @param medium
     * @throws cvm.ncb.handlers.exception.PartyNotAddedException
     * @throws cvm.ncb.handlers.exception.NoSessionException
     */
    @Method(name="enableMedium", parameters = {"session", "medium"})
    void enableMedium(String session, String medium) throws PartyNotAddedException, NoSessionException;

    /**
     * This function is used to setup reception of a communicaciot medium.
     * @param session
     * @param medium
     * @throws cvm.ncb.handlers.exception.PartyNotAddedException
     * @throws cvm.ncb.handlers.exception.NoSessionException
     */
    @Method(name="enableMediumReceiver", parameters = {"session", "medium"})
    void enableMediumReceiver(String session, String medium) throws PartyNotAddedException, NoSessionException;

    /**
     * This function is used to disable a communicaciot medium. This will deactivate the
     * audio in the communication or chat...
     * @param session
     * @param medium
     * @throws cvm.ncb.handlers.exception.PartyNotAddedException
     * @throws cvm.ncb.handlers.exception.NoSessionException
     */
    @Method(name="disableMedium", parameters = {"session", "medium"})
    void disableMedium(String session, String medium) throws PartyNotFoundException, NoSessionException;

    /**
     * This function checks if communication medium for a session has failed
     * true if failed, false otherwise
     * @param session
     * @param medium
     */
    @Method(name="hasMediumFailed", parameters = {"session", "medium"})
    boolean hasMediumFailed(String session, String medium);

    String getName();
}
