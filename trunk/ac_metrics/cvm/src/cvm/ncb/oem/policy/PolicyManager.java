package cvm.ncb.oem.policy;

import cvm.model.CVM_Debug;
import cvm.ncb.repository.FilePolicyRepository;
import cvm.ncb.repository.loader.FilePolicyLoader;
import cvm.ncb.repository.policy.Policy;
import cvm.ncb.repository.policy.PolicyRepository;
import org.apache.commons.lang3.StringUtils;

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

        public void addDesiredFeature(String feat, String attr, Object val, int opcode) {
            Map param = new HashMap();
            param.put("feature", feat);
            param.put("attribute", attr);
            param.put("value", val);
            param.put("operation", opcode);
            paramList.add(param);
        }
    }

    public TreeSet<Metadata> getConformingObjects(TreeSet<Metadata> availMetadatas, String request, String operation, Map<String, Object> paramValues) {
        DesiredFeaturesList req = buildDesiredFeatureList(request, operation, paramValues);

        return reduceSet(availMetadatas, req);
    }

    private DesiredFeaturesList buildDesiredFeatureList(String request, String operation, Map<String, Object> paramValues) {
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
