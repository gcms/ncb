package cvm.ncb.repository.loader;
import cvm.ncb.repository.policy.*;

public interface LoaderInterface {
	public NCBPolicy load (String policyURL) throws Exception;
	

}
