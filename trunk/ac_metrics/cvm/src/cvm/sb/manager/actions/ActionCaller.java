package cvm.sb.manager.actions;


import cvm.sb.expression.ContextProvider;
import cvm.sb.expression.ValueEvaluator;
import cvm.sb.manager.ManagerContext;
import sb.base.common.ActionExecution;

import java.util.Map;

public class ActionCaller {
    private ActionExecution execution;
    private ActionInstance action;

    public ActionCaller(ActionExecution execution, ActionInstance action) {
        this.execution = execution;
        this.action = action;
    }

    public Object execute(ManagerContext manager, Map<String, Object> params) {
        return action.execute(manager, params);
    }

    public Object execute(ManagerContext manager, ContextProvider ctx) {
        return execute(manager, getParams(ctx));
    }

    private Map<String, Object> getParams(ContextProvider ctx) {
        return ValueEvaluator.getParams(ctx, execution.getBindings());
    }
}
