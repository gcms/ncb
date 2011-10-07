package cvm.ncb.handlers.event;

import cvm.model.Handles_Event;

/**
 * This class will represent an event coming from NCB that
 * encapsulate the reply of the login request
 * 
 * @author Guangqiang Zhao
 */

public class NotifyLoginReply_Event extends Handles_Event
{
	/**
	 * The contact list of the logged in user
	 */
	private Object contactList = null;

	/**
	 * The constructor 
	 * @param eventSource the object that fires this event
	 * @param reply reply message containing the contact list
	 */
	public NotifyLoginReply_Event(Object eventSource, Object reply) 
	{
		super(eventSource);
		this.contactList = reply;
	}

	/**
	 * This method returns the contact list of the user
	 * @return the contact list of the user
	 */
	public Object getContactList() 
	{
		return contactList;
	}
	
}
