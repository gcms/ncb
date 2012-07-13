package cvm.ncb.oem.pe.actions;

import cvm.sb.manager.ActionInstance;
import cvm.sb.state.StateHolder;
import cvm.sb.manager.ManagerContext;
import cvm.sb.manager.SignalInstance;

import java.util.LinkedHashMap;
import java.util.Map;

public class MediumAction implements ActionInstance {

    public Object execute(ManagerContext ctx, Map<String, Object> params) {
        String signal = (String) params.get("signal");
        Object session = params.get("session");
        Object medium = params.get("medium");

        StateHolder state = ctx.getStateManager().getType("Connection").get(session);

        Map<String, Object> newParams = new LinkedHashMap<String, Object>();
        newParams.put("session", session);
        newParams.put("medium", medium);
        state.getResource("framework").enqueue(new SignalInstance(null, signal, newParams));

        return null;
    }


}
