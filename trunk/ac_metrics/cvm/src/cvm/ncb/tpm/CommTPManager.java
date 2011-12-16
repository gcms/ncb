package cvm.ncb.tpm;

import edu.fiu.strg.ACSTF.manager.GenericManager;

import java.net.URI;
import java.util.Set;

public class CommTPManager extends GenericManager<CommFWTouch> {

    public CommTPManager(String name, URI policyFileName, CommFWResource aResource) {
		super(name, policyFileName, aResource);
    }

    public Set<String> getResetFWTableIterator() {
		return this.touchpoint.getResetFWTable();
	}

	public boolean removeFWFromResetFWTable(String fwName) {
		return this.touchpoint.getResetFWTable().remove(fwName);
	}

}
