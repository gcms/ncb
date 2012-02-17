package cvm.ncb.ks;

import java.util.HashSet;
import java.util.LinkedHashSet;

public class StateManager {
    private HashSet<StateHolder> objects = new LinkedHashSet<StateHolder>();

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
        StateHolder con = new StateHolder(id);
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

    public Iterable<StateHolder> getAll() {
        return objects;
    }
}


