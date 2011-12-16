package cvm.ncb.oem.pe.handlers;

import cvm.ncb.csm.BridgeCall;
import cvm.ncb.ks.Connection;
import cvm.ncb.oem.pe.NCBCall;
import cvm.ncb.oem.pe.NCBCallHandler;
import cvm.ncb.oem.pe.PolicyEvalManager;
import cvm.ncb.oem.policy.Framework;
import cvm.ncb.repository.loader.GlobalConstant;
import cvm.ncb.tpm.CommFWResource;

/**
 * User: Gustavo Sousa
 * Date: 12/12/11
 * Time: 00:38
 */
public class MediumHandler implements NCBCallHandler {
    public boolean canHandle(NCBCall call) {
        return call.getCallName().contains("Medium");
    }

    public void handle(NCBCall call, CommFWResource resource, PolicyEvalManager pem) {
        Connection con = resource.getConIDMappingTable().getConnection(call.sessionID());
        con.offerNCBCall(new BridgeCall(resource.getConIDMappingTable().getConnection(call.sessionID()), call));

        GlobalConstant.RequestedType reqType = call.getMedium().equalsIgnoreCase("AUDIO")
                ? GlobalConstant.RequestedType.Audio
                : GlobalConstant.RequestedType.Video;

        GlobalConstant.OperationType opType = GlobalConstant.OperationType.request;

//        Connection con = resource.getConIDMappingTable().getConnection(call.sessionID());
        Framework nextFramework = pem.findNewFramework(reqType, opType, con.getNumOfUsers());

        String currentFwName = con.getComObj(call.getMedium());
        if (currentFwName == null || !currentFwName.equals(nextFramework.getName())) {
            con.setComObj(call.getMedium(), nextFramework.getName());
            generateChangeCalls(con);
        }
        pem.executeAllCommands(resource.getConIDMappingTable().getConnection(call.sessionID()));
    }

    private void generateChangeCalls(Connection con) {
        Object[] conIDs = {con.getConId()};
        for (String medium : con.getDeactivatedMedia()) {
            if (medium != null) {
                con.offerNCBCall(new BridgeCall(con.getComObj(medium), "destroySession", medium, conIDs));
            }
        }

        con.offerNCBCall(new BridgeCall(con.getComObj(con.getDefaultMedium()), "createSession", con.getDefaultMedium(), conIDs));
        for (String party : con.getParticipants()) {
            Object[] params = {con.getConId(), party};

//            con.getParticipants().add(party);
            con.offerNCBCall(new BridgeCall(con.getComObj(con.getDefaultMedium()), "addAParticipant", con.getDefaultMedium(), params));
        }
    }
}
