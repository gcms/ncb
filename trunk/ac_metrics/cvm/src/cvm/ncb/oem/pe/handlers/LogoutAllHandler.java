package cvm.ncb.oem.pe.handlers;

import cvm.ncb.csm.ManagedObject;
import cvm.ncb.oem.pe.Call;
import cvm.ncb.oem.pe.SignalHandler;
import cvm.ncb.oem.pe.PolicyEvalManager;
import cvm.ncb.tpm.CommFWResource;

public class LogoutAllHandler implements SignalHandler {
    public boolean canHandle(Call call) {
        return call.getName().equals("logoutAll");
    }

    public void handle(Call call, CommFWResource resource, PolicyEvalManager pem) {
        for (ManagedObject managedObject : resource.getObjects()) {
            managedObject.execute("logout");
        }
    }
}
