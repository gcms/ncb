package cvm.ncb.oem.policy;

import cvm.ncb.csm.Resource;
import cvm.ncb.ks.ResourceManager;
import cvm.ncb.oem.policy.repository.FilePolicyRepository;
import cvm.ncb.oem.policy.repository.loader.FilePolicyLoader;
import cvm.ncb.oem.policy.repository.policy.Policy;
import cvm.ncb.oem.policy.repository.policy.PolicyRepository;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.*;

public class PolicyManager {
    private static Log log = LogFactory.getLog(PolicyManager.class);
    private static final String ROOT_FEATURE = "CommFeatureRoot";
    private PolicyRepository policyRepository;
//    private Feature featTree;

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

        Stack<String> superFeatures = getSuperFeatures(inSet, feature);

        //See if policy is satisfied by each framework
        for (Feature feat : inSet) {
            log.debug("Current feature is [" + feat + "]");
            log.debug("Looking for feature is [" + feature + "] and super feature is [" + superFeatures + "]");

            // This is not a subfeature, so do checks at this level
            if (superFeatures.isEmpty() || superFeatures.peek().equals(ROOT_FEATURE)) {
                // Is this the feature we want?
                if (feat.getName().equals(feature)) { // does this feature exist
                    // found the feature, evaluate desired value vs framwork's reported value
                    if (feat.hasAttributeValue(attr, value, opcode)) //
                        return true;
                }
            } else {
                // We have a subfeature
                while (!superFeatures.empty()) {
                    String superFeature = superFeatures.pop();
                    if (feat.getName().equals(superFeature)) {
                        log.debug("Found super feature of the requestd type [" + superFeature + "]");
                        return hasSubFeature(feat, feature, attr, value, opcode, superFeatures);
                    }
                }
            }
        }
        return false;
    }

    private boolean hasSubFeature(Feature feat, String feature, String attr, Object value,
                                  int opcode, Stack<String> superFeatures) {
        if (feat == null)
            return false;

        if (!superFeatures.empty()) {
            String superFeature = superFeatures.pop();
            return hasSubFeature(feat.getSubFeature(superFeature), feature, attr, value, opcode, superFeatures);
        }

        return feat.getSubFeature(feature).hasAttributeValue(attr, value, opcode);
    }

    public TreeSet<Metadata> reduceSet(TreeSet<Metadata> inSet, DesiredFeaturesList reqFeats) {
        return reduceSet(inSet, reqFeats.paramList.iterator());
    }

    public TreeSet<Metadata> reduceSet(TreeSet<Metadata> inSet, Iterator<Map> req) {
        if (!req.hasNext()) {
            log.debug("Done reducing!!!!!");
            return inSet;
        }

        TreeSet<Metadata> outSet = new TreeSet<Metadata>();
        Map params = req.next();

        String requestedFeature = (String) params.get("feature");
        String attr = (String) params.get("attribute");
        Object value = params.get("value");
        Integer operation = (Integer) params.get("operation");

        for (Metadata fw : inSet) {
            log.debug("Object name is " + fw.getName());

            if (hasFeature(fw.getFeatures(), requestedFeature, attr, value, operation))
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

    public Resource findConformingObject(ResourceManager resourceManager, PolicyContext pec) {
        return findConformingObject(resourceManager, pec.getFeature(), "request", pec.getParams());
    }

    public Resource findConformingObject(ResourceManager resourceManager, String feature, String operation, Map<String, Object> params) {
        TreeSet<Metadata> allMetadatas = resourceManager.getAvailableObjects();
        TreeSet<Metadata> fwSet = getConformingObjects(allMetadatas, feature, operation, params);

        log.debug("All objects: " + allMetadatas + " Reduced set: " + fwSet);
        Metadata metadata = fwSet.iterator().next();
        return metadata != null ? resourceManager.getObject(metadata.getName()) : null;
    }

    public TreeSet<Metadata> getConformingObjects(TreeSet<Metadata> availMetadatas, String feature, String operation, Map<String, Object> paramValues) {
        DesiredFeaturesList req = buildDesiredFeatureList(feature, operation, paramValues);

        return reduceSet(availMetadatas, req);
    }

    private DesiredFeaturesList buildDesiredFeatureList(String feature, String operation, Map<String, Object> paramValues) {
        DesiredFeaturesList req = new DesiredFeaturesList();

//        featTree = policyRepository.loadFeatures();
        List<Policy> policies = policyRepository.load(feature, operation);

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
                log.debug("Unrecognizable operator " + thePolicy.getDecision().getOperation());

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

//    private Feature foundFeature = null;

    private Stack<String> getSuperFeatures(Collection<Feature> inSet, String feature) {
        for (Feature feat : inSet) {
            Feature result = depthFirstSearch(feat, feature);
            if (result != null)
                return getSuperFeatures(feat);
        }

        return new Stack<String>();
    }

//    private Stack<String> getSuperFeatures(String feature) {
//        Feature foundFeature = depthFirstSearch(featTree, feature);
//
//        return getSuperFeatures(foundFeature);
//    }

    private Stack<String> getSuperFeatures(Feature foundFeature) {
        Stack<String> parentFeatures = new Stack<String>();
        while (foundFeature.getParentFeature() != null) {
            parentFeatures.push(foundFeature.getParentFeature().getName());
            foundFeature = foundFeature.getParentFeature();
        }

        return parentFeatures;
    }

//    private void depthFirstSearchWithoutReturn(Feature treeNode, String feature) {
//        if (treeNode.getName().equals(feature))
//            foundFeature = treeNode;
//
//        if (treeNode.getSubFeatures().size() > 0) {
//            for (Feature feat : treeNode.getSubFeatures()) {
//                depthFirstSearchWithoutReturn(feat, feature);
//            }
//        }
//    }

    private Feature depthFirstSearch(Feature treeNode, String feature) {
        if (treeNode.getName().equals(feature))
            return treeNode;

        Feature result = null;
        for (Feature subFeature : treeNode.getSubFeatures()) {
            result = depthFirstSearch(subFeature, feature);
            if (result != null)
                break;
        }

        return result;
    }
}
