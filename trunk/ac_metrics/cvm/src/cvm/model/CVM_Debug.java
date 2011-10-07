package cvm.model;

/**
 * This is used for simple debuging.
 * 1 - It prints debugs messages when the bPrintDebug flag is set.
 * 
 * @author Frank Hernandez
 *
 */
public class CVM_Debug 
{
	private boolean bPrintDebug = false;
	private static CVM_Debug instance = null;
	
	private CVM_Debug()
	{
		bPrintDebug = true;
	}
	
	public static CVM_Debug getInstance()
	{
		if(instance==null)
			instance = new CVM_Debug();
		
		return instance;
	}
	/**
	 * This methods print debug messages only if the bPrintDebug flag is set.
	 * @param mess
	 */
	public void printDebugMessage(String mess)
	{
		if(bPrintDebug)
			System.out.println("DEBUG: "+mess+" :END DEBUG");
	}
	
	/**
	 * This methods print debug messages only if the bPrintDebug flag is set.
	 * @param mess
	 */
	public void printDebugMessage(String messID, String mess)
	{
		if(bPrintDebug)
			System.out.println("DEBUG ID="+messID+" : "+mess+" :END DEBUG");
	}

	/**
	 * This methods print debug messages only if the bPrintDebug flag is set.
	 * @param mess
	 */
	public void printDebugErrorMessage(String mess)
	{
		if(bPrintDebug)
			System.err.println("DEBUG: "+mess+" :END DEBUG");
	}

	/**
	 * Retrun the value of bPrintDebug
	 * @return
	 */
	public boolean isBPrintDebug() 
	{
		return bPrintDebug;
	}

	/**
	 * Set the value of bPrintDebug
	 * @param printDebug
	 */
	public void setBPrintDebug(boolean printDebug) 
	{
		bPrintDebug = printDebug;
	}
	
	
}
