/**
 * 
 */
package cvm.ncb.tpm;

import cvm.ncb.handlers.exception.LoginException;
import cvm.ncb.adapters.*;
/**
 * @author aalle004
 *
 */
public interface ACManagementInterface{
	
	/**
	 * This functions returns a coma separated list with the device capabilities.
	 * @return list of capabilities as defined in the NCBBridgeCapability class.
	 * @see NCBBridgeCapability
	 */
	public abstract String getCapability();
	
	/**
	 * Restarts the adapter.
	 *
	 */
	public abstract void restartService();
	
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
	 * This function checks if communication medium for a session has failed
	 * true if failed, false otherwise
	 * @param sessID
	 * @param medium_type
	 */
	public abstract boolean hasMediumFailed(String sessID, String medium_type);

	public String getFWName();
}
