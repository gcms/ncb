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
