/* @author Mario J Lorenzo */ package cvm.ncb.adapters.ncblite.rg.common;

import java.util.Random;

import org.mr.api.blocks.ScalableDispatcher;
import org.mr.api.blocks.ScalableFactory;
import org.mr.api.blocks.ScalableHandler;
import org.mr.api.blocks.ScalableStage;

public class ObjectDispatcher
{
	
	private ScalableStage toDBEngine;
	
	ScalableDispatcher fromDBEngine;
	
	String comKey;

	public ObjectDispatcher()
	{
		comKey = new Random().nextInt()+".fromDBEngine";
		
		toDBEngine = ScalableFactory.getStage("toDBEngine", true);

		fromDBEngine = ScalableFactory.getDispatcher(comKey, true);
		
		fromDBEngine.addHandler(new ScalableHandler()
		{
			public void handle(Object arg0)
			{
				ObjectResult.getInstance().setResponse(arg0);
			}			
		});
		
	}
	
	public Object callDB(String cmd, Object o) throws Exception
	{
		
		toDBEngine.queue(new Transaction(cmd,o,comKey));
		
		ObjectResult objectResult = ObjectResult.getInstance();
		
		while(!ObjectResult.getInstance().ready());
		
		Transaction theTransaction = (Transaction)objectResult.getResponse();	
		
		if(theTransaction.errors)
		{
			throw new Exception(theTransaction.errorMsg);
		}	
		
		return theTransaction.response;
	}
	
	public void disconnect()
	{
		System.exit(-1);
	}

}
