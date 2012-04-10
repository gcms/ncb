package cvm.ncb.oem.pe;

import cvm.ncb.oem.pe.actions.ActionCaller;
import cvm.ncb.oem.pe.actions.ManagerContext;
import sb.base.common.Signal;

public class ActionSignalHandler implements SignalHandler {
    private Signal signal;
    public ActionCaller action;

    public ActionSignalHandler(Signal signal, ActionCaller action) {
        this.signal = signal;
        this.action = action;
    }

    public boolean canHandle(SignalInstance signal) {
        return signal.getName().equals(this.signal.getName());
    }

    public HandlingResult handle(SignalInstance signal, ManagerContext ctx) {
        return canHandle(signal)
                ? new HandlingResult(action.execute(ctx, signal))
                : HandlingResult.NOT_HANDLED;
    }
}
