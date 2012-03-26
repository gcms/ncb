package cvm.ncb.oem.pe.actions;

import cvm.ncb.csm.Resource;
import cvm.ncb.oem.pe.SignalInstance;

import java.util.Map;


public class LogoutAction implements ActionInstance {
    public Object execute(ManagerContext ctx, Map<String, Object> params) {
        for (Resource framework : ctx.getResourceManager().getAll()) {
            framework.execute(new SignalInstance("logout"));
        }

        return null;
    }
}
