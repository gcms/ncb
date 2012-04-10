package cvm.service;

import util.CVM_Debug;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

/**
 * Bridge specific property file.
 * @author Frank Hernandez
 *
 */
class CSM_PropBridgeSpecifics
{
	public static final String SB_USERNAME = "sb_username";
	public static final String SB_PASWORD = "sb_password";
	
	private Properties p;

    public CSM_PropBridgeSpecifics(String configFilename)
	{
		p = new CSM_PBSDefault();		
		try {
            File configFile = new File(configFilename);
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

    public String getUserName() {
        return getProperty(SB_USERNAME);
    }

    public String getPassword() {
        return getProperty(SB_PASWORD);
    }
}
