package cvm.sb.expression;


import java.util.Map;

public class ContextProviderParams implements ContextProvider {
    private Map<String, Object> params;

    public ContextProviderParams(Map<String, Object> params) {
        this.params = params;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public Object getVariable(String name) {
        return getParams().get(name);
    }
}
