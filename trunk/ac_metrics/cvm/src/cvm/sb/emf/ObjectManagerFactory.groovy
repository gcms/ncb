package cvm.sb.emf

import sb.base.Instance
import sb.base.InstanceResourceManager
import sb.base.ResourceManager

class ObjectManagerFactory {
    ManagedObjectFactory objectFactory = new ManagedObjectFactory()

    public cvm.ncb.ks.ResourceManager createObjectManager(ResourceManager resourceManager) {
        doCreateObjectManager(resourceManager)
    }

    private cvm.ncb.ks.ResourceManager doCreateObjectManager(InstanceResourceManager resourceManager) {
        cvm.ncb.ks.ResourceManager manager = new cvm.ncb.ks.ResourceManager()
        resourceManager.instances.each { Instance instance ->
            manager.addObject(objectFactory.createManagedObject(instance))
        }

        manager
    }

    private cvm.ncb.ks.ResourceManager doCreateObjectManager(ResourceManager resourceManager) {
        null
    }

}
