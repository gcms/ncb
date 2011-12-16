package cvm.ncb.csm;

import cvm.ncb.ks.Connection;
import cvm.ncb.oem.pe.NCBCall;

public class BridgeCall implements Comparable<BridgeCall> {
    private String name;
    private String medium;
    private Object[] params;
    private String fwName;

    public BridgeCall(String fwName, String name, String medium, Object[] params) {
        this.fwName = fwName;
        this.name = name;
        this.medium = medium;
        this.params = params;
    }

    public BridgeCall(String fwName, NCBCall call) {
        this(fwName, call.getName(), call.getMedium(), call.getParams());
    }

    public BridgeCall(Connection con, NCBCall call) {
        this(con.getComObj(call.getMedium()), call);
    }

    public String getFwName() {
        return fwName;
    }

    public String getName() {
        return name;
    }

    public String getMedium() {
        return medium;
    }

    public Class[] getParamTypes() {
        Class[] paramTypes = new Class[params.length];

        for (int i = 0; i < params.length; i++) {
            paramTypes[i] = params[i].getClass();
        }

        return paramTypes;
    }

    public Object[] getParams() {
        return params;
    }


    public enum CommandType {
        LOGINALL, SENDSCHEMA, DESTROYSESSION, CREATESESSION, DISABLE, ENABLE, OTHER
    }

    public CommandType getCommandType() {
        CommandType cType;

        if (this.getName().equalsIgnoreCase("sendSchema"))
            cType = CommandType.SENDSCHEMA;
        else if (this.getName().equalsIgnoreCase("destroySession"))
            cType = CommandType.DESTROYSESSION;
        else if (this.getName().equalsIgnoreCase("createSession"))
            cType = CommandType.CREATESESSION;
        else if (this.getName().startsWith("disable"))
            cType = CommandType.DISABLE;
        else if (!this.getName().startsWith("enable"))
            cType = CommandType.ENABLE;
        else
            cType = CommandType.OTHER;

        return cType;
    }


    public int compareTo(BridgeCall o) {
        return getCommandType().compareTo(o.getCommandType());
    }

//    public boolean equals(Object o) {
//        BridgeCall other = (BridgeCall) o;
//
//        return name.equals(other.name)
//                && medium.equals(other.medium)
//                && Arrays.equals(params, other.params);
//    }
}
