package cvm.sb.emf

import cvm.ncb.adapters.Manageable
import cvm.ncb.csm.ManagedObject
import cvm.ncb.oem.policy.Metadata
import sb.base.Instance
import sb.base.metadata.Attribute
import sb.base.metadata.Feature

class ManagedObjectFactory {
    public ManagedObject createManagedObject(Instance instance) {
        Manageable manageable = Class.forName(instance.impl).newInstance()
        Metadata metadata = createMetadata(instance.name, instance.features)

        new ManagedObject(manageable, metadata)
    }

    public Metadata createMetadata(String name, Collection<Feature> features) {
        Metadata result = new Metadata(name)

        features.each { Feature feature ->
            result.addFeature(createFeature(null, feature))
        }

        result
    }

    cvm.ncb.oem.policy.Feature createFeature(cvm.ncb.oem.policy.Feature parent, Feature feature) {
        cvm.ncb.oem.policy.Feature result = new cvm.ncb.oem.policy.Feature(feature.name, parent)
        feature.subFeatures.each { Feature subFeature ->
            result.addSubFeature(createFeature(result, subFeature))
        }
        feature.attributes.each { Attribute attribute ->
            result.addAttribute(createAttribute(attribute))
        }
        result
    }

    cvm.ncb.oem.policy.Attribute createAttribute(Attribute attribute) {
        new cvm.ncb.oem.policy.Attribute(attribute.name, attribute.value)
    }
}
