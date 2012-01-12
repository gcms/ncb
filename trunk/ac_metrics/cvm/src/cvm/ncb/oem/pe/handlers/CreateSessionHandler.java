package cvm.ncb.oem.pe.handlers;

import cvm.ncb.csm.BridgeCall;
import cvm.ncb.ks.StateManager;
import cvm.ncb.ks.Connection;
import cvm.ncb.oem.pe.Call;
import cvm.ncb.oem.pe.SignalHandler;
import cvm.ncb.oem.pe.PolicyEvalManager;
import cvm.ncb.tpm.CommFWResource;

public class CreateSessionHandler implements SignalHandler {
    public boolean canHandle(Call call) {
        return call.getName().equals("createSession");
    }

    public void handle(Call call, CommFWResource resource, PolicyEvalManager pem) {
        StateManager mappingTable = resource.getStateManager();

        Connection con = mappingTable.getConnection((String) call.getParam("session"));
        con.enqueue(new BridgeCall("createSession", "", call.getParams()));
    }
}
