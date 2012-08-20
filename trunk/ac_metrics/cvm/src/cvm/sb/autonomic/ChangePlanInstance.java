package cvm.sb.autonomic;

import cvm.sb.expression.ContextProvider;
import sb.base.autonomic.ChangePlan;
import sb.base.common.ActionExecution;

import java.util.LinkedHashMap;
import java.util.Map;


public class ChangePlanInstance implements ContextProvider {
    private ChangePlan plan;
    private ChangeRequestInstance request;

    public ChangePlanInstance(ChangePlan plan, ChangeRequestInstance request) {
        this.plan = plan;
        this.request = request;
    }

    public ActionExecution getAction() {
        return plan.getAction();
    }

    public Map<String, Object> getParams() {
        return request.getParams();
    }


    public Object getVariable(String name) {
        return getParams().get(name);
    }
}
