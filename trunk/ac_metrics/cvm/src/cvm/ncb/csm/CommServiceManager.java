package cvm.ncb.csm;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import cvm.ncb.handlers.NCBExceptionHandler;
import cvm.ncb.handlers.exception.LoginException;

import cvm.ncb.ks.ConIDMappingTable;
import cvm.ncb.ks.UserIDMappingTable;
import cvm.ncb.tpm.CommFWResource;
import cvm.ncb.adapters.NCBBridge;

import edu.fiu.strg.ACSTF.touchpoint.AbstractTouchPoint;
import cvm.model.*;

/**
 * Manages the communication objects of NCB.
 * @author Frank Hernandez
 *
 */
public class CommServiceManager 
{
	private HashMap<String, CommObject> m_hmSessionMap;
	private CommObject m_coCurrCommObj;
	private CommObjectManager m_comCMan;
	private static CommServiceManager instance = null;
	private NCBExceptionHandler m_xhXHandler = null;
	private ConIDMappingTable m_conToFwTable = null;
	private Method[] m_bridgeMethods = null;
	private Method[] m_ncbMethods = null;
	
	private CommServiceManager()
	{
		m_xhXHandler = NCBExceptionHandler.Instance();
		m_hmSessionMap = new HashMap<String, CommObject>();
		m_conToFwTable = ConIDMappingTable.getInstance();
		m_bridgeMethods = NCBBridge.class.getMethods();
		m_ncbMethods = CommServiceManager.class.getMethods();
		this.intitializeCommObjects();
		
	}
	
	/**
	 * Implementation of the Singleton design pattern.
	 * @return
	 */
	public static CommServiceManager getInstance()
	{
		if(instance == null)
			instance = new CommServiceManager();
		
		return instance;
	}
	
	/**
	 * This function returns the capabilities of
	 * all the communication objects.
	 * @return
	 */
	public String getCapabilities()
	{
		ArrayList <CommObject> tempList =m_comCMan.getCommObjectList(); 
		String tempCaps = "";
		
		for(int i=0; i< tempList.size(); i++)
		{
			tempCaps += ((CommObject)tempList.get(i)).getNCBBridge().getCapability()+" @ ";		
		}
		return tempCaps;
	}
	
	/**
	 * This function returns the currently selected communication object.
	 * @return
	 */
	public CommObject getCommunicationObject()
	{
		return this.m_coCurrCommObj; 
	}
	
	/**
	 * This function returns the currently selected communication object.
	 * @return
	 */
	public CommObject getCommunicationObject(String fwname)
	{
		for(CommObject comObj: m_comCMan.getCommObjectList()){
			if(comObj.getName().equals(fwname)){
				return comObj; 
			}
		}
		return null;
	}

	/**
	 * This function returns the list of communication objects.
	 * @return
	 */
	public ArrayList<CommObject> getCommObjectList()
	{
		return this.m_comCMan.getCommObjectList(); 
	}

	/**
	 * This function sets the current communication object.
	 * 
	 */
	public void setCommunicationObject(int index)
	{
		m_coCurrCommObj = m_comCMan.getCommObjectList().get(index); 
		CVM_Debug.getInstance().printDebugMessage("Reset coCurrCommObj");
	}

	/**
	 * This function logs the user into all the existing
	 * communication objects.
	 *
	 */
	public void loginAll()
	{
		ArrayList <CommObject> tempList =m_comCMan.getCommObjectList(); 
		CommObject tempComm = null;
		String userName = null, password;
		for(int i=0; i< tempList.size(); i++)
		{
			tempComm = ((CommObject)tempList.get(i));
			userName = tempComm.getNCBBridge().username;
			password = tempComm.getNCBBridge().password;
			try
			{
				tempComm.getNCBBridge().login(userName, password);
			}
			catch(LoginException e)
			{
				m_xhXHandler.handleException(e);
			}
		}
	}
	/**
	 * This function logs the user out of all the existing
	 * communication objects.
	 *
	 */
	public void logoutAll()
	{
		ArrayList <CommObject> tempList =m_comCMan.getCommObjectList(); 
		CommObject tempComm = null;
		String userName = null, password;
		for(int i=0; i< tempList.size(); i++)
		{
			tempComm = ((CommObject)tempList.get(i));
			userName = tempComm.getNCBBridge().username;
			tempComm.getNCBBridge().logout(userName);
		}
	}
	
	/**
	 * This function is used by for the autonomic computing
	 * side of the CMS.
	 *
	 */
	public void applyPolicies()
	{
		
	}
	
	/**
	 * This function initializes all the 
	 * communication objects.
	 *
	 */
	public void intitializeCommObjects()
	{
		m_comCMan = CommObjectManager.getInstance();
		m_coCurrCommObj = (CommObject)m_comCMan.getCommObjectList().get(0);
		
	}
	/**
	 * Maps the communicaiotn and the session. 
	 * Currently it Maps the connection to the current communication object
	 * then it creates a session in this object.
	 * @param connectionID
	 * @param sessionID
	 */
	public void mapConnToSession(String connectionID, String sessionID)
	{
		if(m_coCurrCommObj == null)
			this.intitializeCommObjects();
		m_hmSessionMap.put(connectionID, m_coCurrCommObj);
		//If the session is not created already then create it.
		if(!m_coCurrCommObj.getNCBBridge().isSessionCreated(sessionID))
			m_coCurrCommObj.getNCBBridge().createSession(sessionID);
		
	}
	
	private String getCommObjName(String conID, String medium){
		while(m_conToFwTable.isTableLocked()){
			
		}
		return m_conToFwTable.getMapping(conID, medium);
	}
	
	private String getOldCommObjName(String conID, String medium){
		while(m_conToFwTable.isTableLocked()){
			
		}
		return m_conToFwTable.getConnection(conID).getPreviousComObj(medium);
	}

	public void executeCall(String conID, String mName, String medium,Object [] params){
		CVM_Debug.getInstance().printDebugMessage("Method name: "+mName+" and medium: "+medium);
		try {
			for(Method method: m_bridgeMethods) {
				if(method.getName().equals(mName)) {
					if(mName.equalsIgnoreCase("destroySession")){
						String prevComObj = getOldCommObjName(conID,medium);
						CVM_Debug.getInstance().printDebugMessage("Old comobj is "+prevComObj);
						if(prevComObj != null){
							method.invoke(getCommunicationObject(prevComObj
									).getNCBBridge(),params);
						}
					}else {
						method.invoke(getCommunicationObject(getCommObjName(conID,
							medium)).getNCBBridge(),params);
					}
					return;
				}
			}
			CVM_Debug.getInstance().printDebugErrorMessage(mName+": Not in bridge class!!!!!");
			for(Method method: m_ncbMethods) {
				if(method.getName().equals(mName)) {
					method.invoke(this,params);
					return;
				}
			}
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		CVM_Debug.getInstance().printDebugMessage("Error: Unknown Call!!!");
	}

	/**
	 * This function logs the user into all the existing
	 * communication objects.
	 *
	 */
	public void sendSchema(String sID, String senderID, String listReceiver, String control_xcml,String data_xcml)
	{
		for(CommObject tempComm: m_comCMan.getCommObjectList())
		{
			CVM_Debug.getInstance().printDebugMessage("NCBManager : NCB sendSchema called with sID:\""+sID+"\" and senderID:\""+senderID+"\" and receiverID:\""+listReceiver+"\" and Control_XCML:\""+control_xcml+"\" and Data_XCML:\""+data_xcml+"\".");
			//Currently there is not list of receivers implemented, all happens one at a time.
			if(control_xcml != null && !control_xcml.equals("") && !control_xcml.equals("null"))
			{
				Scanner tmp = new Scanner(listReceiver);
				while (tmp.hasNext()){
					tempComm.getNCBBridge().sendSchema(control_xcml, 
							UserIDMappingTable.getInstance().lookupContact(
									tempComm.getName(), tmp.next()));
				}
			}
			
			if(data_xcml !=null && !data_xcml.equals("") && !data_xcml.equals("null"))
			{
				Scanner tmp = new Scanner(listReceiver);
				while (tmp.hasNext()){
					tempComm.getNCBBridge().sendSchema(data_xcml, 
							UserIDMappingTable.getInstance().lookupContact(
							tempComm.getName(), tmp.next()));
				}
			}
			CVM_Debug.getInstance().printDebugMessage("NCBManager : NCB sendSchema OVER");
		}
	}

}
		
	
	
	


