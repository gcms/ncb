package cvm.ncb.csm;

import java.util.HashMap;
import java.util.Map;

/**
 * /**
 * This class holds the definition of the different
 * types of networks bridges.
 *
 * @author fhern006
 */
public class CSM_NBTypes {
    private static Map<String, CSM_NBTypes> instances = new HashMap<String, CSM_NBTypes>();

    public static final CSM_NBTypes SKYPE = new CSM_NBTypes("Skype", "skype.config");
    public static final CSM_NBTypes GOOGLETALK = new CSM_NBTypes("GTalk", "gtalk.config");
    public static final CSM_NBTypes SMACK = new CSM_NBTypes("Smack", "smack.config");
    public static final CSM_NBTypes ASTERISK = new CSM_NBTypes("Asterisk", "peers.config");
    public static final CSM_NBTypes JMML = new CSM_NBTypes("JMML", "jmml.config");
    public static final CSM_NBTypes NCBNATIVE = new CSM_NBTypes("NCBNAT", "ncbnat.config");

    public static void registerFramework(String fwName, String configFile) {
        new CSM_NBTypes(fwName, configFile);
    }

    private String fwName;
    public String configFile;

    CSM_NBTypes(String fwName, String configFile) {
        this.fwName = fwName;
        this.configFile = configFile;
        instances.put(fwName, this);
    }

    public String getFwName() {
        return fwName;
    }

    public String getConfigFile() {
        return configFile;
    }

    public static CSM_NBTypes getType(String fwName) {
        return instances.get(fwName);
    }

    public Class getAdapterClass() throws ClassNotFoundException {
        return Class.forName("cvm.ncb.adapters." + fwName + "Adapter");
    }
}
