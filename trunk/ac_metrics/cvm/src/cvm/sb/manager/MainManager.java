package cvm.sb.manager;

import cvm.sb.expression.ValueEvaluator;
import cvm.sb.policy.metadata.Metadata;
import cvm.sb.resource.AbstractTouchpoint;
import cvm.sb.resource.EventListener;
import cvm.sb.resource.ResourceManager;
import cvm.sb.resource.Touchpoint;
import cvm.sb.state.StateManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MainManager extends AbstractTouchpoint implements EventListener {
    private static Log log = LogFactory.getLog(MainManager.class);

    private SignalHandlerManager signalHandlerManager;
    private ResourceManager resourceManager;
    private StateManager stateManager;

    public MainManagerContext getContext() {
        return new MainManagerContext();
    }

    private class MainManagerContext implements ManagerContext {

        public StateManager getStateManager() {
            return MainManager.this.getStateManager();
        }

        public MainManager getMainManager() {
            return MainManager.this;
        }

        public ResourceManager getResourceManager() {
            return MainManager.this.getResourceManager();
        }
    }

    public MainManager(Metadata metadata, SignalHandlerManager signalHandlerManager, ResourceManager resourceManager,
                       StateManager stateManager) {
        super(metadata);
        this.signalHandlerManager = signalHandlerManager;
        this.resourceManager = resourceManager;
        this.stateManager = stateManager;
        ValueEvaluator.stateManager = stateManager;
    }

    public ResourceManager getResourceManager() {
        return resourceManager;
    }

    public StateManager getStateManager() {
        return stateManager;
    }

    public void start() {
        resourceManager.setEventListener(this);
        resourceManager.start();
        super.start();
    }

    public void stop() {
        super.stop();
        resourceManager.stop();
    }

    /**
     * evaluating the signal against the frameworks to
     * find the appropriate framework
     */
    public Object execute(SignalInstance signal) {
        log.debug("Evaluating: " + signal);

//        evaluatePolicies(signal);
        HandlingResult result = signalHandlerManager.handle(signal, getContext());
        if (result != null)
            return result.getResult();

        if (getEventListener() != null)
            getEventListener().notify(signal);
        log.warn("Signal [" + signal + "] not handled!");

        return null;
    }

    public void throwEvent(SignalInstance e) {
        execute(e);
    }

    public void notify(SignalInstance event) {
        enqueue(event.getSource(), event.getName(), event.getParams());
    }
}
