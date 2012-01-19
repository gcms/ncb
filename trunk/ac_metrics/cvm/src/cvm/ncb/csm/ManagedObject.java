package cvm.ncb.csm;

import cvm.model.EventException;
import cvm.ncb.adapters.Manageable;
import cvm.ncb.oem.pe.Call;
import cvm.ncb.oem.policy.Metadata;

import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Communication object used by CSM
 *
 * @author Frank Hernandez
 */
public class ManagedObject {
    private Manageable bridge;
    private Metadata metadata;
    private ManagedObjectRunner runner = new ManagedObjectRunner();

    public ManagedObject(Manageable bridge, Metadata metadata) {
        this.bridge = bridge;
        this.metadata = metadata;
        runner.start();
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public String getName() {
        return metadata.getName();
    }

    public Object execute(Call call) {
        return execute(call.getName(), call.getParams());
    }

    public Object execute(String message, Map<String, Object> params) {
        try {
            return new BridgeExecutor(bridge).execute(message, params);
        } catch (InvocationTargetException e) {
            if (e.getCause() instanceof EventException) {
                throw (RuntimeException) e.getCause();
            } else if (e.getCause() instanceof RuntimeException)
                throw (RuntimeException) e.getCause();
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        // TODO: fixme
        return null;
    }

    public Object execute(String message) {
        return execute(message, new LinkedHashMap<String, Object>());
    }

    public boolean executeBoolean(String message, Map<String, Object> params) {
        try {
            return new BridgeExecutor(bridge).executeBoolean(message, params);
        } catch (InvocationTargetException e) {
            if (e.getCause() instanceof RuntimeException)
                throw (RuntimeException) e.getCause();
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return false;
    }

    public void enqueue(Call call) {
        calls.offer(call);
        runner.wake();
    }

    public Queue<Call> calls = new ConcurrentLinkedQueue<Call>();

    public class ManagedObjectRunner extends Thread {
        public void run() {
            while (true) {
                while (!calls.isEmpty()) {
                    process(calls.poll());
                }
                doWait();
            }
        }

        private void doWait() {
            try {
                synchronized (this) {
                    this.wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }

        public void process(Call call) {
            execute(call);
        }

        public void wake() {
            synchronized (this) {
                this.notify();
            }
        }
    }
}
