package cvm.sb.policy;

import cvm.sb.expression.ValueEvaluator;
import cvm.sb.manager.HandlingResult;
import cvm.sb.manager.ManagerContext;
import cvm.sb.manager.SignalHandler;
import cvm.sb.manager.SignalInstance;
import cvm.sb.policy.repository.DefaultPolicyRepository;
import cvm.sb.resource.Resource;
import sb.base.policy.PolicyEvaluationPoint;

import java.util.Collection;

public class PolicyEvaluationManager implements SignalHandler {
    private Collection<PolicyEvaluationPoint> evalPoints;
    private PolicyManager policyManager;

    public PolicyEvaluationManager(sb.base.PolicyManager policyManager) {
        this.policyManager = new PolicyManager(new DefaultPolicyRepository(policyManager.getPolicies()));
        this.evalPoints = policyManager.getPoints();
    }

    public HandlingResult handle(SignalInstance signal, ManagerContext ctx) {
        for (PolicyEvaluationPoint evalPoint : evalPoints) {
            eval(signal, ctx, evalPoint);
        }

        return HandlingResult.NOT_HANDLED;
    }

    private void eval(SignalInstance signal, ManagerContext ctx, PolicyEvaluationPoint evalPoint) {
        PolicyRequest request = getRequest(signal, evalPoint);
        if (request == null)
            return;

        Resource resource = policyManager.findConformingObject(ctx.getResourceManager(), request.getPolicyContext());
        if (resource != null)
            request.getHandler().handleResource(request, ctx, resource);
    }

    private PolicyRequest getRequest(SignalInstance signal, PolicyEvaluationPoint evalPoint) {
        if (!signal.getName().equals(evalPoint.getSignal().getName()))
            return null;

        return new PolicyRequest(evalPoint.getRequest(), ValueEvaluator.getParams(signal, evalPoint.getRequest().getBindings()));
    }

    public SignalHandler getSignalHandler() {
        return this;
    }
}
