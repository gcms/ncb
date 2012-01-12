package cvm.ncb.csm;

import util.StringUtils;

import java.util.Arrays;

public class BridgeCall implements Comparable<BridgeCall> {
    private String name;
    private String medium;
    private Object[] params;

    public BridgeCall(String name, String medium, Object[] params) {
        this.name = name;
        this.medium = medium;
        this.params = params;
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

    public String toString() {
        return StringUtils.methodToString(getName(), getParams());
    }

    public boolean equals(Object o) {
        BridgeCall other = (BridgeCall) o;

        return name.equals(other.name)
//                && medium.equals(other.medium)
                && Arrays.equals(params, other.params);
    }

    public int hashCode() {
        return name.hashCode() * 3
//                + medium.hashCode() * 7
                + Arrays.hashCode(params);
    }
}
