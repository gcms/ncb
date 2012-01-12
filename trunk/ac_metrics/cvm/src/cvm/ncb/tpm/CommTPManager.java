package cvm.ncb.tpm;

import edu.fiu.strg.ACSTF.manager.GenericManager;

import java.net.URI;

public class CommTPManager extends GenericManager<CommFWTouch> {

    public CommTPManager(String name, URI policyFileName, CommFWResource aResource) {
		super(name, policyFileName, aResource);
    }
}
