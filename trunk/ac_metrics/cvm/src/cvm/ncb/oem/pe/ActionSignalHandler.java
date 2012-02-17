package cvm.ncb.oem.pe;

import cvm.ncb.oem.pe.actions.ActionContext;
import cvm.ncb.oem.pe.actions.ActionInstance;

public class ActionSignalHandler implements SignalHandler {
    private String signal;
    public ActionInstance action;

    public ActionSignalHandler(String signal, ActionInstance action) {
        this.signal = signal;
        this.action = action;
    }

    public boolean canHandle(SignalInstance signal) {
        return signal.getName().equals(this.signal);
    }

    public Object handle(SignalInstance signal, ActionContext pem) {
        return action.execute(pem, signal);
    }
}
