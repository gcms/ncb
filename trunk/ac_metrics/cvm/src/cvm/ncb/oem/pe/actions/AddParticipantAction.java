package cvm.ncb.oem.pe.actions;

import cvm.ncb.ks.Connection;
import cvm.ncb.oem.pe.Call;

public class AddParticipantAction implements ActionInstance {
    public Object execute(ActionContext ctx, Call call) {
        String session = (String) call.getParams().get("session");
        String participant = (String) call.getParams().get("participant");

        Connection con = ctx.getStateManager().getConnection(session);
        con.getParticipants().add(participant);

        return null;
    }
}
