package cvm.ncb.oem.pe;

import cvm.ncb.oem.pe.actions.ActionContext;

public interface SignalHandler {
    boolean canHandle(SignalInstance signal);
    Object handle(SignalInstance signal, ActionContext pem);
}
