package cvm.ncb.oem.pe;

import cvm.ncb.csm.Resource;
import java.util.Map;

public interface PolicyEvalManager {
    public void evaluatePolicies(SignalInstance signal);
    public Resource findConformingObject(String requestType, String operationType, Map<String, Object> params);
}
