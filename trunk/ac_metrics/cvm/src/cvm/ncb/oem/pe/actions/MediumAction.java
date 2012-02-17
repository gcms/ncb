package cvm.ncb.oem.pe.actions;

import cvm.ncb.ks.StateHolder;
import cvm.ncb.oem.pe.SignalInstance;

import java.util.LinkedHashMap;
import java.util.Map;

public class MediumAction implements ActionInstance {
    public Object execute(ActionContext ctx, SignalInstance signal) {
        Object session = signal.getParam("session");
        Object medium = signal.getParam("medium");

        Map<String,Object> params = new LinkedHashMap<String, Object>();
        params.put("session", session);
        params.put("medium", medium);

        StateHolder state = ctx.getStateManager().get(session);
        state.getFramework().enqueue(new SignalInstance(null, signal.getName(), params));

        return null;
    }
}
