package cvm.ncb.oem.pe.actions;

import cvm.sb.manager.actions.MacroActionInstance;
import cvm.sb.manager.ManagerContext;

import java.util.Map;

public class CreateSessionAction implements MacroActionInstance {
    public Object execute(ManagerContext ctx, Map<String, Object> params) {        
        return ctx.getStateManager().getType("Connection").create(params.get("session"));
    }
}
