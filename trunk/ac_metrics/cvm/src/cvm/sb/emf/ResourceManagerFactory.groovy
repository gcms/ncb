package cvm.sb.emf

import sb.base.Instance
import sb.base.InstanceResourceManager
import sb.base.ResourceManager

class ResourceManagerFactory {
    ManagedObjectFactory objectFactory = new ManagedObjectFactory()

    public cvm.sb.resource.ResourceManager createObjectManager(ResourceManager resourceManager) {
        doCreateObjectManager(resourceManager)
    }

    private cvm.sb.resource.ResourceManager doCreateObjectManager(InstanceResourceManager resourceManager) {
        cvm.sb.resource.ResourceManager manager = new cvm.sb.resource.ResourceManager()
        resourceManager.instances.each { Instance instance ->
            manager.addObject(objectFactory.createManagedObject(instance))
        }

        manager
    }

    private cvm.sb.resource.ResourceManager doCreateObjectManager(ResourceManager resourceManager) {
        null
    }

}
