package cvm.ncb.oem.policy;


import cvm.ncb.csm.Resource;
import cvm.ncb.oem.pe.actions.ManagerContext;

public interface PolicyEvaluationHandler {
    public void handleResource(PolicyRequest request, ManagerContext ctx, Resource selected);
}
