package cvm.ncb.ks;

import cvm.ncb.csm.Resource;
import cvm.ncb.oem.pe.ContextProvider;
import sb.base.context.State;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class StateHolder implements ContextProvider {
    private State state;

    private Map<String, Object> attributes = new LinkedHashMap<String, Object>();
    private Map<String, StateTypeManager> children = new LinkedHashMap<String, StateTypeManager>();

    public StateHolder(State state, Object id) {
        this.state = state;
        attributes.put(state.getKey().getName(), id);
    }

    public Object getId() {
        return attributes.get(state.getKey().getName());
    }

    public Set getSet(String name) {
        Set set = (Set) attributes.get(name);
        if (set == null) {
            set = new LinkedHashSet();
            attributes.put(name, set);
        }

        return set;
    }

    public Object get(String name) {
        return attributes.get(name);
    }

    public void set(String name, Object value) {
        attributes.put(name, value);
    }

    public Resource getResource(String name) {
        return (Resource) get(name);
    }

    public Map<String, Object> getParams() {
        return attributes;
    }

    public String toString() {
        return state.getName() + "(" + getId() + ")";
    }
}
