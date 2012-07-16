package cvm.sb.autonomic;

import cvm.sb.manager.ManagerContext;
import sb.base.autonomic.ChangePlan;

import java.util.Collection;

public class PlannerImpl {
    Collection<ChangePlan> plans;

    public PlannerImpl(Collection<ChangePlan> plans) {
        this.plans = plans;
    }

   private ExecutorImpl executor;
    public void setExecutor(ExecutorImpl executor) {
        this.executor = executor;
    }

    public void change(ChangeRequestInstance request) {
        for (ChangePlan plan : plans) {
            if (plan.getBasedOn().equals(request.getRequest())) {
                executor.execute(new ChangePlanInstance(plan, request));
            }
        }
    }
}
