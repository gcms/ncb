package cvm.ncb.oem.policy;
import java.util.HashMap;

public class KnowledgeMgr {

	HashMap<String,Framework> fwmap = new HashMap<String,Framework>();

	public boolean updateKnowledge(String fw, String feat, String attr, String val){
		setValue(getAttribute(getFeature(getFramework(fw), feat), attr),val);
		return false;
	}
	
	public boolean updateKnowledge(Framework fw){
		fwmap.put(fw.getFrameworkName(), fw);
		return true;
	}

	public void applyPolicy(String policy){
		
	}
	
	private Framework getFramework(String fw) {
		return fwmap.get(fw);
	}
	
	private Feature getFeature(Framework fw, String feat){
		if (fw== null) return null;
		return fw.getFeatureList().get(feat);
	}

	private Attribute getAttribute(Feature feat, String attr){
		if (feat== null) return null;
		return feat.getAttribute(attr);
	}

	private boolean setValue(Attribute attr, String val){
		if (attr== null) return false;
		attr.attributeValue = val;
		return true;
	}
}
