package cvm.ncb.oem.pe.actions;

import cvm.ncb.oem.pe.Call;

public class CreateSessionAction implements ActionInstance {
    public Object execute(ActionContext ctx, Call call) {
        ctx.getStateManager().getConnection((String) call.getParams().get("session"));
        return null;
    }
}
