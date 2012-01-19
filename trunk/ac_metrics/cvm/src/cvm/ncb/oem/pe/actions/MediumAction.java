package cvm.ncb.oem.pe.actions;

import cvm.ncb.csm.ManagedObject;
import cvm.ncb.ks.Connection;
import cvm.ncb.oem.pe.Call;
import cvm.ncb.oem.pe.PolicyEvalManager;

import java.util.LinkedHashMap;
import java.util.Map;

public class MediumAction implements ActionInstance {
    public Object execute(ActionContext ctx, Call call) {
        String session = (String) call.getParams().get("session");
        String medium = (String) call.getParams().get("medium");

        Connection con = ctx.getStateManager().getConnection(session);

        ManagedObject nextFw = findConformingFramework(con, medium, ctx.getPolicyEvalManager());
        ManagedObject currentFw = con.getFramework();

        String currentMedium = con.getMedium();

        System.out.println("CurrentFW: " + currentFw + "/NextFW: " + nextFw);
        if (currentFw == null || !currentFw.equals(nextFw) || currentMedium == null || !currentMedium.equals(medium)) {
            disableCurrentFramework(con, currentFw);
            enableNextFw(con, medium, nextFw);
        }
        nextFw.enqueue(call);

        return null;
    }

    private void disableCurrentFramework(Connection con, ManagedObject currentFw) {
        if (currentFw != null) {
            Map<String, Object> params = new LinkedHashMap<String, Object>();
            params.put("session", con.getId());
            currentFw.enqueue(new Call("destroySession", params));
        }
    }

    private void enableNextFw(Connection con, String medium, ManagedObject nextFw) {
        con.setFramework(nextFw);
        con.setMedium(medium);

        Map<String, Object> params = new LinkedHashMap<String, Object>();
        params.put("session", con.getId());
        nextFw.enqueue(new Call("createSession", params));

        for (String party : con.getParticipants()) {
            Map<String, Object> partyParams = new LinkedHashMap<String, Object>();
            partyParams.put("session", con.getId());
            partyParams.put("participant", party);
            nextFw.enqueue(new Call("addAParticipant", partyParams));
        }
    }

    private ManagedObject findConformingFramework(Connection con, String medium, PolicyEvalManager policyEvalManager) {
        Map<String, Object> params = new LinkedHashMap<String, Object>();
        params.put("NumberOfUsers", con.getParticipants().size());
        return policyEvalManager.findConformingObject(medium, "request", params);
    }
}
