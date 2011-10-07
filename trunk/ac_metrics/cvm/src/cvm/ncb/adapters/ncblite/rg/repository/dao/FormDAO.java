/* @author Mario J Lorenzo */ package cvm.ncb.adapters.ncblite.rg.repository.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import cvm.model.Form;

public class FormDAO {
	
	public static Form getFormTypeSchemaByUserName(String userName, Connection conn)
	{
		Form typeSchema = null;
		
		
		try {
			
			String query = "SELECT formTypeSchema FROM FORM WHERE userName=?;";
			
			// Build SQL statement...
			PreparedStatement prepStat = conn.prepareStatement(query);
			
			prepStat.setString(1, userName);
			
			// Execute SQL statement...
			ResultSet rs = prepStat.executeQuery();
			
			
			if(rs.next())
				typeSchema = new Form(rs.getString("formTypeSchema"));			
			
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return typeSchema;
	}
	
	public static Form getFormInstanceSchemaByUserName(String userName, Connection conn)
	{
		Form instanceSchema = null;
		
		
		try {
			
			String query = "SELECT formInstanceSchema FROM FORM WHERE userName=?;";
			
			// Build SQL statement...
			PreparedStatement prepStat = conn.prepareStatement(query);
			
			prepStat.setString(1, userName);
			
			// Execute SQL statement...
			ResultSet rs = prepStat.executeQuery();
			
			
			if(rs.next())
				instanceSchema = new Form(rs.getString("formInstanceSchema"));			
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return instanceSchema;
	}
	
	public static Form getFormTypeSchemaByUserNameAndFormTypeName(String userName, String formTypeName, Connection conn)
	{
		Form typeSchema = null;
		
		try{
			
			String query = "SELECT formTypeSchema from FORM WHERE userName=? and formTypeName=?;";
			
			PreparedStatement prepStat = conn.prepareStatement(query);
			
			prepStat.setString(1, userName);
			prepStat.setString(2, formTypeName);
			
			ResultSet rs = prepStat.executeQuery();
			
			
			rs.next();
			
			typeSchema = new Form(rs.getString("formTypeSchema"));
			
		}catch (SQLException e) {
			e.printStackTrace();
			
		}
		
		return typeSchema;
	}
	
	public static Form getFormTypeSchemaByUserNameAndFormID(String userName, String formID, Connection conn)
	{
		Form instanceSchema = null;
		
		try{
			
			String query = "SELECT formInstanceSchema FROM Form WHERE userName=? AND formID=?;";
			
			PreparedStatement prepStat = conn.prepareStatement(query);
			
			prepStat.setString(1, userName);
			prepStat.setString(2, formID);
			
			ResultSet rs = prepStat.executeQuery();
			
			rs.next();
			
			instanceSchema = new Form(rs.getString("formInstanceSchema"));
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
		
		return instanceSchema;
	}
	
	
}

