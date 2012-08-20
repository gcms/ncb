package cvm.sb.autonomic;


import cvm.sb.expression.ContextProvider;
import sb.base.autonomic.ChangeRequest;

import java.util.LinkedHashMap;
import java.util.Map;

public class ChangeRequestInstance implements ContextProvider {
    private ChangeRequest request;
    private SymptomOccurrence symptom;

    public ChangeRequestInstance(ChangeRequest request, SymptomOccurrence symptom) {
        this.request = request;
        this.symptom = symptom;
    }

    public ChangeRequest getRequest() {
        return request;
    }

    public Map<String, Object> getParams() {
        return symptom.getParams();
    }

    public Object getVariable(String name) {
        return getParams().get(name);
    }

}
