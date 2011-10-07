package cvm.ncb.handlers.event;

import cvm.model.Handles_Event;

/**
 * This class will represent an event coming from NCB that
 * encapsulate the reply of the add party request
 * 
 * @author Guangqiang Zhao
 */


public class PartyAddedReply_Event extends Handles_Event 
{
	/**
	 * The variable holding whether the party is added or not
	 */
	private boolean notifyPartyAdded = false;
	
	/**
	 * The constructor 
	 * @param eventSource the object that fires this event
	 * @param notifyPartyAdded  containing the result of adding the party
	 */
	public PartyAddedReply_Event(Object eventSource, boolean notifyPartyAdded) 
	{
		super(eventSource);
		this.notifyPartyAdded = notifyPartyAdded;
	}

	/**
	 * This method returns a value indicating whether the party is added successfully or not
	 * @return a boolean value indicating whether the party is added successfully or not
	 */
	public boolean getNotifyPartyAdded() 
	{
		return notifyPartyAdded;
	}
	

}
