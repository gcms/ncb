package cvm.ncb.oem.pe.handlers;

import cvm.ncb.csm.ManagedObject;
import cvm.ncb.handlers.exception.LoginException;
import cvm.ncb.oem.pe.Call;
import cvm.ncb.oem.pe.PolicyEvalManager;
import cvm.ncb.oem.pe.SignalHandler;
import cvm.ncb.tpm.CommFWResource;

public class LoginAllHandler implements SignalHandler {
    public boolean canHandle(Call call) {
        return call.getName().equals("loginAll");
    }

    public void handle(Call call, CommFWResource resource, PolicyEvalManager pem) {
        for (ManagedObject managedObject : resource.getObjects()) {
            try {
                managedObject.execute("login");
            } catch (LoginException e) {
                managedObject.getMetadata().fail();
                pem.handleException(e);
            }
        }
    }
}
