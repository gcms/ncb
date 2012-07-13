package cvm.sb.resource;

import cvm.sb.manager.SignalInstance;

public interface Touchpoint extends Effector, Sensor {
    void enqueue(SignalInstance signal);
}
