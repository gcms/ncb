package cvm.ncb.oem.autonomic;

import cvm.ncb.oem.pe.actions.ActionCaller;
import cvm.ncb.oem.pe.actions.ManagerContext;
import cvm.sb.emf.HandlerFactory;
import sb.base.autonomic.ChangePlan;
import sb.base.autonomic.Planner;

public class PlannerImpl {
    Planner planner;
    private ManagerContext context;

    public PlannerImpl(Planner planner) {
        this.planner = planner;
    }

    public void change(ChangeRequestInstance request) {
        for (ChangePlan plan : planner.getPlans()) {
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
