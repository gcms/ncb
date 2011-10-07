package cvm.ncb.handlers.event;

import cvm.model.Handles_Event;

/**
 * Event used to notify the occurance of a SchemaNotSavedException
 * @author Frank Hernandez
 *
 */
public class SchemaNotSavedException_Event  extends Handles_Event
{

	public SchemaNotSavedException_Event(Object eventSource) 
	{
		super(eventSource);
	}

}
