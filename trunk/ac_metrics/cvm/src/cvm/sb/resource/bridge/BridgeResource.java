package cvm.sb.resource.bridge;

import cvm.sb.adapters.Event;
import cvm.sb.adapters.EventException;
import cvm.sb.adapters.EventNotifier;
import cvm.sb.adapters.Manageable;
import cvm.sb.manager.SignalInstance;
import cvm.sb.policy.metadata.Metadata;
import cvm.sb.resource.AbstractTouchpoint;
import cvm.sb.resource.Resource;

import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Communication object used by CSM
 *
 * @author Frank Hernandez
 */
public class BridgeResource extends AbstractTouchpoint implements EventNotifier, Resource {
    private Manageable bridge;

    public BridgeResource(Metadata metadata, Manageable bridge) {
        super(metadata);
        bridge.setEventNotifier(this);
        this.bridge = bridge;
    }

    public Object execute(SignalInstance signal) {
        return execute(signal.getName(), signal.getParams());
    }

    public Object execute(String message, Map<String, Object> params) {
        try {
            return new BridgeExecutor(bridge).execute(message, params);
        } catch (InvocationTargetException e) {
            if (e.getCause() instanceof EventException) {
                EventException ee = (EventException) e.getCause();
                throwEvent(ee.getEvent());
            } else if (e.getCause() instanceof RuntimeException)
                throw (RuntimeException) e.getCause();
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        // TODO: fixme
        return null;
    }

    public Object execute(String message) {
        return execute(message, new LinkedHashMap<String, Object>());
    }

    public void notify(SignalInstance signal) {
        getEventListener().notify(signal);
    }

    public void notify(Event event) {
        notify(newSignalInstance(event));
    }

    public void throwEvent(SignalInstance signal) throws EventException {
        getEventListener().throwEvent(signal);
    }

    public void throwEvent(Event event) {
        throwEvent(newSignalInstance(event));
    }

    private SignalInstance newSignalInstance(Event event) {
        return new SignalInstance(this, event.getName(), event.getParams());
    }

    public String toString() {
        return getName();
    }
}
