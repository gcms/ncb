package cvm.sb.resource;

import cvm.sb.policy.metadata.Metadata;

public interface Resource extends Touchpoint, Executable {
    public String getName();
    public Metadata getMetadata();
}
