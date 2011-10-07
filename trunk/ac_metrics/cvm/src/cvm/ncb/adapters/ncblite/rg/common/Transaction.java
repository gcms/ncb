/* @author Mario J Lorenzo */ /* @author Mario J Lorenzo */ package cvm.ncb.adapters.ncblite.rg.common;

import java.io.Serializable;

public class Transaction implements Serializable
{

	public String cmd = "";
	public Object parms;

	public boolean errors = false;
	public String errorMsg = "";
	public Object response;
	
	public String responsePipe;
	
	
	public Transaction(String cmd, Object payload, String pipe)
	{
		super();
		this.cmd = cmd;
		this.parms = payload;
		this.responsePipe = pipe;
	}
	
	public String toString()
	{
		String str = "";
		
		str += "Commmand: "+cmd;
		
		str += "| Error?: "+errors;
		
		if(errors)
			str += "| Error Msg: "+errorMsg;
		
		else
			str += "| Response: " + response.toString();
		
		
		return str;
		
	}
	
	
}
