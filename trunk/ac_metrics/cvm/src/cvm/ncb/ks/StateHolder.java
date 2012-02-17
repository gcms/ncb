package cvm.ncb.ks;

import cvm.ncb.csm.Resource;

import java.util.*;

public class StateHolder {
    private Object id;

    private Map<String, Object> attributes = new LinkedHashMap<String, Object>();
    private Map<String, Collection<StateHolder>> children = new LinkedHashMap<String, Collection<StateHolder>>();

    private Resource framework;

    public StateHolder(Object conID) {
        id = conID;
    }

    public Object getId() {
        return id;
    }

    public Resource getFramework() {
        return framework;
    }

    public void setFramework(Resource framework) {
        assert framework != null;
        this.framework = framework;
    }


    public Set getSet(String name) {
        Set set = (Set) attributes.get(name);
        if (set == null) {
            set = new LinkedHashSet();
            attributes.put(name, set);
        }

        return set;
    }

    public Object getAttr(String name) {
        return attributes.get(name);
    }

    public void setAttr(String name, Object value) {
        attributes.put(name, value);
    }
}
