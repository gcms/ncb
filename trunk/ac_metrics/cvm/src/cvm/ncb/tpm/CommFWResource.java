package cvm.ncb.tpm;

import cvm.ncb.csm.ManagedObject;
import cvm.ncb.ks.ObjectManager;
import cvm.ncb.ks.StateManager;
import cvm.ncb.oem.pe.CallQueue;
import edu.fiu.strg.ACSTF.resource.AbstractResource;

import java.util.Collection;

public class CommFWResource extends AbstractResource {
    private CallQueue callQueue;
    private StateManager stateManager;
    private ObjectManager csMgr;

    public CommFWResource(CallQueue m_callQueue, StateManager stateManager, ObjectManager csMgr) {
        this.callQueue = m_callQueue;
        this.stateManager = stateManager;
        this.csMgr = csMgr;
    }

    public ObjectManager getCommObjectManager() {
        return csMgr;
    }

    public Collection<ManagedObject> getObjects() {
        return getCommObjectManager().getAllObjects();
    }

    public CallQueue getCallQueue() {
        return callQueue;
    }

    public StateManager getStateManager() {
        return stateManager;
    }
}
