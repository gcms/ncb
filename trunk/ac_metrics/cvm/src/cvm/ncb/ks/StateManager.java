package cvm.ncb.ks;

import java.util.HashSet;
import java.util.LinkedHashSet;

public class StateManager {
    private HashSet<Connection> objects = new LinkedHashSet<Connection>();

    private Connection getObjectInternal(String id) {
        for (Connection con : this.objects) {
            if (con.getId().equals(id)) {
                return con;
            }
        }
        return null;
    }

    public synchronized Connection createConnection(String id) {
        assert getObjectInternal(id) == null;
        Connection con = new Connection(id);
        objects.add(con);
        return con;
    }

    public synchronized Connection getConnection(String id) {
        Connection con = getObjectInternal(id);
        return con != null ? con : createConnection(id);
    }


    public boolean remove(String id) {
        Connection con = getObjectInternal(id);
        return con != null ? objects.remove(con) : false;
    }

    public Iterable<Connection> getAllConnections() {
        return objects;
    }
}


