package cvm.sb.emf

import cvm.ncb.ks.ResourceManager
import cvm.ncb.ks.StateManager
import cvm.ncb.oem.autonomic.MAPE
import cvm.ncb.oem.autonomic.MonitorSignalHandler
import cvm.ncb.oem.pe.MainManager
import cvm.ncb.oem.pe.SignalHandlerManager
import cvm.ncb.oem.policy.Metadata
import cvm.ncb.oem.policy.PolicyEvaluationSignalHandler
import sb.base.Manager

class ManagerFactory {
    private HandlerFactory handlerFactory = new HandlerFactory()
    private ResourceManagerFactory objectManagerFactory = new ResourceManagerFactory()
    private MetadataFactory metadataFactory = new MetadataFactory()

    MainManager createManager(Manager manager) {
        SignalHandlerManager signalHandlerManager = handlerFactory.createSignalHandlerManager(manager.handlers)
        ResourceManager resourceManager = objectManagerFactory.createObjectManager(manager.resourceManager)

        Metadata metadata = metadataFactory.createMetadata(manager)
        MainManager mainManager = new MainManager(metadata, signalHandlerManager, resourceManager, new StateManager(manager.stateManager.stateTypes))

        if (manager.autonomicManager) {
            MAPE mape = new MAPE(manager.autonomicManager, mainManager.getContext())
            signalHandlerManager.register(-1, new MonitorSignalHandler(mape.getMonitor()))
        }

        if (manager.policyManager) {
            signalHandlerManager.register(-1, new PolicyEvaluationSignalHandler(manager.policyManager.points))
        }


        mainManager
    }
}
