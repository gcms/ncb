package cvm.ncb.csm;

import cvm.ncb.adapters.*;
import cvm.ncb.handlers.NCBEventObjectManager;
import cvm.ncb.ks.CommFWCapabilitySet;
import cvm.ncb.oem.policy.Framework;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles the creation of communication objects
 * This class generaters an instance each communication bridge
 * with the property files.
 *
 * @author Frank Hernandez
 */
public class CommObjectManager {
    private static Log log = LogFactory.getLog(CommObjectManager.class);

    private ArrayList<CommObject> m_coContainer;

    /*
      * 1 - Get network bridge list from CSM_Propert file.
      * 2 - For every bridge.
      * 		I - Create a communication object
      * 		II - Add communication object to the list.
      */
    CommObjectManager(NCBEventObjectManager eventObjectManager) {
        log.info("Creating CommObjectManager instance");
        m_coContainer = new ArrayList<CommObject>();

        for (String bridgeName : getBridgeNames()) {
            try {
                log.info("Creating communication object: " + bridgeName);

                CommObject commObject = createCommunicationObject(bridgeName);
                commObject.getBridge().setEventObjectManager(eventObjectManager);
                m_coContainer.add(commObject);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (IllegalAccessException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (InstantiationException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }

        }
    }

    private static Iterable<String> getBridgeNames() {
        List<String> bridgeNames = new ArrayList<String>();
        for (Framework framework : CommFWCapabilitySet.getInstance().getAvailableFrameworks()) {
            bridgeNames.add(framework.getName());
        }

        return bridgeNames;
    }

    /**
     * This function returns a copy of the list of communication objects.
     * The list contains all the communication objects that
     * were created from the property file. This communication objects
     * in turn contain an instance an adapter.
     *
     * @return List of communicaion objects.
     * @see CommObject
     */
    public ArrayList<CommObject> getCommObjectList() {
        return m_coContainer;
    }

    /**
     * This function returns the currently selected communication object.
     *
     * @return
     */
    public CommObject getCommunicationObject(String fwName) {
        for (CommObject comObj : getCommObjectList()) {
            if (comObj.getName().equals(fwName)) {
                return comObj;
            }
        }
        return null;
    }

    private CommObject createCommunicationObject(String fwName) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        CSM_NBTypes type = CSM_NBTypes.getType(fwName);

        NCBBridge adapter = (NCBBridge) type.getAdapterClass().newInstance();
        NCBBridge bridge = NCBBridgeProxy.wrap(adapter);
        return new CommObject(bridge);
    }

}