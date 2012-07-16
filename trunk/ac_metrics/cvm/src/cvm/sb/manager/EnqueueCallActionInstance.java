package cvm.sb.manager;

import cvm.sb.expression.ValueEvaluator;
import sb.base.common.EnqueueCall;

import java.util.Map;

public class EnqueueCallActionInstance implements ActionInstance {
    private EnqueueCall call;

    public EnqueueCallActionInstance(EnqueueCall call) {
        this.call = call;
    }

    public Object execute(ManagerContext ctx, Map<String, Object> params) {
        Map<String, Object> callParams = ValueEvaluator.getParams(new ContextProviderParams(params), call.getBindings());

        ctx.getMainManager().enqueue(new SignalInstance(call.getCall().getName(), callParams));

        return null;
    }
}
