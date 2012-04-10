package cvm.ncb.oem.policy;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashSet;
import java.util.Iterator;

public class Feature {
    private static Log log = LogFactory.getLog(Feature.class);

    private String name;
    private HashSet<Attribute> attributes;
    private HashSet<Feature> subFeatures;
    private Feature parentFeature;

    public static final int EQ = 0;
    public static final int LEQ = -1;
    public static final int GEQ = 1;
    public static final int LT = -2;
    public static final int GT = 2;

    public Feature(String fn) {
        name = fn;
        attributes = new HashSet<Attribute>();
        subFeatures = new HashSet<Feature>();
        parentFeature = null;
    }

    public Feature(String nodeName, Feature parent) {
        this(nodeName);
        setParent(parent);
    }

    public HashSet<Attribute> getAttributes() {
        return attributes;
    }

    public HashSet<Feature> getSubFeatures() {
        return subFeatures;
    }

    public String getName() {
        return name;
    }


    public void addAttribute(Attribute attr) {
        attributes.add(attr);
    }

    public void addSubFeature(Feature feat) {
        subFeatures.add(feat);
    }

    public Attribute getAttribute(String attributeName) {
        for (Attribute attribute : getAttributes()) {
            if (attribute.name.equals(attributeName))
                return attribute;
        }

        return null;
    }

    public Feature getSubFeature(String featureName) {
        for (Feature feature : getSubFeatures()) {
            if (feature.getName().equals(featureName))
                return feature;
        }

        return null;
    }

    public boolean hasAttributeValue(String attrName, Object val, int opcode) {
        if (val instanceof Integer)
            return hasAttributeValue(attrName, (Integer) val, opcode);

        if (val instanceof Boolean)
            return hasAttributeValue(attrName, (Boolean) val, opcode);

        return hasAttributeValue(attrName, val.toString(), opcode);
    }

    public boolean hasAttributeValue(String attrName, Integer val, int opcode) {
        Attribute attr = getAttribute(attrName);
        if (attr == null)
            return false;

        int attrVal;
        try {
            attrVal = Integer.parseInt(attr.attributeValue);
        } catch (NumberFormatException e0) {
            log.debug("Attribute value is not an integer!! "
                    + attr.attributeValue);
            return false;
        }

        log.debug("@@@@@@@@@@@@@@@@@@@@@@@@@");
        log.debug("The integer comparison is " + val + " " + opcode + " " + attrVal);
        switch (opcode) {
            case EQ:
                return val == attrVal;
            case LEQ:
                return val <= attrVal;
            case GEQ:
                return val >= attrVal;
            default:
                log.debug("Invalid opcode!!");
                return false;
        }
    }

    public boolean hasAttributeValue(String attrName, Boolean val, int opcode) {
        log.debug("Finding attr " + attrName);

        Attribute attr = getAttribute(attrName);
        if (attr == null)
            return false;

        log.debug("Found attribute");
        boolean attrVal = Boolean.parseBoolean(attr.attributeValue);
        return attrVal == val;
    }

    public boolean hasAttributeValue(String attrName, String val, int opcode) {
        log.debug("here in string");

        Attribute attr = getAttribute(attrName);
        return attr != null && attr.attributeValue.equals(val);
    }

    public String toString() {
        String str = "Feature: " + this.getName() + " ( ";
        Iterator<Attribute> attrIterator = this.attributes.iterator();
        while (attrIterator.hasNext())
            str = str + ((Attribute) attrIterator.next()).name + " ";
        str = str + " )  Parent: " + ((this.getParentFeature() != null) ? this.getParentFeature().getName() : "");

        if (this.subFeatures.size() > 0)
            str = str + " has the following " + this.subFeatures.size() + " children\n";

        Iterator<Feature> featIterator = this.subFeatures.iterator();
        while (featIterator.hasNext())
            str = str + "\t" + ((Feature) featIterator.next()).toString() + "\n";


        return str;
    }

    public Feature getParentFeature() {
        return parentFeature;
    }

    public void setParent(Feature parent) {
        parentFeature = parent;
    }
}
