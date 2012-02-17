package cvm.ncb.csm;

import cvm.ncb.oem.pe.SignalInstance;

public interface Touchpoint extends Effector, Sensor {
    void enqueue(SignalInstance signal);
}
