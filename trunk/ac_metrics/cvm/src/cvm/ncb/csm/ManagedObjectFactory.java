package cvm.ncb.csm;

import cvm.ncb.adapters.EventNotifier;
import cvm.ncb.adapters.Manageable;
import cvm.ncb.oem.policy.Metadata;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Handles the creation of communication objects
 * This class generaters an instance each communication bridge
 * with the property files.
 *
 * @author Frank Hernandez
 */
public class ManagedObjectFactory {
    private static Log log = LogFactory.getLog(ManagedObjectFactory.class);

    private EventNotifier eventManager;

    public ManagedObjectFactory(EventNotifier eventManager) {
        this.eventManager = eventManager;
    }

    public ManagedObject createCommunicationObject(Metadata metadata) {
        log.info("Creating managed object: " + metadata.getName());

        try {
            CSM_NBTypes type = CSM_NBTypes.getType(metadata.getName());
            Manageable bridge = type.createBridge();
            bridge.setEventNotifier(eventManager);
            return new ManagedObject(bridge, metadata);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}