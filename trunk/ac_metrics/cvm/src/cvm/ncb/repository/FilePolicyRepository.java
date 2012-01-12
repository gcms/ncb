package cvm.ncb.repository;

import cvm.model.CVM_Debug;
import cvm.ncb.oem.policy.Feature;
import cvm.ncb.repository.loader.FilePolicyLoader;
import cvm.ncb.repository.loader.GlobalConstant;
import cvm.ncb.repository.policy.Policy;
import cvm.ncb.repository.policy.PolicyRepository;

import java.util.Collections;
import java.util.List;


public class FilePolicyRepository implements PolicyRepository {
    private FilePolicyLoader repLoader;

    FilePolicyRepository(FilePolicyLoader ploader) {
        repLoader = ploader;
    }

    public static FilePolicyRepository createInstance(FilePolicyLoader ploader) {
        return new FilePolicyRepository(ploader);
    }

    public List<Policy> load(GlobalConstant.RequestedType request, GlobalConstant.OperationType oper) {
        List<Policy> arrPolicy = repLoader.lookup(request.toString(), oper.toString());
        CVM_Debug.getInstance().printDebugMessage("Size of policy array is " + arrPolicy.size());


        Collections.sort(arrPolicy);
        return arrPolicy;
    }

    public Feature loadFeatures() {
        return repLoader.loadFeatureTree();
    }
}
