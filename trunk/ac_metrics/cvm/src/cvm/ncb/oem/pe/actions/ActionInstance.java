package cvm.ncb.oem.pe.actions;

import java.util.Map;

public interface ActionInstance {
    Object execute(ManagerContext ctx, Map<String, Object> params);
}
