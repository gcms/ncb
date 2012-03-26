package cvm.ncb.csm;

import cvm.ncb.oem.pe.SignalInstance;

public interface SignalLogger {
    public SignalInstance getSignalInstance(String name);
}
