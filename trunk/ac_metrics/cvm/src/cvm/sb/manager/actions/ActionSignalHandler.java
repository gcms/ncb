package cvm.sb.manager.actions;

import cvm.sb.manager.HandlingResult;
import cvm.sb.manager.ManagerContext;
import cvm.sb.manager.SignalHandler;
import cvm.sb.manager.SignalInstance;
import sb.base.common.Signal;

public class ActionSignalHandler implements SignalHandler {
    private Signal signal;
    public ActionCaller action;

    public ActionSignalHandler(Signal signal, ActionCaller action) {
        this.signal = signal;
        this.action = action;
    }

    private boolean canHandle(SignalInstance signal) {
        return signal.getName().equals(this.signal.getName());
    }

    public HandlingResult handle(SignalInstance signal, ManagerContext ctx) {
        return canHandle(signal)
                ? new HandlingResult(action.execute(ctx, signal))
                : HandlingResult.NOT_HANDLED;
    }
}
