/* @author Mario J Lorenzo */ package cvm.ncb.adapters.ncblite.rg.repository;

import java.util.List;

import cvm.ncb.adapters.ncblite.rg.repository.facades.DMFacade;
import cvm.ncb.adapters.ncblite.rg.repository.facades.PMFacade;
import cvm.ncb.adapters.ncblite.rg.repository.session.Session;

import cvm.model.*;

public class Gateway {
	PMFacade pm;
	DMFacade dm;
	
	public Gateway() {
		
		pm = new PMFacade();
		dm = new DMFacade();
	}
	
	public void testConnection()
	{
		pm.testConnection();		
	}
	
	public User authUser(String userName, String password, String hostURL)
	{
		CVM_Debug.getInstance().printDebugMessage("authUser");
		User theUser = pm.getUserByUserNameComplex(userName);//pm.getUserNameByUserNameSimple(userName);
		
		if(theUser.getPassword().equals(password))
		{
			Session.getInstance().setUserActive(userName);
			
			Session.getInstance().setUserHost(userName, hostURL);
			pm.updateVideoStreamURL(userName, hostURL);
			theUser.setHostURL(hostURL);
					
			return theUser;
		}
		else
		{
			return null;
			
		}
	}
	
	public String queryUserHost(String userName) {
		
		return Session.getInstance().getUserHost(userName);
		
	}
	
	public List getAllContacts() {
		
		return pm.getAllContacts();
		
		
	}
	
	public String[] requestSipKeyPair()
	{
		return Session.getInstance().getSipKeyPair();
			
	}
	
	public boolean updateProfile(User user)
	{
		return pm.updateUserByUserName(user);
	}
	
	public boolean logoutUser(String userName)
	{
		pm.logoutByUserName(userName);
		return true;
	}
	
	public boolean addContactToContactList(String userName, String contactUserName)
	{
		pm.addContactToContactList(userName, contactUserName);
		return true;
	}
	
	public boolean deleteContactFromContactList(String userName, String contactUserName)
	{
		return pm.deleteContactFromContactList(userName,contactUserName);
		
	}
	
	public User refreshUI(String userName)
	{
		CVM_Debug.getInstance().printDebugMessage("refreshUI");
		User theUser = pm.getUserByUserNameComplex(userName);
		
		return theUser; 
	}
	
	public Form loadFormType(String userName,String formTypeName)
	{
		return dm.loadFormTypeSchema(userName, formTypeName);
	}
	
	public Form loadFormInstance(String userName, String formName)
	{
		return dm.loadFormInstanceSchema(userName, formName);
		
	}
	
	public String getApplication(String userName, String applicationName)
	{
		return dm.getApplication(userName,applicationName);
	}

	public List getApplicationList(String userName)
	{
		return dm.getApplicationList(userName);
	}
	
	public boolean isUserActive(String userName)
	{
		return pm.isUserActive(userName);
	}
	
	
	public void shutdown() 
	{
		
		pm.shutdown();
		dm.shutdown();
	}
	
	//-================================================
	public boolean addApplication(String userName, String appName, String memberList)
	{
		return pm.addApplication(userName, appName, memberList);
	}
	
	public boolean addMemberToApp (String appID, String memUsername)
	{
		return pm.addMemberToApp (appID, memUsername);
	}

	public boolean deleteMemberFromApp(String appID, String memUsername)
	{
		return pm.deleteMemberFromApp(appID, memUsername);
	}
	
	public boolean deleteApplication (String appID)
	{
		return pm.deleteApplication (appID);
	}
	
	
}
