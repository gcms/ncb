package cvm.ncb.ks;

import cvm.model.CVM_Debug;
import cvm.ncb.adapters.NCBUserInfoStore;
import cvm.ncb.adapters.NCBUserMapper;
import cvm.ncb.csm.CSM_NBTypes;
import cvm.ncb.csm.CSM_PropBridgeSpecifics;

public class UserIDMappingTable implements NCBUserMapper, NCBUserInfoStore {

    private static UserIDMappingTable instance = null;

    private UserIDMappingTable() {

    }

    public static UserIDMappingTable getInstance() {
        if (instance == null)
            instance = new UserIDMappingTable();
        return instance;
    }

    public String lookupContact(String fwName, String ncbName) {
        CVM_Debug.getInstance().printDebugMessage("User lookup for " + ncbName + " on fw " + fwName);
        if (fwName.startsWith("Mock"))
            return ncbName;

        if (ncbName.equals("Andrew") && fwName.equals("Skype"))
            return "geapis1";
        if (ncbName.equals("Yali") && fwName.equals("Skype"))
            return "geapis2";
        if (ncbName.equals("Tariq") && fwName.equals("Skype"))
            return "geapis3";

        if (ncbName.equals("Andrew") && fwName.equals("Smack"))
            return "geapis1@gustavosousa-pc";
        if (ncbName.equals("Yali") && fwName.equals("Smack"))
            return "geapis2@gustavosousa-pc";
        if (ncbName.equals("Tariq") && fwName.equals("Smack"))
            return "geapis3@gustavosousa-pc";

        if (ncbName.equals("Andrew") && fwName.equals("Skype"))
            return "andrew.a.allen";
        if (ncbName.equals("Andrew") && fwName.equals("Smack"))
            return "test@squire.cs.fiu.edu";
        if (ncbName.equals("Andrew") && fwName.equals("Asterisk"))
            return "demo5@asterisk.cs.fiu.edu";
        if (ncbName.equals("Tariq") && fwName.equals("Skype"))
            return "ecs.212c";
        if (ncbName.equals("Tariq") && fwName.equals("Smack"))
            return "test1@squire.cs.fiu.edu";
        if (ncbName.equals("Tariq") && fwName.equals("Asterisk"))
            return "demo1@asterisk.cs.fiu.edu";
        if (ncbName.equals("Yali") && fwName.equals("Skype"))
            return "ecs.212b";
        if (ncbName.equals("Yali") && fwName.equals("Smack"))
            return "test2@squire.cs.fiu.edu";
        if (ncbName.equals("Yali") && fwName.equals("Asterisk"))
            return "demo2@asterisk.cs.fiu.edu";
        if (ncbName.equals("Yingbo") && fwName.equals("Skype"))
            return "ecs-212";
        if (ncbName.equals("Yingbo") && fwName.equals("Smack"))
            return "test3@squire.cs.fiu.edu";
        if (ncbName.equals("Yingbo") && fwName.equals("Asterisk"))
            return "demo3@asterisk.cs.fiu.edu";
        if (ncbName.equals("Karl") && fwName.equals("Skype"))
            return "ecs.212a";
        if (ncbName.equals("Karl") && fwName.equals("Smack"))
            return "test4@squire.cs.fiu.edu";
        if (ncbName.equals("Karl") && fwName.equals("Asterisk"))
            return "demo44@asterisk.cs.fiu.edu";
        return "";
    }

    public CSM_PropBridgeSpecifics getProperties(String fwName) {
        CSM_NBTypes type = CSM_NBTypes.getType(fwName);
        return new CSM_PropBridgeSpecifics(type.getConfigFile());
    }

    public String getFwUserName(String fwName) {
        CSM_PropBridgeSpecifics tempProp = getProperties(fwName);
        return tempProp == null ? null : tempProp.getUserName();
    }

    public String getFwPassword(String fwName) {
        CSM_PropBridgeSpecifics tempProp = getProperties(fwName);
        return tempProp == null ? null : tempProp.getPassword();
    }
}
