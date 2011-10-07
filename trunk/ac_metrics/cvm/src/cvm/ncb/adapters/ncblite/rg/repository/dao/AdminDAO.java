/* @author Mario J Lorenzo */ package cvm.ncb.adapters.ncblite.rg.repository.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class AdminDAO {

    public static void addForm(String userName,String formTypeSchema,String formInstanceSchema,String formID,String formTypeName, Connection conn) 
	{ 
		
	 	try { 
			
			String query = "INSERT INTO FORM VALUES (?,?,?,?,?);"; 
			
			//Build SQL statement... 
			PreparedStatement prepStat = conn.prepareStatement(query); 
			
			prepStat.setString(1, userName); 
			prepStat.setString(2, formTypeSchema);
			prepStat.setString(3, formInstanceSchema);
			prepStat.setString(4, formTypeName);
			prepStat.setString(5, formID) ;
			
			
			} catch (SQLException e) { 
				e.printStackTrace(); 
				} 
	} 
	
	public static void deleteForm(String userName, Connection conn){
		try{
			
			String query = "DELETE  FROM FORM WHERE userName = ?;"; 
			
			PreparedStatement prepStat = conn.prepareStatement(query); 
			prepStat.setString(1, userName); 
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
    public static void addApplication(String userName, String applicationName, String applicationSchema, Connection conn) 
	{ 
						
		try { 
			
			String query = "INSERT INTO APPLICATION VALUES (?,?,?);"; 
			
			//Build SQL statement... 
			PreparedStatement prepStat = conn.prepareStatement(query); 
			prepStat.setString(1, userName); 
			prepStat.setString(2, applicationName);
			prepStat.setString(3, applicationSchema);
			} catch (SQLException e) { 
				e.printStackTrace(); 
			} 
	} 
		
    public static void deleteApplication(String userName, Connection conn) 
	{ 
						
		try { 
			
			String query = "DELETE  FROM CONTACTLIST WHERE userName = ?;"; 
			
			//Build SQL statement... 
			PreparedStatement prepStat = conn.prepareStatement(query); 
			prepStat.setString(1, userName); 
						
			} catch (SQLException e) { 
				e.printStackTrace(); 
			} 
	} 
}
