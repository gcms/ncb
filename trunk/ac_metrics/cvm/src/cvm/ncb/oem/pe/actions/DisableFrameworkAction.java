package cvm.ncb.oem.pe.actions;


import cvm.sb.manager.actions.MacroActionInstance;
import cvm.sb.manager.ManagerContext;
import cvm.sb.resource.Resource;

import java.util.Map;

public class DisableFrameworkAction implements MacroActionInstance {
    public Object execute(ManagerContext ctx, Map<String, Object> params) {
        Resource framework = (Resource) params.get("framework");
        framework.getMetadata().fail();

        return null;
    }
}
