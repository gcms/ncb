package cvm.ncb.csm;

import cvm.ncb.adapters.NCBBridge;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Communication object used by CSM
 *
 * @author Frank Hernandez
 */
public class CommObject {
    private String name;
    private NCBBridge bridge = null;

    public CommObject(NCBBridge bridge) {
        this.bridge = bridge;
        this.name = bridge.getFWName();
    }

    public NCBBridge getBridge() {
        return bridge;
    }

    public String getName() {
        return name;
    }

    public void execute(BridgeCall call) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = getBridge().getClass().getMethod(call.getName(), call.getParamTypes());
        method.invoke(getBridge(), call.getParams());
    }
}
