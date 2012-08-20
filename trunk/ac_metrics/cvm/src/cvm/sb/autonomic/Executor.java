package cvm.sb.autonomic;

import cvm.sb.emf.HandlerFactory;
import cvm.sb.expression.ContextProvider;
import cvm.sb.manager.actions.ActionCaller;
import cvm.sb.manager.ManagerContext;


public class Executor {
    private ManagerContext context;

     public void execute(ChangePlanInstance plan) {
        ActionCaller caller = new HandlerFactory().createActionCaller(plan.getAction());
        caller.execute(context, plan);
    }

     public void setContext(ManagerContext context) {
        this.context = context;
    }
}
