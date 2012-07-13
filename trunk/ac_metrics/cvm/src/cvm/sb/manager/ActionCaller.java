package cvm.sb.manager;


import cvm.sb.expression.ContextProvider;
import cvm.sb.expression.ValueEvaluator;
import cvm.sb.manager.ActionInstance;
import cvm.sb.manager.ManagerContext;
import sb.base.common.ActionExecution;

import java.util.Map;

public class ActionCaller {
    private ActionExecution execution;
    private ActionInstance instance;

    public ActionCaller(ActionExecution execution, ActionInstance instance) {
        this.execution = execution;
        this.instance = instance;
    }

    public Object execute(ManagerContext manager, Map<String, Object> params) {
        return instance.execute(manager, params);
    }

    public Object execute(ManagerContext manager, ContextProvider ctx) {
        return execute(manager, getParams(ctx));
    }

    private Map<String, Object> getParams(ContextProvider ctx) {
        return ValueEvaluator.getParams(ctx, execution.getBindings());
    }
}
