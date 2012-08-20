package cvm.sb.resource;

import cvm.sb.manager.SignalInstance;
import cvm.sb.policy.metadata.Metadata;

public interface Resource extends Touchpoint, Executable {
    void enqueue(SignalInstance signal);    
    public String getName();
    public Metadata getMetadata();
}
