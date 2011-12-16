package cvm.ncb.oem.pe.handlers;

import cvm.model.CVM_Debug;
import cvm.ncb.csm.CommObject;
import cvm.ncb.ks.UserIDMappingTable;
import cvm.ncb.oem.pe.NCBCall;
import cvm.ncb.oem.pe.NCBCallHandler;
import cvm.ncb.oem.pe.PolicyEvalManager;
import cvm.ncb.tpm.CommFWResource;

import java.util.Scanner;

public class SendSchemaHandler implements NCBCallHandler {
    public boolean canHandle(NCBCall call) {
        return call.getCallName().equals("sendSchema");
    }

    public void handle(NCBCall call, CommFWResource resource, PolicyEvalManager pem) {
        for (CommObject tempComm : resource.getCObjectList()) {
            sendSchemaToCommObj((String) call.getParams()[0], (String) call.getParams()[1],
                    (String) call.getParams()[2], (String) call.getParams()[3],
                    (String) call.getParams()[4], tempComm);
        }
    }

    private void sendSchemaToCommObj(String sessionID, String senderID, String listReceiver, String control_xcml, String data_xcml, CommObject commObject) {
        CVM_Debug.getInstance().printDebugMessage("NCBManager : NCB sendSchema called with sID:\"" + sessionID + "\" and senderID:\"" + senderID + "\" and receiverID:\"" + listReceiver + "\" and Control_XCML:\"" + control_xcml + "\" and Data_XCML:\"" + data_xcml + "\".");
        //Currently there is not list of receivers implemented, all happens one at a time.
        if (control_xcml != null && !control_xcml.equals("") && !control_xcml.equals("null")) {
            Scanner tmp = new Scanner(listReceiver);
            while (tmp.hasNext()) {
                commObject.getBridge().sendSchema(control_xcml,
                        UserIDMappingTable.getInstance().lookupContact(
                                commObject.getName(), tmp.next()));
            }
        }

        if (data_xcml != null && !data_xcml.equals("") && !data_xcml.equals("null")) {
            Scanner tmp = new Scanner(listReceiver);
            while (tmp.hasNext()) {
                commObject.getBridge().sendSchema(data_xcml,
                        UserIDMappingTable.getInstance().lookupContact(
                                commObject.getName(), tmp.next()));
            }
        }
        CVM_Debug.getInstance().printDebugMessage("NCBManager : NCB sendSchema OVER");
    }

}
