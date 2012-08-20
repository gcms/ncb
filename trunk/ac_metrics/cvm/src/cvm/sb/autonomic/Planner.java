package cvm.sb.autonomic;

import sb.base.autonomic.ChangePlan;

import java.util.Collection;

public class Planner {
    Collection<ChangePlan> plans;

    public Planner(Collection<ChangePlan> plans) {
        this.plans = plans;
    }

   private Executor executor;
    public void setExecutor(Executor executor) {
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
