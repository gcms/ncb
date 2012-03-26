package cvm.ncb.oem.pe.actions;

import cvm.ncb.csm.Resource;
import cvm.ncb.ks.StateHolder;
import cvm.ncb.oem.policy.PolicyContext;
import cvm.ncb.oem.pe.SignalInstance;
import cvm.ncb.oem.policy.PolicyManager;
import cvm.ncb.oem.policy.repository.loader.FilePolicyLoader;

import java.net.URISyntaxException;
import java.util.LinkedHashMap;
import java.util.Map;

public class MediumAction implements ActionInstance {
    private static PolicyManager createPolicyManager(String pmConfigFile) throws URISyntaxException {
        FilePolicyLoader loader = FilePolicyLoader.createInstance(PolicyManager.class.getResource(pmConfigFile).toURI());
        return new PolicyManager(loader);
    }

    PolicyManager policyManager;

    public MediumAction() {
        try {
            policyManager = createPolicyManager("./repository/examples");
        } catch (URISyntaxException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }


    public Object execute(ManagerContext ctx, Map<String, Object> params) {
        String signal = (String) params.get("signal");
        Object session = params.get("session");
        Object medium = params.get("medium");

        StateHolder state = ctx.getStateManager().getType("Connection").get(session);

//        evaluatePolicies(ctx, state, params);

        Map<String, Object> newParams = new LinkedHashMap<String, Object>();
        newParams.put("session", session);
        newParams.put("medium", medium);
        state.getResource("framework").enqueue(new SignalInstance(null, signal, newParams));

        return null;
    }


    public PolicyContext getPolicyContext(StateHolder state, String medium) {
        Map<String, Object> params = new LinkedHashMap<String, Object>();
        params.put("NumberOfUsers", state.getSet("participants").size());

        return new PolicyContext(medium, params);
    }

    public void evaluatePolicies(ManagerContext ctx, StateHolder state, Map<String, Object> params) {
        PolicyContext pec = getPolicyContext(state, params.get("medium").toString());

        Resource nextFw = policyManager.findConformingObject(ctx.getResourceManager(), pec.getFeature(), "request", pec.getParams());
        Resource currentFw = state.getResource("framework");

        Object nextMedium = params.get("medium");
        Object currentMedium = state.get("medium");

        System.out.println("CurrentFW: " + currentFw + "/NextFW: " + nextFw);
        if (currentFw == null || !currentFw.equals(nextFw) || currentMedium == null || !currentMedium.equals(nextMedium)) {
            disableCurrentFramework(state, currentFw);
            enableNextFw(state, nextMedium, nextFw);
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
