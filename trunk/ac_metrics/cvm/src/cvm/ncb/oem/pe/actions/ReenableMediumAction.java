package cvm.ncb.oem.pe.actions;

import cvm.ncb.ks.StateHolder;
import cvm.ncb.oem.pe.SignalInstance;

import java.util.LinkedHashMap;
import java.util.Map;


public class ReenableMediumAction implements ActionInstance {
    public Object execute(ActionContext ctx, SignalInstance signal) {
        Object medium = signal.getParam("medium");
        Object framework = signal.getSource();

        for (StateHolder con : ctx.getStateManager().getAll()) {
            if (con.getFramework() != null && con.getFramework().equals(framework)
                    && con.getAttr("medium") != null && con.getAttr("medium").equals(medium)) {
                con.getFramework().getMetadata().fail();
                Map<String, Object> params = new LinkedHashMap<String, Object>();
                params.put("session", con.getId());
                params.put("medium", con.getAttr("medium"));
                ctx.getTouchpoint().enqueue(new SignalInstance(null, "enableMedium", params));
            }
        }

        return null;
    }
}
