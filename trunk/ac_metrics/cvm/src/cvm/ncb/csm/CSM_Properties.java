package cvm.ncb.csm;

import cvm.model.CVM_Debug;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

/**
 * Basic property file for loading Bridges
 * @author Frank Hernandez
 *
 */
public class CSM_Properties 
{
	public static final String SB_BRIDGES = "sb_bridges";
	
	private Properties p;
	private String m_sFileName = "birdeges.config.properties";
	private File configFile;
	
	public CSM_Properties() 
	{
		p = new CSM_Default_Property();		
		try {
			configFile = new File(m_sFileName);
			if (!configFile.exists()){
				p.store(new FileOutputStream(configFile), "Default Values. Change them accordingly. Repository Types: " + CSM_NBTypes.SKYPE + ", " + CSM_NBTypes.GOOGLETALK + ", " + CSM_NBTypes.JMML + ", "+ CSM_NBTypes.NCBNATIVE);
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

    public String getBridges() {
        return getProperty(SB_BRIDGES);
    }
	
}
