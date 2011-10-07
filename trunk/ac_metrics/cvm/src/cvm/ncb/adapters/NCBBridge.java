package cvm.ncb.adapters;

import cvm.ncb.UserObject;
import cvm.ncb.handlers.exception.LoginException;
import cvm.ncb.handlers.exception.NoSessionException;
import cvm.ncb.handlers.exception.PartyNotAddedException;
import cvm.ncb.handlers.exception.PartyNotFoundException;

import cvm.ncb.ks.UserIDMappingTable;

/**
 * Target class of the Abstract design pattern.
 *
 */
public abstract class NCBBridge implements ManagementInterface
{

	public String username, password, fwName;
	
	/**
	 * This functions returns a coma separated list with the device capabilities.
	 * @return list of capabilities as defined in the NCBBridgeCapability class.
	 * @see NCBBridgeCapability
	 */
	public abstract String getCapability();
	
	/**
	 * This function logs the user into the communication bridge i.e Skype, GTalk...
	 * @param userName Username registered under the specific bridge.
	 * @param password Password registered under the specific bridge.
	 * @return UserObject with the user infromation.
	 * @throws LoginException
	 * @see UserObject
	 */
	public abstract UserObject login(String userName, String password) throws LoginException;
	
	/**
	 * Logs the user out of the Bridge.
	 * @param userName
	 */
	public abstract void logout(String userName);
	
	/**
	 * Restarts the adapter.
	 *
	 */
	public abstract void restartService();
	
	/**
	 * This function creates a session in the Networks Bridge
	 * for the specified session ID.
	 * @param sessionID Id of the session to create.
	 */
	public abstract void createSession(String sessionID);
	
	
	/**
	 * This function destroys a session in the Networks Bridge
	 * for the specified session ID.
	 * @param sessionID Id of the session to create.
	 */
	public abstract void destroySession(String sessionID);
	
	
	/**
	 * Adds a participant to the session.
	 * @param sID Id of the session to add.
	 * @param participantID Id of the participant to add. i.e useranem: crinsomkairos. 
	 * @throws PartyNotAddedException 
	 * @throws NoSessionException 
	 */
	public void addAParticipant(String sID,String participantID) throws PartyNotAddedException, NoSessionException {
		addParticipant(sID, UserIDMappingTable.getInstance().lookupContact(this.fwName, participantID));
	}

	/**
	 * Adds a participant to the session.
	 * @param sID Id of the session to add.
	 * @param participantID Id of the participant to add. i.e useranem: crinsomkairos. 
	 * @throws PartyNotAddedException 
	 * @throws NoSessionException 
	 */
	public abstract void addParticipant(String sID,String participantID) throws PartyNotAddedException, NoSessionException;

	/**
	 * Removes a participant to the session.
	 * @param sID Id of the session to remove.
	 * @param participantID Id of the participant to remove. i.e useranem: crinsomkairos. 
	 * @throws PartyNotFoundException 
	 * @throws NoSessionException 
	 */
	public void removeAParticipant(String sID, String participant) throws PartyNotFoundException, NoSessionException{
		removeParticipant(sID, UserIDMappingTable.getInstance().lookupContact(fwName, participant));
	}

	/**
	 * Removes a participant to the session.
	 * @param sID Id of the session to remove.
	 * @param participantID Id of the participant to remove. i.e useranem: crinsomkairos. 
	 * @throws PartyNotFoundException 
	 * @throws NoSessionException 
	 */
	public abstract void removeParticipant(String sID, String participant) throws PartyNotFoundException, NoSessionException;
	
	/**
	 * This functions checks if the bridge is looged in with the specified username.
	 * @param userName user handle to check for ownership of the Bridge.
	 * @return true if the user is the owner of the bridge.
	 * @throws LoginException 
	 */
	public abstract boolean isLoggedIn(String userName) throws LoginException;
	
	/**
	 * Checks is the session is currntly created.
	 * @param sID Session to check.
	 * @return true if the session exist.
	 */
	public abstract boolean isSessionCreated(String sID);
	
	/**
	 * This function sends the schema file to the specified user.
	 * @param schema Schema File.
	 * @param participantID Id of the user.
	 */
	public abstract void sendSchema(String schema, String participantID);
	
	/**
	 * This function is used to enable a communicaciot medium. This will activate the
	 * audio in the communication or chat...
	 * @param connectionID
	 * @param mediumName
	 * @throws PartyNotAddedException
	 * @throws NoSessionException
	 */
	public abstract void enableMedium(String connectionID, String mediumName) throws PartyNotAddedException, NoSessionException;
	
	/**
	 * This function is used to setup reception of a communicaciot medium.
	 * @param connectionID
	 * @param mediumName
	 * @throws PartyNotAddedException
	 * @throws NoSessionException
	 */
	public abstract void enableMediumReceiver(String connectionID, String mediumName) throws PartyNotAddedException, NoSessionException;

	/**
	 * This function is used to disable a communicaciot medium. This will deactivate the
	 * audio in the communication or chat...
	 * @param connectionID
	 * @param mediumName
	 * @throws PartyNotAddedException
	 * @throws NoSessionException
	 */
	public abstract void disableMedium(String connectionID, String mediumName) throws PartyNotFoundException, NoSessionException;
	
	
	/**
	 * This function checks if communication medium for a session has failed
	 * true if failed, false otherwise
	 * @param sessID
	 * @param medium_type
	 */
	public abstract boolean hasMediumFailed(String sessID, String medium_type);

	public String getFWName(){
		return fwName;
	}
}



