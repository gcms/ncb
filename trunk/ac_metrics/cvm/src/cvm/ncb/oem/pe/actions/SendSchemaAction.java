package cvm.ncb.oem.pe.actions;

import cvm.sb.manager.actions.MacroActionInstance;
import cvm.sb.manager.ManagerContext;
import cvm.sb.manager.SignalInstance;
import cvm.sb.resource.Resource;
import cvm.service.UserIDMappingTable;

import java.util.LinkedHashMap;
import java.util.Map;

public class SendSchemaAction implements MacroActionInstance {
    public Object execute(ManagerContext ctx, Map<String, Object> params) {
        for (Resource framework : ctx.getResourceManager().getAll()) {
            sendSchema((String) params.get("receivers"),
                    params.get("controlSchema"), params.get("dataSchema"), framework);
        }

        return null;
    }

    private void sendSchema(String listReceiver, Object controlSchema, Object dataSchema, Resource resourceObject) {
        String[] receivers = listReceiver.trim().split("\\s+");
        Map<String, Object> params = new LinkedHashMap<String, Object>();
        //Currently there is not list of receivers implemented, all happens one at a time.
        if (controlSchema != null && !controlSchema.equals("") && !controlSchema.equals("null")) {
            for (String receiver : receivers) {
                sendSchema(controlSchema, resourceObject, params, receiver);
            }
        }

        if (dataSchema != null && !dataSchema.equals("") && !dataSchema.equals("null")) {
            for (String receiver : receivers) {
                sendSchema(dataSchema, resourceObject, params, receiver);
            }
        }
    }

    private void sendSchema(Object schema, Resource resourceObject, Map<String, Object> params, String receiver) {
        params.put("schema", schema);
        params.put("participant", UserIDMappingTable.getInstance().lookupContact(resourceObject.getName(), receiver));

        resourceObject.execute(new SignalInstance(null, "sendSchema", params));
    }
}
