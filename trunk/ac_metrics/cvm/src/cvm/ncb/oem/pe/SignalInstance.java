package cvm.ncb.oem.pe;

import java.util.LinkedHashMap;
import java.util.Map;

public class SignalInstance implements ContextProvider {
    private Object source;
    private String name;
    private Map<String, Object> params;

    public SignalInstance(Object source, String name, Map<String, Object> params) {
        this.source = source;
        this.name = name;
        this.params = params;
    }

    public SignalInstance(String name, Map<String, Object> params) {
        this(null, name, params);
    }

    public SignalInstance(String name) {
        this(name, new LinkedHashMap<String, Object>());
    }

    public String getName() {
        return name;
    }

    public Map<String, Object> getParams() {
        Map<String, Object> newParams = new LinkedHashMap<String, Object>();
        newParams.putAll(params);
        newParams.put("source", source);
        return newParams;
    }

    public String toString() {
        return getName() + "(" + getParams() + ")";
    }

    public Object getSource() {
        return source;
    }
}
