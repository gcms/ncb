package cvm.ncb.oem.policy;

import cvm.ncb.repository.loader.FilePolicyLoader;
import cvm.ncb.repository.loader.GlobalConstant;

import java.net.URISyntaxException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeSet;

public class PolicyManagerExample {
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
        TreeSet<Metadata> set = pm2.getConformingObjects(hs, GlobalConstant.RequestedType.Audio.toString(), GlobalConstant.OperationType.request.toString(), params);

        //CVM_Debug.getInstance().printDebugMessage(set);
        //	CVM_Debug.getInstance().printDebugMessage(pm2.getFrameworkSet(set,GlobalConstant.RequestedType.PC2Phone, GlobalConstant.OperationType.request, 2));

    }
}
