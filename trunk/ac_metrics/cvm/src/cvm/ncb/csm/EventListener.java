package cvm.ncb.csm;

import cvm.ncb.adapters.EventException;
import cvm.ncb.oem.pe.SignalInstance;

public interface EventListener {
    public void notify(SignalInstance event);
    public void throwEvent(SignalInstance event) throws EventException;
}
