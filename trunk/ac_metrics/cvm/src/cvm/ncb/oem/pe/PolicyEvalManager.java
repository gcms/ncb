package cvm.ncb.oem.pe;

import cvm.ncb.csm.ManagedObject;
import cvm.ncb.handlers.ExceptionHandler;
import cvm.ncb.handlers.exception.LoginException;
import cvm.ncb.ks.ObjectManager;
import cvm.ncb.ks.StateManager;
import cvm.ncb.oem.pe.actions.ActionContext;
import cvm.ncb.oem.policy.Metadata;
import cvm.ncb.oem.policy.PolicyManager;
import cvm.ncb.repository.loader.FilePolicyLoader;
import cvm.ncb.tpm.TouchpointManager;
import edu.fiu.strg.ACSTF.resource.AbstractResource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

public class PolicyEvalManager extends AbstractResource implements Runnable, ActionContext {
    private static Log log = LogFactory.getLog(PolicyEvalManager.class);

    private CallQueue signalQueue = new CallQueue();
    private List<SignalHandler> callHandlers = new ArrayList<SignalHandler>();
    private boolean run = false;

    private PolicyManager policyManager;
    private TouchpointManager touchpointManager;
    private ExceptionHandler exceptionHandler;
    private ObjectManager objectManager;
    private StateManager stateManager;


    public PolicyEvalManager(String acConfigFile, String pmConfigFile, ObjectManager objectManager, ExceptionHandler exceptionHandler) throws URISyntaxException {
        this(acConfigFile, createPolicyManager(pmConfigFile), objectManager, new StateManager(), exceptionHandler);
    }

    public PolicyEvalManager(String acConfigFile, PolicyManager policyManager, ObjectManager objectManager,
                             StateManager stateManager, ExceptionHandler exceptionHandler) throws URISyntaxException {
        this.policyManager = policyManager;
        this.objectManager = objectManager;
        this.stateManager = stateManager;
        this.exceptionHandler = exceptionHandler;

        touchpointManager = createTouchPointManager(this, acConfigFile);
    }

    private static PolicyManager createPolicyManager(String pmConfigFile) throws URISyntaxException {
        FilePolicyLoader loader = FilePolicyLoader.createInstance(PolicyManager.class.getResource(pmConfigFile).toURI());
        return new PolicyManager(loader);
    }


    private static TouchpointManager createTouchPointManager(PolicyEvalManager cr, String acConfigFile) throws URISyntaxException {
        return new TouchpointManager("CVM_SC_MGR", PolicyEvalManager.class.getResource(acConfigFile).toURI(), cr);
    }

    public void registerHandler(SignalHandler handler) {
        callHandlers.add(handler);
    }

    public void start() {
        run = true;
        Thread t = new Thread(this);
        t.start();
    }

    public void stop() {
        run = false;
        touchpointManager.stop();
    }

    public void run() {
        touchpointManager.manage();

        while (run) {
            if (signalQueue.hasNext()) {
                Call m_call = signalQueue.next();
                log.debug("Next command on queue: " + m_call);
                evaluateCall(m_call);
            }
            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    /**
     * evaluating the call against the frameworks to
     * find the appropriate framework
     */
    private void evaluateCall(Call m_call) {
        log.debug("Evaluating: " + m_call);

        for (SignalHandler callHandler : callHandlers) {
            if (callHandler.canHandle(m_call)) {
                callHandler.handle(m_call, this);
                return;
            }
        }

        log.warn("Call not handled!");
    }

    public ManagedObject findConformingObject(String requestType, String operationType, Map<String, Object> params) {
        TreeSet<Metadata> allMetadatas = objectManager.getAvailableObjects();
        TreeSet<Metadata> fwSet = policyManager.getConformingObjects(allMetadatas, requestType, operationType, params);

        log.debug("All objects: " + allMetadatas + " Reduced set: " + fwSet);
        Metadata metadata = fwSet.iterator().next();
        return metadata != null ? objectManager.getObject(metadata.getName()) : null;
    }

    public CallQueue getCallQueue() {
        return signalQueue;
    }

    public void handleException(LoginException e) {
        exceptionHandler.handleException(e);
    }

    public StateManager getStateManager() {
        return stateManager;
    }

    public PolicyEvalManager getPolicyEvalManager() {
        return this;
    }

    public ObjectManager getObjectManager() {
        return objectManager;
    }
}
