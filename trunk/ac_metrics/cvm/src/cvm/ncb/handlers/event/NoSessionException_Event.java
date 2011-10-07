package cvm.ncb.handlers.event;

import cvm.model.Handles_Event;

/**
 * Event used to notify the occurance of a NoSessionException
 * @author Frank Hernandez
 *
 */
public class NoSessionException_Event extends Handles_Event 
{

	private String sID;
	public NoSessionException_Event(Object eventSource, String sID)
	{
		super(eventSource);
		this.sID = sID;
		
	}
	/**
	 * This method returns the session id.
	 * @return session ID for failed session.
	 */
	public String getSID()
	{
		return sID;
	}

}
