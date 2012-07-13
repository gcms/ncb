package cvm.sb.autonomic;

import cvm.sb.manager.HandlingResult;
import cvm.sb.manager.SignalHandler;
import cvm.sb.manager.SignalInstance;
import cvm.sb.manager.ManagerContext;

public class MonitorSignalHandler implements SignalHandler {
    MonitorImpl monitor;

    public MonitorSignalHandler(MonitorImpl monitor) {
        this.monitor = monitor;
    }

    public HandlingResult handle(SignalInstance signal, ManagerContext ctx) {
        monitor.sense(signal);
        return HandlingResult.NOT_HANDLED;
    }
}
