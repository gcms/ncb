package cvm.ncb.oem.policy;

import cvm.ncb.csm.Resource;
import cvm.ncb.oem.pe.ContextProvider;
import cvm.ncb.oem.pe.actions.ManagerContext;
import cvm.sb.emf.ValueEvaluator;
import sb.base.policy.PolicyEvaluation;
import sb.base.policy.PolicyEvaluationRequest;

import java.util.Map;

public class PolicyRequest implements ContextProvider {
    private PolicyEvaluationRequest request;
    private Map<String, Object> params;

    public PolicyRequest(PolicyEvaluationRequest request, Map<String, Object> params) {
        this.request = request;
        this.params = params;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public PolicyContext getPolicyContext() {
        return createPolicyContext(this, request.getHandler().getEvaluation());
    }

    private static PolicyContext createPolicyContext(ContextProvider context, PolicyEvaluation evaluation) {
        Map<String, Object> params = ValueEvaluator.getParams(context, evaluation.getContextBinding());
        Object feature = ValueEvaluator.getValue(context, evaluation.getFeature());
        return new PolicyContext(feature.toString(), params);
    }

    public PolicyEvaluationHandler getHandler() {
        try {
            return (PolicyEvaluationHandler) Class.forName(request.getHandler().getHandlerImpl()).newInstance();
        } catch (Exception ignored) {
        }

        return new PolicyEvaluationHandler() {
            public void handleResource(PolicyRequest request, ManagerContext ctx, Resource selected) {
            }
        };
    }
}
