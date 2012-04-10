package cvm.ncb.oem.pe.actions;

import cvm.ncb.oem.pe.SignalInstance;
import cvm.sb.emf.ValueEvaluator;
import sb.base.common.EnqueueCall;

import java.util.Map;

public class EnqueueCallActionInstance implements ActionInstance {
    private EnqueueCall call;

    public EnqueueCallActionInstance(EnqueueCall call) {
        this.call = call;
    }

    public Object execute(ManagerContext ctx, Map<String, Object> params) {
        Map<String, Object> callParams = ValueEvaluator.getParams(new ContextProviderParams(params), call.getBindings());

        ctx.getTouchpoint().enqueue(new SignalInstance(call.getCall().getName(), callParams));

        return null;
    }
}
