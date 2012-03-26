package cvm.ncb.oem.pe.actions;

import cvm.ncb.csm.Resource;
import cvm.ncb.oem.pe.SignalInstance;
import cvm.service.UserIDMappingTable;

import java.util.LinkedHashMap;
import java.util.Map;

public class SendSchemaAction implements ActionInstance {
    public Object execute(ManagerContext ctx, Map<String, Object> params) {
        for (Resource framework : ctx.getResourceManager().getAll()) {
            sendSchema((String) params.get("receivers"),
                    params.get("controlSchema"), params.get("dataSchema"), framework);
        }

        return null;
    }

    private void sendSchema(String listReceiver, Object controlSchema, Object dataSchema, Resource managedObject) {
        String[] receivers = listReceiver.trim().split("\\s+");
        Map<String, Object> params = new LinkedHashMap<String, Object>();
        //Currently there is not list of receivers implemented, all happens one at a time.
        if (controlSchema != null && !controlSchema.equals("") && !controlSchema.equals("null")) {
            for (String receiver : receivers) {
                sendSchema(controlSchema, managedObject, params, receiver);
            }
        }

        if (dataSchema != null && !dataSchema.equals("") && !dataSchema.equals("null")) {
            for (String receiver : receivers) {
                sendSchema(dataSchema, managedObject, params, receiver);
            }
        }
    }

    private void sendSchema(Object schema, Resource managedObject, Map<String, Object> params, String receiver) {
        params.put("schema", schema);
        params.put("participant", UserIDMappingTable.getInstance().lookupContact(managedObject.getName(), receiver));

        managedObject.execute(new SignalInstance(null, "sendSchema", params));
    }
}
