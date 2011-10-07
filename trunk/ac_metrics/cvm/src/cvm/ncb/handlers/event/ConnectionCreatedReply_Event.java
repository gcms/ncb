package cvm.ncb.handlers.event;

import cvm.model.Handles_Event;

/**
 * This class will represent an event coming from NCB that
 * encapsulate the reply of the create connection request
 * @author Guangqiang Zhao
 *
 */


public class ConnectionCreatedReply_Event extends Handles_Event 
{
	/**
	 * the variable holding whether the connection is created successfully or not 
	 */
	private boolean notifyConnectionReply = false;
	
	/**
	 * The constructor 
	 * @param eventSource the object that fires this event
	 * @param notifyConnectionReply containing the result of the create connection request
	 */
	public ConnectionCreatedReply_Event(Object eventSource, boolean notifyConnectionReply) 
	{
		super(eventSource);
		this.notifyConnectionReply = notifyConnectionReply;
	}

	/**
	 * This method returns whether the connection is created successfully or not 
	 * @return the result of the create connection request
	 */
	public boolean getNotifyConnection()
	{
		return notifyConnectionReply;
	}
	
}
