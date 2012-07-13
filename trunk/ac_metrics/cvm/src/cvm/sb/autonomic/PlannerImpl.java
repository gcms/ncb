package cvm.sb.autonomic;

import cvm.sb.manager.ActionCaller;
import cvm.sb.manager.ManagerContext;
import cvm.sb.emf.HandlerFactory;
import sb.base.autonomic.ChangePlan;

import java.util.Collection;

public class PlannerImpl {
    Collection<ChangePlan> plans;
    private ManagerContext context;

    public PlannerImpl(Collection<ChangePlan> plans) {
        this.plans = plans;
    }

    public void change(ChangeRequestInstance request) {
        for (ChangePlan plan : plans) {
            if (plan.getBasedOn().equals(request.getRequest())) {
                executePlan(request, plan);
            }
        }
    }

    private void executePlan(ChangeRequestInstance request, ChangePlan plan) {
        ActionCaller caller = new HandlerFactory().createActionCaller(plan.getAction());
        caller.execute(context, request);
    }

    public void setContext(ManagerContext context) {
        this.context = context;
    }
}
