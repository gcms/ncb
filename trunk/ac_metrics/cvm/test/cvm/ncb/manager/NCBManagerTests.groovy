package cvm.ncb.manager

import cvm.ncb.adapters.Mock2Adapter
import cvm.ncb.adapters.MockAdapter
import cvm.ncb.csm.CSM_NBTypes
import cvm.ncb.csm.ManagedObjectFactory
import cvm.ncb.drivers.NCBDriver
import cvm.ncb.handlers.EventManager
import cvm.ncb.ks.ObjectManager
import cvm.ncb.oem.policy.Attribute
import cvm.ncb.oem.policy.Feature
import cvm.ncb.oem.policy.Metadata

/**
 * Created by IntelliJ IDEA.
 * User: Gustavo Sousa
 * Date: 08/12/11
 * Time: 19:50
 * To change this template use File | Settings | File Templates.
 */
class NCBManagerTests extends GroovyTestCase {
    public void registerMock() {
        String fwName = 'Mock'
        CSM_NBTypes.registerFramework(fwName, "${fwName}.config".toLowerCase())

        Metadata managedObject = new Metadata(fwName)
        Feature audio = new Feature("Audio")
        audio.addAttribute(new Attribute("Enabled", "true"))
        audio.addAttribute(new Attribute("NumberOfUsers", "2"))
        managedObject.addFeature(audio)

        objectManager.addObject(managedObject)
    }

    public void registerMock2() {
        String fwName = 'Mock2'
        CSM_NBTypes.registerFramework(fwName, "${fwName}.config".toLowerCase())

        Metadata managedObject = new Metadata(fwName)
        Feature audio = new Feature("Audio")
        audio.addAttribute(new Attribute("Enabled", "true"))
        audio.addAttribute(new Attribute("NumberOfUsers", "2"))
        managedObject.addFeature(audio)
        Feature video = new Feature('Video')
        video.addAttribute(new Attribute("Enabled", "true"))
        video.addAttribute(new Attribute("NumberOfUsers", "2"))
        video.addAttribute(new Attribute("onlineStatus.Enabled", "true"))
        managedObject.addFeature(video)

        objectManager.addObject(managedObject)
    }

    private EventManager eventManager
    private ObjectManager objectManager

    void setUp() {
        eventManager = new EventManager()
        ManagedObjectFactory mof = new ManagedObjectFactory(eventManager);
        objectManager = new ObjectManager(mof)
        objectManager.clearAllObjects()
        registerMock()
    }


    void testInitOk() {
        NCBManager manager = new NCBManager(objectManager, eventManager)
        manager.login("Andrew", "password");
        manager.createSession("101");
        manager.addParty("101", "Yali");
        manager.sendSchema("101 ", "Andrew", " Yali", "101 " + "AUDIO" + " Yali Andrew", null);
        manager.enableMedium("101", "AUDIO");
        waitThreadsFinish(4000)

        manager.logout("Andrew")
        waitThreadsFinish(1000)

        List calls = MockAdapter.instance.calls
        assertEquals("login()", calls.remove(0))
        assertEquals("sendSchema(101 AUDIO Yali Andrew, Yali)", calls.remove(0))
//        assertEquals("createSession(101)", calls.remove(0))
        assertEquals("createSession(101)", calls.remove(0))
        assertEquals("addParticipant(101, Yali)", calls.remove(0))
//        assertEquals("addParticipant(101, Yali)", calls.remove(0))
        assertEquals("enableMedium(101, AUDIO)", calls.remove(0))
        assertEquals("logout()", calls.remove(0))
    }

    void testTwoMedia() {
        registerMock2()

        NCBManager manager = new NCBManager(objectManager, eventManager)
        manager.login("Andrew", "password");
        manager.createSession("101");
        manager.addParty("101", "Yali");
        manager.sendSchema("101 ", "Andrew", " Yali", "101 " + "AUDIO" + " Yali Andrew", null);
        manager.enableMedium("101", "AUDIO");
        waitThreadsFinish(4000)

        manager.enableMedium("101", "VIDEO");
        waitThreadsFinish(4000)

        manager.logout("Andrew")
        waitThreadsFinish(1000)

        List calls = MockAdapter.instance.calls
        assertEquals("login()", calls.remove(0))
        assertEquals("sendSchema(101 AUDIO Yali Andrew, Yali)", calls.remove(0))
        assertEquals("createSession(101)", calls.remove(0))
//        assertEquals("createSession(101)", calls.remove(0))
        assertEquals("addParticipant(101, Yali)", calls.remove(0))
//        assertEquals("addParticipant(101, Yali)", calls.remove(0))
        assertEquals("enableMedium(101, AUDIO)", calls.remove(0))
        assertEquals("destroySession(101)", calls.remove(0))

        List calls2 = Mock2Adapter.instance.calls
        assertEquals("login()", calls2.remove(0))
        assertEquals("sendSchema(101 AUDIO Yali Andrew, Yali)", calls2.remove(0))
        assertEquals("createSession(101)", calls2.remove(0))
        assertEquals("addParticipant(101, Yali)", calls2.remove(0))
        assertEquals("enableMedium(101, VIDEO)", calls2.remove(0))
    }

    void testFailedFramework() {
        NCBManager manager = new NCBManager(objectManager, eventManager)
        manager.login("Andrew", "password");
        manager.createSession("101");
        manager.sendSchema("101 ", "Andrew", " Yali", "101 " + "AUDIO" + " Yali Andrew", null);
        manager.addParty("101", "Yali");
        manager.enableMedium("101", "AUDIO");

        assertNotNull objectManager.getAvailableObjects().find { it.name == 'Mock' }
        MockAdapter.instance.markFailed()

        waitThreadsFinish(2000)

        assertNull objectManager.getAvailableObjects().find { it.name == 'Mock' }
    }

    void testFrameworkChange() {
        registerMock2()

        NCBManager manager = new NCBManager(objectManager, eventManager)
        manager.login("Andrew", "password");
        manager.createSession("101");
        manager.sendSchema("101 ", "Andrew", " Yali", "101 " + "AUDIO" + " Yali Andrew", null);
        manager.addParty("101", "Yali");
        manager.enableMedium("101", "AUDIO");

        waitThreadsFinish(3000)

        MockAdapter.instance.markFailed()

        waitThreadsFinish(2000)

        assertNull objectManager.getAvailableObjects().find { it.name == 'Mock' }
        assertNotNull objectManager.getAvailableObjects().find { it.name == 'Mock2' }


        List calls = MockAdapter.instance.calls

        assertEquals("login()", calls.remove(0))
        assertEquals("sendSchema(101 AUDIO Yali Andrew, Yali)", calls.remove(0))
        assertEquals("createSession(101)", calls.remove(0))
//        assertEquals("createSession(101)", calls.remove(0))
        assertEquals("addParticipant(101, Yali)", calls.remove(0))
//        assertEquals("addParticipant(101, Yali)", calls.remove(0))
        assertEquals("enableMedium(101, AUDIO)", calls.remove(0))
        assertEquals("destroySession(101)", calls.remove(0))

        List calls2 = Mock2Adapter.instance.calls
        assertEquals("login()", calls2.remove(0))
        assertEquals("sendSchema(101 AUDIO Yali Andrew, Yali)", calls2.remove(0))
        assertEquals("createSession(101)", calls2.remove(0))
        assertEquals("addParticipant(101, Yali)", calls2.remove(0))
        assertEquals("enableMedium(101, AUDIO)", calls2.remove(0))
    }

    void testWaitingCall() {
        NCBManager manager = new NCBManager(objectManager, eventManager)
        manager.login("Yali", "password");
        manager.createSession("101");

        waitThreadsFinish(2000)

        manager.eventObjectManager.addUpListener(new NCBDriver(manager, "Yali"))
        MockAdapter.instance.notifySchemaReceivedEvent("101 AUDIO Yali Andrew")

        waitThreadsFinish(2000)

        List calls = MockAdapter.instance.calls
        println calls

        assertEquals("login()", calls.remove(0))
        assertEquals("createSession(101)", calls.remove(0))
//        assertEquals("createSession(101)", calls.remove(0))
//        assertEquals("createSession(101)", calls.remove(0))
        assertEquals("addParticipant(101, Andrew)", calls.remove(0))
//        assertEquals("addParticipant(101, Andrew)", calls.remove(0))
        assertEquals("enableMediumReceiver(101, AUDIO)", calls.remove(0))
    }

    void testLoginException() {
        registerMock2()

        NCBManager manager = new NCBManager(objectManager, eventManager)
        MockAdapter.instance.loginShouldFail()

        manager.login("Andrew", "password");
        manager.createSession("101");
        manager.sendSchema("101 ", "Andrew", " Yali", "101 " + "AUDIO" + " Yali Andrew", null);
        manager.addParty("101", "Yali");
        manager.enableMedium("101", "AUDIO");

        waitThreadsFinish(2000)

        assertNull objectManager.getAvailableObjects().find { it.name == 'Mock' }
        assertNotNull objectManager.getAvailableObjects().find { it.name == 'Mock2' }


        List calls = MockAdapter.instance.calls

        assertEquals("login()", calls.remove(0))
        assertEquals("sendSchema(101 AUDIO Yali Andrew, Yali)", calls.remove(0))

        List calls2 = Mock2Adapter.instance.calls
        assertEquals("login()", calls2.remove(0))
        assertEquals("sendSchema(101 AUDIO Yali Andrew, Yali)", calls2.remove(0))
        assertEquals("createSession(101)", calls2.remove(0))
//        assertEquals("createSession(101)", calls2.remove(0))
        assertEquals("addParticipant(101, Yali)", calls2.remove(0))
//        assertEquals("addParticipant(101, Yali)", calls2.remove(0))
        assertEquals("enableMedium(101, AUDIO)", calls2.remove(0))
    }

    void testTwoMediaSameFw() {
        objectManager.removeObject('Mock')
        registerMock2()

        NCBManager manager = new NCBManager(objectManager, eventManager)
        manager.login("Andrew", "password");
        manager.createSession("101");
        manager.addParty("101", "Yali");
        manager.sendSchema("101 ", "Andrew", " Yali", "101 " + "AUDIO" + " Yali Andrew", null);
        manager.enableMedium("101", "AUDIO");
        waitThreadsFinish(4000)

        manager.addParty('101', 'Tariq')
        manager.enableMedium("101", "VIDEO");
        waitThreadsFinish(4000)

        manager.logout("Andrew")
        waitThreadsFinish(1000)

        List calls2 = Mock2Adapter.instance.calls
        assertEquals("login()", calls2.remove(0))
        assertEquals("sendSchema(101 AUDIO Yali Andrew, Yali)", calls2.remove(0))
        assertEquals("createSession(101)", calls2.remove(0))
//        assertEquals("createSession(101)", calls2.remove(0))
        assertEquals("addParticipant(101, Yali)", calls2.remove(0))
//        assertEquals("addParticipant(101, Yali)", calls2.remove(0))
        assertEquals("enableMedium(101, AUDIO)", calls2.remove(0))
        assertEquals("addParticipant(101, Tariq)", calls2.remove(0))
        assertEquals("enableMedium(101, VIDEO)", calls2.remove(0))
        assertEquals("logout()", calls2.remove(0))
    }

    private void waitThreadsFinish(long time) {
        println "Waiting ${time/60} seconds"
        ThreadGroup tg = Thread.currentThread().threadGroup
        Thread[] ths = new Thread[tg.activeCount()]
        tg.enumerate(ths)

        for (Thread th: ths) {
            if (th != null && th.isAlive())
                th.join(time)
        }
    }

}
