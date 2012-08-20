package cvm.sb.manager.actions;

import cvm.sb.manager.ManagerContext;

import java.util.Map;

public interface ActionInstance {
    Object execute(ManagerContext ctx, Map<String, Object> params);    
}
