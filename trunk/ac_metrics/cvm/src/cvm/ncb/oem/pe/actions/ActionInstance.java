package cvm.ncb.oem.pe.actions;

import cvm.ncb.oem.pe.SignalInstance;

public interface ActionInstance {
    Object execute(ActionContext ctx, SignalInstance signal);
}
