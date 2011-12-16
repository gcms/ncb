package cvm.ncb.oem.pe;

import util.StringUtils;

import java.util.Arrays;

public class NCBCall {

    private String sessID = null;
    private int count = -1;
    private Object[] params = null;
    private String command = null;
    private String medium = null;

    public NCBCall(String sessionID, int cnt, String cmd, String medium, Object[] par) {
        this.sessID = sessionID;
        this.count = cnt;
        this.command = cmd;
        this.params = par;
        this.medium = medium;

    }

    public String getName() {
        return getCallName();
    }

    public Class[] getParamTypes() {
        Class[] out = new Class[params.length];
        int i = 0;
        for (Object o : params) {
            out[i] = o.getClass();
            i++;
        }
        return out;
    }

    public String getCallName() {
        return command;
    }

    public Object[] getParams() {
        return params;
    }

    public String sessionID() {
        return sessID;
    }

    public int getCount() {
        return count;
    }

    public String getMedium() {
        return medium;
    }

    public boolean equals(NCBCall other) {
        return sessID.equals(other.sessID)
                && getCount() == other.getCount()
                && Arrays.equals(params, other.params)
                && command.equals(other.command)
                && medium.equals(other.medium);
    }

    public String toString() {
        return StringUtils.methodToString(getCallName(), getParams());
    }
}
