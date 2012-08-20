package cvm.ncb.oem.pe.actions;

import cvm.sb.manager.actions.MacroActionInstance;
import cvm.sb.manager.ManagerContext;
import cvm.sb.state.StateHolder;

import java.util.Map;
import java.util.Set;

public class AddParticipantAction implements MacroActionInstance {
    public Object execute(ManagerContext ctx, Map<String, Object> params) {
        Object session = params.get("session");
        Object participant = params.get("participant");

        StateHolder con = ctx.getStateManager().getType("Connection").get(session);
        Set participants = con.getAsSet("participants");
        return participants.add(participant);
    }
}
