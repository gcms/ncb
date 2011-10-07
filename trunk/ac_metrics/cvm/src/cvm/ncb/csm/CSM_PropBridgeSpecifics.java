package cvm.ncb.csm;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

import cvm.model.*;

/**
 * Bridge specific property file.
 * @author Frank Hernandez
 *
 */
public class CSM_PropBridgeSpecifics
{
	public static final String SB_USERNAME = "sb_username";
	public static final String SB_PASWORD = "sb_password";
	
	private Properties p;
	private File configFile;
	
	CSM_PropBridgeSpecifics(String configFilename)
	{
		p = new CSM_PBSDefault();		
		try {
			configFile = new File(configFilename);
			if (!configFile.exists()){
				p.store(new FileOutputStream(configFile), "Default Values. Change them accordingly.");
				CVM_Debug.getInstance().printDebugErrorMessage("Config file not loaded. Change Defaults");				
			}
			else {
				p.load(new FileInputStream(configFile));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}	
		
	}
	/**
	 * This function returns the value of the property.
	 * @param prop
	 * @return
	 */
	public String getProperty(String prop) 
	{
		return p.getProperty(prop);
	}
	
	public String toString() 
	{
		return p.toString();
	}
}
