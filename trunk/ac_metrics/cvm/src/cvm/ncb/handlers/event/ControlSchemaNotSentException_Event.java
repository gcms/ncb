package cvm.ncb.handlers.event;

import cvm.model.Handles_Event;

/**
 * Event used to notify the occurance of a ControlSchemaNotSentException
 * @author Frank Hernandez
 *
 */

public class ControlSchemaNotSentException_Event  extends Handles_Event
{

	public ControlSchemaNotSentException_Event(Object eventSource)
	{
		super(eventSource);
	}

}
