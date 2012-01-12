package cvm.ncb.oem.pe.handlers;

import cvm.ncb.csm.BridgeCall;
import cvm.ncb.ks.Connection;
import cvm.ncb.oem.pe.Call;
import cvm.ncb.oem.pe.PolicyEvalManager;
import cvm.ncb.oem.pe.SignalHandler;
import cvm.ncb.oem.policy.Metadata;
import cvm.ncb.repository.loader.GlobalConstant;
import cvm.ncb.tpm.CommFWResource;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * User: Gustavo Sousa
 * Date: 12/12/11
 * Time: 00:38
 */
public class MediumHandler implements SignalHandler {
    public boolean canHandle(Call call) {
        return call.getName().contains("Medium");
    }

    public void handle(Call call, CommFWResource resource, PolicyEvalManager pem) {
        String session = (String) call.getParam("connection");
        String medium = (String) call.getParam("medium");

        Connection con = resource.getStateManager().getConnection(session);
        con.enqueue(new BridgeCall(call.getName(), medium, call.getParams()));

        GlobalConstant.RequestedType reqType = GlobalConstant.RequestedType.getRequestType(medium);

        GlobalConstant.OperationType opType = GlobalConstant.OperationType.request;

//        Connection con = resource.getState().getConnection(call.sessionID());
        Map<String, Object> params = new LinkedHashMap<String, Object>();
        params.put("NumberOfUsers", con.getParticipants().size());
        Metadata nextMetadata = pem.findConformingObject(reqType, opType, params);

        String nextFwName = nextMetadata.getName();
        String currentFwName = con.getFramework(medium);

        if (currentFwName == null || !currentFwName.equals(nextFwName)) {
            generateChangeCalls(con, medium, currentFwName, nextFwName);
        }
        executeAllCommands(pem, con);
    }

    private void generateChangeCalls(Connection con, String medium, String currentFwName, String nextFwName) {
        for (String activeMedium : con.getActiveMedia()) {
            if (activeMedium != null) {
                assert con.getFramework(medium) == currentFwName || con.getFramework(medium).equals(currentFwName);
                con.enqueue(new BridgeCall("destroySession", activeMedium, new Object[]{con.getId()}));
            }
        }

        con.setFramework(medium, nextFwName);

        con.enqueue(new BridgeCall("createSession", con.getDefaultMedium(), new Object[]{con.getId()}));
        for (String party : con.getParticipants()) {
            Object[] params = {con.getId(), party};

            con.getParticipants().add(party);
            con.enqueue(new BridgeCall("addAParticipant", con.getDefaultMedium(), params));
        }
    }

    public void executeAllCommands(PolicyEvalManager pem, Connection con) {
        while (con.peekCall() != null) {
            pem.executeCSMCommand(con, con.pollCall());
        }
    }
}
