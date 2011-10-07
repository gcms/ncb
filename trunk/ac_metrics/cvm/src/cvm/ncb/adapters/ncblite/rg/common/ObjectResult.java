/* @author Mario J Lorenzo */ package cvm.ncb.adapters.ncblite.rg.common;


public class ObjectResult
{
	
	private static ObjectResult result;
	
	private Object response;
	
	private Object mutex;
	
	
	
	private ObjectResult()
	{
		mutex = new Object();
	}
	
	public static ObjectResult getInstance()
	{
		if(result != null)
			return result;
		
		result = new ObjectResult();
		return result;
		
	}
	
	public boolean ready()
	{

		synchronized(mutex)
		{
			
			return response != null? true : false;
			
		}
		
	}
	
	public void setResponse(Object o)
	{		
		synchronized(mutex)
		{
			
			response = o;
			
		}
		
	}
	
	public Object getResponse()
	{
		synchronized(mutex)
		{
			return response;
		}
		
	}
	

}
