package cvm.ncb.tpm;

import cvm.model.CVM_Debug;
import cvm.ncb.csm.ManagedObject;
import cvm.ncb.ks.Connection;
import cvm.ncb.ks.StateManager;
import edu.fiu.strg.ACSTF.touchpoint.AbstractTouchPoint;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

public class CommFWTouch extends AbstractTouchPoint<CommFWResource> {
    private CopyOnWriteArraySet<String> failedTable = null;
    private Map<String, Boolean> frameworkFailed;

    public CommFWTouch(CommFWResource commFWs) {
        super(commFWs);
        failedTable = new CopyOnWriteArraySet<String>();
        frameworkFailed = new HashMap<String, Boolean>();
    }


    private void updateFailedTable(String fwName, Boolean hasFailed) {
        Boolean oldHasFailed = frameworkFailed.get(fwName);
        frameworkFailed.put(fwName, hasFailed);

        if (hasFailed && oldHasFailed != hasFailed) {
            failedTable.add(fwName);
        }
    }

    private void checkFrameworkForFailure() {
        StateManager stateManager = getResource().getStateManager();

        for (Connection con : stateManager.getAllConnections()) {
            for (ManagedObject comObj : this.getResource().getObjects()) {
                if (con.containsFramework(comObj.getName())) {
                    for (String medium : con.getActiveMedia()) {
                        Map<String, Object> params = new LinkedHashMap<String, Object>();
                        params.put("session", con.getId());
                        params.put("medium", medium);

                        boolean hasFailed = comObj.executeBoolean("hasMediumFailed", params);

                        updateFailedTable(comObj.getName(), hasFailed);
                    }
                }
            }
        }
    }

    public boolean hasFailed() {
        checkFrameworkForFailure();
        return !failedTable.isEmpty();
    }

    public void notifyFailedFramework() {
        CVM_Debug.getInstance().printDebugMessage("Detected:" + System.currentTimeMillis());

        for (String fwName : failedTable) {
            CVM_Debug.getInstance().printDebugMessage(fwName + " is no longer available");

            getResource().getCommObjectManager().getObject(fwName).getMetadata().fail();
            Map<String, Object> params = new LinkedHashMap<String, Object>();
            params.put("framework", fwName);
            getResource().getCallQueue().add("failedFramework", params);
            failedTable.remove(fwName);
        }
    }

    public boolean hasFailedReset() {
        return false;
    }

    public void reInstantiate() {
        CVM_Debug.getInstance().printDebugMessage("Restored Number to 5.");
    }

    private void doPause(int iTimeInSeconds) {
        long t0, t1;
        CVM_Debug.getInstance().printDebugMessage("timer start");
        t0 = System.currentTimeMillis();
        t1 = System.currentTimeMillis() + (iTimeInSeconds * 1000);

        CVM_Debug.getInstance().printDebugMessage("T0: " + t0);
        CVM_Debug.getInstance().printDebugMessage("T1: " + t1);

        do {
            t0 = System.currentTimeMillis();

        } while (t0 < t1);

        CVM_Debug.getInstance().printDebugMessage("timer end");

    }
}
