package cvm.ncb.handlers.event;

import cvm.model.Handles_Event;

/**
 * This class will represent an event coming from NCB that
 * encapsulate the reply of the sendSchema request
 * 
 * @author Guangqiang Zhao
 *
 */


public class SendSchemaReply_Event extends Handles_Event
{
	/**
	 * A boolean variable holding if the schema is sent successfully
	 */
	private boolean sendStatus = false;

	/**
	 * The constructor 
	 * @param eventSource the object that fires this event
	 * @param sendStatus indicating if the status of the sent schema
	 */
	public SendSchemaReply_Event(Object eventSource, boolean sendStatus) 
	{
		super(eventSource);
		this.sendStatus = sendStatus;
	}
    /**
     * This method returns the status of the schema sent
     * @return the status of the schema sent
     */
	public boolean isSendStatus() 
	{
		return sendStatus;
	}

}
