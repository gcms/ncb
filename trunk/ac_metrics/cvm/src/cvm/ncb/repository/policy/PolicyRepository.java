package cvm.ncb.repository.policy;

import cvm.ncb.oem.policy.Feature;
import cvm.ncb.repository.loader.GlobalConstant;
import java.util.List;

public interface PolicyRepository {
    List<Policy> load(GlobalConstant.RequestedType request, GlobalConstant.OperationType oper);

    public Feature loadFeatures();
}
