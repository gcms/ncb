package cvm.sb.autonomic;

import cvm.sb.emf.HandlerFactory;
import cvm.sb.manager.ActionCaller;
import cvm.sb.manager.ManagerContext;


public class ExecutorImpl {
    private ManagerContext context;

     public void execute(ChangePlanInstance plan) {
        ActionCaller caller = new HandlerFactory().createActionCaller(plan.getAction());
        caller.execute(context, plan.getContext());
    }

     public void setContext(ManagerContext context) {
        this.context = context;
    }
}
