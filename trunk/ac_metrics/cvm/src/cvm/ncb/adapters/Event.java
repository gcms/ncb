package cvm.ncb.adapters;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: gustavosousa
 * Date: 20/01/12
 * Time: 18:14
 * To change this template use File | Settings | File Templates.
 */
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
