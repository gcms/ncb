package cvm.ncb.csm;

import cvm.ncb.oem.policy.Metadata;

public interface Resource extends Touchpoint, Executable {
    public String getName();
    public Metadata getMetadata();
}
