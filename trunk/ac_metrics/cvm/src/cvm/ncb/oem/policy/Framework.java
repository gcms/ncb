package cvm.ncb.oem.policy;

import java.util.HashMap;

public class Framework implements Comparable<Framework> {

    private String name;
    private boolean available = true;
    private HashMap<String, Feature> features = new HashMap<String, Feature>();

    public Framework(String name) {
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

    public int compareTo(Framework other) {
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
