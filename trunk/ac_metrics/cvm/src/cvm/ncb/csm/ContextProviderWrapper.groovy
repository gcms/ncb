package cvm.ncb.csm

import cvm.ncb.oem.pe.ContextProvider

class ContextProviderWrapper {
    public static Map wrap(Map context) {
        Map wrapped = [:]
        context.each { String k, Object v ->
            if (v instanceof ContextProvider)
                wrapped[k] = new ContextProviderWrapper(v)
            else if (v instanceof Map)
                wrapped[k] = wrap(v)
            else
                wrapped[k] = v
        }
        wrapped
    }

    public ContextProvider context

    public ContextProviderWrapper(ContextProvider context) {
        this.context = context
    }

    public getProperty(String name) {
        Object value = getValue(name)
        value instanceof ContextProvider ? new ContextProviderWrapper(value) : value
    }

    private Object getValue(String name) {
        context.getParams().get(name) ?: context."${name}"
    }

    public String toString() {
        context
    }
}
