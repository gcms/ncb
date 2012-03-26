package cvm.ncb.oem.autonomic;

import cvm.ncb.oem.pe.HandlingResult;
import cvm.ncb.oem.pe.SignalHandler;
import cvm.ncb.oem.pe.SignalInstance;
import cvm.ncb.oem.pe.actions.ManagerContext;

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
