package cvm.ncb.oem.pe.actions;

import cvm.ncb.ks.StateHolder;
import cvm.ncb.oem.pe.SignalInstance;

public class AddParticipantAction implements ActionInstance {
    public Object execute(ActionContext ctx, SignalInstance signal) {
        Object session = signal.getParams().get("session");
        Object participant = signal.getParams().get("participant");

        StateHolder con = ctx.getStateManager().get(session);
        con.getSet("participants").add(participant);

        return null;
    }
}
