package cvm.ncb.handlers.exception;
/**
 * The DataNotFoundException is thrown when the program
 * tries to send a file or a form that is not found
 * @author Guangqiang Zhao
 *
 */

 
public class DataNotFoundException extends Exception
{
	/**
	 * The name of the data which could not be found
	 */
	private String dataName;
	public DataNotFoundException(String name) 
	{
		super();
		dataName= name;
	}
	/**
	 * This method returns the name of the file or form
	 * @return the name of the file or form
	 */
	public String getDataName() 
	{
		return dataName;
	}
	
}
