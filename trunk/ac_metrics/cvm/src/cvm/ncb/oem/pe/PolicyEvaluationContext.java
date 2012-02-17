package cvm.ncb.oem.pe;

import java.util.Map;

public class PolicyEvaluationContext {
    private String feature;
    private Map<String,Object> params;

    public PolicyEvaluationContext(String feature, Map<String, Object> params) {
        this.feature = feature;
        this.params = params;
    }

    public String getFeature() {
        return feature;
    }

    public Map<String, Object> getParams() {
        return params;
    }
}
