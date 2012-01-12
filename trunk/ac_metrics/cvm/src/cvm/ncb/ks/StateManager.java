package cvm.ncb.ks;

import java.util.HashSet;

public class StateManager {
    private HashSet<Connection> objects = null;

    public StateManager() {
        objects = new HashSet<Connection>();
    }

    private Connection getObjectInternal(String id) {
        for (Connection con : this.objects) {
            if (con.getId().equals(id)) {
                return con;
            }
        }
        return null;
    }

    public synchronized Connection createConnection(String id, String medium, String fw) {
        assert getObjectInternal(id) == null;
        Connection con = new Connection(id, medium, fw);
        objects.add(con);
        return con;
    }

    public synchronized Connection getConnection(String id) {
        Connection con = getObjectInternal(id);
        return con != null ? con : createConnection(id, null, null);
    }


    public boolean remove(String id) {
        Connection con = getObjectInternal(id);
        return con != null ? objects.remove(con) : false;
    }

    public Iterable<Connection> getAllConnections() {
        return objects;
    }
}


