package cvm.ncb.tpm;

import cvm.model.CVM_Debug;
import cvm.ncb.csm.CommObject;
import cvm.ncb.ks.CommFWCapabilitySet;
import cvm.ncb.ks.ConIDMappingTable;
import cvm.ncb.ks.Connection;
import edu.fiu.strg.ACSTF.touchpoint.AbstractTouchPoint;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

public class CommFWTouch extends AbstractTouchPoint<CommFWResource> {
    private CopyOnWriteArraySet<String> failedTable = null;
    private CopyOnWriteArraySet<String> resetFWTable = null;
    private Map<String, Boolean> frameworkFailed;

    public CommFWTouch(CommFWResource commFWs) {
        super(commFWs);
        failedTable = new CopyOnWriteArraySet<String>();
        resetFWTable = new CopyOnWriteArraySet<String>();
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
        ConIDMappingTable m_conToFwTable = getResource().getConIDMappingTable();

        for (Connection con : m_conToFwTable.getAllConnections()) {
            for (CommObject comObj : this.getResource().getCObjectList()) {
                if (con.containsComObj(comObj.getName())) {
                    for (String medium : con.getActiveMedia()) {
                        boolean hasFailed = comObj.getBridge().hasMediumFailed(con.getConId(),
                                medium);

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

            CommFWCapabilitySet.getInstance().getFramework(fwName).fail();
            getResource().getCallQueue().add("", 0, "failedFramework", "", new Object[]{fwName});
            failedTable.remove(fwName);
        }
    }

    public void resetFramework() {
        for (String fwName : failedTable) {
            getObjectByName(fwName).getBridge().restartService();
            resetFWTable.add(fwName);
            failedTable.remove(fwName);
            CVM_Debug.getInstance().printDebugMessage("Removed " + fwName + " from failed table");
        }
    }

    public CommObject getObjectByName(String fwName) {
        for (CommObject comObj : getResource().getCObjectList()) {
            if (comObj.getName().equalsIgnoreCase(fwName)) {
                return comObj;
            }
        }

        return null;
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

    public CopyOnWriteArraySet<String> getResetFWTable() {
        return resetFWTable;
    }
}
