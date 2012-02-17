package cvm.ncb.oem.pe;

import cvm.ncb.csm.*;
import cvm.ncb.ks.ResourceManager;
import cvm.ncb.ks.StateHolder;
import cvm.ncb.ks.StateManager;
import cvm.ncb.oem.pe.actions.ActionContext;
import cvm.ncb.oem.policy.PolicyManager;
import cvm.ncb.oem.policy.repository.loader.FilePolicyLoader;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MainManager extends AbstractTouchpoint implements PolicyEvalManager, EventListener {
    private static Log log = LogFactory.getLog(MainManager.class);

    private List<SignalHandler> callHandlers = new ArrayList<SignalHandler>();

    private PolicyManager policyManager;
    private EventListener eventListener;
    private ResourceManager resourceManager;
    private StateManager stateManager;

    public ManagerContext getActionContext() {
        return new ManagerContext();
    }

    private class ManagerContext implements ActionContext {

        public StateManager getStateManager() {
            return stateManager;
        }

        public Touchpoint getTouchpoint() {
            return MainManager.this;
        }

        public PolicyEvalManager getPolicyEvalManager() {
            return MainManager.this;
        }

        public ResourceManager getResourceManager() {
            return resourceManager;
        }
    }


    public MainManager(String pmConfigFile, ResourceManager resourceManager, EventListener eventListener) throws URISyntaxException {
        this(createPolicyManager(pmConfigFile), resourceManager, new StateManager(), eventListener);
    }

    public MainManager(PolicyManager policyManager, ResourceManager resourceManager,
                       StateManager stateManager, EventListener eventListener) throws URISyntaxException {
        this.policyManager = policyManager;
        this.resourceManager = resourceManager;
        this.stateManager = stateManager;
        this.eventListener = eventListener;
    }

    private static PolicyManager createPolicyManager(String pmConfigFile) throws URISyntaxException {
        FilePolicyLoader loader = FilePolicyLoader.createInstance(PolicyManager.class.getResource(pmConfigFile).toURI());
        return new PolicyManager(loader);
    }

    public void registerCallHandler(SignalHandler handler) {
        callHandlers.add(handler);
    }

    public void start() {
        resourceManager.setEventListener(this);
        resourceManager.start();
        super.start();
    }

    public void stop() {
        super.stop();
        resourceManager.stop();
    }

    /**
     * evaluating the signal against the frameworks to
     * find the appropriate framework
     */
    public Object execute(SignalInstance signal) {
        log.debug("Evaluating: " + signal);

        for (SignalHandler callHandler : callHandlers) {
            if (callHandler.canHandle(signal)) {
                evaluatePolicies(signal);
                return callHandler.handle(signal, getActionContext());
            }
        }

        eventListener.notify(signal);
        log.warn("SignalInstance not handled!");
        return null;
    }

    public StateHolder getState(SignalInstance signal) {
        if (signal.getParams().containsKey("session"))
            return getActionContext().getStateManager().get(signal.getParam("session"));

        return null;
    }

    public PolicyEvaluationContext getPolicyContext(StateHolder state, SignalInstance signal) {
        if (signal.getParams().containsKey("session") && signal.getParams().containsKey("medium")) {
            Map<String, Object> params = new LinkedHashMap<String, Object>();
            params.put("NumberOfUsers", state.getSet("participants").size());

            return new PolicyEvaluationContext(signal.getParam("medium").toString(), params);
        }

        return null;
    }

    public void evaluatePolicies(SignalInstance signal) {
        StateHolder con = getState(signal);
        PolicyEvaluationContext ctx = getPolicyContext(con, signal);

        if (ctx == null)
            return;

        Resource nextFw = findConformingObject(ctx.getFeature(), "request", ctx.getParams());
        Resource currentFw = con.getFramework();

        Object nextMedium = signal.getParam("medium");
        Object currentMedium = con.getAttr("medium");

        System.out.println("CurrentFW: " + currentFw + "/NextFW: " + nextFw);
        if (currentFw == null || !currentFw.equals(nextFw) || currentMedium == null || !currentMedium.equals(nextMedium)) {
            disableCurrentFramework(con, currentFw);
            enableNextFw(con, nextMedium, nextFw);
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
        con.setFramework(nextFw);
        con.setAttr("medium", medium);

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

    public Resource findConformingObject(String requestType, String operationType, Map<String, Object> params) {
        return policyManager.findConformingObject(resourceManager, requestType, operationType, params);
    }

    public void throwEvent(SignalInstance e) {
//        exceptionHandler.handleException(e);
    }

    public void notify(SignalInstance event) {
        enqueue(event.getSource(), event.getName(), event.getParams());
//        eventListener.notify(event);
    }
}
