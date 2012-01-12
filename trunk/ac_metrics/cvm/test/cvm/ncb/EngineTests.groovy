package cvm.ncb

import cvm.ncb.adapters.MockAdapter
import cvm.ncb.model.*

 /**
 * User: Gustavo Sousa
 * Date: 20/12/11
 * Time: 22:51
 */
class EngineTests extends GroovyTestCase {
    void testBuildAndRunModel() {
        Interface iface = Interface.createFromClass(cvm.ncb.adapters.NCBBridge)

        Bridge bridge = new Bridge(iface: iface)
        bridge.implementations.add(createMockAdapter())
        bridge.implementations.add(createMock2Adapter())
    }

    private Implementation createMockAdapter() {
        Feature audio = new Feature(name: "Audio")
        audio.attributes.add(new Attribute(name: "Enabled", value: "true"))
        audio.attributes.add(new Attribute(name: "NumberOfUsers", value: "2"))
        
        new Implementation(name: 'Mock', impl: MockAdapter, features: [audio])
    }

    private Implementation createMock2Adapter() {
        Feature audio = new Feature(name: "Audio")
        audio.attributes.add(new Attribute(name: "Enabled", value: "true"))
        audio.attributes.add(new Attribute(name: "NumberOfUsers", value: "2"))
        
        
        Feature video = new Feature(name: "Video")
        video.attributes.add(new Attribute(name: "Enabled", value: "true"))
        video.attributes.add(new Attribute(name: "NumberOfUsers", value: "2"))
        video.attributes.add(new Attribute(name: "onlineStatus.Enabled", value: "true"))
        
        new Implementation(name: 'Mock2', impl: MockAdapter, features: [audio, video])
    }
}
