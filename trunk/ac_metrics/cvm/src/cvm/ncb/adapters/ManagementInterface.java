package cvm.ncb.adapters;

import cvm.ncb.handlers.NCBEventObjectManager;
import cvm.ncb.handlers.exception.LoginException;

public interface ManagementInterface {

    public void setEventObjectManager(NCBEventObjectManager eventObjectManager);
	
	/**
	 * This functions returns a comma separated list with the device capabilities.
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
	 * This functions checks if the bridge is logged in with the specified username.
	 * @param userName user handle to check for ownership of the Bridge.
	 * @return true if the user is the owner of the bridge.
	 * @throws LoginException 
	 */
	public abstract boolean isLoggedIn(String userName) throws LoginException;
	
	/**
	 * Checks if the session is currently created.
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
