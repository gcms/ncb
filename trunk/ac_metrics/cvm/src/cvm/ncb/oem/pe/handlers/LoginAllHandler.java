package cvm.ncb.oem.pe.handlers;

import cvm.ncb.csm.CommObject;
import cvm.ncb.handlers.exception.LoginException;
import cvm.ncb.ks.CommFWCapabilitySet;
import cvm.ncb.oem.pe.NCBCall;
import cvm.ncb.oem.pe.NCBCallHandler;
import cvm.ncb.oem.pe.PolicyEvalManager;
import cvm.ncb.tpm.CommFWResource;

public class LoginAllHandler implements NCBCallHandler {
    public boolean canHandle(NCBCall call) {
        return call.getCallName().equals("loginAll");
    }

    public void handle(NCBCall call, CommFWResource resource, PolicyEvalManager pem) {
        for (CommObject commObject : resource.getCObjectList()) {
            try {
                commObject.getBridge().login();
            } catch (LoginException e) {
                CommFWCapabilitySet.getInstance().getFramework(commObject.getName()).fail();
                pem.handleException(e);
            }
        }
    }
}
