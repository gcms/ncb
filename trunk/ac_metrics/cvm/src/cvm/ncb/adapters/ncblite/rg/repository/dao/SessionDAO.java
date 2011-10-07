/* @author Mario J Lorenzo */ package cvm.ncb.adapters.ncblite.rg.repository.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import cvm.ncb.adapters.ncblite.rg.repository.session.Session;



public class SessionDAO {
	
	public static void addUserToSession(String userName, Connection conn)
	{
		Session.getInstance().setUserActive(userName);
	}
	public static void logoutByUserName(String userName, Connection conn)
	{
		Session.getInstance().setUserNotActive(userName);
	}
	
	public static boolean isUserActive(String userName, Connection conn)
	{
		return Session.getInstance().isActive(userName);

	}
	
}
