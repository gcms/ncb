package cvm.ncb.repository;

import cvm.ncb.oem.policy.*;
import cvm.ncb.repository.policy.*;
import cvm.ncb.repository.loader.*;

import java.util.ArrayList;
import java.io.File;
import java.util.Collections;
import java.util.Arrays;




public class NCB_R_Facade {
	private LoaderInterface repLoader;
	private static NCB_R_Facade instance;
	
	
	NCB_R_Facade (FilePolicyLoader ploader)
	{
		repLoader= ploader;
	}
	
	public static NCB_R_Facade getInstance(FilePolicyLoader ploader)
	{
		if (instance == null)
			instance = new NCB_R_Facade(ploader);
		
		return instance;	
	}
	
	public ArrayList<NCBPolicy> load(GlobalConstant.RequestedType request, GlobalConstant.OperationType oper)
	{
		ArrayList<NCBPolicy> arrPolicy = new ArrayList<NCBPolicy>();
		ArrayList <File> arrFile = new ArrayList<File>();
		arrFile = ((FilePolicyLoader)repLoader).lookup(request, oper);
		
		try {
			for (File f : arrFile)
			{
				arrPolicy.add(((FilePolicyLoader)repLoader).load(f.toString()));
			}
			Collections.sort(arrPolicy);
		}
		catch (Exception e) {e.printStackTrace();}
		return arrPolicy;	
	/*	if ( request instanceof GlobalConstant.FeatureType)
			outputFile=((FilePolicyLoader)repLoader).lookup((GlobalConstant.FeatureType)request, (GlobalConstant.OperationType)oper);
		else if (request instanceof GlobalConstant.SymptomType)
			outputFile=((FilePolicyLoader)repLoader).lookup((GlobalConstant.SymptomType)request, (GlobalConstant.OperationType)oper);
	*/	
	//	arr.add(((FilePolicyLoader)repLoader).load(outputFile.toString()));
	}
	
	public Feature loadFeatures(String featureURL)
	{
		return ((FilePolicyLoader)repLoader).loadFeatureTree(featureURL);
	}
}
