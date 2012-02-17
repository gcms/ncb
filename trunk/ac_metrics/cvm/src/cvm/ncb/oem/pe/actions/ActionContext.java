package cvm.ncb.oem.pe.actions;

import cvm.ncb.ks.ResourceManager;
import cvm.ncb.ks.StateManager;
import cvm.ncb.oem.pe.PolicyEvalManager;


public interface ActionContext {
    public StateManager getStateManager();

    public cvm.ncb.csm.Touchpoint getTouchpoint();

    public PolicyEvalManager getPolicyEvalManager();

    public ResourceManager getResourceManager();
}
