package cvm.sb.resource;

import cvm.sb.adapters.EventException;
import cvm.sb.manager.SignalInstance;

public interface EventListener {
    public void notify(SignalInstance event);
    public void throwEvent(SignalInstance event) throws EventException;
}
