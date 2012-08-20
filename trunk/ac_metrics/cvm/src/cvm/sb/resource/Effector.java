package cvm.sb.resource;

import cvm.sb.manager.SignalInstance;
import sb.base.common.Signal;

public interface Effector {
    Object execute(SignalInstance signal);
}
