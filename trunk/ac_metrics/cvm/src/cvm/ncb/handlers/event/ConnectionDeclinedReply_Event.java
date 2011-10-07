package cvm.ncb.handlers.event;

import cvm.model.Handles_Event;

/**
 * This class will represent an event 
 * encapsulate the reply of the decline connection request
 * @author Frank Hernandez
 *
 */
public class ConnectionDeclinedReply_Event extends Handles_Event 
{
	/**
	 * The variable holds whether the connection is declined successfully or not 
	 */
	private boolean notifyConnectionReply = false;
	
	/**
	 * The constructor 
	 * @param eventSource the object that fires this event
	 * @param notifyConnectionReply containing the result of the declined connection request
	 */
	public ConnectionDeclinedReply_Event(Object eventSource, boolean notifyConnectionReply) 
	{
		super(eventSource);
		this.notifyConnectionReply = notifyConnectionReply;
	}
	/**
	 * This method returns whether the connection is declined successfully or not 
	 * @return the result of the create connection request
	 */
	public boolean getNotifyConnection()
	{
		return notifyConnectionReply;
	}
	
}