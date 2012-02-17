package cvm.ncb.oem.policy.repository.policy;

public class BusinessValue {
	private String businessGroup;
	private String value;
	
	public BusinessValue(String bgroup, String v)
	{
		businessGroup=bgroup;
		value=v;
	}
	
	public String getBusinessGroup()
	{
		return businessGroup;
	}
	
	public String getValue()
	{
		return value;
	}
	
}
