package cvm.ncb.oem.pe.actions;

import cvm.ncb.csm.ManagedObject;
import cvm.ncb.handlers.exception.LoginException;
import cvm.ncb.oem.pe.Call;


public class LoginAction implements ActionInstance {
    public Object execute(ActionContext ctx, Call call) {
        for (ManagedObject framework : ctx.getObjectManager().getAllObjects()) {
            try {
                framework.execute("login");
            } catch (LoginException e) {
                framework.getMetadata().fail();
            }
        }

        return null;
    }
}
