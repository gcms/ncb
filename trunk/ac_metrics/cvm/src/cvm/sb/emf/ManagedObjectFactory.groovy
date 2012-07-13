package cvm.sb.emf

import cvm.sb.adapters.Manageable
import cvm.sb.resource.bridge.BridgeResource
import cvm.sb.policy.metadata.Metadata
import sb.base.Instance

class ManagedObjectFactory {
    private MetadataFactory metadataFactory = new MetadataFactory()

    public BridgeResource createManagedObject(Instance instance) {
        Manageable manageable = Class.forName(instance.impl).newInstance()
        Metadata metadata = metadataFactory.createMetadata(instance)

        new BridgeResource(metadata, manageable)
    }

}
