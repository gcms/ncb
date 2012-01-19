package cvm.ncb.oem.pe.actions;

import cvm.ncb.oem.pe.Call;


public interface ActionInstance {
    Object execute(ActionContext ctx, Call call);
}
