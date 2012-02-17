/* @author Mario J Lorenzo */ package cvm.ncb.adapters.ncblite.rg.repository.facades;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import cvm.ncb.adapters.ncblite.model.Form;
import cvm.ncb.adapters.ncblite.rg.repository.dao.ApplicationDAO;
import cvm.ncb.adapters.ncblite.rg.repository.dao.FormDAO;
import cvm.ncb.adapters.ncblite.rg.repository.util.DBConnectionFactory;

public class DMFacade
{
	private Connection conn;
	
	public DMFacade()
	{
		conn = DBConnectionFactory.getDBConnection(DBConnectionFactory.DM_CON);
	}
	
	public List getApplicationList(String userName)
	{
		return ApplicationDAO.getApplicationList(userName, conn);
	}
	
	
	public String getApplication(String userName,String applicationName)
	{
		return ApplicationDAO.getApplicationSchemaByUserNameAndApplicationName(userName,applicationName,conn);
	}
	
	
	public Form getFormTypeSchemaByUserName(String userName)
	{
		return FormDAO.getFormTypeSchemaByUserName(userName, conn);
	}
	
	
	public Form  getFormInstanceSchemaByUserName(String userName)
	{
		return FormDAO.getFormInstanceSchemaByUserName(userName, conn);
	}
	
	public Form loadFormTypeSchema(String userName,String formTypeName)
	{
		return FormDAO.getFormTypeSchemaByUserNameAndFormTypeName(userName,formTypeName, conn);
	}
	
	
	public Form loadFormInstanceSchema(String userName, String formID)
	{
		return FormDAO.getFormTypeSchemaByUserNameAndFormID(userName, formID, conn);
	}
	
	 
	public void shutdown()
	{
		try
		{
			conn.close();
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}

