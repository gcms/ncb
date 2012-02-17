package cvm.ncb.handlers;

import cvm.model.EventException;
import cvm.model.UsesEventListener;
import cvm.ncb.csm.EventListener;
import cvm.ncb.oem.pe.SignalInstance;

import java.util.ArrayList;
import java.util.List;

/**
 * This class handles the notification of events to the
 * regisetered listeners. This is class specifically signals
 * events to the NCB layer listener.
 *
 * @author fhern006_1
 */
public class EventManager implements EventListener {
    private static List<UsesEventListener> listeners = new ArrayList<UsesEventListener>();

    public synchronized void addUpListener(UsesEventListener listener) {
        listeners.add(listener);
    }

    public void notify(SignalInstance event) {
        if (listeners != null && !listeners.isEmpty()) {
            List<UsesEventListener> list;
            synchronized (this) {
                list = new ArrayList<UsesEventListener>(listeners);
            }
            for (UsesEventListener listener : list) {
                listener.use(event);
            }
        }
    }

    public void throwEvent(SignalInstance event) {
        throw new EventException(event);
    }
}
