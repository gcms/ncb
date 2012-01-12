package cvm.ncb.csm;

import cvm.ncb.oem.policy.Metadata;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Communication object used by CSM
 *
 * @author Frank Hernandez
 */
public class ManagedObject {
    private Object bridge = null;
    private Metadata metadata;

    public ManagedObject(Object bridge, Metadata metadata) {
        this.bridge = bridge;
        this.metadata = metadata;
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public String getName() {
        return metadata.getName();
    }

    public void execute(BridgeCall call) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = bridge.getClass().getMethod(call.getName(), call.getParamTypes());
        method.invoke(bridge, call.getParams());
    }

    public void execute(String message, Map<String, Object> params) {
        try {
            new BridgeExecutor(bridge).execute(message, params);
        } catch (InvocationTargetException e) {
            if (e.getCause() instanceof RuntimeException)
                throw (RuntimeException) e.getCause();
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public void execute(String message) {
        execute(message, new LinkedHashMap<String, Object>());
    }

    public boolean executeBoolean(String message, Map<String, Object> params) {
        try {
            return new BridgeExecutor(bridge).executeBoolean(message, params);
        } catch (InvocationTargetException e) {
            if (e.getCause() instanceof RuntimeException)
                throw (RuntimeException) e.getCause();
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return false;
    }
}
