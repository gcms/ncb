package cvm.ncb.tpm;

import edu.fiu.strg.ACSTF.manager.GenericManager;
import edu.fiu.strg.ACSTF.resource.AbstractResource;

import java.net.URI;

public class TouchpointManager extends GenericManager<Touchpoint> {

    public TouchpointManager(String name, URI policyFileName, AbstractResource aResource) {
		super(name, policyFileName, aResource);
    }
}
