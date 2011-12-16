package cvm.ncb.oem.pe.handlers;

import cvm.ncb.csm.BridgeCall;
import cvm.ncb.ks.ConIDMappingTable;
import cvm.ncb.ks.Connection;
import cvm.ncb.oem.pe.NCBCall;
import cvm.ncb.oem.pe.NCBCallHandler;
import cvm.ncb.oem.pe.PolicyEvalManager;
import cvm.ncb.tpm.CommFWResource;

public class CreateSessionHandler implements NCBCallHandler {
    public boolean canHandle(NCBCall call) {
        return call.getCallName().equals("createSession");
    }

    public void handle(NCBCall call, CommFWResource resource, PolicyEvalManager pem) {
        ConIDMappingTable mappingTable = resource.getConIDMappingTable();

        Connection con = mappingTable.getConnection(call.sessionID());
        if (con == null) {
            con = mappingTable.createConnection(call.sessionID(), null, null);
        }

        con.offerNCBCall(new BridgeCall(con, call));
    }
}
