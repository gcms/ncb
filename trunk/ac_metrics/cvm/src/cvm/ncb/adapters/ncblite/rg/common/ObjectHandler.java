/* @author Mario J Lorenzo */ package cvm.ncb.adapters.ncblite.rg.common;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;

import org.mr.api.blocks.ScalableDispatcher;
import org.mr.api.blocks.ScalableFactory;
import org.mr.api.blocks.ScalableHandler;
import org.mr.api.blocks.ScalableStage;

import cvm.ncb.adapters.ncblite.rg.repository.Gateway;
import util.CVM_Debug;

public class ObjectHandler
{
	ScalableStage toDBEngine;
	
	ScalableDispatcher fromDBEngine;
	
	Gateway gateway = new Gateway();
	
	Class gatewayCls = gateway.getClass();

	public ObjectHandler()
	{	
		toDBEngine = ScalableFactory.getStage("toDBEngine", true);

		toDBEngine.addHandler(new ScalableHandler(){

			public void handle(Object arg0)
			{
				
				Transaction tran = (Transaction)arg0;
				
				System.out.print("[Transaction "+new Timestamp(System.currentTimeMillis())+"]" +"\t [ID: "+tran.responsePipe+"]");
								
				fromDBEngine = ScalableFactory.getDispatcher(tran.responsePipe, true);
				
				String methodName = tran.cmd;
				List parmList = (List)tran.parms;
				
				int size = parmList.size();
				
				Class[] parmTypes = new Class[size];
				Object[] parmArgs = new Object[size];
				
				Iterator itr = parmList.iterator();
				
				int pos = 0;
				
				while(itr.hasNext())
				{
					parmArgs[pos] = itr.next();
					
					parmTypes[pos] = parmArgs[pos].getClass();
					
					pos++;
				}
				
				Method methLst;
				
				try
				{
					methLst = gatewayCls.getMethod(methodName, parmTypes);
				} 
				catch (SecurityException e)
				{
					processError(tran, e);
					return;
				} 
				catch (NoSuchMethodException e)
				{
					processError(tran, e);
					return;
				}
				
				
				Object result;
				
				try
				{
					result = methLst.invoke(gateway, parmArgs);

				} 
				catch (IllegalArgumentException e)
				{
					processError(tran, e);
					return;
				} 
				catch (IllegalAccessException e)
				{
					processError(tran, e);
					return;
				} 
				catch (InvocationTargetException e)
				{
					processError(tran, e);
					return;
				}
				
				if(result instanceof Exception)
				{
					tran.errors = true;
					tran.errorMsg = ((Exception)result).getMessage();
					CVM_Debug.getInstance().printDebugMessage("\t[Failure] (0003)");
				}
				else
				{					
					tran.response = result;	
					
					System.out.print("\t [Transaction Summary: "+tran+ "]");
					
					System.out.print("\t[Success] (0004)");
					
				}
				
				CVM_Debug.getInstance().printDebugMessage("");
				
				fromDBEngine.dispatch(tran);
				
			}});
	}
	
	public void processError(Transaction tran, Exception e)
	{
		tran.errors = true;
		tran.errorMsg = e.getMessage();
		CVM_Debug.getInstance().printDebugMessage("\t[Failure] (0005)");
		fromDBEngine.dispatch(tran);
	}
	
}
