package cvm.ncb.adapters;

import cvm.model.CVM_Debug;
import cvm.ncb.handlers.event.SchemaReceived_Event;

import java.util.ArrayList;
/**
 * This class contains all the schemas that have been 
 * received and prepares them to be sent up to NCB
 * 
 * @author Frank Hernandez, Eddie Incer, Raidel Batista
 *
 */
public class SkypeAdapterDataContainer 
{
	private ArrayList<String> m_SchemasRecevied = new ArrayList<String>();
	private SkypeAdapterThread schemaThread = null;
	public static int sentCount = 0;
	private final String SchemaTagOpen = "<NEGOTIATION";
	private final String SchemaTagClose = "</NEGOTIATION>";
	private final String CallAddTagOpen = "<CALL>";
	private final String CallAddTagClose = "</CALL>";
	private final String CallRemTagOpen = "<CALLR>";
	private final String CallRemTagClose = "</CALLR>";
	private final String ChatAddTagOpen = "<CHAT>";
	private final String ChatAddTagClose = "</CHAT>";
    SkypeAdapter adapter;

    SkypeAdapterDataContainer(SkypeAdapter adapter)
	{
        this.adapter = adapter;
		schemaThread = new SkypeAdapterThread(this);
		schemaThread.start();
	}
	
	/**
	 * Places the schemas in a container for processing.
	 * @param arg0
	 */
	public void enqueueSchemaForProcessing(String arg0)
	{
		m_SchemasRecevied.add(arg0);	
	}
	public synchronized void signalSchemaReceived()
	{
		notify();
	}
	/**
	 * This function checks if what was recieved was a schema.
	 * @param message Information received.
	 * @return Schema found or not.
	 */
	public boolean isSchema(String message)
	{
		//String schemaTagOpen = "<schema>", schemaTagClose = "</schema>";
		
		CVM_Debug.getInstance().printDebugMessage("SkypeAdapterData: isSchema Called.");
		//Check for schema delimiters.
		String tempMess = message;
		tempMess = tempMess.toUpperCase();
		if(tempMess.contains(this.SchemaTagOpen) && tempMess.contains(this.SchemaTagClose))
		{
			CVM_Debug.getInstance().printDebugMessage("SkypeAdapterData - isSchema: Yes");		
			return true;
		}
		return false;
	}
	
	/**
	 * Private this method handles all the schemas that have been
	 * received and have yet to be sent to the upper layers.
	 * @throws InterruptedException 
	 *
	 */
	public synchronized void handledReceviedSchemas() throws InterruptedException
	{
		String temp = null;
		if(m_SchemasRecevied.isEmpty())
		{
			CVM_Debug.getInstance().printDebugMessage("No Schemas= Waiting now.");
			wait();
		}
		else
		{
			
			CVM_Debug.getInstance().printDebugMessage("Dealing With Schemas");
			
			while(!m_SchemasRecevied.isEmpty())
			{
				temp = m_SchemasRecevied.remove(0);
				dealWithSchema(temp);
				CVM_Debug.getInstance().printDebugMessage("Schema :" +temp+" \n Hendled");
				CVM_Debug.getInstance().printDebugMessage("Schema List Size: "+m_SchemasRecevied.size());
			}
			CVM_Debug.getInstance().printDebugMessage("Schema List Size: "+m_SchemasRecevied.size());
		}
	}
	
	/**
	 * This function notifies NCB of a schema received event.
	 * @param schema schema file.
	 */
	private void dealWithSchema(String schema)
	{
		CVM_Debug.getInstance().printDebugMessage("SkypeAdapterData: dealWithSchema Called.");
        SchemaReceived_Event event = new SchemaReceived_Event(this, schema);
		adapter.notifyEvent(event);
		//Remove Tags.
		//Code HERE.
		
		//Notify with the schema received.
		//NCBEventObjectManager.Istance().notifySchemaReceivedEvent(schema);;
		
	}
	
	

}
