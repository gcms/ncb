package util;

import cvm.ncb.oem.policy.Attribute;
import cvm.ncb.oem.policy.Feature;
import cvm.ncb.oem.policy.Metadata;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: gustavosousa
 * Date: 09/01/12
 * Time: 18:20
 * To change this template use File | Settings | File Templates.
 */
public class FeaturesParser {
    enum Features {
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


    public static Metadata parseMetadata(String fw) {
        Scanner scan = new Scanner(fw);
        if (!scan.hasNext())
            return null;

        Metadata metadata = new Metadata(scan.next());

        for (Feature feature : parseFeatures(scan)) {
            metadata.addFeature(feature);
        }
        return metadata;
    }

    private static Collection<Feature> parseFeatures(Scanner scan) {
        Collection<Feature> features = new ArrayList<Feature>();

        while (scan.hasNext()) {
            String featName = null;
            for (Features f : Features.values()) {
                if (f.name().equalsIgnoreCase(featName))
                    features.add(f.createFeature(true, 2));
            }
        }

        return features;
    }

    public static List<Metadata> createAllFrameworks() {

        // Frameworks that are available
        Metadata fw1 = new Metadata("Skype");
        Feature fw1feat1 = new Feature("Audio");
        fw1feat1.addAttribute(new Attribute("Enabled", "true"));
        fw1feat1.addAttribute(new Attribute("NumberOfUsers", "2"));

        Feature fw1feat2 = new Feature("Video");
        fw1feat2.addAttribute(new Attribute("Enabled", "true"));
        fw1feat2.addAttribute(new Attribute("NumberOfUsers", "2"));
        fw1feat2.addAttribute(new Attribute("onlineStatus.Enabled", "true"));

        fw1.addFeature(fw1feat1);
        fw1.addFeature(fw1feat2);

        Metadata fw2 = new Metadata("Smack");
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

        return Arrays.asList(fw1, fw2);
    }

}
