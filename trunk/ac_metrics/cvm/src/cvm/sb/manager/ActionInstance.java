package cvm.sb.manager;

import cvm.sb.manager.ManagerContext;

import java.util.Map;

public interface ActionInstance {
    Object execute(ManagerContext ctx, Map<String, Object> params);
}
