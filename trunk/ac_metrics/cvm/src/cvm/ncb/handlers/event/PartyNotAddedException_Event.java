package cvm.ncb.handlers.event;

import cvm.model.Handles_Event;

/**
 * Event used to notify the occurance of a PartyNotAddedException
 * @author Frank Hernandez
 *
 */
public class PartyNotAddedException_Event extends Handles_Event {

	private String userID;
	public PartyNotAddedException_Event(Object eventSource, String userID) 
	{
		super(eventSource);
		this.userID = userID;
	}
	
	public String getUserID()
	{
		return this.userID;
	}

}
