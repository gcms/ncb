/**
 * @author Yali Wu
 * @since November 19 2008
 * 
 * This class looks up the policy from the repository, and 
 * loads the policy in XML format to an object representation
 */


package cvm.ncb.repository.loader;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import cvm.ncb.oem.policy.*;
import cvm.ncb.repository.policy.*;

import org.apache.xerces.parsers.DOMParser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class FilePolicyLoader implements LoaderInterface{
	
	private final static String scopeTagLabel = "scope";
	private final static String conditionTagLabel = "condition";
	private final static String bvalueTagLabel = "businessValue";
	private final static String decisionTagLabel = "decision";


	private final static String serviceTagLabel = "service";
	private final static String operationSTagLabel = "operation";
	private final static String activeTagLable =  "active";
	
	private final static String featureTagLabel = "feature";
	private final static String operationCTagLabel = "operation";
	private final static String literalTagLabel = "literal";

	private final static String bgroupTagLabel = "businessGroup";
	private final static String bvalTagLabel = "value";
	
	private final static String paramTagLabel = "param";
	private final static String operationDTagLabel = "operation";
	private final static String valueTagLabel = "value";
	
	private final static String policyTypeTagLable = "policyType";
	private final static String policyNameTagLable = "policyName";
	
	
	
	
	
	private static File dir;
	private static FilePolicyLoader instance;
	
	FilePolicyLoader(String path) 
	{
		dir = new File(path);
		if (!dir.exists() || !dir.isDirectory())
			dir.mkdir();
	}
	
	public static FilePolicyLoader getInstance(String path) 
	{
		if (instance == null)
			instance = new FilePolicyLoader(path);
		
		return instance;
	}
	
	public NCBPolicy load(String policyURL) throws Exception
	{
		Document policyDoc = getPolicyDocument(policyURL);
		Node scopeNode = policyDoc.getElementsByTagName(scopeTagLabel).item(0);
		Node conditionNode = policyDoc.getElementsByTagName(conditionTagLabel).item(0);
		Node decisionNode = policyDoc.getElementsByTagName(decisionTagLabel).item(0);
		Node bvalueNode = policyDoc.getElementsByTagName(bvalueTagLabel).item(0);
		
		Node serviceNode = ((Element)scopeNode).getElementsByTagName(serviceTagLabel).item(0);
		Node operationSNode = ((Element)scopeNode).getElementsByTagName(operationSTagLabel).item(0);
		Node activeNode =((Element)scopeNode).getElementsByTagName(activeTagLable).item(0);
		Scope scope = new Scope(serviceNode.getTextContent(), operationSNode.getTextContent(),activeNode.getTextContent());
		
		Node featureNode = ((Element)conditionNode).getElementsByTagName(featureTagLabel).item(0);
		Node operationCNode = ((Element)conditionNode).getElementsByTagName(operationCTagLabel).item(0);
		Node literalNode =((Element)conditionNode).getElementsByTagName(literalTagLabel).item(0);
		
		Condition condition = new Condition(featureNode.getTextContent(), operationCNode.getTextContent(),literalNode.getTextContent());
		
		
		Node businessGroupNode =((Element)bvalueNode).getElementsByTagName(bgroupTagLabel).item(0);
		Node bvalNode = ((Element)bvalueNode).getElementsByTagName(bvalTagLabel).item(0);	
		BusinessValue bvalue = new BusinessValue(businessGroupNode.getTextContent(), bvalNode.getTextContent());
		
		Node paramNode = ((Element)decisionNode).getElementsByTagName(paramTagLabel).item(0);
		Node operationDNode = ((Element)decisionNode).getElementsByTagName(operationDTagLabel).item(0);
		Node valueNode = ((Element)decisionNode).getElementsByTagName(valueTagLabel).item(0);
		Decision decision = new Decision(paramNode.getTextContent(), operationDNode.getTextContent(), valueNode.getTextContent());
		
		
		String theType = policyDoc.getDocumentElement().getAttributes().getNamedItem(policyTypeTagLable).getNodeValue();
		String theName = policyDoc.getDocumentElement().getAttributes().getNamedItem(policyNameTagLable).getNodeValue();
		NCBPolicy ncbPolicy = new NCBPolicy(scope,condition, bvalue, decision, theType,theName);

		return ncbPolicy;
	}
	
	public ArrayList<File> lookup(GlobalConstant.RequestedType feat, GlobalConstant.OperationType oper) 
	{
		ArrayList<File> arr = new ArrayList<File>();
		Document aDoc = null;
		for (File f : dir.listFiles())
		{	
			if (f.getName().endsWith("xml") && f.getName().compareTo("featureTree.xml")!=0)
			{
				aDoc = getPolicyDocument(f.toString());
				Node conditionNode = aDoc.getElementsByTagName(conditionTagLabel).item(0);
				if (((Element)conditionNode).getElementsByTagName(featureTagLabel).item(0).getTextContent().compareTo(feat.toString())==0  &&
						((Element)conditionNode).getElementsByTagName(operationCTagLabel).item(0).getTextContent().compareTo(oper.toString())== 0)
					arr.add(f);
			}
		}
		return arr;
	}



	private Document getPolicyDocument (String policyURL)
	{
		File policyFile = new File(policyURL);
		FileInputStream pFileIn = null;
		BufferedInputStream pBufferIn = null;
		DOMParser policyParser = new DOMParser();
		try
		{
			pFileIn = new FileInputStream(policyFile);

			pBufferIn = new BufferedInputStream(pFileIn);
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		InputSource pInputSource = new InputSource(pBufferIn);
		try
		{
			policyParser.parse(pInputSource);
		}
		catch (SAXException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		Document policyDocument = policyParser.getDocument();
		try
		{
			
			pFileIn.close();
			pBufferIn.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return policyDocument;
	}

	public Feature loadFeatureTree(String treeURL)
	{
		String absURL = dir+ treeURL;
		Document treeDoc= getPolicyDocument(absURL);
		Feature root=loadFeatures(treeDoc.getDocumentElement(), null);
		
		return root;
	}
	/*
	 * Load the features from the document recursively
	 */
	private Feature loadFeatures(Element ele, Feature parent)
	{
		Attribute attr;
		Feature aFeature = new Feature(ele.getNodeName());
		aFeature.setParent(parent);
		for (int i=0; i< ele.getAttributes().getLength(); i++)
		{
			attr = new Attribute ( ele.getAttributes().item(i).getNodeName(),ele.getAttributes().item(i).getNodeValue());
			aFeature.addAttribute(attr);
		}

		if (ele.getChildNodes().getLength()!=0)
		{
		
			for (int i= 0; i< ele.getChildNodes().getLength();i++ )
			{
				if (ele.getChildNodes().item(i).getNodeType()!= Node.TEXT_NODE)
					aFeature.addSubFeature(loadFeatures((Element)(ele.getChildNodes().item(i)), aFeature));
			}
		}
		return aFeature;		
	}
}
		

