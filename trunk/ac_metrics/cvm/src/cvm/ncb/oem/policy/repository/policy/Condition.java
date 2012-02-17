package cvm.ncb.oem.policy.repository.policy;

public class Condition {
	private String feature;
	private String operation;
	private String literal;
	
	public Condition(String aFeature, String anOperation, String anLiteral) {
		feature = aFeature;
		operation=anOperation;
		literal=anLiteral;
	}
	
	public String getFeature()
	{
		return feature;
	}
	
	public String getOperation()
	{
		return operation;
	}
	
	public String getLiteral()
	{
		return literal;
	}
	
}
