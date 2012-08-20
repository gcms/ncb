package cvm.ncb.oem.pe.actions;

import cvm.sb.manager.actions.MacroActionInstance;
import cvm.sb.manager.ManagerContext;
import cvm.sb.resource.Resource;
import cvm.sb.manager.SignalInstance;

import java.util.Map;


public class LogoutAction implements MacroActionInstance {
    public Object execute(ManagerContext ctx, Map<String, Object> params) {
        for (Resource framework : ctx.getResourceManager().getAll()) {
            framework.execute(new SignalInstance("logout"));
        }

        return null;
    }
}
