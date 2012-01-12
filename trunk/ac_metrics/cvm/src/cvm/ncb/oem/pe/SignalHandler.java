package cvm.ncb.oem.pe;

import cvm.ncb.tpm.CommFWResource;

public interface SignalHandler {
    boolean canHandle(Call call);
    void handle(Call call, CommFWResource resource, PolicyEvalManager pem);
}
