package cvm.ncb.ks;

import sb.base.context.State;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;

public class StateTypeManager {
    private State state;
    private HashSet<StateHolder> objects = new LinkedHashSet<StateHolder>();

    public StateTypeManager(State state) {
        this.state = state;
    }

    public String getName() {
        return state.getName();
    }

    private StateHolder getObjectInternal(Object id) {
        for (StateHolder con : this.objects) {
            if (con.getId().equals(id)) {
                return con;
            }
        }

        return null;
    }

    public synchronized StateHolder create(Object id) {
        assert getObjectInternal(id) == null;
        StateHolder con = new StateHolder(state, id);
        objects.add(con);
        return con;
    }

    public synchronized StateHolder get(Object id) {
        StateHolder con = getObjectInternal(id);
        return con != null ? con : create(id);
    }


    public boolean remove(Object id) {
        StateHolder con = getObjectInternal(id);
        return con != null ? objects.remove(con) : false;
    }

    public Collection<StateHolder> getAll() {
        return objects;
    }
}
