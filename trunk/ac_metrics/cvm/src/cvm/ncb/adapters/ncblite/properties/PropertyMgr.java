/* @author Mario J Lorenzo */ package cvm.ncb.adapters.ncblite.properties;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import cvm.model.*;

public class PropertyMgr 
{

	private Properties props;
	
	private static PropertyMgr target = null;
	
	public static PropertyMgr getInstance()
	{
		if(target == null) target = new PropertyMgr();
		
		return target;
	}
	
	private PropertyMgr()
	{
		props = new Properties();
		try {
			props.load(getClass().getResourceAsStream("nativeserver.properties"));
			
			CVM_Debug.getInstance().printDebugMessage(props.toString());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getValue(String key)
	{
		
		return props.getProperty(key);
	}
	
	
	
	
}
