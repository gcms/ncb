package cvm.sb.manager.actions;

import cvm.sb.expression.ValueEvaluator;
import cvm.sb.expression.ContextProviderParams;
import cvm.sb.manager.ManagerContext;
import cvm.sb.manager.SignalInstance;
import sb.base.common.EnqueueCall;

import java.util.Map;

public class CallActionInstance implements ActionInstance {
    private EnqueueCall call;

    public CallActionInstance(EnqueueCall call) {
        this.call = call;
    }

    public Object execute(ManagerContext ctx, Map<String, Object> params) {
        Map<String, Object> callParams = ValueEvaluator.getParams(new ContextProviderParams(params), call.getBindings());

        ctx.getMainManager().enqueue(new SignalInstance(call.getCall().getName(), callParams));

        return null;
    }
}
