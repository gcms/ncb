/* @author Mario J Lorenzo */ package cvm.ncb.adapters.ncblite.rg.repository.session;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import cvm.model.*;

public class Session 
{
	
	private static boolean TRACE = true;
	
	//INSTANCE VARS
	private List userSessions;
	private Map userHosts;
								
	int sipKeyPairCount = 0;
	
	//STATIC VARS
	private static Session target;
	
	
	public static Session getInstance()
	{
		
		if(target == null) target = new Session();
		
		return target;
	}
	
	private Session()
	{
		trace();
		userSessions = new LinkedList();
		userHosts = new HashMap();
	}

	//User related functions
	
	public void setUserNotActive(String userName)
	{
		trace();
		userSessions.remove(userName);
		userHosts.remove(userName);
	}
	
	public void setUserActive(String userName)
	{
		trace();
		userSessions.add(userName);
	}
	
	public void setUserHost(String userName, String host) {
		userHosts.put(userName, host);
	}
	
	public String getUserHost(String userName) {
		
		return (String)userHosts.get(userName);
		
	}
	
	public boolean isActive(String userName)
	{
		trace();
		return userSessions.contains(userName);	
	}
	
	
	public String[] getSipKeyPair()
	{
		sipKeyPairCount++;
		
		if(sipKeyPairCount % 2 == 0) {
			return new String[]{"IPCOMM01","IPCOMM02"};
		} else {
			return new String[]{"IPCOMM02","IPCOMM01"};
		}
		

	}

	//general functions
	
	public void kill()
	{
		trace();
		target = null;
	}
	
	public void trace()
	{
		if(TRACE)
		{
			StackTraceElement[] stk = new Throwable().getStackTrace();
		
			CVM_Debug.getInstance().printDebugMessage(stk[1].getClassName()+" "+stk[1].getMethodName()+"() "+stk[1].getLineNumber());
		}
		
	}
	
	public void trace(String msg)
	{
		if(TRACE)
		{
			StackTraceElement[] stk = new Throwable().getStackTrace();
		
			CVM_Debug.getInstance().printDebugMessage(stk[1].getClassName()+" "+stk[1].getMethodName()+"() "
					+stk[1].getLineNumber()+": "+msg);
		}
	
	}
	
}
