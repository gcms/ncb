package cvm.ncb.oem.pe.actions;

import cvm.sb.manager.actions.MacroActionInstance;
import cvm.sb.manager.ManagerContext;

import java.util.Map;

public class DestroySessionAction implements MacroActionInstance {
    public Object execute(ManagerContext ctx, Map<String, Object> params) {        
        return ctx.getStateManager().getType("Connection").remove(params.get("session"));
    }
}
