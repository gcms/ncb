package cvm.ncb.oem.pe.handlers;

import cvm.ncb.csm.BridgeCall;
import cvm.ncb.ks.Connection;
import cvm.ncb.oem.pe.NCBCall;
import cvm.ncb.oem.pe.NCBCallHandler;
import cvm.ncb.oem.pe.PolicyEvalManager;
import cvm.ncb.tpm.CommFWResource;

public class DefaultHandler implements NCBCallHandler {
    public boolean canHandle(NCBCall call) {
        return true;
    }

    public void handle(NCBCall call, CommFWResource resource, PolicyEvalManager pem) {
        Connection con = resource.getConIDMappingTable().getConnection(call.sessionID());
        con.offerNCBCall(new BridgeCall(con, call));
    }
}
