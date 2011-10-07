package cvm.ncb.handlers.event;

import cvm.model.Handles_Event;

/**
 * Event used to notify the occurance of a LoginExcetption
 * @author Frank Hernandez
 *
 */
public class LoginExcetption_Event extends Handles_Event {

	public LoginExcetption_Event(Object eventSource) 
	{
		super(eventSource);
	}

}
