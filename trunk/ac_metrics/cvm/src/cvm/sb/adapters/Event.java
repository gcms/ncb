package cvm.sb.adapters;

import java.util.LinkedHashMap;
import java.util.Map;

public class Event {
    private String name;
    private Map<String,Object> params;

    public Event(String name) {
        this(name, new LinkedHashMap<String, Object>());
    }

    public Event(String name, Map<String, Object> params) {
        this.name = name;
        this.params = params;
    }

    public void setParam(String name, Object value) {
        params.put(name, value);
    }

    public String getName() {
        return name;
    }

    public Map<String, Object> getParams() {
        return params;
    }
}
