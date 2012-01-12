package cvm.ncb.oem.policy;

import java.util.HashMap;

public class Metadata implements Comparable<Metadata> {

    private String name;
    private boolean available = true;

    private HashMap<String, Feature> features = new HashMap<String, Feature>();

    public Metadata(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public HashMap<String, Feature> getFeatures() {
        return features;
    }

    public void addFeature(Feature feat) {
        features.put(feat.getName(), feat);
    }

    public String toString() {
        return name;
    }

    public int compareTo(Metadata other) {
        return name.compareTo(other.name);
    }

    public boolean isAvailable() {
        return available;
    }

    public void fail() {
        available = false;
    }

    public void restore() {
        available = true;
    }
}
