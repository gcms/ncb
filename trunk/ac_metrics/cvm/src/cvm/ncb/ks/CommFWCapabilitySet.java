package cvm.ncb.ks;

import cvm.model.CVM_Debug;
import cvm.ncb.oem.policy.Attribute;
import cvm.ncb.oem.policy.Feature;
import cvm.ncb.oem.policy.Framework;

import java.util.Scanner;
import java.util.TreeSet;
import java.util.concurrent.CopyOnWriteArraySet;

public class CommFWCapabilitySet {

    private static CommFWCapabilitySet instance;
    private CopyOnWriteArraySet<Framework> frameworks = new CopyOnWriteArraySet<Framework>();

    private CommFWCapabilitySet() {
        CVM_Debug.getInstance().printDebugMessage("CommFWCapabilityMappingTable created!");
        createAllFrameworks(); //temporary
    }

    public static void reset() {
        instance = null;
    }


    public static CommFWCapabilitySet getInstance() {
        if (instance == null)
            instance = new CommFWCapabilitySet();

        return instance;
    }

    public void addFramework(Framework fw) {
        if (frameworks.contains(fw))
            frameworks.remove(fw);

        frameworks.add(fw);
    }

    public void addFramework(String fw) {
        Framework newfw = null;
        Scanner scan = new Scanner(fw);
        if (scan.hasNext()) {
            newfw = new Framework(scan.next());
        }
        if (newfw != null) {
            while (scan.hasNext()) {
                String featName = null;
                for (Features f : Features.values()) {
                    if (f.name().equalsIgnoreCase(featName))
                        newfw.addFeature(f.createFeature(true, 2));
                }
            }
            addFramework(newfw);
        }
    }


    public Framework getFramework(String fwName) {
        for (Framework fw : frameworks) {
            if (fw.getName().equals(fwName))
                return fw;
        }

        return null;
    }

    public boolean removeFramework(String fwName) {
        Framework fw = getFramework(fwName);
        return fw != null && frameworks.remove(fw);
    }

    public TreeSet<Framework> getAvailableFrameworks() {
        TreeSet<Framework> availSet = new TreeSet<Framework>();

        for (Framework fw : frameworks) {
            if (fw.isAvailable())
                availSet.add(fw);
        }

        return availSet;
    }

    public void clearAllFrameworks() {
        frameworks.clear();
    }

    private void createAllFrameworks() {

        // Frameworks that are available
        Framework fw1 = new Framework("Skype");
        Feature fw1feat1 = new Feature("Audio");
        fw1feat1.addAttribute(new Attribute("Enabled", "true"));
        fw1feat1.addAttribute(new Attribute("NumberOfUsers", "2"));

        Feature fw1feat2 = new Feature("Video");
        fw1feat2.addAttribute(new Attribute("Enabled", "true"));
        fw1feat2.addAttribute(new Attribute("NumberOfUsers", "2"));
        fw1feat2.addAttribute(new Attribute("onlineStatus.Enabled", "true"));

        fw1.addFeature(fw1feat1);
        fw1.addFeature(fw1feat2);

        Framework fw2 = new Framework("Smack");
        Feature fw2feat1 = new Feature("Audio");
        fw2feat1.addAttribute(new Attribute("Enabled", "true"));
        fw2feat1.addAttribute(new Attribute("NumberOfUsers", "2"));
        fw2.addFeature(fw2feat1);

        Feature fw2feat2 = new Feature("Video");
        fw2feat2.addAttribute(new Attribute("Enabled", "true"));
        fw2feat2.addAttribute(new Attribute("NumberOfUsers", "4"));
        fw2feat2.addAttribute(new Attribute("onlineStatus.Enabled", "true"));
        fw2.addFeature(fw2feat2);

//		Framework fw3 = new Framework("Asterisk");
//		Feature fw3feat1 = new Feature("Audio");
//		fw3feat1.addAttribute(new Attribute("Enabled", "true"));
//		fw3feat1.addAttribute(new Attribute("NumberOfUsers","8"));
//		fw3.addFeature(fw3feat1);

        // Available for NCB
//		addFramework(fw3);
        addFramework(fw2);
        addFramework(fw1);

    }

    private enum Features {
        Chat {
            Feature createFeature(boolean enabled, int count) {
                Feature feat = new Feature("Chat");
                feat.addAttribute(new Attribute("Enabled", "" + enabled));
                feat.addAttribute(new Attribute("NumberOfUsers", "" + count));
                return feat;
            }
        },
        Video {
            Feature createFeature(boolean enabled, int count) {
                Feature feat = new Feature("Video");
                feat.addAttribute(new Attribute("Enabled", "" + enabled));
                feat.addAttribute(new Attribute("NumberOfUsers", "" + count));
                return feat;
            }
        },
        Audio {
            Feature createFeature(boolean enabled, int count) {
                Feature feat = new Feature("Video");
                feat.addAttribute(new Attribute("Enabled", "" + enabled));
                feat.addAttribute(new Attribute("NumberOfUsers", "" + count));
                return feat;
            }
        };

        // Create feature and attributes represented by this constant
        abstract Feature createFeature(boolean enabled, int count);
    }
}
