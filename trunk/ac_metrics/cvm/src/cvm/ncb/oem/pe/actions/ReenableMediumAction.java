package cvm.ncb.oem.pe.actions;

import cvm.ncb.ks.StateHolder;
import cvm.ncb.oem.pe.SignalInstance;

import java.util.LinkedHashMap;
import java.util.Map;


public class ReenableMediumAction implements ActionInstance {
    public Object execute(ManagerContext ctx, Map<String, Object> params) {
        Object medium = params.get("medium");
        Object framework = params.get("framework");

        for (StateHolder con : ctx.getStateManager().getType("Connection").getAll()) {
            if (con.getResource("framework") != null && con.getResource("framework").equals(framework)
                    && con.get("medium") != null && con.get("medium").equals(medium)) {
                enableMedium(ctx, con);
            }
        }

        return null;
    }

    private void enableMedium(ManagerContext ctx, StateHolder con) {
        Map<String, Object> params = new LinkedHashMap<String, Object>();
        params.put("session", con.getId());
        params.put("medium", con.get("medium"));
        ctx.getTouchpoint().enqueue(new SignalInstance(null, "enableMedium", params));
    }
}
