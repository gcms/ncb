package cvm.ncb.adapters.ncblite.rg.repository.facades;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import cvm.ncb.adapters.ncblite.model.RoleList;
import cvm.ncb.adapters.ncblite.model.User;
import cvm.ncb.adapters.ncblite.rg.repository.dao.ApplicationInstDAO;
import cvm.ncb.adapters.ncblite.rg.repository.dao.ContactListDAO;
import cvm.ncb.adapters.ncblite.rg.repository.dao.RoleListDAO;
import cvm.ncb.adapters.ncblite.rg.repository.dao.SessionDAO;
import cvm.ncb.adapters.ncblite.rg.repository.dao.UserDAO;
import cvm.ncb.adapters.ncblite.rg.repository.util.DBConnectionFactory;

public class PMFacade
{
	private Connection conn;
	
	/*connection to the database*/
	
	public PMFacade()
	{
		conn = DBConnectionFactory.getDBConnection(DBConnectionFactory.PM_CON);
	}
	
	public void testConnection()
	{
		UserDAO.testConnection(conn);
	}
	
	/* calls the method getUserNameByUserNameSimple from userDAO*/
	
	public User getUserNameByUserNameSimple(String userName)
	{
		return UserDAO.getUserByUserNameSimple(userName, conn);
	}
	
	/*calls the method getUserByUserNameComplex from userDAO*/
	
	public User getUserByUserNameComplex(String userName)
	{
		return UserDAO.getUserByUserNameComplex(userName, conn);
	}
	
	/*calls the method updateUserByUserName from userDAO*/
	
	public boolean updateUserByUserName(User user)
	{
		return UserDAO.updateUserByUserName(user, conn);
	}
	
	/*calls the method getContactListByUserNamefrom ContactListDAO*/
	
	public List getContactListByUserName(String userName)
	{
		return ContactListDAO.getContactListByUserName(userName, conn);	
	}
	
	/*calls the method getRoleListByUserName from RoleListDAO*/
	
	public RoleList getRoleListByUserName(String userName)
	{
		return RoleListDAO.getRoleListByUserName(userName, conn);
	}
	
	/*calls the method logoutByUserName from SessionDAO*/
	
	public void logoutByUserName(String userName)
	{
		SessionDAO.logoutByUserName(userName, conn);
		
	}
	
	public List getAllContacts() {
		
		return ContactListDAO.getAllContacts(conn);
		
		
	}
	
	/*calls the method addContactToContactList from ContactListDAO*/
	
	public void addContactToContactList(String userName, String contactUserName)
	{
		ContactListDAO.addContactToContactList(userName,contactUserName,conn);
	}
	
	/*calls the method deleteContactFromContactList from  ContactListDAO*/
	
	public boolean deleteContactFromContactList(String userName, String contactUserName)                        
	{
		return ContactListDAO.deleteContactFromContactList(userName,contactUserName,conn); 
		
	}
	
	public List getContactListByUserNameComplex(String userName)
	{
		return ContactListDAO.getContactListByUserNameComplex(userName, conn);
	}
	
	public boolean isUserActive(String userName)
	{
		return SessionDAO.isUserActive(userName, conn);
	}
	
	public void shutdown()
	{
		try
		{
			conn.close();
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	public List getAllUsers() {
		
		return UserDAO.getAllUsers(conn);
		
	}
	
	
	
	//-================================================
	public boolean addApplication(String userName, String appName, String memberList)
	{
		return ApplicationInstDAO.addApplication(userName, appName, memberList, conn);
	}
	
	public boolean addMemberToApp (String appID, String memUsername)
	{
		return ApplicationInstDAO.addMemberToApp (appID, memUsername, conn);
	}

	public boolean deleteMemberFromApp(String appID, String memUsername)
	{
		return ApplicationInstDAO.deleteMemberFromApp(appID, memUsername, conn);
	}
	
	public boolean deleteApplication (String appID)
	{
		return ApplicationInstDAO.deleteApplication (appID, conn);
	}
	
	public void updateVideoStreamURL(String userName, String videoStreamURL)
	{
		UserDAO.updateVideoStreamURL(userName, videoStreamURL, conn);
	}
	
}