package cvm.ncb.csm;

import cvm.ncb.oem.pe.SignalInstance;

public interface Effector {
    Object execute(SignalInstance signal);
}
