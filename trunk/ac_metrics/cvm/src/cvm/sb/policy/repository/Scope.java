package cvm.sb.policy.repository;

public class Scope {
	private String service;
	private String operation;
	private String active;
	
	public Scope (String aService, String anOperation, String isActive)
	{
		service=aService;
		operation=anOperation;
		active = isActive;
	}
	public String getService()
	{
		return service;
	}
	
	public String getOperation()
	{
		return operation;
	}
	
	public String getActive()
	{
		return active;
	}

}
