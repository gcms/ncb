package cvm.ncb.oem.policy.repository.policy;

import java.util.List;

public interface PolicyRepository {
    List<Policy> load(String request, String oper);
}
