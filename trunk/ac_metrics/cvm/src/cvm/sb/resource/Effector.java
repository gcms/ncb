package cvm.sb.resource;

import cvm.sb.manager.SignalInstance;

public interface Effector {
    Object execute(SignalInstance signal);
}
