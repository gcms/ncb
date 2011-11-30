package edu.fiu.strg.ACSTF.manager;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


import org.apache.xerces.parsers.DOMParser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import edu.fiu.strg.ACSTF.resource.*;
import edu.fiu.strg.ACSTF.touchpoint.*;
import edu.fiu.strg.ACSTF.manager.mape.*;
import edu.fiu.strg.ACSTF.manager.knowledge.*;

public class GenericManager<Touchpoint>{
	private String name;
    private MAPEGroup<Touchpoint> mape;
    private KnowledgeInterface<Touchpoint> knowledge;
	protected AbstractTouchPoint touchpoint;
	private String sensor;
	
	// XML Tag Constants
	private final static String monitorTagLabel = "monitor";
	private final static String touchPackageLabel = "touchpackage";
	private final static String touchpointLabel = "touchpoint";
	private final static String sensorLabel = "sensor";
	private final static String analyzerTagLabel = "analyzer";
	private final static String accessorLabel = "var";
	private final static String operatorLabel = "op";
	private final static String valueLabel = "val";
	private final static String symptomLabel = "symptom";
	private final static String mappingLabel = "mapping";
	private final static String sidLabel = "sid";
	private final static String plannerTagLabel = "planner";
	private final static String planLabel = "plan";
	private final static String pidLabel = "pid";
	private final static String actionLabel = "action";
	private final static String effectorLabel = "effector";
    
	public GenericManager (String name, URI policyFileName, AbstractResource aResource) {

		File policyFile = new File(policyFileName);
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
		loadPolicy(name, policyDocument, aResource);
	}
	
	public void loadPolicy(String name, Document policyDocument, AbstractResource aResource)
	{
		this.knowledge = new Knowledge<Touchpoint>();
		loadMonitorTag(policyDocument, aResource);
		loadAnalyzerTag(policyDocument);
		loadPlannerTag(policyDocument);
		this.mape = new MAPEGroup<Touchpoint>(name);
		this.mape.setMonitor(new Monitor<Touchpoint>(this.mape, name, this.knowledge, this.touchpoint, this.sensor));
		this.mape.setAnalyze(new Analyzer<Touchpoint>(this.mape, name, this.knowledge));
		this.mape.setPlan(new Planner<Touchpoint>(this.mape, name, this.knowledge));
		this.mape.setExecute(new Executer<Touchpoint>(this.mape, name, this.knowledge, this.touchpoint));
	}

	public void loadMonitorTag(Document policyDocument,
			AbstractResource aResource)
	{
		NodeList monitorNodes = policyDocument
				.getElementsByTagName(monitorTagLabel);
		NamedNodeMap monitorAttribs = monitorNodes.item(0).getAttributes();
		String touchPackageName = (monitorAttribs
				.getNamedItem(touchPackageLabel)).getNodeValue();
		String touchpointName = (monitorAttribs.getNamedItem(touchpointLabel))
				.getNodeValue();
		try
		{
			Class sensorClass = Class
					.forName(touchPackageName + touchpointName);
			Constructor[] cons = sensorClass.getDeclaredConstructors();
			System.out.println(cons[0]);
			touchpoint = (AbstractTouchPoint) cons[0]
					.newInstance(new Object[] { aResource });
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		catch (ClassNotFoundException e)
		{
			System.out.println(e.getMessage());
		}
		catch (SecurityException e)
		{
			e.printStackTrace();
		}
		catch (InstantiationException e)
		{
			e.printStackTrace();
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (IllegalArgumentException e)
		{
			e.printStackTrace();
		}
		this.sensor = (monitorAttribs.getNamedItem(sensorLabel)).getNodeValue();
	}

	public void loadAnalyzerTag(Document policyDocument)
	{
		NodeList analyzerNodes = policyDocument
				.getElementsByTagName(analyzerTagLabel);
		NodeList symptomNodes = ((Element) analyzerNodes.item(0))
				.getElementsByTagName(symptomLabel);
		Map<String, Symptom> tempSymptoms = new HashMap<String, Symptom>();
		for (int j = 0; j < symptomNodes.getLength(); j++)
		{
			String tempSID = ((Element) symptomNodes.item(j))
					.getAttribute(sidLabel);
			NodeList mappingNodes = ((Element) symptomNodes.item(j))
					.getElementsByTagName(mappingLabel);
			ArrayList<Mapping> tempMappingArray = new ArrayList<Mapping>();
			for (int k = 0; k < mappingNodes.getLength(); k++)
			{
				NamedNodeMap mappingAttribs = mappingNodes.item(k)
						.getAttributes();
				Mapping m = new Mapping(mappingAttribs.getNamedItem(
						accessorLabel).getNodeValue(), mappingAttribs
						.getNamedItem(operatorLabel).getNodeValue(),
						mappingAttribs.getNamedItem(valueLabel).getNodeValue());
				tempMappingArray.add(m);
			}
			Symptom tempSymptom = new Symptom(tempSID, tempMappingArray);
			tempSymptoms.put(tempSID, tempSymptom);
		}
		this.knowledge.setSymptoms(tempSymptoms);
	}

	public void loadPlannerTag(Document policyDocument)
	{
		NodeList plannerNodes = policyDocument
				.getElementsByTagName(plannerTagLabel);
		NodeList planNodes = ((Element) plannerNodes.item(0))
				.getElementsByTagName(planLabel);
		Map<String, ChangePlan> tempPlans = new HashMap<String, ChangePlan>();
		for (int j = 0; j < planNodes.getLength(); j++)
		{
			String tempPID = ((Element) planNodes.item(j))
					.getAttribute(pidLabel);
			NodeList actionNodes = ((Element) planNodes.item(j))
					.getElementsByTagName(actionLabel);
			ArrayList<Action> tempActionArray = new ArrayList<Action>();
			for (int k = 0; k < actionNodes.getLength(); k++)
			{
				NamedNodeMap actionAttribs = actionNodes.item(k)
						.getAttributes();
				// Somewhere here should add another nested to do parameter list
				// when finished.
				Action a = new Action(actionAttribs.getNamedItem(effectorLabel)
						.getNodeValue(), new ArrayList<Parameter>());
				tempActionArray.add(a);
			}
			ChangePlan tempChangePlan = new ChangePlan(tempPID, tempActionArray);
			tempPlans.put(tempPID, tempChangePlan);
		}
		this.knowledge.setPlans(tempPlans);
	}
	
	public void manage() {
		this.mape.start();
		this.knowledge.start();
	}

	public void stop() {
		this.knowledge.stop();
		this.getMape().joinAll();
		System.out.println("Manager Stopped");
	}
	
	public void suspend() {
		this.knowledge.suspend();
	}
	
	public void resume() {
		this.knowledge.resume();
	}
	
	public boolean isSuspended() {
		return this.mape.hasAllSuspended();
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public MAPEGroup<Touchpoint> getMape() {
		return mape;
	}

	public void setMape(MAPEGroup<Touchpoint> mape) {
		this.mape = mape;
	}
	
}
