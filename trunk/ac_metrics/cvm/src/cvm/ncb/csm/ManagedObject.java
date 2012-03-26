package cvm.ncb.csm;

import cvm.ncb.adapters.EventException;
import cvm.ncb.adapters.EventNotifier;
import cvm.ncb.adapters.Manageable;
import cvm.ncb.oem.pe.SignalInstance;
import cvm.ncb.oem.policy.Metadata;

import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Communication object used by CSM
 *
 * @author Frank Hernandez
 */
public class ManagedObject extends AbstractTouchpoint implements EventNotifier, Resource {
    private Manageable bridge;

    public ManagedObject(Metadata metadata, Manageable bridge) {
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

    public void notify(cvm.ncb.adapters.Event event) {
        notify(newSignalInstance(event));
    }

    public void throwEvent(SignalInstance signal) throws EventException {
        getEventListener().throwEvent(signal);
    }

    public void throwEvent(cvm.ncb.adapters.Event event) {
        throwEvent(newSignalInstance(event));
    }

    private SignalInstance newSignalInstance(cvm.ncb.adapters.Event event) {
        return new SignalInstance(this, event.getName(), event.getParams());
    }

    public String toString() {
        return getName();
    }
}
