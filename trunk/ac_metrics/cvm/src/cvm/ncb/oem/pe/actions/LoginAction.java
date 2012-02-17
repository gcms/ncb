package cvm.ncb.oem.pe.actions;

import cvm.ncb.csm.Resource;
import cvm.ncb.handlers.exception.LoginException;
import cvm.ncb.oem.pe.SignalInstance;

public class LoginAction implements ActionInstance {
    public Object execute(ActionContext ctx, SignalInstance signal) {
        for (Resource framework : ctx.getResourceManager().getAll()) {
            try {
                framework.execute(new SignalInstance("login"));
            } catch (LoginException e) {
                framework.getMetadata().fail();
            }
        }

        return null;
    }
}
