package cvm.ncb.ks;

import cvm.model.*;

public class UserIDMappingTable {

	private static UserIDMappingTable instance = null;
	
	private UserIDMappingTable() {
		
	}
	
	public static UserIDMappingTable getInstance() {
		if(instance == null) 
			instance = new UserIDMappingTable();
		return instance;
	}
	public String lookupContact(String fwName, String ncbName){
		CVM_Debug.getInstance().printDebugMessage("User lookup for "+ncbName+" on fw "+fwName);
		if(ncbName.equals("Andrew") && fwName.equals("Skype"))
			return "andrew.a.allen";
		if(ncbName.equals("Andrew") && fwName.equals("Smack"))
			return "test@squire.cs.fiu.edu";
		if(ncbName.equals("Andrew") && fwName.equals("Asterisk"))
			return "demo5@asterisk.cs.fiu.edu";
		if(ncbName.equals("Tariq") && fwName.equals("Skype"))
			return "ecs.212c";
		if(ncbName.equals("Tariq") && fwName.equals("Smack"))
			return "test1@squire.cs.fiu.edu";
		if(ncbName.equals("Tariq") && fwName.equals("Asterisk"))
			return "demo1@asterisk.cs.fiu.edu";
		if(ncbName.equals("Yali") && fwName.equals("Skype"))
			return "ecs.212b";
		if(ncbName.equals("Yali") && fwName.equals("Smack"))
			return "test2@squire.cs.fiu.edu";
		if(ncbName.equals("Yali") && fwName.equals("Asterisk"))
			return "demo2@asterisk.cs.fiu.edu";
		if(ncbName.equals("Yingbo") && fwName.equals("Skype"))
			return "ecs-212";
		if(ncbName.equals("Yingbo") && fwName.equals("Smack"))
			return "test3@squire.cs.fiu.edu";
		if(ncbName.equals("Yingbo") && fwName.equals("Asterisk"))
			return "demo3@asterisk.cs.fiu.edu";
		if(ncbName.equals("Karl") && fwName.equals("Skype"))
			return "ecs.212a";
		if(ncbName.equals("Karl") && fwName.equals("Smack"))
			return "test4@squire.cs.fiu.edu";
		if(ncbName.equals("Karl") && fwName.equals("Asterisk"))
			return "demo44@asterisk.cs.fiu.edu";
		return "";
	}
	
}
