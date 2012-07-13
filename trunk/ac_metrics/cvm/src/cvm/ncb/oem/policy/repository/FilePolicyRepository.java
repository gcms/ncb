package cvm.ncb.oem.policy.repository;

import cvm.sb.policy.repository.Policy;
import cvm.sb.policy.repository.PolicyRepository;
import util.CVM_Debug;
import cvm.ncb.oem.policy.repository.loader.FilePolicyLoader;

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

    public List<Policy> load(String feature, String oper) {
        List<Policy> arrPolicy = repLoader.lookup(feature, oper);
        CVM_Debug.getInstance().printDebugMessage("Size of policy array is " + arrPolicy.size());


        Collections.sort(arrPolicy);
        return arrPolicy;
    }

}
