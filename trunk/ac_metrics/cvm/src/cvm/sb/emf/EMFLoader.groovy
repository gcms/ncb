package cvm.sb.emf

import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl
import sb.base.impl.BasePackageImpl

class EMFLoader {
    private static ResourceSet resourceSet
    public static ResourceSet getResourceSet() {
        if (resourceSet == null) {
            resourceSet = new ResourceSetImpl();
            Resource.Factory.Registry fr = resourceSet.getResourceFactoryRegistry();

            fr.getExtensionToFactoryMap().put("ecore", new EcoreResourceFactoryImpl());
            // Register the default resource factory -- only needed for stand-alone!
            fr.getExtensionToFactoryMap().put("xmi", new XMIResourceFactoryImpl());

            BasePackageImpl.init()
        }

        resourceSet
    }

    public static Resource loadURL(URL uri) {
        getResourceSet().getResource(org.eclipse.emf.common.util.URI.createURI(uri.toURI().toString()), true)
    }

    public static Resource loadResource(String name) {
        loadURL(new File("ac_metrics/cvm/emf/${name}.xmi").toURL())
    }

    public static <T> T loadFirst(String name, Class<T> type) {
        loadResource(name).contents.find { type.isAssignableFrom(it.getClass()) }
    }

    public static <T> T loadFirst(Class<T> type) {
        loadFirst(type.simpleName, type)
    }

    public static <T> Collection<T> loadAll(String name, Class<T> type) {
        loadResource(name).contents.findAll { type.isAssignableFrom(it.getClass()) }
    }
}
