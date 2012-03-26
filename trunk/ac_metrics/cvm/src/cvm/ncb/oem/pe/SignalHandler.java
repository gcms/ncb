package cvm.ncb.oem.pe;

import cvm.ncb.oem.pe.actions.ManagerContext;

public interface SignalHandler {
    HandlingResult handle(SignalInstance signal, ManagerContext ctx);
}
