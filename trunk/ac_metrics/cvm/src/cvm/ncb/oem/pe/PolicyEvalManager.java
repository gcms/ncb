package cvm.ncb.oem.pe;

import cvm.ncb.csm.BridgeCall;
import cvm.ncb.csm.ManagedObject;
import cvm.ncb.handlers.ExceptionHandler;
import cvm.ncb.handlers.exception.LoginException;
import cvm.ncb.ks.Connection;
import cvm.ncb.ks.ObjectManager;
import cvm.ncb.ks.StateManager;
import cvm.ncb.oem.policy.Metadata;
import cvm.ncb.oem.policy.PolicyManager;
import cvm.ncb.repository.loader.FilePolicyLoader;
import cvm.ncb.repository.loader.GlobalConstant;
import cvm.ncb.tpm.CommFWResource;
import cvm.ncb.tpm.CommTPManager;
import edu.fiu.strg.ACSTF.resource.AbstractResource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

public class PolicyEvalManager extends AbstractResource implements Runnable {
    private static Log log = LogFactory.getLog(PolicyEvalManager.class);

    private CallQueue signalQueue = new CallQueue();
    private PolicyManager pm = null;
    private CommTPManager scManager;
    private boolean useACF = true;
    private boolean run = false;

    private CommFWResource commFWResource;

    private List<SignalHandler> callHandlers;
    private ExceptionHandler exceptionHandler;
    private ObjectManager objectManager;


    public PolicyEvalManager(ObjectManager objectManager, String acConfigFile, String pmConfigFile, ExceptionHandler exceptionHandler) throws URISyntaxException {
        this.objectManager = objectManager;
        this.exceptionHandler = exceptionHandler;
        commFWResource = new CommFWResource(signalQueue, new StateManager(), objectManager);

        scManager = createTouchPointManager(commFWResource, acConfigFile);
        pm = createPolicyManager(pmConfigFile);

        callHandlers = new ArrayList<SignalHandler>();
    }

    private PolicyManager createPolicyManager(String pmConfigFile) throws URISyntaxException {
        FilePolicyLoader loader = FilePolicyLoader.createInstance(this.getClass().getResource(pmConfigFile).toURI());
        return new PolicyManager(loader);
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
    }

    private CommTPManager createTouchPointManager(CommFWResource cr, String acConfigFile) throws URISyntaxException {
        return new CommTPManager("CVM_SC_MGR",
                this.getClass().getResource(acConfigFile).toURI(), cr);
    }

    public void run() {
        if (useACF)
            scManager.manage();

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
                callHandler.handle(m_call, commFWResource, this);
                return;
            }
        }

        log.warn("Call not handled!");
    }

    public Metadata findConformingObject(GlobalConstant.RequestedType reqType, GlobalConstant.OperationType opType, Map<String, Object> params) {
        TreeSet<Metadata> allMetadatas = objectManager.getAvailableObjects();
        TreeSet<Metadata> fwSet = pm.getConformingObjects(allMetadatas, reqType, opType, params);

        log.debug("All objects: " + allMetadatas + " Reduced set: " + fwSet);
        return fwSet.iterator().next();
    }

    /**
     * executing the call
     */
    public void executeCSMCommand(Connection con, BridgeCall call) {
        log.debug("Executing call: " + call);
        try {
            String commObjectName = con.getFramework(call);

            log.debug("Calling: " + call + " [" + call.getMedium() + "] on " + commObjectName);

            ManagedObject managedObject = objectManager.getObject(commObjectName);
            managedObject.execute(call);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (InvocationTargetException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IllegalAccessException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public CallQueue getCallQueue() {
        return signalQueue;
    }

    public void handleException(LoginException e) {
        exceptionHandler.handleException(e);
    }

    public ObjectManager getObjectManager() {
        return objectManager;
    }
}
