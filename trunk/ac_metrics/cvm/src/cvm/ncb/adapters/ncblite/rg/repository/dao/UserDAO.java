/* @author Mario J Lorenzo */ package cvm.ncb.adapters.ncblite.rg.repository.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import cvm.ncb.adapters.ncblite.model.User;
import util.CVM_Debug;

public class UserDAO {
	
	public static void updateVideoStreamURL(String userName, String videoStreamURL, Connection conn)
	{
		
		try
		{
			String update = "UPDATE USER SET videoStreamURL = ? WHERE userName = ?;";
			
			PreparedStatement stmt = conn.prepareStatement(update);
			
			stmt = conn.prepareStatement(update);
			stmt.setString(1, videoStreamURL);
			stmt.setString(2, userName);
			
			if (stmt.executeUpdate() > 0) 
			{	
				
			}	
			
		}
		catch (SQLException e1)
		{
			e1.printStackTrace();
		}		
		
		
	}
	
	public static void testConnection(Connection conn)
	{
		
	try {
			
			String query = "SELECT * FROM USER;";
			
			// Build SQL statement...
			PreparedStatement prepStat = conn.prepareStatement(query);
			
			// Execute SQL statement...
			ResultSet rs = prepStat.executeQuery();
						
			CVM_Debug.getInstance().printDebugMessage("Recived DB Probe Test: Connection Successful");
			
		} catch (SQLException e) {
			CVM_Debug.getInstance().printDebugMessage("Recived DB Probe Test: Connection Failed");
		}
		
		
		
	}
	
	
	
	public static User getUserByUserNameSimple(String userName, Connection conn) {
		
		User theUser = null;
		
		try {
			
			String query = "SELECT * FROM USER WHERE userName = ?;";
			
			// Build SQL statement...
			PreparedStatement prepStat = conn.prepareStatement(query);
			
			prepStat.setString(1, userName);
			
			// Execute SQL statement...
			ResultSet rs = prepStat.executeQuery();
			
			rs.next();
			
			theUser = new User(rs.getString("firstName"), rs.getString("lastName"), rs.getString("password"), rs.getString("profilePic") , rs.getString("userName"),Boolean.toString(rs.getBoolean("voiceEnable")),rs.getString("videoStreamURL"));
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return theUser;
	}
	
	
	public static User getUserByUserNameComplex(String userName, Connection conn) {
		
		User theUser = null;
		
		try {
			
			String query = "SELECT * FROM USER WHERE userName = ?;";
			
			// Build SQL statement...
			PreparedStatement prepStat = conn.prepareStatement(query);
			
			prepStat.setString(1, userName);
			
			// Execute SQL statement...
			ResultSet rs = prepStat.executeQuery();
			
			if(rs.next())
			
			theUser = new User(rs.getString("firstName"), rs.getString("lastName"), rs.getString("password"), rs.getString("profilePic") , rs.getString("userName"),rs.getString("voiceEnable"),rs.getString("videoStreamURL"));
			
			
			
			theUser.setContactList(ContactListDAO.getContactListByUserNameComplex(userName, conn));
			
			theUser.setRoleList(RoleListDAO.getRoleListByUserName(userName,conn));
			
			//TODO
			//theUser.setApplicationList(ApplicationInstDAO.getApplicationListByUserName(userName, conn));			
			theUser.setApplicationList(new LinkedList());
	
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		
		
		
		
		return theUser;
	}
	
	
	public static List getAllUsers(Connection conn) {
		
		User theUser = null;
		List users = new LinkedList();
		
		try {
			
			String query = "SELECT * FROM USER WHERE;";
			
			// Build SQL statement...
			PreparedStatement prepStat = conn.prepareStatement(query);
				
			// Execute SQL statement...
			ResultSet rs = prepStat.executeQuery();
			
			while(rs.next()) {
			
			theUser = new User(rs.getString("firstName"), rs.getString("lastName"), rs.getString("password"), rs.getString("profilePic") , rs.getString("userName"),rs.getString("voiceEnable"),rs.getString("videoStreamURL"));
			
			theUser.setContactList(ContactListDAO.getContactListByUserNameComplex(theUser.getUserName(), conn));
			
			theUser.setRoleList(RoleListDAO.getRoleListByUserName(theUser.getUserName(),conn));
		
			theUser.setApplicationList(new LinkedList());
			
			users.add(theUser);
	
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		
		
		
		
		return users;
	}

	
	public static boolean updateUserByUserName(User user, Connection conn)
	{
		
		try {
			
			String query = "UPDATE USER SET password = ?, firstName = ?, lastName = ?, voiceEnable = ?, profilePic = ? WHERE userName = ?;";
			
			// Build SQL statement...
			PreparedStatement prepStat = conn.prepareStatement(query);
			
			prepStat.setString(1,user.getPassword());
			prepStat.setString(2,user.getFirstName());
			prepStat.setString(3,user.getLastName());
			prepStat.setBoolean(4,user.getVoiceEnable().equals("true"));
			prepStat.setString(5,user.getProfilePic());
			prepStat.setString(6,user.getUserName());
						
			// Execute SQL statement...
			if(prepStat.executeUpdate() > 0) return true;
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
}

