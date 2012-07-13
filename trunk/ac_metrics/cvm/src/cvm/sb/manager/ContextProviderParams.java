package cvm.sb.manager;


import cvm.sb.expression.ContextProvider;

import java.util.Map;

public class ContextProviderParams implements ContextProvider {
    private Map<String, Object> params;

    public ContextProviderParams(Map<String, Object> params) {
        this.params = params;
    }

    public Map<String, Object> getParams() {
        return params;
    }

}
