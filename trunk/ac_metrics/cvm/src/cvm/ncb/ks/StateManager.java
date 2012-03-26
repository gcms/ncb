package cvm.ncb.ks;

import sb.base.context.State;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class StateManager {
    private Map<State, StateTypeManager> typeManagers = new LinkedHashMap<State, StateTypeManager>();

    public StateManager(Collection<State> types) {
        for (State type : types) {
            typeManagers.put(type, new StateTypeManager(type));
        }
    }

    public Collection<StateTypeManager> getTypes() {
        return typeManagers.values();
    }

    public StateTypeManager getType(State type) {
        return typeManagers.get(type);
    }

    public StateTypeManager getType(String name) {
        return getType(findType(name));
    }

    private State findType(String name) {
        for (State type : typeManagers.keySet()) {
            if (type.getName().equals(name))
                return type;
        }

        return null;
    }
}


