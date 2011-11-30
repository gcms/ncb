package cvm.ncb.adapters;

import cvm.ncb.UserObject;
import cvm.ncb.handlers.exception.LoginException;
import cvm.ncb.handlers.exception.NoSessionException;
import cvm.ncb.handlers.exception.PartyNotAddedException;
import cvm.ncb.handlers.exception.PartyNotFoundException;

/**
 * Created by IntelliJ IDEA.
 * User: Gustavo Sousa
 * Date: 10/10/11
 * Time: 16:46
 * To change this template use File | Settings | File Templates.
 */
public interface NCBBridge extends ManagementInterface {
    /**
     * This functions returns a coma separated list with the device capabilities.
     * @return list of capabilities as defined in the NCBBridgeCapability class.
     * @see cvm.ncb.adapters.NCBBridgeCapability
     */
    String getCapability();

    /**
     * This function logs the user into the communication bridge i.e Skype, GTalk...
     * @param userName Username registered under the specific bridge.
     * @param password Password registered under the specific bridge.
     * @return UserObject with the user infromation.
     * @throws cvm.ncb.handlers.exception.LoginException
     * @see cvm.ncb.UserObject
     */
    UserObject login(String userName, String password) throws LoginException;

    /**
     * Logs the user out of the Bridge.
     * @param userName
     */
    void logout(String userName);

    /**
     * Restarts the adapter.
     *
     */
    void restartService();

    /**
     * This function creates a session in the Networks Bridge
     * for the specified session ID.
     * @param sessionID Id of the session to create.
     */
    void createSession(String sessionID);

    /**
     * This function destroys a session in the Networks Bridge
     * for the specified session ID.
     * @param sessionID Id of the session to create.
     */
    void destroySession(String sessionID);

    void addAParticipant(String sID, String participantID) throws PartyNotAddedException, NoSessionException;

    /**
     * Adds a participant to the session.
     * @param sID Id of the session to add.
     * @param participantID Id of the participant to add. i.e useranem: crinsomkairos.
     * @throws cvm.ncb.handlers.exception.PartyNotAddedException
     * @throws cvm.ncb.handlers.exception.NoSessionException
     */
    void addParticipant(String sID, String participantID) throws PartyNotAddedException, NoSessionException;

    void removeAParticipant(String sID, String participant) throws PartyNotFoundException, NoSessionException;

    /**
     * Removes a participant to the session.
     * @param sID Id of the session to remove.
     * @param participantID Id of the participant to remove. i.e useranem: crinsomkairos.
     * @throws cvm.ncb.handlers.exception.PartyNotFoundException
     * @throws cvm.ncb.handlers.exception.NoSessionException
     */
    void removeParticipant(String sID, String participant) throws PartyNotFoundException, NoSessionException;

    /**
     * This functions checks if the bridge is looged in with the specified username.
     * @param userName user handle to check for ownership of the Bridge.
     * @return true if the user is the owner of the bridge.
     * @throws cvm.ncb.handlers.exception.LoginException
     */
    boolean isLoggedIn(String userName) throws LoginException;

    /**
     * Checks is the session is currntly created.
     * @param sID Session to check.
     * @return true if the session exist.
     */
    boolean isSessionCreated(String sID);

    /**
     * This function sends the schema file to the specified user.
     * @param schema Schema File.
     * @param participantID Id of the user.
     */
    void sendSchema(String schema, String participantID);

    /**
     * This function is used to enable a communicaciot medium. This will activate the
     * audio in the communication or chat...
     * @param connectionID
     * @param mediumName
     * @throws cvm.ncb.handlers.exception.PartyNotAddedException
     * @throws cvm.ncb.handlers.exception.NoSessionException
     */
    void enableMedium(String connectionID, String mediumName) throws PartyNotAddedException, NoSessionException;

    /**
     * This function is used to setup reception of a communicaciot medium.
     * @param connectionID
     * @param mediumName
     * @throws cvm.ncb.handlers.exception.PartyNotAddedException
     * @throws cvm.ncb.handlers.exception.NoSessionException
     */
    void enableMediumReceiver(String connectionID, String mediumName) throws PartyNotAddedException, NoSessionException;

    /**
     * This function is used to disable a communicaciot medium. This will deactivate the
     * audio in the communication or chat...
     * @param connectionID
     * @param mediumName
     * @throws cvm.ncb.handlers.exception.PartyNotAddedException
     * @throws cvm.ncb.handlers.exception.NoSessionException
     */
    void disableMedium(String connectionID, String mediumName) throws PartyNotFoundException, NoSessionException;

    /**
     * This function checks if communication medium for a session has failed
     * true if failed, false otherwise
     * @param sessID
     * @param medium_type
     */
    boolean hasMediumFailed(String sessID, String medium_type);

    String getFWName();

    String getUsername();

    String getPassword();

    void setPassword(String password);

    void setUsername(String username);
}
