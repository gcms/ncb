package cvm.service;

import java.util.Properties;

/**
 * Defual property file for the bridge specific property files.
 * @author Frank Hernandez
 *
 */
class CSM_PBSDefault extends Properties
{

	public CSM_PBSDefault()
	{
		this.setProperty(CSM_PropBridgeSpecifics.SB_USERNAME, "crinsomkairos");
		this.setProperty(CSM_PropBridgeSpecifics.SB_PASWORD, "Maxwell");
	}
	
}
