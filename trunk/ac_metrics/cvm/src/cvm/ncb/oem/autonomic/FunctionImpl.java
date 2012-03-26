package cvm.ncb.oem.autonomic;

import cvm.ncb.oem.pe.actions.ManagerContext;

public class FunctionImpl {
    private ManagerContext context;

    public void setContext(ManagerContext context) {
        this.context = context;
    }

    protected ManagerContext getContext() {
        return context;
    }

}
