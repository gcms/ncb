package cvm.ncb.oem.pe.actions;

import cvm.ncb.ks.Connection;
import cvm.ncb.oem.pe.Call;

import java.util.LinkedHashMap;
import java.util.Map;


public class ReenableMediumAction implements ActionInstance {
    public Object execute(ActionContext ctx, Call call) {
        String fwName = (String) call.getParam("framework");
        for (Connection con : ctx.getStateManager().getAllConnections()) {
            if (con.getFramework() != null && con.getFramework().getName().equals(fwName)) { // framework name
                Map<String, Object> params = new LinkedHashMap<String, Object>();
                params.put("session", con.getId());
                params.put("medium", con.getMedium());
                ctx.getCallQueue().add("enableMedium", params);
            }
        }

        return null;
    }
}
