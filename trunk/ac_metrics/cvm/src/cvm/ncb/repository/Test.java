package cvm.ncb.repository;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import cvm.ncb.repository.loader.GlobalConstant;
import cvm.model.*;

public class Test {

	public static void main(String arags[])
	{
		lookup();
/*				
 String superFeatures= "CommFeatureRoot Audio";
		Scanner scan  = new Scanner(superFeatures);
		String toBeMatched ="Audio";
  String[] dataArray = superFeatures.split(" ");
		
		for (int i=0;i< dataArray.length;i++)
		{
			if  (feat.getName().equals(dataArray[i]))
			{
				CVM_Debug.getInstance().printDebugMessage("Found superfeature");
				return hasSubFeature(feat,feature,attr,value,opcode,scan);
			}
		}*/
		//scan.useDelimiter(";");

		//CVM_Debug.getInstance().printDebugMessage(findSubFeatures(toBeMatched, scan));

		
		/*while (scan.hasNext())
		{
			CVM_Debug.getInstance().printDebugMessage(scan.next());
		}*/
	}
	
	private static boolean findSubFeatures(String tobeFound, Scanner scan)
	{
		boolean isFound;
		if (!scan.hasNext()) isFound = false;
		else 
		{
		//	isFound= findSubFeatures(tobeFound, scan);
			if (tobeFound.compareTo(scan.next())==0)
				isFound=true;
			else
				isFound= findSubFeatures(tobeFound, scan);
		}
		return isFound;
	}
	
	public static void lookup() 
	{
		ArrayList<File> arr = new ArrayList<File>();
		File dir = new File("C:/Documents and Settings/ywu001/workspace/Self_Config/src/cvm/ncb/repository/examples");
		for (File f : dir.listFiles())
		{	
			//if (getFileExtension(f.getName()).compareTo("xml")==0 && f.getName().compareTo("featureTree.xml")!=0)
			if (f.getName().endsWith("xml") && f.getName().compareTo("featureTree.xml")!=0)
			{
				CVM_Debug.getInstance().printDebugMessage(f.getName());
			}
		}
	}
	
	public static String getFileExtension(String fileName) {
		File tmpFile = new File(fileName);
		tmpFile.getName();
		int whereDot = tmpFile.getName().lastIndexOf('.');
		if (0 < whereDot && whereDot <= tmpFile.getName().length() - 2 ) {
	      return tmpFile.getName().substring(whereDot+1);
	   }
	   return "";
	 }
}
