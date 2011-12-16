package cvm.ncb.oem.pe.handlers;

import cvm.ncb.csm.CommObject;
import cvm.ncb.oem.pe.NCBCall;
import cvm.ncb.oem.pe.NCBCallHandler;
import cvm.ncb.oem.pe.PolicyEvalManager;
import cvm.ncb.tpm.CommFWResource;

public class LogoutAllHandler implements NCBCallHandler {
    public boolean canHandle(NCBCall call) {
        return call.getCallName().equals("logoutAll");
    }

    public void handle(NCBCall call, CommFWResource resource, PolicyEvalManager pem) {
        for (CommObject commObject : resource.getCObjectList()) {
            commObject.getBridge().logout();
        }
    }
}
