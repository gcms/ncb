package cvm.ncb.ks;

import cvm.ncb.csm.ManagedObject;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

public class Connection {
    private String id;
    private Set<String> participants = new LinkedHashSet<String>();
    private String medium = null;
    private ManagedObject framework;

    public Connection(String conID) {
        id = conID;
    }

    public String getId() {
        return id;
    }

    public ManagedObject getFramework() {
        return framework;
    }

    public void setFramework(ManagedObject framework) {
        assert framework != null;
        this.framework = framework;
    }

    public String getMedium() {
        return medium;
    }

    public Collection<String> getParticipants() {
        return participants;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }
}
