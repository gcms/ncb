package cvm.ncb.oem.pe.actions;

import cvm.ncb.csm.ManagedObject;
import cvm.ncb.ks.UserIDMappingTable;
import cvm.ncb.oem.pe.Call;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;


public class SendSchemaAction implements ActionInstance {
    public Object execute(ActionContext ctx, Call call) {
        for (ManagedObject framework : ctx.getObjectManager().getAllObjects()) {
            sendSchemaToCommObj((String) call.getParams().get("receivers"),
                    call.getParams().get("controlSchema"), call.getParams().get("dataSchema"), framework);
        }

        return null;
    }

    private void sendSchemaToCommObj(String listReceiver, Object control_xcml, Object data_xcml, ManagedObject managedObject) {
        Map<String, Object> params = new LinkedHashMap<String, Object>();
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
    }
}
