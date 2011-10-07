package cvm.ncb.handlers.event;

import cvm.model.Handles_Event;

/**
 * Event used to notify the occurance of a DataSchemaNotSentException
 * @author Frank Hernandez
 *
 */
public class DataSchemaNotSentException_Event extends Handles_Event
{

	public DataSchemaNotSentException_Event(Object eventSource) 
	{
		super(eventSource);
	}

}
