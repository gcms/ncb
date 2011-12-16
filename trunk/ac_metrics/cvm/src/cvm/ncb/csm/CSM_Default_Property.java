package cvm.ncb.csm;

import java.util.Properties;

//import cvm.ucm.repository.Rep_Properties;
//import cvm.ucm.repository.RepositoryType;

/**
 * Used for loading data when no property file is found.
 *
 * @author Frank Hernandez
 */
public class CSM_Default_Property extends Properties {

    public CSM_Default_Property() {
        this.setProperty(CSM_Properties.SB_BRIDGES, CSM_NBTypes.SKYPE.getFwName());
    }

}
