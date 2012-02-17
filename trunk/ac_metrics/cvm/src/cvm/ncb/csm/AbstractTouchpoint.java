package cvm.ncb.csm;

import cvm.ncb.oem.pe.SignalInstance;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

public abstract class AbstractTouchpoint implements Touchpoint {
    private static Log log = LogFactory.getLog(AbstractTouchpoint.class);

    private ThreadedQueue queue = new ThreadedQueue(new ConcurrentLinkedQueue<SignalInstance>());
    private EventListener eventListener;

    public void start() {
        queue.start(this);
    }

    public final void enqueue(SignalInstance signal) {
        log.debug("Call enqueued: " + signal);
        queue.enqueue(signal);
    }

    public final void enqueue(Object source, String message) {
        enqueue(source, message, new LinkedHashMap<String, Object>());
    }

    public final void enqueue(Object source, String message, Map<String, Object> params) {
        enqueue(new SignalInstance(source, message, params));
    }

    public final void setEventListener(EventListener eventListener) {
        this.eventListener = eventListener;
    }

    protected final EventListener getEventListener() {
        return eventListener;
    }

    public void stop() {
        queue.stop();
    }
}
