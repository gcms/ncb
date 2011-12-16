package cvm.ncb.ks;

import java.util.HashSet;

public class ConIDMappingTable {
    private HashSet<Connection> conIDs = null;

    public ConIDMappingTable() {
        conIDs = new HashSet<Connection>();
    }

    private Connection getConnectionInternal(String conID) {
        for (Connection con : this.conIDs) {
            if (con.getConId().equals(conID)) {
                return con;
            }
        }
        return null;
    }

    public synchronized Connection createConnection(String conID, String medium, String comObj) {
        assert getConnectionInternal(conID) == null;
        Connection con = new Connection(conID, medium, comObj);
        conIDs.add(con);
        return con;
    }

    public synchronized Connection getConnection(String conID) {
        return getConnectionInternal(conID);
    }


    public boolean remove(String conID) {
        Connection con = getConnectionInternal(conID);
        return con != null ? conIDs.remove(con) : false;
    }

    public Iterable<Connection> getAllConnections() {
        return conIDs;
    }
}


