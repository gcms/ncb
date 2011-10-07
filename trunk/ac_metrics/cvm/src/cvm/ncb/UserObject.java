package cvm.ncb;

import cvm.model.CVM_Debug;

/**
 * Simple implementation of the UserObject class.
 * @author Frank Hernandez.
 *
 */
public class UserObject {

	public UserObject( String uName, String uPW)
	{
		
	}

	/**
	 * This method returns the user schema.
	 * @return user schema.
	 */
	public Object getSchema()
	{
		CVM_Debug.getInstance().printDebugMessage("UserObject getSchema called\n");
	 return new Object();
	}

	
}
