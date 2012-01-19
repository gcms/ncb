package cvm.ncb.tpm;

import cvm.ncb.oem.pe.PolicyEvalManager;
import edu.fiu.strg.ACSTF.manager.GenericManager;

import java.net.URI;

public class TouchpointManager extends GenericManager<Touchpoint> {

    public TouchpointManager(String name, URI policyFileName, PolicyEvalManager aResource) {
		super(name, policyFileName, aResource);
    }
}
