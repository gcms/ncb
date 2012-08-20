package cvm.sb.expression

class ContextProviderWrapper {
    public static Map wrap(Map context) {
        Map wrapped = [:]
        context.each { String k, Object v ->
            wrapped[k] = wrap(v)
        }
        wrapped
    }

    public static Object wrap(Object v) {
        if (v instanceof ContextProvider)
            return new ContextProviderWrapper(v)
        else if (v instanceof Map)
            return wrap(v)
        else
            return v
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
        context.getVariable(name) ?: context."${name}"
    }

    public String toString() {
        context
    }
}
