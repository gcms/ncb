package cvm.ncb.oem.pe.handlers;

import cvm.ncb.ks.Connection;
import cvm.ncb.oem.pe.Call;
import cvm.ncb.oem.pe.PolicyEvalManager;
import cvm.ncb.oem.pe.SignalHandler;
import cvm.ncb.tpm.CommFWResource;

import java.util.LinkedHashMap;
import java.util.Map;

public class FailedFrameworkHandler implements SignalHandler {
    public boolean canHandle(Call call) {
        return call.getName().equals("failedFramework");
    }

    public void handle(Call call, CommFWResource context, PolicyEvalManager pem) {
        String fwName = (String) call.getParam("framework");
        for (Connection con : context.getStateManager().getAllConnections()) {
            if (con.containsFramework(fwName)) { // framework name
                Map<String, Object> params = new LinkedHashMap<String, Object>();
                params.put("connection", con.getId());
                params.put("medium", con.getDefaultMedium());
                context.getCallQueue().add("enableMedium", params);
            }
        }
    }
}

