package cvm.ncb.oem.autonomic;


import cvm.ncb.oem.pe.ContextProvider;
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
        Map<String, Object> params = new LinkedHashMap<String, Object>(symptom.getParams());
        params.put("symptom", symptom);
        return params;
    }

    public Object getSelf() {
        return this;
    }
}
