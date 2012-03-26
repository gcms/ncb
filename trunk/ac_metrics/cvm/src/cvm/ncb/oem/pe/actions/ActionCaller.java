package cvm.ncb.oem.pe.actions;


import cvm.ncb.oem.pe.ContextProvider;
import cvm.sb.emf.ValueEvaluator;
import sb.base.ActionExecution;

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
