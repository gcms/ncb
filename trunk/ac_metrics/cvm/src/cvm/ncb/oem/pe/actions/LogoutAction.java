package cvm.ncb.oem.pe.actions;

import cvm.ncb.csm.Resource;
import cvm.ncb.oem.pe.SignalInstance;


public class LogoutAction implements ActionInstance {
    public Object execute(ActionContext ctx, SignalInstance signal) {
//        for (Connection con : ctx.getStateManager().getAllConnections()) {
//            Map<String, Object> params = new LinkedHashMap<String,Object>();
//            params.put("session", con.getId());
//            con.getFramework().enqueue(new SignalInstance(null, "destroySession", params));
//        }

        for (Resource framework : ctx.getResourceManager().getAll()) {
            framework.execute(new SignalInstance("logout"));
        }

        return null;
    }
}
