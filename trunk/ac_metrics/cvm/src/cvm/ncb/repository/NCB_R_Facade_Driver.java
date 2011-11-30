package cvm.ncb.repository;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import cvm.ncb.repository.policy.*;
import cvm.ncb.repository.loader.*;
import cvm.model.*;
public class NCB_R_Facade_Driver {
	
	public static void main(String args[])
	{

        NCB_R_Facade ncb_facade = null;
        try {
            ncb_facade = NCB_R_Facade.getInstance(FilePolicyLoader.getInstance(new URI("file:///C:/Documents and Settings/ywu001/workspace/Self_Config/src/cvm/ncb/repository/examples")));
        } catch (URISyntaxException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        ArrayList<NCBPolicy> arr = ncb_facade.load(GlobalConstant.RequestedType.Video, GlobalConstant.OperationType.request);
		CVM_Debug.getInstance().printDebugMessage("The number of policies applied for this request is " +arr.size());
		
		
		
		for (NCBPolicy p: arr)
		{
			CVM_Debug.getInstance().printDebugMessage("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
			CVM_Debug.getInstance().printDebugMessage("Policy name is "+p.getName());
			CVM_Debug.getInstance().printDebugMessage(p.toString());
			CVM_Debug.getInstance().printDebugMessage("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		}
		
		arr = ncb_facade.load(GlobalConstant.RequestedType.Bandwidth, GlobalConstant.OperationType.lessThan);
		CVM_Debug.getInstance().printDebugMessage("The number of policies applied for this request is " +arr.size());
		
		for (NCBPolicy p: arr)
		{
			CVM_Debug.getInstance().printDebugMessage("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
			CVM_Debug.getInstance().printDebugMessage("Policy name is "+p.getName());
			CVM_Debug.getInstance().printDebugMessage(p.toString());
			CVM_Debug.getInstance().printDebugMessage("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
			}	
	}
}
