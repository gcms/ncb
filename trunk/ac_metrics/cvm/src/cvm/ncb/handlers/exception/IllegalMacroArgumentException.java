package cvm.ncb.handlers.exception;
/**
 * This class represents the exception thrown by the macro interpreter
 * when an illegal argument is encountered
 * @author Guangqiang Zhao
 *
 */

public class IllegalMacroArgumentException extends Exception
{
	/**
	 * the name of the macro that encounters an unexpected argument
	 */
	private String macroName;
	/**
	 * the name of the argument that courses this exception
	 */
	private String argName;
	
	public IllegalMacroArgumentException(String macro, String arg) 
	{
		super();
		macroName = macro;
		argName= arg;
	}
	/**
	 * This method returns the name of the macro
	 * @return the name of the macro that encounters an unexpected argument
	 */
	public String getMacroName()
	{
		return macroName;
	}
	/**
	 * This method returns the name of the argument
	 * @return the name of the argument that courses this exception
	 */
	public String getIllegalArgument()
	{
		return argName;
	}
	
}
