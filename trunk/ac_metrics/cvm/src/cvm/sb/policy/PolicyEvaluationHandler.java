package cvm.sb.policy;


import cvm.sb.manager.ManagerContext;
import cvm.sb.resource.Resource;

public interface PolicyEvaluationHandler {
    public void handleResource(PolicyRequest request, ManagerContext ctx, Resource selected);
}
