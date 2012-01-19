package cvm.ncb.oem.pe.actions;

import cvm.ncb.csm.ManagedObject;
import cvm.ncb.oem.pe.Call;


public class LogoutAction implements ActionInstance {
    public Object execute(ActionContext ctx, Call call) {
        for (ManagedObject framwork : ctx.getObjectManager().getAllObjects()) {
            framwork.execute("logout");
        }

        return null;
    }
}
