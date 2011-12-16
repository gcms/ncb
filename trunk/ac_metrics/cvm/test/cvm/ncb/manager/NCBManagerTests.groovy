package cvm.ncb.manager

import cvm.ncb.adapters.Mock2Adapter
import cvm.ncb.adapters.MockAdapter
import cvm.ncb.csm.CSM_NBTypes
import cvm.ncb.drivers.NCBDriver
import cvm.ncb.ks.CommFWCapabilitySet
import cvm.ncb.oem.policy.Attribute
import cvm.ncb.oem.policy.Feature
import cvm.ncb.oem.policy.Framework

/**
 * Created by IntelliJ IDEA.
 * User: Gustavo Sousa
 * Date: 08/12/11
 * Time: 19:50
 * To change this template use File | Settings | File Templates.
 */
class NCBManagerTests extends GroovyTestCase {
    private static Framework createMockFramework(String fwName) {
        Framework fw1 = new Framework(fwName);
        Feature fw1feat1 = new Feature("Audio");
        fw1feat1.addAttribute(new Attribute("Enabled", "true"));
        fw1feat1.addAttribute(new Attribute("NumberOfUsers", "2"));

        fw1.addFeature(fw1feat1);

        fw1
    }

    private static void registerFramework(String fwName) {
        CommFWCapabilitySet.instance.addFramework(createMockFramework(fwName))
        CSM_NBTypes.registerFramework(fwName, "${fwName}.config".toLowerCase())
    }

    void setUp() {
        CommFWCapabilitySet.reset()
        CommFWCapabilitySet.instance.removeFramework('Skype')
        CommFWCapabilitySet.instance.removeFramework('Smack')
        registerFramework('Mock')
    }


    void testInitOk() {
        NCBManager manager = new NCBManager()
        manager.login("Andrew", "password");
        manager.createSession("101");
        manager.sendSchema("101 ", "Andrew", " Yali", "101 " + "AUDIO" + " Yali Andrew");
        manager.addParty("101", "Yali");
        manager.enableMedium("101", "AUDIO");

        waitThreadsFinish(4000)

        manager.logout("Andrew")
        waitThreadsFinish(1000)

        List calls = MockAdapter.instance.calls
        assertEquals("login()", calls.remove(0))
        assertEquals("sendSchema(101 AUDIO Yali Andrew, Yali)", calls.remove(0))
        assertEquals("createSession(101)", calls.remove(0))
        assertEquals("createSession(101)", calls.remove(0))
        assertEquals("addParticipant(101, Yali)", calls.remove(0))
        assertEquals("addParticipant(101, Yali)", calls.remove(0))
        assertEquals("enableMedium(101, AUDIO)", calls.remove(0))
        assertEquals("logout()", calls.remove(0))
    }

    void testFailedFramework() {
        NCBManager manager = new NCBManager()
        manager.login("Andrew", "password");
        manager.createSession("101");
        manager.sendSchema("101 ", "Andrew", " Yali", "101 " + "AUDIO" + " Yali Andrew");
        manager.addParty("101", "Yali");
        manager.enableMedium("101", "AUDIO");

        assertNotNull CommFWCapabilitySet.instance.getAvailableFrameworks().find { it.name == 'Mock' }
        MockAdapter.instance.markFailed()

        waitThreadsFinish(2000)

        assertNull CommFWCapabilitySet.instance.getAvailableFrameworks().find { it.name == 'Mock' }
    }

    void testFrameworkChange() {
        registerFramework('Mock2')

        NCBManager manager = new NCBManager()
        manager.login("Andrew", "password");
        manager.createSession("101");
        manager.sendSchema("101 ", "Andrew", " Yali", "101 " + "AUDIO" + " Yali Andrew");
        manager.addParty("101", "Yali");
        manager.enableMedium("101", "AUDIO");

        waitThreadsFinish(3000)

        MockAdapter.instance.markFailed()

        waitThreadsFinish(2000)

        assertNull CommFWCapabilitySet.instance.getAvailableFrameworks().find { it.name == 'Mock' }
        assertNotNull CommFWCapabilitySet.instance.getAvailableFrameworks().find { it.name == 'Mock2' }


        List calls = MockAdapter.instance.calls

        assertEquals("login()", calls.remove(0))
        assertEquals("sendSchema(101 AUDIO Yali Andrew, Yali)", calls.remove(0))
        assertEquals("createSession(101)", calls.remove(0))
        assertEquals("createSession(101)", calls.remove(0))
        assertEquals("addParticipant(101, Yali)", calls.remove(0))
        assertEquals("addParticipant(101, Yali)", calls.remove(0))
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
        NCBManager manager = new NCBManager()
        manager.login("Yali", "password");
        manager.createSession("101");

        waitThreadsFinish(2000)

        manager.eventObjectManager.addUpListener(new NCBDriver(manager, "Yali"))
        manager.eventObjectManager.notifiySchemaReceivedEvent("101 AUDIO Yali Andrew")

        waitThreadsFinish(2000)

        List calls = MockAdapter.instance.calls
        println calls

        assertEquals("login()", calls.remove(0))
        assertEquals("createSession(101)", calls.remove(0))
        assertEquals("createSession(101)", calls.remove(0))
        assertEquals("createSession(101)", calls.remove(0))
        assertEquals("addParticipant(101, Andrew)", calls.remove(0))
        assertEquals("addParticipant(101, Andrew)", calls.remove(0))
        assertEquals("enableMediumReceiver(101, AUDIO)", calls.remove(0))
    }

    void testLoginException() {
        registerFramework('Mock2')

        NCBManager manager = new NCBManager()
        MockAdapter.instance.loginShouldFail()

        manager.login("Andrew", "password");
        manager.createSession("101");
        manager.sendSchema("101 ", "Andrew", " Yali", "101 " + "AUDIO" + " Yali Andrew");
        manager.addParty("101", "Yali");
        manager.enableMedium("101", "AUDIO");

        waitThreadsFinish(2000)

        assertNull CommFWCapabilitySet.instance.getAvailableFrameworks().find { it.name == 'Mock' }
        assertNotNull CommFWCapabilitySet.instance.getAvailableFrameworks().find { it.name == 'Mock2' }


        List calls = MockAdapter.instance.calls

        assertEquals("login()", calls.remove(0))
        assertEquals("sendSchema(101 AUDIO Yali Andrew, Yali)", calls.remove(0))

        List calls2 = Mock2Adapter.instance.calls
        assertEquals("login()", calls2.remove(0))
        assertEquals("sendSchema(101 AUDIO Yali Andrew, Yali)", calls2.remove(0))
        assertEquals("createSession(101)", calls2.remove(0))
        assertEquals("createSession(101)", calls2.remove(0))
        assertEquals("addParticipant(101, Yali)", calls2.remove(0))
        assertEquals("addParticipant(101, Yali)", calls2.remove(0))
        assertEquals("enableMedium(101, AUDIO)", calls2.remove(0))
    }


    private void waitThreadsFinish(long time) {
        ThreadGroup tg = Thread.currentThread().threadGroup
        Thread[] ths = new Thread[tg.activeCount()]
        tg.enumerate(ths)

        for (Thread th: ths) {
            if (th != null && th.isAlive())
                th.join(time)
        }
    }

}
