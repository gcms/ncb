/* @author Mario J Lorenzo */ package cvm.ncb.adapters.ncblite.rg.repository.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import cvm.model.RoleList;

public class RoleListDAO {
	
	
	public static RoleList getRoleListByUserName(String userName, Connection conn)
	{
		
		List roleList = new LinkedList();
		
		try {
			
			String query = "SELECT roleType FROM ROLELIST WHERE userName = ?;";
			
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
				
				roleList.add(rs.getString("roleType"));			
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return new RoleList(roleList);
		
	}
	
}