package cvm.ncb.csm;

import java.util.ArrayList;

import cvm.ncb.adapters.SIPAdapter;
import cvm.ncb.adapters.SmackAdapter;
import cvm.ncb.adapters.JMMLAdapter;
import cvm.ncb.adapters.NCBBridge;
import cvm.ncb.adapters.NCBNativeAdapter;
import cvm.ncb.adapters.SkypeAdapter;
import cvm.model.*;

/**
 * Handles the creation of communication objects
 * This class generaters an instance each communication bridge
 * with the property files.
 * @author Frank Hernandez
 *
 */
public class CommObjectManager 
{

	private ArrayList<CommObject>  m_coContainer;  
	private static CommObjectManager instance = null;
	/*
	 * 1 - Get network bridge list from CSM_Propert file.
	 * 2 - For every bridge.
	 * 		I - Create a communication object
	 * 		II - Add communication object to the list. 
	 */
	private CommObjectManager()
	{
		CSM_Properties m_pBridges = new CSM_Properties();
		String m_sAllowedBridges = m_pBridges.getProperty(m_pBridges.SB_BRIDGES);
		m_coContainer = new ArrayList<CommObject>();
		//Create Skype Communication object if defined.
		createCommunicationObjectSkype(m_sAllowedBridges);
		//Create Google Talk Communication object if defined.
		createCommunicationObjectSmack(m_sAllowedBridges);
		//Create Asterisk Communication object if defined.
		createCommunicationObjectAsterisk(m_sAllowedBridges);
		//Create JMML Communication Object if defined.
		//createCommunicationObjectJMML(m_sAllowedBridges);
		//Create NCBNative Communication Object if defined.
		//createCommunicationObjectNCBNative(m_sAllowedBridges);	
	}
	
	public static CommObjectManager getInstance()
	{
		if(instance == null)
			instance = new CommObjectManager();
		return instance;
	}
	/**
	 * This function returns a copy of the list of communication objects.
	 * The list contains all the communication objects that 
	 * were created from the property file. This communication objects
	 * in turn contain an instance an adapter.
	 * @return List of communicaion objects.
	 * @see CommObject
	 */
	public ArrayList<CommObject> getCommObjectList()
	{
		return m_coContainer;
	}
	
	/**
	 * Handles the creation of a communication object specific to Skype.
	 * @param m_sAllowedBridges list of allowed bridges from the config file.s
	 */
	private void createCommunicationObjectSkype(String m_sAllowedBridges)
	{
		
		CommObject tempComm = null;
		NCBBridge tempBridge = null;
		CSM_PropBridgeSpecifics tempProp = null;
		String tempUserName = null, tempPassword = null;
		
		//Create the Skype Communication Object
		if(m_sAllowedBridges.contains(CSM_NBTypes.SKYPE))
		{
			//try
			//{
				tempProp = new CSM_PropBridgeSpecifics(CSM_NBTypes.SKYPECONFIG);
				tempBridge = new SkypeAdapter();
				tempUserName = tempProp.getProperty(tempProp.SB_USERNAME);
				tempPassword = tempProp.getProperty(tempProp.SB_PASWORD);
				/*
				 * Log into adapter --- Not used for skype, skype must be 
				 * logged in and running for the Skype4Java API to Work.
				 */
				//tempBridge.login(tempUserName, tempPassword);
			
				tempBridge.password = tempPassword;
				tempBridge.username = tempUserName;
				
				tempComm = new CommObject(tempBridge);
				//If the communication object is not in the list already.
				if( !m_coContainer.contains(tempComm))
					m_coContainer.add(tempComm);
			//}
			//catch(LoginException e)
			//{
				//Handle exception probably call the exception handler.
				
			//}
		}
	}

	/**
	 * Handles the creation of a communication object specific to GTalk.
	 * @param m_sAllowedBridges list of allowed bridges from the config file.s
	 */
	private void createCommunicationObjectSmack(String m_sAllowedBridges)
	{
		CommObject tempComm = null;
		NCBBridge tempBridge = null;
		CSM_PropBridgeSpecifics tempProp = null;
		String tempUserName = null, tempPassword = null;
		
		//Create the Google Talk Communication Object
		if(m_sAllowedBridges.contains(CSM_NBTypes.SMACK))
		{
			tempProp = new CSM_PropBridgeSpecifics(CSM_NBTypes.SMACKCONFIG);
			//Create adapter instance.
			tempBridge = new SmackAdapter();
			tempUserName = tempProp.getProperty(tempProp.SB_USERNAME);
			tempPassword = tempProp.getProperty(tempProp.SB_PASWORD);
	
			//tempBridge.login(tempUserName, tempPassword);
			
			tempBridge.password = tempPassword;
			tempBridge.username = tempUserName;
		
			tempComm = new CommObject(tempBridge);
			//If the communication object is not in the list already.
			if( !m_coContainer.contains(tempComm))
				m_coContainer.add(tempComm);
		}
		System.out.println("Done initialize smack");
	}

	/**
	 * Handles the creation of a communication object specific to GTalk.
	 * @param m_sAllowedBridges list of allowed bridges from the config file.s
	 */
	private void createCommunicationObjectAsterisk(String m_sAllowedBridges)
	{
		CommObject tempComm = null;
		NCBBridge tempBridge = null;
		CSM_PropBridgeSpecifics tempProp = null;
		String tempUserName = null, tempPassword = null;
		
		//Create the Google Talk Communication Object
		if(m_sAllowedBridges.contains(CSM_NBTypes.ASTERISK))
		{
			tempProp = new CSM_PropBridgeSpecifics(CSM_NBTypes.ASTERISKCONFIG);
			//Create adapter instance.
			tempBridge = new SIPAdapter();
			tempUserName = tempProp.getProperty(tempProp.SB_USERNAME);
			tempPassword = tempProp.getProperty(tempProp.SB_PASWORD);
	
			//tempBridge.login(tempUserName, tempPassword);
			
			tempBridge.password = tempPassword;
			tempBridge.username = tempUserName;
		
			tempComm = new CommObject(tempBridge);
			//If the communication object is not in the list already.
			if( !m_coContainer.contains(tempComm))
				m_coContainer.add(tempComm);
		}
	}

	/**
	 * Handles the creation of a communication object specific to JMML.
	 * @param m_sAllowedBridges list of allowed bridges from the config file.s
	 */
	private void createCommunicationObjectJMML(String m_sAllowedBridges)
	{
		
		CommObject tempComm = null;
		NCBBridge tempBridge = null;
		CSM_PropBridgeSpecifics tempProp = null;
		String tempUserName = null, tempPassword = null;
		
		//Create the JMML Communication Object
		if(m_sAllowedBridges.contains(CSM_NBTypes.JMML))
		{
			//try
			//{
				tempProp = new CSM_PropBridgeSpecifics(CSM_NBTypes.JMMLCONFIG);
				tempBridge = new JMMLAdapter();
				tempUserName = tempProp.getProperty(tempProp.SB_USERNAME);
				tempPassword = tempProp.getProperty(tempProp.SB_PASWORD);
				
				//tempBridge.login(tempUserName, tempPassword);
				
				tempBridge.password = tempPassword;
				tempBridge.username = tempUserName;
			
				tempComm = new CommObject(tempBridge);
				//If the communication object is not in the list already.
				if( !m_coContainer.contains(tempComm))
					m_coContainer.add(tempComm);

			//}
			//catch(LoginException e)
			//{
				//Handle exception probably call the exception handler.
				
			//}
		}
	}
	/**
	 * Handles the creation of a communication object specific to NCBNative.
	 * @param m_sAllowedBridges list of allowed bridges from the config file.s
	 */
	private void createCommunicationObjectNCBNative(String m_sAllowedBridges)
	{
		
		CommObject tempComm = null;
		NCBBridge tempBridge = null;
		CSM_PropBridgeSpecifics tempProp = null;
		String tempUserName = null, tempPassword = null;
		
		//Create the NCBNative Communication Object
		if(m_sAllowedBridges.contains(CSM_NBTypes.NCBNATIVE))
		{
			//try
			//{
				tempProp = new CSM_PropBridgeSpecifics(CSM_NBTypes.NCBNATIVECONFIG);
				tempBridge = new NCBNativeAdapter();
				tempUserName = tempProp.getProperty(tempProp.SB_USERNAME);
				tempPassword = tempProp.getProperty(tempProp.SB_PASWORD);
				
				// tempBridge.login(tempUserName, tempPassword);
				
				tempBridge.password = tempPassword;
				tempBridge.username = tempUserName;
			
				tempComm = new CommObject(tempBridge);
				//If the communication object is not in the list already.
				if( !m_coContainer.contains(tempComm))
					m_coContainer.add(tempComm);

			//}
			//catch(LoginException e)
			//{
				//Handle exception probably call the exception handler.
				
			//}
		}
	}
}