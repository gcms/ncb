package cvm.sb.emf

import cvm.ncb.adapters.Manageable
import cvm.ncb.csm.ManagedObject
import cvm.ncb.oem.policy.Metadata
import sb.base.Instance

class ManagedObjectFactory {
    private MetadataFactory metadataFactory = new MetadataFactory()

    public ManagedObject createManagedObject(Instance instance) {
        Manageable manageable = Class.forName(instance.impl).newInstance()
        Metadata metadata = metadataFactory.createMetadata(instance)

        new ManagedObject(metadata, manageable)
    }

}
