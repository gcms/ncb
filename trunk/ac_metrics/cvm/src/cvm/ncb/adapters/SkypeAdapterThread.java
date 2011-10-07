package cvm.ncb.adapters;

import com.skype.Application;
import cvm.model.*;

/**
 * This thread propagation of schemas as they are received.
 * @author Frank Hernandez
 *
 */
public class SkypeAdapterThread extends Thread
{

	private final int CHECKTIME  = 10000;
	private boolean bRunThread = false;
	private SkypeAdapterDataContainer adapt = null;
	public Application m_appSkypeApp;
	
	public SkypeAdapterThread(SkypeAdapterDataContainer checkAdapt)
	{
		adapt = checkAdapt;
	}
	public void run()
	{
		
		bRunThread = true;
		while(bRunThread)
		{
			//CVM_Debug.getInstance().printDebugMessage("testing");
			try
			{
				//sleep(CHECKTIME);
				adapt.handledReceviedSchemas();
				CVM_Debug.getInstance().printDebugMessage("Done Dealing Schemas");
			}
			catch(InterruptedException e)
			{
				bRunThread = false;
			}	
		}
			
	}

}
