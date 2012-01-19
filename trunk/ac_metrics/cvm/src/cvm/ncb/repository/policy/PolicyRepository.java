package cvm.ncb.repository.policy;

import cvm.ncb.oem.policy.Feature;

import java.util.List;

public interface PolicyRepository {
    List<Policy> load(String request, String oper);

    public Feature loadFeatures();
}
