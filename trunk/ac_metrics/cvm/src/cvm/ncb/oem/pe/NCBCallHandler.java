package cvm.ncb.oem.pe;

import cvm.ncb.tpm.CommFWResource;

public interface NCBCallHandler {
    boolean canHandle(NCBCall call);
    void handle(NCBCall call, CommFWResource resource, PolicyEvalManager pem);
}
