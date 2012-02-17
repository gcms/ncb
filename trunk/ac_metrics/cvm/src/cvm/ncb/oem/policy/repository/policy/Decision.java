package cvm.ncb.oem.policy.repository.policy;

public class Decision {
	private String param;
	private String operation;
	private String value;
	
	public Decision(String par, String anOperaiton, String val)
	{
		param=par;
		operation= anOperaiton; 
		value=val;
	}
	
	public String getParam()
	{
		return param;
	}
	
	public String getOperation()
	{
		return operation;
	}
	
	public String getValue()
	{
		return value;
	}


}
