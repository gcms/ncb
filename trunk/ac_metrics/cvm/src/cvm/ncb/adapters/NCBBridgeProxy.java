package cvm.ncb.adapters;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import util.StringUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by IntelliJ IDEA.
 * User: Gustavo Sousa
 * Date: 10/10/11
 * Time: 16:52
 * To change this template use File | Settings | File Templates.
 */
public class NCBBridgeProxy implements InvocationHandler {
    private static Log log = LogFactory.getLog(NCBBridgeProxy.class);

    private NCBBridge internalBridge;

    public NCBBridgeProxy(NCBBridge bridge) {
        internalBridge = bridge;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (!Thread.currentThread().getName().startsWith("CVM_SC_MGR"))
            log.debug(internalBridge.toString() + "." + getToString(method, args));

        method = internalBridge.getClass().getMethod(method.getName(), method.getParameterTypes());

        try {
            return method.invoke(internalBridge, args);
        } catch (InvocationTargetException e) {
            log.debug("Event detected: " + e);
            throw e.getTargetException();
        } catch (Exception e) {
            log.debug("Error detected: " + e);
            throw e;
        }
    }

    private String getToString(Method method, Object[] args) {
        return StringUtils.methodToString(method.getName(), args);
    }

    public static NCBBridge wrap(NCBBridge bridge) {
        InvocationHandler handler = new NCBBridgeProxy(bridge);
        Class proxyClass = Proxy.getProxyClass(
                NCBBridge.class.getClassLoader(), new Class[]{NCBBridge.class});
        try {
            return (NCBBridge) proxyClass.
                    getConstructor(new Class[]{InvocationHandler.class}).
                    newInstance(new Object[]{handler});
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return null;
    }
}
