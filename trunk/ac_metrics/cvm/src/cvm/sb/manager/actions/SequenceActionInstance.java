package cvm.sb.manager.actions;

import cvm.sb.expression.ContextProviderParams;
import cvm.sb.manager.ManagerContext;

import java.util.Collection;
import java.util.Map;

public class SequenceActionInstance implements ActionInstance {
    private Collection<ActionCaller> callers;

    public SequenceActionInstance(Collection<ActionCaller> callers) {
        this.callers = callers;
    }

    public Object execute(ManagerContext ctx, Map<String, Object> params) {
        Object result = null;

        for (ActionCaller caller : callers) {
            result = caller.execute(ctx, new ContextProviderParams(params));
        }

        return result;
    }
}
