package cvm.ncb.oem.pe.actions;

import cvm.ncb.ks.StateHolder;

import java.util.Map;
import java.util.Set;

public class AddParticipantAction implements ActionInstance {
    public Object execute(ManagerContext ctx, Map<String, Object> params) {
        Object session = params.get("session");
        Object participant = params.get("participant");

        StateHolder con = ctx.getStateManager().getType("Connection").get(session);
        Set participants = con.getSet("participants");
        return participants.add(participant);
    }
}
