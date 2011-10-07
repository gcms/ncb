package cvm.ncb.oem.policy;

import java.util.Iterator;

public class FeatureTreeTraversal {
	static Feature featTree=null;
	private Feature foundFeature=null;
	private  String getSupperFeatures(String feature)
    {
    	String parentFeature =new String() ;
    	//depthFirstSearchWithoutReturn(featTree, feature);
    	foundFeature = depthFirstSearchWithReturn(featTree, feature);
    	while (foundFeature.getParentFeature()!=null)
    	{
    		parentFeature = foundFeature.getParentFeature().getFeatureName()+" "+parentFeature;
    		foundFeature = foundFeature.getParentFeature();
    	}
    	return parentFeature;
    }
	
	private  void depthFirstSearchWithoutReturn(Feature treeNode, String feature)
	{	
		if (treeNode.getFeatureName().compareTo(feature)==0)
			foundFeature = treeNode;
		if(treeNode.getSubFeatureList().size()>0)
		{
			Iterator<Feature> iterator= treeNode.getSubFeatureList().iterator();
			while (iterator.hasNext())
			{
				Feature feat = iterator.next();
				depthFirstSearchWithoutReturn(feat, feature);
			}
		}
	}
	
	private Feature depthFirstSearchWithReturn(Feature treeNode, String feature)
	{
		
		if (treeNode.getFeatureName().equals(feature))
			return treeNode;
		Feature foundFeature = null;
		Iterator<Feature> it = treeNode.getSubFeatureList().iterator();

		while(it.hasNext())
		{

			foundFeature=depthFirstSearchWithReturn(it.next(), feature);
			if (foundFeature!=null)
				break;
		}
		return foundFeature;
	}


}
