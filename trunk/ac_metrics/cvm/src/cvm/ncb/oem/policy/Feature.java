package cvm.ncb.oem.policy;

import java.util.HashSet;
import java.util.Iterator;
import cvm.model.*;

public class Feature {

	private String featureName;
	private HashSet <Attribute> attributeList;
	private HashSet <Feature> subFeatureList;
	private Feature parentFeature;
	
	public static final int EQ = 0;
	public static final int LEQ = -1;
	public static final int GEQ = 1;


	public static final int LT = -2;
	public static final int GT = 2;
	
	public Feature(String fn, HashSet<Attribute> aList, HashSet<Feature> fList, Feature parent) {
		featureName = fn;
		attributeList = aList;
		subFeatureList = fList;
		parentFeature = parent;
	}
	
	public Feature(String fn) {
		featureName = fn;
		attributeList = new HashSet<Attribute>();
		subFeatureList = new HashSet<Feature>();
		parentFeature = null;
	}
	
	public HashSet getAttributeList() {
		return attributeList;
	}
	
	public HashSet getSubFeatureList() {
		return subFeatureList;
	}
	
	public String getFeatureName() {
		return featureName;
	}
	
	
	public void addAttribute(Attribute attr) {
		attributeList.add(attr);
	}

	public void addSubFeature(Feature feat) {
		subFeatureList.add(feat);
	}

	public boolean hasAttribute(String attr) {
		return attributeList.contains(attr);
	}
	
	public Attribute getAttribute(String attr){
		Iterator<Attribute> itr = attributeList.iterator();
		while (itr.hasNext()){
			Attribute att = itr.next();
			if(att.attributeName.equals(attr)) return att;
		}
		return null;
	}
	
	public Feature getSubFeature(String feat){
		Iterator<Feature> itr = subFeatureList.iterator();
		Feature fea;
		while (itr.hasNext()){
			fea = itr.next();
			if(fea.featureName.equals(feat)) return fea;
		}
		return null;
	}

	public boolean hasAttributeValue(String attrName, int val, int opcode){
		Attribute attr = getAttribute(attrName);
		if(attr == null) return false;
		int attrVal; 
		try {
			attrVal = Integer.parseInt(attr.attributeValue);
		}catch(NumberFormatException e0){
			CVM_Debug.getInstance().printDebugMessage("Attribute value is not an integer!! "
					+attr.attributeValue);
			return false;
		}
		
		CVM_Debug.getInstance().printDebugMessage("@@@@@@@@@@@@@@@@@@@@@@@@@");
		CVM_Debug.getInstance().printDebugMessage("The integer comparison is " +val+ " "+ opcode +" "+attrVal);
		switch(opcode){
			case EQ: return val == attrVal;
			case LEQ: return val <= attrVal;
			case GEQ: return val >= attrVal;
			default: CVM_Debug.getInstance().printDebugMessage("Invalid opcode!!"); return false;
		}
	}

	public boolean hasAttributeValue(String attrName, boolean val, int opcode){
		CVM_Debug.getInstance().printDebugMessage("Finding attr "+attrName);
		Attribute attr = getAttribute(attrName);
		if(attr == null) return false;
		CVM_Debug.getInstance().printDebugMessage("Found attribute");
		boolean attrVal; 
		attrVal = Boolean.parseBoolean(attr.attributeValue);
		if (attrVal == val) return true;
		else return false;
	}

	public boolean hasAttributeValue(String attrName, String val, int opcode){
		CVM_Debug.getInstance().printDebugMessage("here in string");
		Attribute attr = getAttribute(attrName);
		if(attr == null) return false;
		return attr.attributeValue.equals(val);
	}
	
	public String toString()
	{
		String str= "Feature: "+ this.getFeatureName()+ " ( ";
		Iterator<Attribute> attrIterator = this.attributeList.iterator();
		while (attrIterator.hasNext())
			str= str +  ((Attribute)attrIterator.next()).attributeName+" ";
		str = str + " )  Parent: "+ ((this.getParentFeature()!= null)? this.getParentFeature().getFeatureName():"") ;

		if (this.subFeatureList.size()>0)
			str= str +" has the following "+this.subFeatureList.size() +" children\n";
		
		Iterator<Feature> featIterator = this.subFeatureList.iterator();
		while(featIterator.hasNext())
			str = str  +"\t"+((Feature)featIterator.next()).toString()+"\n";
		
		
		return str;
	}
	
	public Feature getParentFeature ()
	{
		return parentFeature;
	}
	
	public void setParent(Feature parent)
	{
		parentFeature = parent;
	}
}
