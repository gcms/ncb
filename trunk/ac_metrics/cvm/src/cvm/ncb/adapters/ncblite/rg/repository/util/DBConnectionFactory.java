/* @author Mario J Lorenzo */ package cvm.ncb.adapters.ncblite.rg.repository.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import util.CVM_Debug;

public class DBConnectionFactory
{

	public static int PM_CON = 0;
	public static int DM_CON = 1;
	
	public static Connection getDBConnection(int type)
	{
		Connection conn = null;
		try 
		{
			String userid = "";
			String password = "";
			
			
			String driver = "sun.jdbc.odbc.JdbcOdbcDriver";
			String URL = "";
			
			if(type == PM_CON)
			{
				URL ="jdbc:odbc:pm";
			}
			else if(type == DM_CON)
			{
				URL = "jdbc:odbc:dm";
				
			}
			else
			{
				throw new Exception("ERROR invalid connection type!");
				
			}
			
			
			Class.forName(driver);
			conn = DriverManager.getConnection(URL, userid, password);
			CVM_Debug.getInstance().printDebugMessage("SUCCESSFULLY CONNECTED TO THE DATABASE !" + URL);

		} 
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
			CVM_Debug.getInstance().printDebugMessage(
			"Test ConnectionMGR Exception: " + e.toString() + ": " + e.getMessage());
		} 
		catch (SQLException s) 
		{
			s.printStackTrace();
			CVM_Debug.getInstance().printDebugMessage(
			"Test ConnectionMGR Exception: " + s.toString() + ": " + s.getMessage());
		}
		finally
		{			
			return conn;
		}

	}

}
