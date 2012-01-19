package cvm.ncb.oem.pe.actions;

import cvm.ncb.ks.ObjectManager;
import cvm.ncb.ks.StateManager;
import cvm.ncb.oem.pe.CallQueue;
import cvm.ncb.oem.pe.PolicyEvalManager;


public interface ActionContext {
    public StateManager getStateManager();

    public PolicyEvalManager getPolicyEvalManager();

    public ObjectManager getObjectManager();

    public CallQueue getCallQueue();

}
