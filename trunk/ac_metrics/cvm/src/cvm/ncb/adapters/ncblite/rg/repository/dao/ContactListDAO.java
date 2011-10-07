/* @author Mario J Lorenzo */ package cvm.ncb.adapters.ncblite.rg.repository.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import cvm.ncb.adapters.ncblite.rg.repository.session.Session;

import cvm.model.Constants;
import cvm.model.Member;
import cvm.model.User;

public class ContactListDAO {
	
	
	public static List getContactListByUserName(String userName, Connection conn)
	{
		
		List contactList = new LinkedList();
		
		try {
			
			String query = "SELECT contactUserName FROM CONTACTLIST WHERE userName = ?;";
			
			// Build SQL statement...
			PreparedStatement prepStat = conn.prepareStatement(query);
			
			prepStat.setString(1, userName);
			
			// Execute SQL statement...
			ResultSet rs = prepStat.executeQuery();
			
			
			while (rs.next()) // notice that here this will only loop once, if
				// you had a list of things you would
				// collect them all here and add each to the list, then return the
				// list
			{
				Properties props = new Properties();
				props.setProperty(Constants.CONTACT_USER_NAME, rs.getString("contactUserName"));			
				contactList.add(props);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		
		return contactList;
		
	}
	
	public static void addContactToContactList(String userName,String contactUserName, Connection conn) 
	{ 
		
		//List contactList = new LinkedList(); 
		
		try { 
			
			String query = "INSERT INTO CONTACTLIST VALUES (?,?);"; 
			
//			Build SQL statement... 
			PreparedStatement prepStat = conn.prepareStatement(query); 
			
			prepStat.setString(1, userName); 
			prepStat.setString(2, contactUserName); 
			
			prepStat.execute();
			
		} catch (SQLException e) { 
			e.printStackTrace(); 
		} 
		
	} 
	
	
	public static boolean deleteContactFromContactList(String userName,String contactUserName, Connection conn) 
	{ 
		
		//List contactList = new LinkedList(); 
		
		try { 
			
			String query = "DELETE FROM CONTACTLIST WHERE userName = ? AND contactUserName = ?;"; 
			
//			Build SQL statement... 
			PreparedStatement prepStat = conn.prepareStatement(query); 
			
			prepStat.setString(2, contactUserName); 
			prepStat.setString(1, userName); 
			
			return prepStat.execute();
			
		} catch (SQLException e) { 
			e.printStackTrace(); 
		} 
		 return false;
	} 
	
	
	public static List getAllContacts(Connection conn) {
		
		List lst = new LinkedList();
		
		try {
			
			String query = "SELECT * FROM USER;";
			
			// Build SQL statement...
			PreparedStatement prepStat = conn.prepareStatement(query);
			
			
			// Execute SQL statement...
			ResultSet rs = prepStat.executeQuery();
			
			while(rs.next()) {
			
				String userName = rs.getString("userName");
				
				Member mem = new Member(userName,rs.getString("firstName"), rs.getString("lastName"), Session.getInstance().isActive(userName),rs.getString("profilePic"));
			
				lst.add(mem);
			
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return lst;
	}
	
	public static List getContactListByUserNameComplex(String userName, Connection conn)
	{
		
		List outterList = new LinkedList(); // will contain a list for each element
		
		
		List contactList = getContactListByUserName(userName, conn);
		
		Iterator itr = contactList.iterator();
		
		
		while(itr.hasNext())
		{
			String contactUserName = ((Properties)itr.next()).getProperty(Constants.CONTACT_USER_NAME);
			
			// will contain string in this order 1) Username 2)First Name 3)Last Name 4)Active?
			
			User aUser = UserDAO.getUserByUserNameSimple(contactUserName, conn);
		
			
			if(aUser != null)		
			{
				Properties props = new Properties();
				
				props.setProperty(Constants.CONTACT_USER_NAME, contactUserName);
				props.setProperty(Constants.CONTACT_FNAME, aUser.getFirstName());
				props.setProperty(Constants.CONTACT_LNAME, aUser.getLastName());
				props.setProperty(Constants.CONTACT_IS_ACTIVE, new Boolean(SessionDAO.isUserActive(contactUserName, conn)).toString());
				props.setProperty(Constants.CONTACT_PIC_URL, aUser.getProfilePic() != null? aUser.getProfilePic() : "");
				
				String host = Session.getInstance().getUserHost(contactUserName);
				
				props.setProperty(Constants.CONTACT_HOST, (host != null)? host : "");
				
				outterList.add(props);			
			}		
		}
		
	
		
		
		return outterList;
		
	}

	
} 
