package cvm.ncb.repository;

import cvm.model.CVM_Debug;
import cvm.ncb.repository.loader.FilePolicyLoader;
import cvm.ncb.repository.loader.GlobalConstant;
import cvm.ncb.repository.policy.Policy;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

public class NCB_R_Facade_Driver {

    public static void main(String args[]) {

        FilePolicyRepository ncb_facade = null;
        try {
            ncb_facade = FilePolicyRepository.createInstance(FilePolicyLoader.createInstance(new URI("file:///C:/Documents and Settings/ywu001/workspace/Self_Config/src/cvm/ncb/repository/examples")));
        } catch (URISyntaxException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        List<Policy> arr = ncb_facade.load(GlobalConstant.RequestedType.Video, GlobalConstant.OperationType.request);
        CVM_Debug.getInstance().printDebugMessage("The number of policies applied for this request is " + arr.size());


        for (Policy p : arr) {
            CVM_Debug.getInstance().printDebugMessage("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            CVM_Debug.getInstance().printDebugMessage("Policy name is " + p.getName());
            CVM_Debug.getInstance().printDebugMessage(p.toString());
            CVM_Debug.getInstance().printDebugMessage("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        }

        arr = ncb_facade.load(GlobalConstant.RequestedType.Bandwidth, GlobalConstant.OperationType.selection);
        CVM_Debug.getInstance().printDebugMessage("The number of policies applied for this request is " + arr.size());

        for (Policy p : arr) {
            CVM_Debug.getInstance().printDebugMessage("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            CVM_Debug.getInstance().printDebugMessage("Policy name is " + p.getName());
            CVM_Debug.getInstance().printDebugMessage(p.toString());
            CVM_Debug.getInstance().printDebugMessage("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        }
    }
}
