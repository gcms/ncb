package cvm.ncb.manager

import cvm.ncb.adapters.Mock2Adapter
import cvm.ncb.adapters.MockAdapter
import cvm.ncb.drivers.NCBDriver
import cvm.ncb.handlers.EventManager
import cvm.sb.manager.MainManager
import cvm.sb.emf.EMFLoader
import cvm.sb.emf.ManagerFactory
import sb.base.Manager
import cvm.sb.resource.ResourceManager

class NCBManagerTests extends GroovyTestCase {
    public void restoreMock2() {
        resourceManager.getObject('Mock2').metadata.restore()
    }

    private EventManager eventManager
    private MainManager mainManager
    private ResourceManager resourceManager

    void setUp() {
        println "WAITING..."
        Thread.sleep(60000)
        println "START!"
        Manager managerDef = EMFLoader.loadFirst(Manager)

        eventManager = new EventManager()
        mainManager = new ManagerFactory().createManager(managerDef)
        mainManager.setEventListener(eventManager)
        resourceManager = mainManager.resourceManager

        resourceManager.getObject('Mock2').metadata.fail()
    }

    void tearDown() {
        mainManager.stop()
    }

//    void exemplo() {
//
//
//        EventManager signalHandler = new EventManager();
//        Manager managerDef = EMFLoader.loadFirst(Manager);
//
//        ManagerFactory factory = new ManagerFactory();
//        NCBManager manager = new NCBManager(factory.createManager(managerDef), signalHandler);
//
//        manager.login("Andrew", "password");
//        manager.createSession("101");
//        manager.addParty("101", "Yali");
//        manager.sendSchema(" Yali", "101 " + "Audio" + " Yali Andrew", null);
//        manager.enableMedium("101", "Audio");
//
//
//    }


    void testInitOk() {
        NCBManager manager = new NCBManager(mainManager)
        manager.login("Andrew", "password");
        manager.createSession("101");
        manager.addParty("101", "Yali");
        manager.sendSchema(" Yali", "101 " + "Audio" + " Yali Andrew", null);
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

        NCBManager manager = new NCBManager(mainManager)
        manager.login("Andrew", "password");
        manager.createSession("101");
        manager.addParty("101", "Yali");
        manager.sendSchema(" Yali", "101 " + "Audio" + " Yali Andrew", null);
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
        NCBManager manager = new NCBManager(mainManager)
        manager.login("Andrew", "password");
        manager.createSession("101");
        manager.sendSchema(" Yali", "101 " + "Audio" + " Yali Andrew", null);
        manager.addParty("101", "Yali");
        manager.enableMedium("101", "Audio");

        assertNotNull resourceManager.getAvailableObjects().find { it.name == 'Mock' }
        MockAdapter.instance.markFailed('Audio')

        waitThreadsFinish(1000)

        assertNull resourceManager.getAvailableObjects().find { it.name == 'Mock' }
    }

    void testFrameworkChange() {
        restoreMock2()

        NCBManager manager = new NCBManager(mainManager)
        manager.login("Andrew", "password");
        manager.createSession("101");
        manager.sendSchema("Yali", "101 Audio Yali Andrew", null);
        manager.addParty("101", "Yali");
        manager.enableMedium("101", "Audio");

        waitThreadsFinish(500)

        MockAdapter.instance.markFailed('Audio')

        waitThreadsFinish(2000)

        assertNull resourceManager.getAvailableObjects().find { it.name == 'Mock' }
        assertNotNull resourceManager.getAvailableObjects().find { it.name == 'Mock2' }


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
        NCBManager manager = new NCBManager(mainManager)
        manager.login("Yali", "password");
        manager.createSession("101");

        waitThreadsFinish(500)

        eventManager.addUpListener(new NCBDriver(manager, "Yali"))
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

        NCBManager manager = new NCBManager(mainManager)
        MockAdapter.instance.loginShouldFail()

        manager.login("Andrew", "password");
        manager.createSession("101");
        manager.sendSchema(" Yali", "101 " + "Audio" + " Yali Andrew", null);
        manager.addParty("101", "Yali");
        manager.enableMedium("101", "Audio");

        waitThreadsFinish(2000)

        assertNull resourceManager.getAvailableObjects().find { it.name == 'Mock' }
        assertNotNull resourceManager.getAvailableObjects().find { it.name == 'Mock2' }


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
        resourceManager.removeObject('Mock')
        restoreMock2()

        NCBManager manager = new NCBManager(mainManager)
        manager.login("Andrew", "password");
        manager.createSession("101");
        manager.addParty("101", "Yali");
        manager.sendSchema(" Yali", "101 " + "Audio" + " Yali Andrew", null);
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



  void testThreeWay() {
        NCBManager manager = new NCBManager(mainManager)
        manager.login("Andrew", "password");
        manager.createSession("101");

        manager.addParty("101", "Yali");
        manager.addParty("101", "Tariq");

        manager.sendSchema("Yali Tariq", "101 Audio Yali Tariq Andrew", null);

        manager.enableMedium("101", "Audio");
        waitThreadsFinish(1000)

        manager.logout("Andrew")
        waitThreadsFinish(1000)

        List calls = MockAdapter.instance.calls
        assertEquals("login()", calls.remove(0))
        assertEquals("sendSchema(101 Audio Yali Tariq Andrew, Yali)", calls.remove(0))
        assertEquals("sendSchema(101 Audio Yali Tariq Andrew, Tariq)", calls.remove(0))
//        assertEquals("createSession(101)", calls.remove(0))
        assertEquals("createSession(101)", calls.remove(0))
        assertEquals("addParticipant(101, Yali)", calls.remove(0))
        assertEquals("addParticipant(101, Tariq)", calls.remove(0))
//        assertEquals("addParticipant(101, Yali)", calls.remove(0))
        assertEquals("enableMedium(101, Audio)", calls.remove(0))
        assertEquals("logout()", calls.remove(0))

        List calls2 = Mock2Adapter.instance.calls
        assertEquals("login()", calls2.remove(0))
        assertEquals("sendSchema(101 Audio Yali Tariq Andrew, Yali)", calls2.remove(0))
        assertEquals("sendSchema(101 Audio Yali Tariq Andrew, Tariq)", calls2.remove(0))        
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
