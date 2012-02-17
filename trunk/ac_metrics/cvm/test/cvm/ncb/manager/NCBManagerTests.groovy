package cvm.ncb.manager

import cvm.ncb.adapters.Mock2Adapter
import cvm.ncb.adapters.MockAdapter
import cvm.ncb.drivers.NCBDriver
import cvm.ncb.handlers.EventManager
import cvm.sb.emf.EMFLoader
import cvm.sb.emf.ObjectManagerFactory
import sb.base.ResourceManager

class NCBManagerTests extends GroovyTestCase {
    public void restoreMock2() {
        objectManager.getObject('Mock2').metadata.restore()
    }

    private EventManager eventManager
    private cvm.ncb.ks.ResourceManager objectManager

    void setUp() {
        def rm = EMFLoader.loadFirst('InstanceResourceManager', ResourceManager)
        objectManager = new ObjectManagerFactory().createObjectManager(rm)
        eventManager = new EventManager()

        objectManager.getObject('Mock2').metadata.fail()
    }


    void testInitOk() {
        NCBManager manager = new NCBManager(objectManager, eventManager)
        manager.login("Andrew", "password");
        manager.createSession("101");
        manager.addParty("101", "Yali");
        manager.sendSchema("101 ", "Andrew", " Yali", "101 " + "Audio" + " Yali Andrew", null);
        manager.enableMedium("101", "Audio");
        waitThreadsFinish(1000)

        manager.logout("Andrew")
        waitThreadsFinish(1000)

        List calls = MockAdapter.instance.calls
        assertEquals("login()", calls.remove(0))
        assertEquals("sendSchema(101 Audio Yali Andrew, Yali)", calls.remove(0))
//        assertEquals("createSession(101)", calls.remove(0))
        assertEquals("createSession(101)", calls.remove(0))
        assertEquals("addParticipant(101, Yali)", calls.remove(0))
//        assertEquals("addParticipant(101, Yali)", calls.remove(0))
        assertEquals("enableMedium(101, Audio)", calls.remove(0))
        assertEquals("logout()", calls.remove(0))
    }

    void testTwoMedia() {
        restoreMock2()

        NCBManager manager = new NCBManager(objectManager, eventManager)
        manager.login("Andrew", "password");
        manager.createSession("101");
        manager.addParty("101", "Yali");
        manager.sendSchema("101 ", "Andrew", " Yali", "101 " + "Audio" + " Yali Andrew", null);
        manager.enableMedium("101", "Audio");
        waitThreadsFinish(2000)

        manager.enableMedium("101", "Video");
        waitThreadsFinish(2000)

        manager.logout("Andrew")
        waitThreadsFinish(1000)

        List calls = MockAdapter.instance.calls
        assertEquals("login()", calls.remove(0))
        assertEquals("sendSchema(101 Audio Yali Andrew, Yali)", calls.remove(0))
        assertEquals("createSession(101)", calls.remove(0))
//        assertEquals("createSession(101)", calls.remove(0))
        assertEquals("addParticipant(101, Yali)", calls.remove(0))
//        assertEquals("addParticipant(101, Yali)", calls.remove(0))
        assertEquals("enableMedium(101, Audio)", calls.remove(0))
        assertEquals("destroySession(101)", calls.remove(0))

        List calls2 = Mock2Adapter.instance.calls
        assertEquals("login()", calls2.remove(0))
        assertEquals("sendSchema(101 Audio Yali Andrew, Yali)", calls2.remove(0))
        assertEquals("createSession(101)", calls2.remove(0))
        assertEquals("addParticipant(101, Yali)", calls2.remove(0))
        assertEquals("enableMedium(101, Video)", calls2.remove(0))
    }

    void testFailedFramework() {
        NCBManager manager = new NCBManager(objectManager, eventManager)
        manager.login("Andrew", "password");
        manager.createSession("101");
        manager.sendSchema("101 ", "Andrew", " Yali", "101 " + "Audio" + " Yali Andrew", null);
        manager.addParty("101", "Yali");
        manager.enableMedium("101", "Audio");

        assertNotNull objectManager.getAvailableObjects().find { it.name == 'Mock' }
        MockAdapter.instance.markFailed('Audio')

        waitThreadsFinish(1000)

        assertNull objectManager.getAvailableObjects().find { it.name == 'Mock' }
    }

    void testFrameworkChange() {
        restoreMock2()

        NCBManager manager = new NCBManager(objectManager, eventManager)
        manager.login("Andrew", "password");
        manager.createSession("101");
        manager.sendSchema("101 ", "Andrew", " Yali", "101 " + "Audio" + " Yali Andrew", null);
        manager.addParty("101", "Yali");
        manager.enableMedium("101", "Audio");

        waitThreadsFinish(500)

        MockAdapter.instance.markFailed('Audio')

        waitThreadsFinish(2000)

        assertNull objectManager.getAvailableObjects().find { it.name == 'Mock' }
        assertNotNull objectManager.getAvailableObjects().find { it.name == 'Mock2' }


        List calls = MockAdapter.instance.calls

        assertEquals("login()", calls.remove(0))
        assertEquals("sendSchema(101 Audio Yali Andrew, Yali)", calls.remove(0))
        assertEquals("createSession(101)", calls.remove(0))
//        assertEquals("createSession(101)", calls.remove(0))
        assertEquals("addParticipant(101, Yali)", calls.remove(0))
//        assertEquals("addParticipant(101, Yali)", calls.remove(0))
        assertEquals("enableMedium(101, Audio)", calls.remove(0))
        assertEquals("destroySession(101)", calls.remove(0))

        List calls2 = Mock2Adapter.instance.calls
        assertEquals("login()", calls2.remove(0))
        assertEquals("sendSchema(101 Audio Yali Andrew, Yali)", calls2.remove(0))
        assertEquals("createSession(101)", calls2.remove(0))
        assertEquals("addParticipant(101, Yali)", calls2.remove(0))
        assertEquals("enableMedium(101, Audio)", calls2.remove(0))
    }

    void testWaitingCall() {
        NCBManager manager = new NCBManager(objectManager, eventManager)
        manager.login("Yali", "password");
        manager.createSession("101");

        waitThreadsFinish(500)

        manager.eventObjectManager.addUpListener(new NCBDriver(manager, "Yali"))
        MockAdapter.instance.notifySchemaReceivedEvent("101 Audio Yali Andrew")

        waitThreadsFinish(1000)

        List calls = MockAdapter.instance.calls
        println calls

        assertEquals("login()", calls.remove(0))
        assertEquals("createSession(101)", calls.remove(0))
//        assertEquals("createSession(101)", calls.remove(0))
        //        assertEquals("createSession(101)", calls.remove(0))
        assertEquals("addParticipant(101, Andrew)", calls.remove(0))
//        assertEquals("addParticipant(101, Andrew)", calls.remove(0))
        assertEquals("enableMediumReceiver(101, Audio)", calls.remove(0))
    }

    void testLoginException() {
        restoreMock2()

        NCBManager manager = new NCBManager(objectManager, eventManager)
        MockAdapter.instance.loginShouldFail()

        manager.login("Andrew", "password");
        manager.createSession("101");
        manager.sendSchema("101 ", "Andrew", " Yali", "101 " + "Audio" + " Yali Andrew", null);
        manager.addParty("101", "Yali");
        manager.enableMedium("101", "Audio");

        waitThreadsFinish(2000)

        assertNull objectManager.getAvailableObjects().find { it.name == 'Mock' }
        assertNotNull objectManager.getAvailableObjects().find { it.name == 'Mock2' }


        List calls = MockAdapter.instance.calls

        assertEquals("login()", calls.remove(0))
        assertEquals("sendSchema(101 Audio Yali Andrew, Yali)", calls.remove(0))

        List calls2 = Mock2Adapter.instance.calls
        assertEquals("login()", calls2.remove(0))
        assertEquals("sendSchema(101 Audio Yali Andrew, Yali)", calls2.remove(0))
        assertEquals("createSession(101)", calls2.remove(0))
//        assertEquals("createSession(101)", calls2.remove(0))
        assertEquals("addParticipant(101, Yali)", calls2.remove(0))
//        assertEquals("addParticipant(101, Yali)", calls2.remove(0))
        assertEquals("enableMedium(101, Audio)", calls2.remove(0))
    }

    void testTwoMediaSameFw() {
        objectManager.removeObject('Mock')
        restoreMock2()

        NCBManager manager = new NCBManager(objectManager, eventManager)
        manager.login("Andrew", "password");
        manager.createSession("101");
        manager.addParty("101", "Yali");
        manager.sendSchema("101 ", "Andrew", " Yali", "101 " + "Audio" + " Yali Andrew", null);
        manager.enableMedium("101", "Audio");
        waitThreadsFinish(1000)

        manager.addParty('101', 'Tariq')
        manager.enableMedium("101", "Video");
        waitThreadsFinish(800)

        manager.logout("Andrew")
        waitThreadsFinish(800)

        List calls2 = Mock2Adapter.instance.calls
        assertEquals("login()", calls2.remove(0))
        assertEquals("sendSchema(101 Audio Yali Andrew, Yali)", calls2.remove(0))
        assertEquals("createSession(101)", calls2.remove(0))
//        assertEquals("createSession(101)", calls2.remove(0))
        assertEquals("addParticipant(101, Yali)", calls2.remove(0))
//        assertEquals("addParticipant(101, Yali)", calls2.remove(0))
        assertEquals("enableMedium(101, Audio)", calls2.remove(0))
        assertEquals("destroySession(101)", calls2.remove(0))

        assertEquals("createSession(101)", calls2.remove(0))
        assertEquals("addParticipant(101, Yali)", calls2.remove(0))
        assertEquals("addParticipant(101, Tariq)", calls2.remove(0))
        assertEquals("enableMedium(101, Video)", calls2.remove(0))
        assertEquals("logout()", calls2.remove(0))
    }

    private void waitThreadsFinish(long time) {
        println "Waiting ${time / 60} seconds"
        ThreadGroup tg = Thread.currentThread().threadGroup
        Thread[] ths = new Thread[tg.activeCount()]
        tg.enumerate(ths)

        for (Thread th: ths) {
            if (th != null && th.isAlive())
                th.join(time)
        }
    }

}
