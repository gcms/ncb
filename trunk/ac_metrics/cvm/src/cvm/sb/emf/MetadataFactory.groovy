package cvm.sb.emf

import cvm.sb.policy.metadata.Metadata
import sb.base.metadata.Annotable
import sb.base.metadata.Attribute
import sb.base.metadata.Feature

class MetadataFactory {
    public Metadata createMetadata(Annotable annotable) {
        createMetadata(annotable.name, annotable.features)
    }

    public Metadata createMetadata(String name, Collection<Feature> features) {
        Metadata result = new Metadata(name)

        features.each { Feature feature ->
            result.addFeature(createFeature(null, feature))
        }

        result
    }

    cvm.sb.policy.metadata.Feature createFeature(cvm.sb.policy.metadata.Feature parent, Feature feature) {
        cvm.sb.policy.metadata.Feature result = new cvm.sb.policy.metadata.Feature(feature.name, parent)
        feature.subFeatures.each { Feature subFeature ->
            result.addSubFeature(createFeature(result, subFeature))
        }
        feature.attributes.each { Attribute attribute ->
            result.addAttribute(createAttribute(attribute))
        }
        result
    }

    cvm.sb.policy.metadata.Attribute createAttribute(Attribute attribute) {
        new cvm.sb.policy.metadata.Attribute(attribute.name, attribute.value)
    }
}
