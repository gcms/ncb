package cvm.sb.policy.metadata;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class Metadata implements Comparable<Metadata> {

    private String name;
    private boolean available = true;

    private Map<String, Feature> features = new LinkedHashMap<String, Feature>();

    public Metadata(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Collection<Feature> getFeatures() {
        return features.values();
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
