/* @author Mario J Lorenzo */ package cvm.ncb.adapters.ncblite.rg.repository.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class ApplicationDAO {
	
	public static List getApplicationList(String userName, Connection conn)
	{
		List applicationName = new LinkedList();
		
		try{
			String query = "SELECT applicationName FROM APPLICATION WHERE userName = ?;"; 
			
			PreparedStatement prepStat = conn.prepareStatement(query);
			
			prepStat.setString(1, userName);
			
			ResultSet rs = prepStat.executeQuery();
			while(rs.next())
			{
				applicationName.add(rs.getString("applicationName"));
			}
			
		}catch (SQLException e) {
			e.printStackTrace();
		} 
		return (applicationName);
	}
	
	public static String getApplicationSchemaByUserNameAndApplicationName(String userName, String applicationName, Connection conn)
	{
		String schema = null;
		
		try{
			String query = "SELECT applicationSchema FROM APPLICATION WHERE userName = ? AND applicationName = ?;";
			
			PreparedStatement prepStat = conn.prepareStatement(query);
			
			prepStat.setString(1,userName);
			prepStat.setString(2,applicationName);
			
			ResultSet rs = prepStat.executeQuery();
			
			if(rs.next())
						
			schema = (rs.getString("applicationSchema"));
			
		}catch (SQLException e) {
			e.printStackTrace();
		} 
		
		return schema;
		
	}
	
	
}
