package cvm.sb.autonomic;

import cvm.sb.expression.ContextProvider;
import sb.base.autonomic.ChangePlan;
import sb.base.common.ActionExecution;


public class ChangePlanInstance {
    private ChangePlan plan;
    private ContextProvider context;

    public ChangePlanInstance(ChangePlan plan, ChangeRequestInstance request) {
        this.plan = plan;
        this.context = request;
    }

    public ActionExecution getAction() {
        return plan.getAction();
    }

    public ContextProvider getContext() {
        return context;
    }
}
