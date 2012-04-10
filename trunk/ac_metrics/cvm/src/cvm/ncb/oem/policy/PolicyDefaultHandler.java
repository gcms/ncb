package cvm.ncb.oem.policy;

import cvm.ncb.csm.Resource;
import cvm.ncb.ks.StateHolder;
import cvm.ncb.oem.pe.SignalInstance;
import cvm.ncb.oem.pe.actions.ManagerContext;

import java.util.LinkedHashMap;
import java.util.Map;

public class PolicyDefaultHandler implements PolicyEvaluationHandler {
    public void handleResource(PolicyRequest request, ManagerContext ctx, Resource selected) {
        Map<String, Object> params = request.getParams();
        Object session = params.get("session");
        StateHolder state = ctx.getStateManager().getType("Connection").get(session);

        Resource currentFw = state.getResource("framework");

        Object nextMedium = params.get("medium");
        Object currentMedium = state.get("medium");

        System.out.println("CurrentFW: " + currentFw + "/NextFW: " + selected);
        if (currentFw == null || !currentFw.equals(selected)
                || currentMedium == null || !currentMedium.equals(nextMedium)) {
            disableCurrentFramework(state, currentFw);
            enableNextFw(state, nextMedium, selected);
        }
    }

    private void disableCurrentFramework(StateHolder con, Resource currentFw) {
        if (currentFw != null) {
            Map<String, Object> params = new LinkedHashMap<String, Object>();
            params.put("session", con.getId());
            currentFw.enqueue(new SignalInstance(null, "destroySession", params));
        }
    }

    private void enableNextFw(StateHolder con, Object medium, Resource nextFw) {
        con.set("framework", nextFw);
        con.set("medium", medium);

        Map<String, Object> params = new LinkedHashMap<String, Object>();
        params.put("session", con.getId());
        nextFw.enqueue(new SignalInstance(null, "createSession", params));

        for (Object party : con.getSet("participants")) {
            Map<String, Object> partyParams = new LinkedHashMap<String, Object>();
            partyParams.put("session", con.getId());
            partyParams.put("participant", party);
            nextFw.enqueue(new SignalInstance(null, "addAParticipant", partyParams));
        }
    }
}
