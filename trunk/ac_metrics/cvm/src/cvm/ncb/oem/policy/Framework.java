package cvm.ncb.oem.policy;

import java.util.HashMap;

public class Framework implements Comparable<Framework>{

	private String frameworkName;
	private HashMap<String,Feature> featureList;
	
	private boolean available;
	
	public Framework(String name) {
		frameworkName = name;
		featureList = new HashMap<String,Feature>();
		restore();
	}
	
	public String getFrameworkName() {
		return frameworkName;
	}
	
	public HashMap<String,Feature> getFeatureList() {
		return featureList;
	}
	
	public void addFeature(Feature feat){
		featureList.put(feat.getFeatureName(),feat);
	}
	
	public String toString(){
		return frameworkName;
	}

	public int compareTo(Framework other) {
		return this.frameworkName.compareTo(other.frameworkName);
	}

	public boolean isAvailable() 
	{ return this.available; }
	
	public void fail() 
	{
		this.available=false;
	}
	
	public void restore() 
	{
		this.available=true;
	}
}
