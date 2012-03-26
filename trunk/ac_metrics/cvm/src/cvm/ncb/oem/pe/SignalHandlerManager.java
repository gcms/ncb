package cvm.ncb.oem.pe;

import cvm.ncb.oem.pe.actions.ManagerContext;

import java.util.Collection;
import java.util.PriorityQueue;

public class SignalHandlerManager {
    private Collection<HandlerEntry> handlers = new PriorityQueue<HandlerEntry>();

    public void register(int priority, SignalHandler handler) {
        handlers.add(new HandlerEntry(priority, handler));
    }

    public void register(SignalHandler handler) {
        register(0, handler);
    }

    public HandlingResult handle(SignalInstance signal, ManagerContext ctx) {
        for (HandlerEntry entry : handlers) {
            SignalHandler handler = entry.getHandler();
            HandlingResult result = handler.handle(signal, ctx);
            if (result.isHandled())
                return result;
        }

        return null;
    }

    class HandlerEntry implements Comparable<HandlerEntry> {
        private int priority;
        private SignalHandler handler;

        HandlerEntry(int priority, SignalHandler handler) {
            this.priority = priority;
            this.handler = handler;
        }

        public int compareTo(HandlerEntry o) {
            return priority - o.priority;
        }

        public SignalHandler getHandler() {
            return handler;
        }
    }
}
