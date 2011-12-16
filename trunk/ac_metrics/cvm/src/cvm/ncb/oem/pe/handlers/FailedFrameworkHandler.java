package cvm.ncb.oem.pe.handlers;

import cvm.model.CVM_Debug;
import cvm.ncb.ks.Connection;
import cvm.ncb.oem.pe.NCBCall;
import cvm.ncb.oem.pe.NCBCallHandler;
import cvm.ncb.oem.pe.PolicyEvalManager;
import cvm.ncb.tpm.CommFWResource;

public class FailedFrameworkHandler implements NCBCallHandler {
    public boolean canHandle(NCBCall call) {
        return call.getCallName().equals("failedFramework");
    }

    public void handle(NCBCall call, CommFWResource context, PolicyEvalManager pem) {
        String fwName = (String) call.getParams()[0];
        for (Connection con : context.getConIDMappingTable().getAllConnections()) {
            if (con.containsComObj(fwName)) { // framework name
                Object[] obj = {con.getConId(), con.getDefaultMedium()};
                context.getCallQueue().add(con.getConId(), 0, "enableMedium", con.getDefaultMedium(), obj);
                CVM_Debug.getInstance().printDebugMessage("PolicyEvalManager : Queuing NCB sendMedia called with connectionID:\"" +
                        con.getConId() + "\" and MediumName:\"" + con.getDefaultMedium() + "\".");
            }
        }
    }
}

