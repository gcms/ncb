package cvm.sb.expression;

import cvm.sb.manager.SignalInstance;

import java.util.Collection;

public interface SignalLogger {
    public Collection<SignalInstance> getSignalInstances(String name);
}
