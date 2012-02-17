package cvm.ncb.csm;

import cvm.model.EventException;
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
    private Metadata metadata;

    public ManagedObject(Manageable bridge, Metadata metadata) {
        bridge.setEventNotifier(this);
        this.bridge = bridge;
        this.metadata = metadata;
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public String getName() {
        return metadata.getName();
    }

    public Object execute(SignalInstance signal) {
        return execute(signal.getName(), signal.getParams());
    }

    public Object execute(String message, Map<String, Object> params) {
        try {
            return new BridgeExecutor(bridge).execute(message, params);
        } catch (InvocationTargetException e) {
            if (e.getCause() instanceof EventException) {
                throw (RuntimeException) e.getCause();
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

    public void notify(SignalInstance event) {
        getEventListener().notify(event);
    }

    public void throwEvent(SignalInstance event) throws EventException {
        getEventListener().throwEvent(event);
    }

    public void notify(cvm.ncb.adapters.Event event) {
        notify(new SignalInstance(this, event.getName(), event.getParams()));
    }
}
