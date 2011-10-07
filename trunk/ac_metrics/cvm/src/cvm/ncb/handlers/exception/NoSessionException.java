package cvm.ncb.handlers.exception;
/**
 * The NoSessionException is thrown when the program
 * tries to add parties or send data in a connection
 * that has no corresponding physical sessions
 * @author Guangqiang Zhao
 *
 */


public class NoSessionException extends Exception
{
	/**
	 * the connection ID which has not physical session in the NCB layer
	 */
	private String connID;
	public NoSessionException(String ID)
	{
		super();
		connID=ID;
	}
	/**
	 * This method returns the connection ID
	 * @return the connection ID that missed a physical session in the low layer
	 */
	public String getConnectionID() 
	{
		return connID;
	}

}
