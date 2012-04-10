package cvm.ncb.csm

import java.lang.reflect.Method as JavaMethod

import java.lang.reflect.InvocationTargetException
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import cvm.ncb.adapters.Method

class BridgeExecutor {
    private static Log log = LogFactory.getLog(BridgeExecutor)

    private Object bridge

    public BridgeExecutor(Object bridge) {
        this.bridge = bridge
    }

    public JavaMethod getMethod(String name, Map<String, Object> params) {
        bridge.getClass().methods.find { JavaMethod method ->
            Method annotation = method.getAnnotation(cvm.ncb.adapters.Method)
            annotation != null && method.name == name && areParamsEquiv(method, annotation, params)
        }
    }

    boolean areParamsEquiv(JavaMethod method, Method annotation, Map<String, Object> params) {
        if (method.parameterTypes.length != annotation.parameters().size())
            return false

        for (int i = 0; i < annotation.parameters().size(); i++) {
            String paramName = annotation.parameters()[i];
            Class paramType = method.parameterTypes[i];

            Object paramValue = params.get(paramName);
            if (paramValue != null && !paramType.isAssignableFrom(paramValue.getClass()))
                return false
        }

        return true
    }

    public Object execute(String message, Map<String, Object> params) throws InvocationTargetException {
        if (!Thread.currentThread().getName().startsWith("CVM_SC_MGR"))
            log.debug("${bridge}.$message($params)")

        JavaMethod method = getMethod(message, params)
        method.invoke(bridge, orderParameters(method, params))
    }

//    public boolean executeBoolean(String name, Map<String, Object> params) throws InvocationTargetException {
//        execute(name, params)
//    }

    public Object[] orderParameters(JavaMethod method, Map<String, Object> params) {
        Method annotation = method.getAnnotation(Method)

        Object[] result = new Object[annotation.parameters().size()];
        for (int i = 0; i < result.size(); i++) {
            String paramName = annotation.parameters()[i];
            result[i] = params.get(paramName);
        }

        result
    }
}
