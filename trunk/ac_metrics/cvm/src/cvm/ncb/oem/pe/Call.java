package cvm.ncb.oem.pe;

import util.StringUtils;
import java.util.Map;

public class Call {
    private String command;
    private Map<String, Object> params;

    public Call(String cmd, Map<String,Object> params) {
        this.command = cmd;
        this.params = params;
    }

    public String getName() {
        return command;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public Object getParam(String param) {
        return params.get(param);
    }

    public boolean equals(Call other) {
        return command.equals(other.command)
                && params.equals(other.params);
    }

    public String toString() {
        return StringUtils.methodToString(this.getName(), getParams().values().toArray());
    }
}
