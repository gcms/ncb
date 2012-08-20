package cvm.sb.manager;

import cvm.sb.resource.ResourceManager;
import cvm.sb.state.StateManager;


public interface ManagerContext {
    public StateManager getStateManager();

    public MainManager getMainManager();

    public ResourceManager getResourceManager();
}
