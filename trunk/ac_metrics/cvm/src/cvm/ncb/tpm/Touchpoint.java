package cvm.ncb.tpm;

import cvm.model.CVM_Debug;
import cvm.ncb.csm.ManagedObject;
import cvm.ncb.ks.Connection;
import cvm.ncb.ks.StateManager;
import cvm.ncb.oem.pe.actions.ActionContext;
import edu.fiu.strg.ACSTF.touchpoint.AbstractTouchPoint;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

public class Touchpoint extends AbstractTouchPoint<ActionContext> {
    private CopyOnWriteArraySet<String> failedTable = null;
    private Map<String, Boolean> frameworkFailed;

    public Touchpoint(ActionContext commFWs) {
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
            if (con.getFramework() != null) {
                Map<String, Object> params = new LinkedHashMap<String, Object>();
                params.put("session", con.getId());
                params.put("medium", con.getMedium());

                ManagedObject framework = con.getFramework();
                boolean hasFailed = framework.executeBoolean("hasMediumFailed", params);
                updateFailedTable(framework.getName(), hasFailed);
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

            getResource().getObjectManager().getObject(fwName).getMetadata().fail();
            Map<String, Object> params = new LinkedHashMap<String, Object>();
            params.put("framework", fwName);
            getResource().getCallQueue().add("failedFramework", params);
            failedTable.remove(fwName);
        }
    }
}
