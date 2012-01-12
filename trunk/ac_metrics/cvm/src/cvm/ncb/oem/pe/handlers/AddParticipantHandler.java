package cvm.ncb.oem.pe.handlers;

import cvm.ncb.csm.BridgeCall;
import cvm.ncb.ks.Connection;
import cvm.ncb.oem.pe.Call;
import cvm.ncb.oem.pe.PolicyEvalManager;
import cvm.ncb.oem.pe.SignalHandler;
import cvm.ncb.tpm.CommFWResource;

public class AddParticipantHandler implements SignalHandler {
    public boolean canHandle(Call call) {
        return call.getName().equals("addAParticipant");
    }

    public void handle(Call call, CommFWResource resource, PolicyEvalManager pem) {
        String session = (String) call.getParam("session");
        String participant = (String) call.getParam("participant");

        Connection con = resource.getStateManager().getConnection(session);
        con.getParticipants().add(participant);

        con.enqueue(new BridgeCall("addAParticipant", "", new Object[]{session, participant}));
    }
}
