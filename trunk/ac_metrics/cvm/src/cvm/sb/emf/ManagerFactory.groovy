package cvm.sb.emf

import cvm.sb.resource.ResourceManager
import cvm.sb.state.StateManager
import cvm.sb.autonomic.MAPE
import cvm.sb.autonomic.MonitorSignalHandler
import cvm.sb.manager.MainManager
import cvm.sb.manager.SignalHandlerManager
import cvm.sb.policy.metadata.Metadata
import cvm.sb.policy.PolicyEvaluationSignalHandler
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
            signalHandlerManager.register(-1, new PolicyEvaluationSignalHandler(manager.policyManager.policies, manager.policyManager.points))
        }


        mainManager
    }
}
