package cvm.ncb.oem.pe.actions;

import cvm.ncb.csm.Touchpoint;
import cvm.ncb.ks.ResourceManager;
import cvm.ncb.ks.StateManager;


public interface ManagerContext {
    public StateManager getStateManager();

    public Touchpoint getTouchpoint();

    public ResourceManager getResourceManager();
}
