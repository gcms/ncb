package cvm.ncb.ks;

import cvm.ncb.csm.ManagedObject;
import cvm.ncb.csm.ManagedObjectFactory;
import cvm.ncb.oem.policy.Metadata;

import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.CopyOnWriteArraySet;

public class ObjectManager {
    private Set<ManagedObject> objects = new CopyOnWriteArraySet<ManagedObject>();
    private ManagedObjectFactory objectFactory;

    public ObjectManager(ManagedObjectFactory objectFactory) {
        this.objectFactory = objectFactory;
//        createAllFrameworks(); //temporary
    }

    public void addObject(ManagedObject obj) {
        if (obj == null)
            return;

        ManagedObject prev = getObject(obj.getName());
        if (prev != null)
            objects.remove(prev);

        objects.add(obj);
    }

    public void addObject(Metadata fw) {
        ManagedObject managedObject = objectFactory.createCommunicationObject(fw);
        addObject(managedObject);
    }

    public ManagedObject getObject(String fwName) {
        for (ManagedObject fw : objects) {
            if (fw.getName().equals(fwName))
                return fw;
        }

        return null;
    }

    public boolean removeObject(String fwName) {
        ManagedObject fw = getObject(fwName);
        return fw != null && objects.remove(fw);
    }

    public TreeSet<Metadata> getAvailableObjects() {
        TreeSet<Metadata> availSet = new TreeSet<Metadata>();

        for (ManagedObject fw : objects) {
            if (fw.getMetadata().isAvailable())
                availSet.add(fw.getMetadata());
        }

        return availSet;
    }

    public void clearAllObjects() {
        objects.clear();
    }


    public Collection<ManagedObject> getAllObjects() {
        return objects;
    }

}
