package cvm.ncb.handlers;

import cvm.model.CVM_Debug;
import cvm.model.Event;
import cvm.model.UsesEventListener;
//import cvm.ucm.handlers.UCMEventObjectManager;

/**
 * This class listens for and handles possible events coming
 * from NCB. Events not handled are notified to UCM for handling.
 *
 * @author Frank Hernandez
 */

public class EventHandler implements UsesEventListener {
    private static EventHandler instance = null;
    //private UCMEventObjectManager m_ucmNotifier = null;

    /**
     * The NCBEventhandler constructor register itself with the event source,
     * which will fire the events.
     */
    private EventHandler(EventManager notifier) {
        notifier.addUpListener(this);
    }

    /**
     * This method will handle all the events coming from NCB
     *
     * @param e the event that will be handled by the handler class
     */
    private synchronized void handleEvent(Event e) {
        /*
           * For now all there are no Specific events
           * that NCB handles so the overlying layer UCM
           * is notified.
           */
        CVM_Debug.getInstance().printDebugMessage("NCB EventHandler: Firing UCM Event");
    }

    /**
     * Implmentation of the use method
     * from Uses_Listener class
     */
    public void use(Event event) {
        this.handleEvent(event);
    }
}
