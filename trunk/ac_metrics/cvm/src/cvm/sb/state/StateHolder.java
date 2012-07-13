package cvm.sb.state;

import cvm.sb.expression.ContextProvider;
import cvm.sb.resource.Resource;
import sb.base.context.State;

import java.util.*;

public class StateHolder implements ContextProvider {
    private State state;

    private Map<String, Object> attributes = new LinkedHashMap<String, Object>();
    private Map<String, StateTypeManager> children = new LinkedHashMap<String, StateTypeManager>();

    public StateHolder(State state, Object id) {
        this.state = state;
        attributes.put(state.getKey().getName(), id);

        for (State child : state.getChildren()) {
            children.put(child.getName(), new StateTypeManager(child));
        }
    }

    public Object getId() {
        return attributes.get(state.getKey().getName());
    }

    public Set getAsSet(String name) {
        return (Set) get(name, new LinkedHashSet());
    }

    public List getAsList(String name) {
        return (List) get(name, new LinkedList());
    }


    public Object get(String name, Object defaultValue) {
        Object value = attributes.get(name);
        if (value == null) {
            value = defaultValue;
            attributes.put(name, value);
        }

        return value;
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

    public StateTypeManager getChild(String name) {
        return children.get(name);
    }

    public Map<String, Object> getParams() {
        return attributes;
    }

    public String toString() {
        return state.getName() + "(" + getId() + ")";
    }
}
