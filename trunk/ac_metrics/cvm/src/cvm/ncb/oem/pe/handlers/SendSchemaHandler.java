package cvm.ncb.oem.pe.handlers;

import cvm.model.CVM_Debug;
import cvm.ncb.csm.ManagedObject;
import cvm.ncb.ks.UserIDMappingTable;
import cvm.ncb.oem.pe.Call;
import cvm.ncb.oem.pe.PolicyEvalManager;
import cvm.ncb.oem.pe.SignalHandler;
import cvm.ncb.tpm.CommFWResource;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class SendSchemaHandler implements SignalHandler {
    public boolean canHandle(Call call) {
        return call.getName().equals("sendSchema");
    }

    public void handle(Call call, CommFWResource resource, PolicyEvalManager pem) {
        for (ManagedObject tempManaged : resource.getObjects()) {
            try {
            sendSchemaToCommObj((String) call.getParam("session"), (String) call.getParam("sender"),
                    (String) call.getParam("receivers"), (String) call.getParam("controlSchema"),
                    (String) call.getParam("dataSchema"), tempManaged);
            } catch (RuntimeException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void sendSchemaToCommObj(String sessionID, String senderID, String listReceiver, String control_xcml, String data_xcml, ManagedObject managedObject) {
        Map<String, Object> params = new LinkedHashMap<String, Object>();

        CVM_Debug.getInstance().printDebugMessage("NCBManager : NCB sendSchema called with sID:\"" + sessionID + "\" and senderID:\"" + senderID + "\" and receiverID:\"" + listReceiver + "\" and Control_XCML:\"" + control_xcml + "\" and Data_XCML:\"" + data_xcml + "\".");

        //Currently there is not list of receivers implemented, all happens one at a time.
        if (control_xcml != null && !control_xcml.equals("") && !control_xcml.equals("null")) {
            Scanner tmp = new Scanner(listReceiver);
            while (tmp.hasNext()) {
                params.put("schema", control_xcml);
                params.put("participant", UserIDMappingTable.getInstance().lookupContact(managedObject.getName(), tmp.next()));

                managedObject.execute("sendSchema", params);
            }
        }

        if (data_xcml != null && !data_xcml.equals("") && !data_xcml.equals("null")) {
            Scanner tmp = new Scanner(listReceiver);
            while (tmp.hasNext()) {
                params.put("schema", data_xcml);
                params.put("participant", UserIDMappingTable.getInstance().lookupContact(managedObject.getName(), tmp.next()));

                managedObject.execute("sendSchema", params);
            }
        }
        CVM_Debug.getInstance().printDebugMessage("NCBManager : NCB sendSchema OVER");
    }

}
