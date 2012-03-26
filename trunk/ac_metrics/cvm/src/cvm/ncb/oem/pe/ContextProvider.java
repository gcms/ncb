package cvm.ncb.oem.pe;

import java.util.Map;

public interface ContextProvider {
    public Map<String, Object> getParams();
    public Object getSelf();
}
