package cvm.ncb.oem.pe;

import util.StringUtils;

import java.util.LinkedHashMap;
import java.util.Map;

public class SignalInstance {
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
        return params;
    }

    public Object getParam(String param) {
        return params.get(param);
    }

    public void setParam(String name, Object value) {
        params.put(name, value);
    }


    public boolean equals(SignalInstance other) {
        return name.equals(other.name)
                && params.equals(other.params);
    }

    public String toString() {
        return StringUtils.methodToString(this.getName(), getParams().values().toArray());
    }

    public Object getSource() {
        return source;
    }
}
