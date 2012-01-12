package cvm.model;

import java.util.LinkedHashMap;
import java.util.Map;

public class Event {
    private String name;
    private Map<String, Object> params;

    public Event(String name) {
        this(name, new LinkedHashMap<String, Object>());
    }

    public Event(String name, Map<String, Object> params) {
        this.name = name;
        this.params = params;
    }

    public String getName() {
        return name;
    }

    public Object getParam(String name) {
        return params.get(name);
    }

    public void setParam(String name, Object value) {
        params.put(name, value);
    }
}
