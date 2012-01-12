package cvm.ncb.oem.policy;

import cvm.model.CVM_Debug;
import cvm.ncb.repository.FilePolicyRepository;
import cvm.ncb.repository.loader.FilePolicyLoader;
import cvm.ncb.repository.loader.GlobalConstant;
import cvm.ncb.repository.policy.Policy;
import cvm.ncb.repository.policy.PolicyRepository;
import org.apache.commons.lang3.StringUtils;

import java.net.URISyntaxException;
import java.util.*;

public class PolicyManager {
    private static final String ROOT_FEATURE = "CommFeatureRoot ";
    private PolicyRepository policyRepository;
    private Feature featTree;

    public PolicyManager(FilePolicyLoader loader) {
        this(FilePolicyRepository.createInstance(loader));
    }

    public PolicyManager(PolicyRepository policyRepository) {
        this.policyRepository = policyRepository;
    }

    private boolean hasFeature(Collection<Feature> inSet, String feature, String attr,
                               Object value, int opcode) {
        //If feature name is missing then all frameworks satisfy policy
        if (StringUtils.isEmpty(feature))
            return true;

        String superFeatures = getSupperFeatures(feature);

        //See if policy is satisfied by each framework
        for (Feature feat : inSet) {
            CVM_Debug.getInstance().printDebugMessage("Current feature is: " + feat.getName() +
                    "\nLooking for Feature name is: " + feature + " Super feature: " + superFeatures);

            // This is not a subfeature, so do checks at this level
            if (superFeatures == null || superFeatures.equals(ROOT_FEATURE)) {

                // Is this the feature we want?
                if (feat.getName().equals(feature)) { // does this feature exist
                    // found the feature, evaluate desired value vs framwork's reported value
                    if (feat.hasAttributeValue(attr, value, opcode)) //
                        return true;
                }
            } else {
                // We have a subfeature
                Scanner scan = new Scanner(superFeatures);
                while (scan.hasNext()) {
                    if (feat.getName().equals(scan.next())) { // does this feature exist
                        CVM_Debug.getInstance().printDebugMessage("Found superfeature of the requested feature");
                        return hasSubFeature(feat, feature, attr, value, opcode, scan);
                    }
                }
            }
        }
        return false;
    }

    private boolean hasSubFeature(Feature feat, String feature, String attr, Object value,
                                  int opcode, Scanner superFeature) {
        if (feat == null)
            return false;

        if (superFeature.hasNext()) {
            return hasSubFeature(feat.getSubFeature(superFeature.next()), feature, attr,
                    value, opcode, superFeature);
        }

        return feat.getSubFeature(feature).hasAttributeValue(attr, value, opcode);
    }

    public TreeSet<Metadata> reduceSet(TreeSet<Metadata> inSet, DesiredFeaturesList reqFeats) {
        return reduceSet(inSet, reqFeats.paramList.iterator());
    }

    public TreeSet<Metadata> reduceSet(TreeSet<Metadata> inSet, Iterator<Map> req) {
        if (!req.hasNext()) {
            CVM_Debug.getInstance().printDebugMessage("Done reducing!!!!!");
            return inSet;
        }

        TreeSet<Metadata> outSet = new TreeSet<Metadata>();
        Map params = req.next();

        String reqFeat = (String) params.get("feature");
        String attr = (String) params.get("attribute");
        Object value = params.get("value");
        Integer operation = (Integer) params.get("operation");

        for (Metadata fw : inSet) {
            CVM_Debug.getInstance().printDebugMessage("Object name is " + fw.getName());

            if (hasFeature(fw.getFeatures().values(), reqFeat, attr, value, operation))
                outSet.add(fw);
        }
        return reduceSet(outSet, req);
    }

    private class DesiredFeaturesList {
        List<Map> paramList;

        public DesiredFeaturesList() {
            paramList = new ArrayList<Map>();
        }

        public void addDesiredFeature(String feat, String attr, Object val,
                                      int opcode) {
            Map param = new HashMap();
            param.put("feature", feat);
            param.put("attribute", attr);
            param.put("value", val);
            param.put("operation", opcode);
            paramList.add(param);
        }
    }

    public static void main(String args[]) throws URISyntaxException {
        FilePolicyLoader loader = FilePolicyLoader.createInstance(PolicyManager.class.getResource("../../repository/examples").toURI());

        PolicyManager pm = new PolicyManager(loader);
        TreeSet<Metadata> hs = new TreeSet<Metadata>();

        // Frameworks that are available
        Metadata fw1 = new Metadata("fw1");
        Feature feat1 = new Feature("Audio");
        feat1.addAttribute(new Attribute("Enabled", "true"));
        feat1.addAttribute(new Attribute("NumberOfUsers", "5"));

        Feature feat2 = new Feature("Video");
        feat2.addAttribute(new Attribute("Enabled", "true"));
        feat2.addAttribute(new Attribute("NumberOfUsers", "3"));
        feat2.addAttribute(new Attribute("onlineStatus.Enabled", "true"));

        fw1.addFeature(feat1);
        fw1.addFeature(feat2);

        Metadata fw2 = new Metadata("fw2");
        Feature feat5 = new Feature("Audio");
        feat5.addAttribute(new Attribute("Enabled", "true"));
        feat5.addAttribute(new Attribute("NumberOfUsers", "4"));

        fw2.addFeature(feat5);

        Metadata fw3 = new Metadata("fw3");
        Feature feat7 = new Feature("Video");
        feat7.addAttribute(new Attribute("Enabled", "true"));
        feat7.addAttribute(new Attribute("NumberOfUsers", "7"));
        feat7.addAttribute(new Attribute("onlineStatus.Enabled", "true"));
        fw3.addFeature(feat7);

        Metadata fw4 = new Metadata("fw4");
        Feature feat6 = new Feature("Audio");
        feat6.addAttribute(new Attribute("Enabled", "true"));
        feat6.addAttribute(new Attribute("NumberOfUsers", "7"));

        fw4.addFeature(feat6);

        // The subfeature of a feature
        Feature feat3 = new Feature("PC2Phone");
        feat3.addAttribute(new Attribute("Enabled", "true"));
        feat3.addAttribute(new Attribute("Payment", "0"));


        //	Feature feat4 = new Feature("Payment");
        Feature feat8 = new Feature("PC2Phone");
        feat8.addAttribute(new Attribute("Enabled", "true"));
        feat8.addAttribute(new Attribute("Payment", "2"));

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
		
		FilePolicyRepository ncb_facade = FilePolicyRepository.getInstance(FilePolicyLoader.getInstance("C:/Documents and Settings/ywu001/workspace/Self_Config/src/cvm/ncb/repository/examples/"));
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

        PolicyManager pm2 = new PolicyManager(loader);
        Map<String, Object> params = new LinkedHashMap<String, Object>();
        params.put("NumberOfUsers", 5);
        TreeSet<Metadata> set = pm2.getConformingObjects(hs, GlobalConstant.RequestedType.Audio, GlobalConstant.OperationType.request, params);

        //CVM_Debug.getInstance().printDebugMessage(set);
        //	CVM_Debug.getInstance().printDebugMessage(pm2.getFrameworkSet(set,GlobalConstant.RequestedType.PC2Phone, GlobalConstant.OperationType.request, 2));

    }

    public TreeSet<Metadata> getConformingObjects(TreeSet<Metadata> availMetadatas, GlobalConstant.RequestedType request, GlobalConstant.OperationType operation, Map<String, Object> paramValues) {
        DesiredFeaturesList req = buildDesiredFeatureList(request, operation, paramValues);

        return reduceSet(availMetadatas, req);
    }

    private DesiredFeaturesList buildDesiredFeatureList(GlobalConstant.RequestedType request, GlobalConstant.OperationType operation, Map<String, Object> paramValues) {
        DesiredFeaturesList req = new DesiredFeaturesList();

        featTree = policyRepository.loadFeatures();
        List<Policy> policies = policyRepository.load(request, operation);

        List<Object> objArr = new ArrayList<Object>();
        for (Policy policy : policies) {
            if (policy.getDecision().getParam().endsWith("Enabled"))
                objArr.add(true);
            else
                objArr.add(paramValues.get(policy.getDecision().getParam()));
        }

        for (int i = 0; i < policies.size(); i++) {
            Policy thePolicy = policies.get(i);
            int opcode = convertStringToInt(thePolicy.getDecision().getOperation());

            if (opcode >= 100)
                CVM_Debug.getInstance().printDebugMessage("Unrecognizable operator " + thePolicy.getDecision().getOperation());

            req.addDesiredFeature(thePolicy.getCondition().getFeature(), thePolicy.getDecision().getParam(), objArr.get(i), opcode);
        }

        return req;
    }

    private static int convertStringToInt(String str) {
        if (str.compareTo("equalTo") == 0)
            return Feature.EQ;
        else if (str.compareTo("greaterThanOrEqualTo") == 0)
            return Feature.GEQ;
        else if (str.compareTo("lessThanOrEqualTo") == 0)
            return Feature.LEQ;
        else if (str.compareTo("lessThan") == 0)
            return Feature.LT;
        else if (str.compareTo("greaterThan") == 0)
            return Feature.GT;
        else
            return 100;
    }

    private Feature foundFeature = null;

    private String getSupperFeatures(String feature) {
        String parentFeature = "";
        depthFirstSearchWithoutReturn(featTree, feature);  // two algorithms for depth first search
        //foundFeature = depthFirstSearchWithReturn(featTree, feature);
        while (foundFeature.getParentFeature() != null) {
            parentFeature = foundFeature.getParentFeature().getName() + " " + parentFeature;
            foundFeature = foundFeature.getParentFeature();
        }
        return parentFeature;
    }

    private void depthFirstSearchWithoutReturn(Feature treeNode, String feature) {
        if (treeNode.getName().equals(feature))
            foundFeature = treeNode;

        if (treeNode.getSubFeatures().size() > 0) {
            for (Feature feat : treeNode.getSubFeatures()) {
                depthFirstSearchWithoutReturn(feat, feature);
            }
        }
    }
}
