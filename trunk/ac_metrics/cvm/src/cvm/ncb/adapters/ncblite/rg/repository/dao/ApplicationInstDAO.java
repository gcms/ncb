/* @author Mario J Lorenzo */ package cvm.ncb.adapters.ncblite.rg.repository.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;
import java.util.StringTokenizer;

import util.CVM_Debug;

public class ApplicationInstDAO 
{
	
	public static boolean addApplication(String userName, String appName, String memberList,Connection conn)
	{
		//System.out.print("addApplication()|");
		String id = userName+"."+new Random().nextInt(99999);
		
		
		try
		{
			
			String update = "INSERT INTO APPLICATIONTYPE VALUES( ?, ?, ?);";
			
			
			PreparedStatement stmt = conn.prepareStatement(update);
			
			stmt.setString(1, id);
			stmt.setString(2, appName);
			stmt.setString(3, userName);
			
			//System.out.print(stmt.toString()+"|");
			
			if(stmt.executeUpdate() > 0) //created new application here
			{
				//System.out.print("app added|");
				//do we have nested member list?
				if(memberList != null && !memberList.equals(""))
				{
					
					
					StringTokenizer tkr = new StringTokenizer(memberList,",");
					
					
					while(tkr.hasMoreTokens())
					{
						
						
						String memUserName = tkr.nextToken().trim();
						
						addMemberToApp(id,memUserName,conn);
						
						//System.out.print("app mem added|");
/*
						
						update = "INSERT INTO MEMBER VALUES(?, ?);";
						stmt = conn.prepareStatement(update);
						stmt.setString(1, memUserName);
						stmt.setString(2, id);
						
						if (stmt.executeUpdate() > 0) 
						{
							System.out.print("app mem added|");
						}				
						else
						{//TODO need to do rollback here
							
						}
*/						
					}					
				}
				
				
				return true;
			}
		
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		return false;
		
	}
	
	public static boolean addMemberToApp (String appID, String memUsername, Connection conn)
	{
		try
		{
			
				String update = "INSERT INTO MEMBER VALUES(?, ?);";
				
				PreparedStatement stmt = conn.prepareStatement(update);
				
				stmt = conn.prepareStatement(update);
				stmt.setString(1, memUsername);
				stmt.setString(2, appID);
				
				if (stmt.executeUpdate() > 0) 
				{	
					return true;
				}				
				
		}
		catch(SQLException e)
		{
			//e.printStackTrace();
		}
		
		return false;
	}
	public static boolean deleteMemberFromApp(String appID, String memUsername, Connection conn)
	{
		try
		{
			
				String update = "DELETE FROM MEMBER WHERE userName = ? AND appID = ?;";
				
				PreparedStatement stmt = conn.prepareStatement(update);
				
				stmt = conn.prepareStatement(update);
				stmt.setString(1, memUsername);
				stmt.setString(2, appID);
				
				if (stmt.executeUpdate() > 0) 
				{	
					//TODO delete after testing
					CVM_Debug.getInstance().printDebugMessage(memUsername + " deleted");
					return true;
				}				
				
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			
		}
		//TODO delete after testing
		CVM_Debug.getInstance().printDebugMessage(memUsername + " is not a member.");
		return false;
	}
	
	public static boolean deleteApplication (String appID, Connection conn)
	{
		try
		{
		
			String update = "DELETE FROM APPLICATIONTYPE WHERE id = ?;";
			
			PreparedStatement stmt = conn.prepareStatement(update);
			
			stmt = conn.prepareStatement(update);
			stmt.setString(1, appID);
			
			if (stmt.executeUpdate() > 0) 
			{	
				return true;
			}				
		}
		catch (SQLException e)
		{
			
		}
		
		return false;
	}
/*		
	private static List getApplicationTypesByUserName(String userName, Connection conn)
	{ 
		List appTypeList = new LinkedList();
		
		try{
			String query = "SELECT * FROM APPLICATIONTYPE WHERE userName = ?;"; 
			
			PreparedStatement prepStat = conn.prepareStatement(query);
			
			prepStat.setString(1, userName);
			
			ResultSet rs = prepStat.executeQuery();
						
			
			while(rs.next())
			{
				appTypeList.add(new Application(rs.getString("id"), rs.getString("name")));
			}
			
		}catch (SQLException e) {
			e.printStackTrace();
			
		} 
		return appTypeList;

	}
	*/
	/*
	private static List getMemberListByAppID(String appID, Connection conn)
	{
		List memberList = new LinkedList();
		
		try{
			String query = "SELECT * FROM MEMBER, USER WHERE MEMBER.appID = ? AND USER.userName = MEMBER.userName;"; 
			
			PreparedStatement prepStat = conn.prepareStatement(query);
			
			prepStat.setString(1, appID);
			
			ResultSet rs = prepStat.executeQuery();						
			
			while(rs.next())
			{
				String userName = rs.getString("userName");
				
				memberList.add(
						new Member(userName, 
							rs.getString("firstName"),
							rs.getString("lastName"),
							SessionDAO.isUserActive(userName, conn),
							rs.getString("profilePic")));
			}
			
		}catch (SQLException e) {
			e.printStackTrace();
			
		} 
		return memberList;

	}
	*/
}
