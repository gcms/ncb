package cvm.sb.manager;

import cvm.sb.resource.ResourceManager;
import cvm.sb.state.StateManager;
import cvm.sb.resource.Touchpoint;


public interface ManagerContext {
    public StateManager getStateManager();

    public Touchpoint getMainManager();

    public ResourceManager getResourceManager();
}
