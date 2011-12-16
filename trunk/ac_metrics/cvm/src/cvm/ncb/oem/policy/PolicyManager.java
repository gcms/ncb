package cvm.ncb.oem.policy;

import cvm.model.CVM_Debug;
import cvm.ncb.repository.NCB_R_Facade;
import cvm.ncb.repository.loader.FilePolicyLoader;
import cvm.ncb.repository.loader.GlobalConstant;
import cvm.ncb.repository.policy.NCBPolicy;

import java.net.URISyntaxException;
import java.util.*;

public class PolicyManager {
	static Feature featTree=null;
	private boolean hasFeature(Collection<Feature> inSet, String feature, String attr,
			String value,int opcode){

		String superFeatures = getSupperFeatures(feature);
		//If feature name is missing then all frameworks satisfy policy
		if(feature == null|| feature.equals("")) return true;
		Scanner scan;
		
		//See if policy is satisfied by each framework 
		for(Feature feat: inSet) {
			CVM_Debug.getInstance().printDebugMessage("Current feature is: "+feat.getName()+
					"\nLooking for Feature name is: "+feature+" Super feature: "+superFeatures);

			// This is not a subfeature, so do checks at this level
			if(superFeatures==null || superFeatures.equals("CommFeatureRoot ")){

				// Is this the feature we want?
				if(feat.getName().equals(feature)) { // does this feature exist

					// found the feature, evaluate desired value vs framwork's reported value
					if(feat.hasAttributeValue(attr, value, opcode)) // 
						return true;
				}
			}
			else {
				// We have a subfeature
				scan  = new Scanner(superFeatures);
				while (scan.hasNext())
				{
					if(feat.getName().equals(scan.next())) { // does this feature exist
						CVM_Debug.getInstance().printDebugMessage("Found superfeature of the requested feature");
						return hasSubFeature(feat,feature,attr,value,opcode,scan); 
					}
				}
			}
		}
		return false;
	}

	private boolean hasFeature(Collection<Feature> inSet, String feature, String attr,
			int value, int opcode) {
		String superFeatures = getSupperFeatures(feature);
		if(feature == null|| feature.equals("")) return true;
		Scanner scan;
		for(Feature feat: inSet) {
			CVM_Debug.getInstance().printDebugMessage("Current feature is: "+feat.getName()+
					"\nLooking for Feature name is: "+feature+" Super feature: "+superFeatures);
			if(superFeatures==null || superFeatures.equals("CommFeatureRoot ")){
				if(feat.getName().equals(feature)) { // does this feature exist
					if(feat.hasAttributeValue(attr, value, opcode)) // 
						return true;
				}
			}
			else {
				scan = new Scanner(superFeatures);
				while (scan.hasNext())
				{
					if(feat.getName().equals(scan.next())) { // does this feature exist
						CVM_Debug.getInstance().printDebugMessage("Found superfeature of the requested feature");
						return hasSubFeature(feat,feature,attr,value,opcode,scan); 
					}
				}
			}
		}
		return false;
	}

	private boolean hasFeature(Collection<Feature> inSet, String feature, String attr,
			boolean value, int opcode) {
		String superFeatures = getSupperFeatures(feature);
		Scanner scan;
		if(feature == null|| feature.equals("")) return true;
		for(Feature feat: inSet) {
			CVM_Debug.getInstance().printDebugMessage("Current feature is: "+feat.getName()+
					"\nLooking for Feature name is: "+feature+" Super feature: "+superFeatures);
			if(superFeatures==null || superFeatures.equals("CommFeatureRoot ")){
				if(feat.getName().equals(feature)) { // does this feature exist
					if(feat.hasAttributeValue(attr, value, opcode)) // 
						return true;
				}
			}
			else {
				scan  = new Scanner(superFeatures);
				while (scan.hasNext())
				{
					if(feat.getName().equals(scan.next())) { // does this feature exist
						CVM_Debug.getInstance().printDebugMessage("Found superfeature of the requested feature");
						return hasSubFeature(feat,feature,attr,value,opcode,scan); 
					}
				}	
			}
		}
		return false;
	}

	private boolean hasSubFeature(Feature feat, String feature, String attr, int value,
			int opcode, Scanner superFeature) {
		if(feat == null) return false;
		if(superFeature.hasNext()){
			return hasSubFeature(feat.getSubFeature(superFeature.next()), feature, attr, 
					value, opcode, superFeature);
		}
		if(feat.getSubFeature(feature).hasAttributeValue(attr, value, opcode)) // 
				return true;
		return false;
	}

	private boolean hasSubFeature(Feature feat, String feature, String attr, boolean value,
			int opcode, Scanner superFeature) {
		if(feat == null) return false;
		CVM_Debug.getInstance().printDebugMessage("Current feature is: "+feat.getName()+
				"\nLooking for Feature name is: "+feature);
		if(superFeature.hasNext()){
			return hasSubFeature(feat.getSubFeature(superFeature.next()), feature, attr, 
					value, opcode, superFeature);
		}
		try {
			if(feat.getSubFeature(feature).hasAttributeValue(attr, value, opcode)) // 
				return true;
		}catch(NullPointerException e0) {
			return false;
		}
		return false;
	}

	private boolean hasSubFeature(Feature feat, String feature, String attr, String value,
			int opcode, Scanner superFeature){
		if(feat == null) return false;
		if(superFeature.hasNext()){
			return hasSubFeature(feat.getSubFeature(superFeature.next()), feature, attr, 
					value, opcode, superFeature);
		}
		if(feat.getSubFeature(feature).hasAttributeValue(attr, value, opcode)) // 
			return true;
		return false;
	}	

	public Set reduceSet(TreeSet<Framework> inSet, DesiredFeaturesList reqFeats){
		return reduceSet(inSet,reqFeats.paramList.iterator());
	}

	public TreeSet reduceSet(TreeSet<Framework> inSet, Iterator<ArrayList> req){
		
		if(!req.hasNext()) {
			CVM_Debug.getInstance().printDebugMessage("Done reducing!!!!!");
			return inSet;
		}
		TreeSet<Framework> outSet = new TreeSet<Framework>();
		Iterator param = req.next().iterator();
		String reqFeat=null; if(param.hasNext())reqFeat = (String)param.next();
		String attr=null ; if(param.hasNext()) attr = (String)param.next();
		Object o_val=null; if(param.hasNext()) o_val = param.next();
		int opcode=0; if(param.hasNext()) opcode = ((Integer)(param.next())).intValue();
		for(Framework fw: inSet) {
			CVM_Debug.getInstance().printDebugMessage("Framework name is "+fw.getName());
			if(o_val.getClass().getName().equals(java.lang.String.class.getName())){
				if(hasFeature(fw.getFeatures().values(),reqFeat,attr,(String)o_val,
						opcode))
					outSet.add(fw);
			}
			else if(o_val.getClass().getName().equals(java.lang.Boolean.class.getName())){
				if(hasFeature(fw.getFeatures().values(),reqFeat,attr,((Boolean)o_val).booleanValue(),
					opcode))
				outSet.add(fw);
			}
			else if(o_val.getClass().getName().equals(java.lang.Integer.class.getName())){
				if(hasFeature(fw.getFeatures().values(),reqFeat,attr,((Integer)o_val).intValue(),
					opcode))
				outSet.add(fw);
			}
		}
		return reduceSet(outSet,req);
	}

	public List orderSet(Set<Framework> inSet, DesiredFeaturesList optFeats){
		ArrayList<Framework> outList = new ArrayList<Framework>();
		outList.addAll(inSet);
		return orderSet(outList,optFeats.paramList.iterator());
	}

	public ArrayList<Framework> orderSet(ArrayList<Framework> inList, Iterator<ArrayList> opt){
		if(!opt.hasNext()) return inList;
		ArrayList<Framework> outList = new ArrayList<Framework>();
		Iterator param = opt.next().iterator();
		String reqFeat=null; if(param.hasNext())reqFeat = (String)param.next();
		String attr=null ; if(param.hasNext()) attr = (String)param.next();
		Object o_val=null; if(param.hasNext()) o_val = param.next();
		int opcode=0; if(param.hasNext()) opcode = ((Integer)(param.next())).intValue();
		for(Framework fw: inList) {
			CVM_Debug.getInstance().printDebugMessage("Framework name is "+fw.getName());
			if(o_val.getClass().getName().equals(java.lang.String.class.getName())){
				if(hasFeature(fw.getFeatures().values(),reqFeat,attr,(String)o_val,
					opcode))
					outList.add(fw);
			}
			else if(o_val.getClass().getName().equals(java.lang.Boolean.class.getName())){
				if(hasFeature(fw.getFeatures().values(),reqFeat,attr,((Boolean)o_val).booleanValue(),
					opcode))
					outList.add(fw);
			}
			else if(o_val.getClass().getName().equals(java.lang.Integer.class.getName())){
				if(hasFeature(fw.getFeatures().values(),reqFeat,attr,((Integer)o_val).intValue(),
					opcode))
					outList.add(fw);
			}
		}
		for(Framework fw: inList) {
			if(!outList.contains(fw)) {
				outList.add(fw);
			}
		}
		outList = orderSet(outList,opt);
		return outList;
	}

	private class DesiredFeaturesList {
		ArrayList<ArrayList> paramList;
		
		public DesiredFeaturesList() {
			paramList = new ArrayList<ArrayList>();
		}

		public void addDesiredFeature(String feat, String attr, int val, 
				int opcode) {
			ArrayList param = new ArrayList();
			param.add(feat);
			param.add(attr);
			param.add(new Integer(val));
			param.add(opcode);
			paramList.add(param);
		}

		public void addDesiredFeature(String feat, String attr, boolean val, 
				int opcode) {
			ArrayList param = new ArrayList();
			param.add(feat);
			param.add(attr);
			param.add(new Boolean(val));
			param.add(opcode);
			paramList.add(param);
		}

		public void addDesiredFeature(String feat, String attr, String val, 
				int opcode) {
			ArrayList param = new ArrayList();
			param.add(feat);
			param.add(attr);
			param.add(val);
			param.add(opcode);
			paramList.add(param);
		}
	}
	
	public static void main(String args[]) {
		PolicyManager pm = new PolicyManager();
		TreeSet<Framework> hs = new TreeSet<Framework>();
		
		// Frameworks that are available
		Framework fw1 = new Framework("fw1");
		Feature feat1 = new Feature("Audio");
		feat1.addAttribute(new Attribute("Enabled", "true"));
		feat1.addAttribute(new Attribute("NumberOfUsers","5")); 
		
		Feature feat2 = new Feature("Video");
		feat2.addAttribute(new Attribute("Enabled", "true"));
		feat2.addAttribute(new Attribute("NumberOfUsers", "3"));
		feat2.addAttribute(new Attribute("onlineStatus.Enabled", "true"));
	
		fw1.addFeature(feat1);
		fw1.addFeature(feat2);

		Framework fw2 = new Framework("fw2");
		Feature feat5 = new Feature("Audio");
		feat5.addAttribute(new Attribute("Enabled", "true"));
		feat5.addAttribute(new Attribute("NumberOfUsers","4"));
		
		fw2.addFeature(feat5);

		Framework fw3 = new Framework("fw3");
		Feature feat7 = new Feature("Video");
		feat7.addAttribute(new Attribute("Enabled", "true"));
		feat7.addAttribute(new Attribute("NumberOfUsers","7"));
		feat7.addAttribute(new Attribute("onlineStatus.Enabled", "true"));
		fw3.addFeature(feat7);

		Framework fw4 = new Framework("fw4");
		Feature feat6 = new Feature("Audio");
		feat6.addAttribute(new Attribute("Enabled", "true"));
		feat6.addAttribute(new Attribute("NumberOfUsers","7"));
		
		fw4.addFeature(feat6);

		// The subfeature of a feature
		Feature feat3 = new Feature("PC2Phone");
		feat3.addAttribute(new Attribute ("Enabled","true"));
		feat3.addAttribute(new Attribute("Payment","0"));
		
		
	//	Feature feat4 = new Feature("Payment");
		Feature feat8 = new Feature("PC2Phone");
		feat8.addAttribute(new Attribute ("Enabled","true"));
		feat8.addAttribute(new Attribute("Payment","2"));
		
	//	Feature feat9 = new Feature("Payment");
		feat1.addSubFeature(feat3);
		feat5.addSubFeature(feat8);
		feat6.addSubFeature(feat8);


		// Attributes represent the field and value as stated in the framework
	
		// The feature of each framework
/*		fw1.addFeature(feat1);
		fw1.addFeature(feat2);
		fw2.addFeature(feat5);
		fw3.addFeature(feat7);
		fw4.addFeature(feat6);
		fw4.addFeature(feat2);
*/		
		// Available for NCB
		hs.add(fw1);
		hs.add(fw2);
		hs.add(fw3);
		hs.add(fw4);
		
		/*
		 * Desired features can be required or optional
		 * Required - if the feature is unavailable in the framework, remove framework
		 * Optional - rank the set of framework based on the availablity of feature
		 * Policies will aid in the creation of these
		 * If a policy is satified by a framework, keep the framework
		 * In the case of ordering, do the same but add the rest at the end of the 
		 * list in order.
		 */
/*		DesiredFeaturesList req = pm.new DesiredFeaturesList();
		DesiredFeaturesList opt = pm.new DesiredFeaturesList();
		
		NCB_R_Facade ncb_facade = NCB_R_Facade.getInstance(FilePolicyLoader.getInstance("C:/Documents and Settings/ywu001/workspace/Self_Config/src/cvm/ncb/repository/examples/"));
		featTree =ncb_facade.loadFeatures("/featureTree.xml");
		
		CVM_Debug.getInstance().printDebugMessage("~~~~~~~~~~~~");
		String p =pm.getSupperFeatures("PC2Phone");
		CVM_Debug.getInstance().printDebugMessage("Parent is "+p);
		
		
		ArrayList<NCBPolicy> arr1 = ncb_facade.load(GlobalConstant.RequestedType.Audio, GlobalConstant.OperationType.request);
		NCBPolicy p1 = arr1.get(0);
		
		ArrayList<NCBPolicy> arr2 = ncb_facade.load(GlobalConstant.RequestedType.PC2Phone, GlobalConstant.OperationType.request);
		NCBPolicy p2 = arr2.get(0);
		NCBPolicy p3 = arr2.get(1);		
		
		int opcode1 = convertStringToInt(p1.getDecision().getOperation());
		int opcode2 = convertStringToInt(p2.getDecision().getOperation()); 
		int opcode3 = convertStringToInt(p3.getDecision().getOperation()); 
		
	
		if (opcode1>=100)
			CVM_Debug.getInstance().printDebugMessage("Unrecognizable operator "+ p1.getDecision().getOperation());
		if (opcode2>=100)
			CVM_Debug.getInstance().printDebugMessage("Unrecognizable operator "+ p2.getDecision().getOperation());
		if (opcode3>=100)
			CVM_Debug.getInstance().printDebugMessage("Unrecognizable operator "+ p3.getDecision().getOperation());
		
		req.addDesiredFeature(p1.getCondition().getFeature(),  p1.getDecision().getParam(), 5, opcode1 );
	    req.addDesiredFeature(p2.getCondition().getFeature(),  p2.getDecision().getParam(), true, opcode2);
	    req.addDesiredFeature(p3.getCondition().getFeature(),  p3.getDecision().getParam(), 2, opcode3);
		
	   
	    CVM_Debug.getInstance().printDebugMessage(pm.reduceSet(hs, req));*/
	
	    //	req.addDesiredFeature("Audio", "NumberOfUsers", 5, Feature.LEQ, null);
		//  opt.addDesiredFeature("PC2Phone", "Enabled", true, Feature.EQ, "Audio");*/
		//	req.addDesiredFeature("PC2Phone", "Enabled", true, Feature.EQ, "Audio");
		//	req.addDesiredFeature("Payment", "Enabled", true, Feature.EQ, "Audio PC2Phone");
			
		//	opt.addDesiredFeature("Payment", "Enabled", true, Feature.EQ, "Audio PC2Phone");
		//	CVM_Debug.getInstance().printDebugMessage(pm.orderSet(pm.reduceSet(hs, req),opt));
			
		PolicyManager pm2 = new PolicyManager();
		TreeSet<Framework> set = pm2.getFrameworkSet(hs, GlobalConstant.RequestedType.Audio, GlobalConstant.OperationType.request,5);
		
		//CVM_Debug.getInstance().printDebugMessage(set);
	//	CVM_Debug.getInstance().printDebugMessage(pm2.getFrameworkSet(set,GlobalConstant.RequestedType.PC2Phone, GlobalConstant.OperationType.request, 2));
		
	}
	
	public TreeSet<Framework> getFrameworkSet(TreeSet<Framework> availFrameworks, GlobalConstant.RequestedType request, GlobalConstant.OperationType operation, Object obj)
	{
		DesiredFeaturesList req = new DesiredFeaturesList();
		DesiredFeaturesList opt = new DesiredFeaturesList();

        FilePolicyLoader loader = null;
        try {
            loader = FilePolicyLoader.getInstance(this.getClass().getResource("../../repository/examples").toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        NCB_R_Facade ncb_facade = NCB_R_Facade.getInstance(loader);
		featTree =ncb_facade.loadFeatures("/featureTree.xml");
		
		ArrayList<NCBPolicy> arr = ncb_facade.load(request, operation);
		CVM_Debug.getInstance().printDebugMessage("Size of policyy array is "+arr.size());
		ArrayList<Object> objArr = new ArrayList<Object>();
		if (arr.size()>0)
		{
			for (int i=0; i< arr.size();i++)
			{
				if (arr.get(i).getDecision().getParam().endsWith("Enabled"))
					objArr.add(true);
				else objArr.add(obj);
			}
		}
		int opcode;
		for (int i=0; i< arr.size(); i++)
		{
			NCBPolicy thePolicy= arr.get(i);
			opcode = convertStringToInt(thePolicy.getDecision().getOperation());
			if (opcode>=100)
				CVM_Debug.getInstance().printDebugMessage("Unrecognizable operator "+ thePolicy.getDecision().getOperation());
		
			if (objArr.get(i).getClass().getName().equals(java.lang.String.class.getName()))
				req.addDesiredFeature(thePolicy.getCondition().getFeature(), thePolicy.getDecision().getParam(), (String)objArr.get(i), opcode);
			else if (objArr.get(i).getClass().getName().equals(java.lang.Boolean.class.getName()))
				req.addDesiredFeature(thePolicy.getCondition().getFeature(), thePolicy.getDecision().getParam(), (Boolean)objArr.get(i), opcode);
			else if  (objArr.get(i).getClass().getName().equals(java.lang.Integer.class.getName()))
				req.addDesiredFeature(thePolicy.getCondition().getFeature(), thePolicy.getDecision().getParam(), (Integer)objArr.get(i), opcode);
		}
		
	  //  CVM_Debug.getInstance().printDebugMessage(reduceSet(availFrameworks, req));
	    
	    TreeSet<Framework>  resultSet = (TreeSet<Framework>)reduceSet(availFrameworks, req);
	    
	    return resultSet;
	  
	}
	
	private static int convertStringToInt(String str)
	{
		if (str.compareTo("equalTo")==0)
			return Feature.EQ;
		else if (str.compareTo("greaterThanOrEqualTo")==0)
			return Feature.GEQ;
		else if (str.compareTo("lessThanOrEqualTo")==0)
			return Feature.LEQ;
		else if (str.compareTo("lessThan")==0)
			return Feature.LT;
		else if (str.compareTo("greaterThan")==0)
			return Feature.GT;
		else return 100;
			
	}

	private Feature foundFeature=null;
	private  String getSupperFeatures(String feature)
    {
    	String parentFeature =new String() ;
    	depthFirstSearchWithoutReturn(featTree, feature);  // two algorithms for depth first search
    	//foundFeature = depthFirstSearchWithReturn(featTree, feature);
    	while (foundFeature.getParentFeature()!=null)
    	{
    		parentFeature = foundFeature.getParentFeature().getName()+" "+parentFeature;
    		foundFeature = foundFeature.getParentFeature();
    	}
    	return parentFeature;
    }
	
	private  void depthFirstSearchWithoutReturn(Feature treeNode, String feature)
	{	
		if (treeNode.getName().compareTo(feature)==0)
			foundFeature = treeNode;
		if(treeNode.getSubFeatures().size()>0)
		{
			Iterator<Feature> iterator= treeNode.getSubFeatures().iterator();
			while (iterator.hasNext())
			{
				Feature feat = iterator.next();
				depthFirstSearchWithoutReturn(feat, feature);
			}
		}
	}
	
	private Feature depthFirstSearchWithReturn(Feature treeNode, String feature)
	{
		
		if (treeNode.getName().equals(feature))
			return treeNode;
		Feature foundFeature = null;
		Iterator<Feature> it = treeNode.getSubFeatures().iterator();

		while(it.hasNext())
		{

			foundFeature=depthFirstSearchWithReturn(it.next(), feature);
			if (foundFeature!=null)
				break;
		}
		return foundFeature;
	}
}
