package cvm.ncb.oem.pe.actions;

import cvm.ncb.ks.StateHolder;

import java.util.Map;


public class RemoveParticipantAction implements ActionInstance {
    public Object execute(ManagerContext ctx, Map<String, Object> params) {
        Object session = params.get("session");
        Object participant = params.get("participant");

        StateHolder con = ctx.getStateManager().getType("Connection").get(session);
        con.getSet("participants").remove(participant);

        return null;
    }
}
