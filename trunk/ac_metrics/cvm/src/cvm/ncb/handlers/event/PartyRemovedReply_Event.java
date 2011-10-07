package cvm.ncb.handlers.event;

import cvm.model.Handles_Event;

/**
 * This class will represent an event coming from NCB that
 * encapsulate the reply of the remove party request
 * 
 * @author Guangqiang Zhao
 */


public class PartyRemovedReply_Event extends Handles_Event
{
	/**
	 * The variable holding whether the party is removed or not
	 */
	private boolean notifyPartyRemovedReply = false;
	
	/**
	 * The constructor 
	 * @param eventSource the object that fires this event
	 * @param notifyPartyRemovedReply containing the result of removing the party
	 */
	public PartyRemovedReply_Event(Object eventSource, boolean notifyPartyRemovedReply) 
	{
		super(eventSource);
		this.notifyPartyRemovedReply = notifyPartyRemovedReply;
	}
	/**
	 * This method returns a value indicating whether the party is removed successfully or not
	 * @return a boolean value indicating whether the party is removed successfully or not
	 */
	public boolean getNotifyPartyRemoved() 
	{
		return notifyPartyRemovedReply;
	}
}
