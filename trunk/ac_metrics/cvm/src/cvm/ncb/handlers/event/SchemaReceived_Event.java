package cvm.ncb.handlers.event;

import cvm.model.Handles_Event;

/**
 * This class represents the Schema received event send from the NCB.
 * This event occurs when NCB receives a schema sent by another client.
 * @author Frank Henandez
 *
 */
public class SchemaReceived_Event extends Handles_Event 
{

	private String m_sScheam = null;
	public SchemaReceived_Event(Object eventSource, String schema)
	{
		super(eventSource);
		this.m_sScheam = schema;
		// TODO Auto-generated constructor stub
	}
	public String getSchema()
	{
		return this.m_sScheam;
	}

}
