package cvm.ncb.adapters;

import cvm.model.CVM_Debug;
import org.jivesoftware.smack.*;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smackx.filetransfer.*;
import org.jivesoftware.smackx.jingle.JingleManager;
import org.jivesoftware.smackx.jingle.JingleSession;
import org.jivesoftware.smackx.jingle.JingleSessionRequest;
import org.jivesoftware.smackx.jingle.listeners.CreatedJingleSessionListener;
import org.jivesoftware.smackx.jingle.listeners.JingleSessionRequestListener;
import org.jivesoftware.smackx.jingle.media.JingleMediaManager;
import org.jivesoftware.smackx.jingle.mediaimpl.jmf.JmfMediaManager;
import org.jivesoftware.smackx.jingle.nat.FixedResolver;
import org.jivesoftware.smackx.jingle.nat.FixedTransportManager;
import org.jivesoftware.smackx.jingle.nat.JingleTransportManager;

import java.io.*;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

//import org.jivesoftware.smackx.jingle.mediaimpl.jmf.CloneDS;

/**
 * Smack Jingle Application. It register in a XMPP Server and let users place calls
 * using a full JID.
 * Parameters: Server User Pass.
 */

public class SmackAdapter extends NCBBridgeBase {

    static {
        JingleManager.setJingleServiceEnabled();
//        org.jivesoftware.smackx.ServiceDiscoveryManager.getIdentityName();
    }

    private enum EventType {
        WAITFORCALLBACK, WAITFORRECEIVERS
    }

    public String fwName = "Smack";
    private Connection xmppConnection = null;
    private Object dataSync = new Object();
    private CtlHelper ctlHelper = null;
    private String myJID = null;
    private String server = null;
    private String user = null;
    private String pass = null;
    private boolean mediumFailureStatus = false;
    private BlockingQueue<Boolean> wait_for_reciever_setup = new ArrayBlockingQueue<Boolean>(1);

    private JingleManager jm = null;
    private JingleTransportManager transportManager = null;
    private CreatedJingleSessionListener createdSessionListener = null;
    private FileTransferManager fmanager = null;
    private OutgoingFileTransfer transfer = null;

    private ArrayList<HashSet<String>> jidsList = null;
    private TreeMap<String, ArrayList<JingleManager>> jManagers = null;
    private ArrayList<String> sessMgr = null;
    private TreeMap<String, ArrayList<JingleSession>> iSessions_audio = null;
    private TreeMap<String, ArrayList<JingleSession>> iSessions_video = null;
    private TreeMap<String, ArrayList<JingleSession>> oSessions_audio = null;
    private TreeMap<String, ArrayList<JingleSession>> oSessions_video = null;
    private TreeMap<String, Integer> wait_for_reciever_setup_list = null;
    private BlockingQueue<Boolean> wait_for_sendMedia_call_back = new ArrayBlockingQueue<Boolean>(1);
    private boolean isLocal = false;

    public SmackAdapter() {
//        CloneDS.getCloneDataSource();
        init();
    }

    public SmackAdapter(boolean isLocal) {
//        CloneDS.getCloneDataSource();
        this.isLocal = isLocal;
        init();
    }

    /**
     * This function logs the user into the communication bridge i.e Skype, GTalk...
     *
     * @return UserObject with the user information.
     * @see UserObject
     */
    @Method(name = "login")
    public void login() {
        String userName = getUserInfoStore().getFwUserName(getName());
        String password = getUserInfoStore().getFwPassword(getName());

        /* Need to separate user and server from userName */
        this.myJID = userName;
        this.server = userName.substring(userName.indexOf("@") + 1);
        this.user = userName.substring(0, userName.indexOf("@"));
        this.pass = password;
        /* create a connection to the server,
           * then attempt login
           */
        xmppConnection = new XMPPConnection(server);
        /* To handle NATs */
        /*icetm0 = new ICETransportManager(xmppConnection,
    			"jivesoftware.com", 3478);*/
        try {
            //xmppConnection.connect();
            if (!xmppConnection.isConnected())
                xmppConnection.connect();
            xmppConnection.login(user, pass);

            //Create the file transfer manager
            fmanager = new FileTransferManager(xmppConnection);
            startFileTransferListener();
        } catch (XMPPException e) {
            e.printStackTrace();
            throw new EventException("LoginFailed");
        }
        //Create the roster manager
        startRosterListener();
//        icetm0 = new ICETransportManager(xmppConnection,"stun.xten.com", 3478);

        FixedTransportManager manager = new FixedTransportManager(new FixedResolver("0.0.0.0", 4531));
        transportManager = manager;
        createdSessionListener = manager;

        CVM_Debug.getInstance().printDebugMessage("Done login");
    }

    @Method(name = "createSession", parameters = {"session"})
    public void createSession(String session) {
        if (!sessMgr.contains(session)) {
            sessMgr.add(session);
            initSession(sessMgr.indexOf(session));
        }
    }

    @Method(name = "destroySession", parameters = {"session"})
    public void destroySession(String session) {
        int sessID = sessMgr.indexOf(session);
        jidsList.remove(sessID);
        for (int i = 0; i < jManagers.get(sessID).size(); i++) {
            jManagers.get(sessID).get(i).removeCreationListener(createdSessionListener);
            jManagers.get(sessID); // may need to remove
            jManagers.get(sessID).get(i).disconnectAllSessions();
        }
        jManagers.put(sessID + "", null);
        destroyOutgoingAudioSession(sessID + "");
        destroyOutgoingVideoSession(sessID + "");
        destroyIncomingAudioSession(sessID + "");
        destroyIncomingVideoSession(sessID + "");
        oSessions_audio.put(sessID + "", null);
        oSessions_video.put(sessID + "", null);
        sessMgr.set(sessID, null);
    }

    private boolean isOnline(String participantID) {
        if (xmppConnection.getRoster().getPresence(participantID).isAvailable()) {
            //throw new PartyNotAddedException(participantID);
            CVM_Debug.getInstance().printDebugMessage("participant online");
            return true;
        }
        return true;
    }

    public void addParticipant(String session, String participant) {
        if (participant.equals(myJID)) {
            return;
        }
        if (isOnline(participant) == false) {
            //throw new PartyNotAddedException(participantID);
            CVM_Debug.getInstance().printDebugMessage("participant offline");
            return;
        }
        int sessID = sessMgr.indexOf(session);
        CVM_Debug.getInstance().printDebugMessage("Session id is " + sessID);
        if (sessID < 0) return; // throw some error
        HashSet<String> hs = jidsList.get(sessID);
        if (hs == null) {
            hs = new HashSet<String>();
        }
        hs.add(participant);
        CVM_Debug.getInstance().printDebugMessage("added " + participant);
        jidsList.add(sessID, hs);
        // TODO check for active data stream and add participant
        /*		addToSession(Integer.parseInt(sID),
                        jidsList.get(Integer.parseInt(sID)));
        */
    }


    /**
     * This command will send the specified medium to all, receive will also start
     * the participants during the connection.
     * @param sID
     * @param medium
     * @param mediumURL
     * @return
     */
/*	private boolean sendMedia(String connectionID, String medium, 
			JingleManager jm, ArrayList<JingleSession> outgoingSessions, 
			ArrayList<JingleSession> incomingSessions)
	{
		int sessID = sessMgr.indexOf(connectionID);
		wait_for_reciever_setup_list.put(connectionID,
				new Integer(0));
		HashSet<String> jids = new HashSet<String>();
		boolean timeout = false;
		int tries = 20;
		CVM_Debug.getInstance().printDebugMessage("in send "+medium+" "+sessID);
		for(String jid: jidsList.get(sessID)){
			jids.add(jid);
			sendNCBCtl( connectionID+" StartReceiver "+medium+" "+myJID, jid);
			wait_for_reciever_setup_list.put(connectionID, 
					new Integer(wait_for_reciever_setup_list.get(connectionID).intValue() + 1));
		}
		if(!jids.isEmpty()){
			sendNCBCtl( connectionID+" startSendMedia "+medium+" "+myJID, jids.iterator().next());
			while(!wait_for_reciever_setup || !timeout){
				try {
					Thread.sleep(150);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				tries--;
				if (tries <= 0) timeout = true;
			}
			CVM_Debug.getInstance().printDebugMessage("wait_for_reciever_setup "
					+wait_for_reciever_setup);
			CVM_Debug.getInstance().printDebugMessage("Number of responders "
					+ jidsList.get(sessID).size());
			return 	sendMedia(connectionID, medium, jm, outgoingSessions, incomingSessions, 
					jids);
		}
		return false;
	}
*/

    /**
     * This command will send the specified medium to all, receive will also start
     * the participants during the connection.
     *
     * @param sID
     * @param medium
     * @param mediumURL
     * @param jids
     * @return
     */
    private boolean sendMedia(String connectionID, String medium,
                              JingleManager jm, ArrayList<JingleSession> outgoingSessions,
                              ArrayList<JingleSession> incomingSessions, HashSet<String> jids) {
        return sendMedia(connectionID, medium, jm, outgoingSessions,
                incomingSessions, jids, null);

    }

/*	*//**
     * This command will send the specified medium to all, receive will also start
     * the participants during the connection.
     * @param sID
     * @param medium
     * @param mediumURL
     * @param jids
     * @return
     *//*
	private boolean sendMedia(String connectionID, String medium, 
			JingleManager jm, ArrayList<JingleSession> outgoingSessions, 
			ArrayList<JingleSession> incomingSessions, HashSet<String> jids)
	{
		//boolean success = sendMedia(connectionID, medium, jm, outgoingSessions, 
				incomingSessions, jids);
		SendMediaHelper smh = new SendMediaHelper();
		smh.start();
		smh.sendMedia(connectionID, medium, jm, outgoingSessions, 
				incomingSessions, jids);
		return true;
	}
*/

    /**
     * This command will send the specified medium to all, receive will also start
     * the participants during the connection.
     *
     * @param sID
     * @param medium
     * @param mediumURL
     * @param jids
     * @return
     */
    private boolean sendMedia(String connectionID, String medium,
                              JingleManager jm, ArrayList<JingleSession> outgoingSessions,
                              ArrayList<JingleSession> incomingSessions, HashSet<String> jids,
                              String sender) {
        CVM_Debug.getInstance().printDebugMessage("in send " + medium + " " + connectionID +
                "\n jids are " + jids);
        if (!jids.isEmpty()) {
            String tmp = " ";
            for (String jid : jids) {
                tmp = tmp + jid + " ";
            }
            sendNCBCtl(connectionID + " startSendMedia " + medium + tmp + myJID,
                    jids.iterator().next());
            ctlHelper.handle(new SmackAdptWaitEvent(new Object(), connectionID, medium,
                    jm, outgoingSessions, incomingSessions, jids, sender,
                    EventType.WAITFORCALLBACK));
        }
        return true;
    }

    private void sendMedia2(String connectionID, String medium,
                            JingleManager jm, ArrayList<JingleSession> outgoingSessions,
                            ArrayList<JingleSession> incomingSessions, HashSet<String> jids,
                            String sender) {
        wait_for_reciever_setup_list.put(connectionID, new Integer(0));
        for (String jid : jids) {
            sendNCBCtl(connectionID + " StartReceiver " + medium + " " + myJID, jid);
            wait_for_reciever_setup_list.put(connectionID,
                    new Integer(wait_for_reciever_setup_list.get(connectionID).intValue() + 1));
        }
        CVM_Debug.getInstance().printDebugMessage("Number of responders "
                + jids.size());
        ctlHelper.handle(new SmackAdptWaitEvent(new Object(), connectionID,
                medium, jm, outgoingSessions, incomingSessions, jids, sender,
                EventType.WAITFORRECEIVERS));
    }

    private void sendMedia3(String connectionID, String medium,
                            JingleManager jm, ArrayList<JingleSession> outgoingSessions,
                            ArrayList<JingleSession> incomingSessions, HashSet<String> jids,
                            String sender) {
        for (String jid : jids) {
            String responder = jid + "/Smack";
            CVM_Debug.getInstance().printDebugMessage("In WHILE LOOP: " + responder);
            JingleSession outgoing = null;
            try {
                if (outgoing == null) {
                    if (jm == null) CVM_Debug.getInstance().printDebugMessage(
                            "jm is null");
                    outgoing = jm.createOutgoingJingleSession(responder);
                }
                CVM_Debug.getInstance().printDebugMessage("Outgoing stuff "
                        + outgoing.getInitiator() + " " + outgoing.getResponder()
                        + " " + outgoing.getMediaManagers());
                synchronized (dataSync) {
                    outgoing.startOutgoing();
                    while (!outgoing.isFullyEstablished()) ;
                }
                System.out.println("Fully established!");
                outgoingSessions.add(outgoing);
            } catch (XMPPException e) {
                System.err.println("Exception in sendMedia : ");
                e.printStackTrace();
            }
        }
        CVM_Debug.getInstance().printDebugMessage("End sendMedia 3");
        if (sender != null) {
            sendNCBCtl(connectionID + " remoteSendMediaStarted " + medium + " " + myJID,
                    sender);
        }
    }

    class SendMediaHelper extends Thread {

        boolean done = false;

        public SendMediaHelper() {
        }

        public void run() {
            //while(!wait_for_sendMedia_call_back ){ //|| !wait_for_reciever_setup){
            CVM_Debug.getInstance().printDebugMessage("Thread running");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //}
        }

    }

    /**
     * Returns the device capabilities.
     */
    public String getCapability() {
        String caps = null;

        //String temp = null;
        //temp = Skype.getAudioOutputDevice();
        caps = "Smack - ";
        caps = NCBBridgeCapability.AUDIO_OUTPUT;
        //temp = Skype.getAudioInputDevice();
        caps = caps + ", " + NCBBridgeCapability.AUDIO_INPUT;
        //temp = Skype.getVideoDevice();
        caps = caps + " , " + NCBBridgeCapability.VIDEO;


        return caps;
    }

    /**
     * Logs the user out of the Bridge.
     */
    @Method(name = "logout")
    public void logout() {
        xmppConnection.disconnect();
    }

    /**
     * Removes a participant to the session.
     *
     * @param sID           Id of the session to remove.
     * @param participantID Id of the participant to remove. i.e useranem: crinsomkairos.
     * @throws PartyNotFoundException
     * @throws NoSessionException
     */
    public void removeParticipant(String sID, String participantID) {
        HashSet<String> hs = jidsList.get(Integer.parseInt(sID));
        if (hs == null || !hs.contains(participantID)) {
            Map<String, Object> params = new LinkedHashMap<String, Object>();
            params.put("participant", participantID);
            throw new EventException("PartyNotAdded", params);
        }
        hs.remove(participantID);
        jidsList.add(Integer.parseInt(sID), hs);
        //TODO check if session has active data streams and remove participant
    }

    /**
     * Restarts the adapter.
     */
    @Method(name = "restartService")
    public void restartService() {
        CVM_Debug.getInstance().printDebugErrorMessage("Restarting Smack");
        resetFW();
        init();
    }


    private void sendStringAsFile(String participantID, String description, String content) throws XMPPException, IOException, InterruptedException {
        transfer = fmanager.createOutgoingFileTransfer(participantID + "/Smack");

        File tmp = File.createTempFile("CVM", "tmp");
        PrintStream ps = new PrintStream(tmp);
        ps.print(content);
        ps.close();
        transfer.sendFile(tmp, description);

        do {

            Thread.sleep(500);
            System.out.println(transfer.getStatus());
            System.out.println(transfer.getProgress());
            System.out.println(transfer.getBytesSent());
            System.out.println(transfer.getAmountWritten());
            System.out.println(transfer.getError());
            System.out.println(transfer.isDone());

        } while (!transfer.isDone());

        tmp.delete();
    }

    /**
     * This function sends the schema file to the specified user.
     *
     * @param schema      Schema File.
     * @param participant Id of the user.
     */
    @Method(name = "sendSchema", parameters = {"schema", "participant"})
    public void sendSchema(String schema, String participant) {
        //synchronized (dataSync){

        /* Create the outgoing file transfer */
        CVM_Debug.getInstance().printDebugMessage("transfering schema ...");

        if (isOnline(participant) == false) {
            //throw new PartyNotAddedException(participantID);
            CVM_Debug.getInstance().printDebugMessage("participant offline");
            return;
        }
        try {
            sendStringAsFile(participant, "schema", schema);
        } catch (XMPPException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        CVM_Debug.getInstance().printDebugMessage("transfer done");
    }

    /**
     * This function sends the schema file to the specified user.
     *
     * @param schema        Schema File.
     * @param participantID Id of the user.
     */
    private void sendNCBCtl(String ctl, String participantID) {
        //synchronized (dataSync){

        /* Create the outgoing file transfer
        *
        */
        if (isOnline(participantID) == false) {
            //throw new PartyNotAddedException(participantID);
            CVM_Debug.getInstance().printDebugMessage("participant offline");
            return;
        }

        try {
            sendStringAsFile(participantID, "NCB_CTL_NCB", ctl);
        } catch (XMPPException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        CVM_Debug.getInstance().printDebugMessage("transfer done");
        //}
    }

    @Method(name = "disableMedium", parameters = {"session", "medium"})
    public void disableMedium(String session, String medium) {
        if (medium.equals(NCBBridgeCapability.AUDIO)) {
            destroyOutgoingAudioSession(sessMgr.indexOf(session) + "");
        } else if (medium.equals(NCBBridgeCapability.VIDEO)) {
            destroyOutgoingVideoSession(sessMgr.indexOf(session) + "");
        }
        CVM_Debug.getInstance().printDebugMessage("Smack End:" + System.currentTimeMillis());
    }

    @Method(name = "enableMedium", parameters = {"session", "medium"})
    public void enableMedium(String session, String medium) {
        enableMedium(session, medium, jidsList.get(
                sessMgr.indexOf(session)), null);
    }

    private void enableMedium(String connectionID, String mediumName,
                              HashSet<String> jids, String sender) {
        int sessID = sessMgr.indexOf(connectionID);
        CVM_Debug.getInstance().printDebugMessage("in send media " + sessID + " medium is " + mediumName);
        if (sessID < 0 || jids.size() <= 0) return; // throw some error for sessID
        if (mediumName.equals(NCBBridgeCapability.AUDIO)) {
            initJmfMgr(sessID, mediumName + "_manager", jManagers.get(sessID + ""));
            sendMedia(connectionID, mediumName,
                    jManagers.get(sessID + "").get(0),
                    oSessions_audio.get(sessID + ""), iSessions_audio.get(sessID + ""),
                    jids, sender);
        } else if (mediumName.equals(NCBBridgeCapability.VIDEO)) {
            initJmfMgr(sessID, mediumName + "_manager", jManagers.get(sessID + ""));
            sendMedia(connectionID, mediumName, jManagers.get(sessID + "").get(1),
                    oSessions_video.get(sessID + ""), iSessions_video.get(sessID + ""),
                    jids, sender);
        } else if (mediumName.equals(NCBBridgeCapability.LIVE_AV) || mediumName.equals("liveAV")) {
            //initRecJmfMgr(jManagers.get(sessID).get(1),sessID, "AV_manager");
        } else {
            sendFile(sessID, jids, "", mediumName);
        }
        CVM_Debug.getInstance().printDebugMessage("NCB sendMedia called with sID:\"" + sessID + "\" and Medium:\"" + mediumName + ".");
    }


    public void enableMediumReceiver(String conID, String mediumName, String sender) {
        int sessID = sessMgr.indexOf(conID);
        String managerName;
        CVM_Debug.getInstance().printDebugMessage("In enableMediumReceiver " + conID + " " + mediumName);
        if (mediumName.equals(NCBBridgeCapability.AUDIO)) {
            managerName = "audio_manager";
        } else if (mediumName.equals(NCBBridgeCapability.VIDEO)) {
            managerName = "video_manager";
        } else if (mediumName.equals(NCBBridgeCapability.LIVE_AV)) {
            managerName = "av_manager";
        } else {
            CVM_Debug.getInstance().printDebugMessage("unknown media!!!!!!!!!!!!!");
            return;
        }
        initRecJmfMgr(sessID, managerName, jManagers.get(sessID + ""));
        CVM_Debug.getInstance().printDebugMessage("JM Arraylist " + jManagers.get(sessID + ""));
        CVM_Debug.getInstance().printDebugMessage("End of enableMediumReceiver " + conID + " " + mediumName);
        sendNCBCtl(conID + " ReceiverStarted " + mediumName, sender);
    }

    @Method(name = "enableMediumReceiver", parameters = {"session", "medium"})
    public void enableMediumReceiver(String session, String medium) {
    }

    @Method(name = "hasMediumFailed", parameters = {"session", "medium"})
    public boolean hasMediumFailed(String session, String medium) {
        //CVM_Debug.getInstance().printDebugMessage("Checking medium status");
        /*if(sessMgr.contains(sessID)){
              CVM_Debug.getInstance().printDebugMessage("Found session: "+sessID);
              if(iSessions.get(sessMgr.indexOf(sessID))!=null &&
                      (medium_type.equals("audio")||(medium_type.equals("video")))){
                  CVM_Debug.getInstance().printDebugMessage("Found incomming session mgr");
                  if(!iSessions.get(sessMgr.indexOf(sessID)).isClosed()) return true;
              }if(oSessions.get(sessMgr.indexOf(sessID))!=null &&
                      (medium_type.equals("audio")||(medium_type.equals("video")))){
                  CVM_Debug.getInstance().printDebugMessage("Found outgoing session mgr");
                  if(!oSessions.get(sessMgr.indexOf(sessID)).isClosed()) return true;			}
          }*/
        return mediumFailureStatus;
    }

    public String getName() {
        return "Smack";
    }

    public void terminate(String conID) {
        destroySession(conID);
    }

    public void terminate(String conID, String medium) {

    }

    /* State Object contains:
      * Framework identifier
      * Map of conID to a set of media
      * current list of capabilities
      *
      */
    public void getState() {

    }

    private boolean sendFile(int sessID, HashSet<String> hs, String medium, String mediumURL) {
        for (String jid : hs) {
            try {
                /* Create the outgoing file transfer
                 * Specify the name of the file as medium
                 */
                transfer = fmanager.createOutgoingFileTransfer(jid + "/Smack");
                transfer.sendFile(new File(mediumURL), medium);
            } catch (XMPPException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    private boolean destroyOutgoingAudioSession(String sessID) {
        for (JingleSession oSession : oSessions_audio.get(sessID)) {
            try {
                oSession.terminate();
            } catch (XMPPException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    private boolean destroyOutgoingVideoSession(String sessID) {
        for (JingleSession oSession : oSessions_video.get(sessID)) {
            try {
                oSession.terminate();
            } catch (XMPPException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    private boolean destroyIncomingAudioSession(String sessID) {
        for (JingleSession iSession : iSessions_audio.get(sessID)) {
            try {
                iSession.terminate();
            } catch (XMPPException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    private boolean destroyIncomingVideoSession(String sessID) {
        for (JingleSession iSession : iSessions_video.get(sessID)) {
            try {
                iSession.terminate();
            } catch (XMPPException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    private void initJmfMgr(int sessID, final String mediamgr_name,
                            ArrayList<JingleManager> jm_arr) {
        CVM_Debug.getInstance().printDebugMessage("Begin initializing JMF Manager for " + mediamgr_name + " ");
        if ((mediamgr_name.equalsIgnoreCase("audio_manager") && jm_arr.get(0) == null) ||
                (mediamgr_name.equalsIgnoreCase("video_manager") && jm_arr.get(1) == null)) {
            ArrayList<JingleMediaManager> mediaManagers = new ArrayList<JingleMediaManager>();
            CVM_Debug.getInstance().printDebugMessage("Setup JMF listener for 1111 " + mediamgr_name +
                    " jm is " + jm);
            JmfMediaManager jmm = new JmfMediaManager(mediamgr_name, transportManager);
            mediaManagers.add(jmm);
            jm = new JingleManager(xmppConnection, mediaManagers);
            jm.addCreationListener(createdSessionListener);
            CVM_Debug.getInstance().printDebugMessage("Setup JMF listener for " + mediamgr_name +
                    " jm is " + jm);
            if (mediamgr_name.equalsIgnoreCase("audio_manager")) {
                jm_arr.set(0, jm);
                jManagers.put(sessID + "", jm_arr);
            } else if (mediamgr_name.equalsIgnoreCase("video_manager")) {
                jm_arr.set(1, jm);
                jManagers.put(sessID + "", jm_arr);
            }
        }
        CVM_Debug.getInstance().printDebugMessage("JM Array is now " + jm_arr);
    }

    private void initRecJmfMgr(final int sessID, final String mediamgr_name,
                               ArrayList<JingleManager> jm_arr) {
        CVM_Debug.getInstance().printDebugMessage("Begin initializing Receivers");
        initJmfMgr(sessID, mediamgr_name, jm_arr);
        CVM_Debug.getInstance().printDebugMessage("Setup listeners");
        if (mediamgr_name.equalsIgnoreCase("audio_manager")) {
            jm = jManagers.get(sessID + "").get(0);
            jm.addJingleSessionRequestListener(new JingleSessionRequestListener() {
                public void sessionRequested(JingleSessionRequest request) {
                    String tmp = request.getJingle().toXML();
                    CVM_Debug.getInstance().printDebugMessage("TMP contains " + tmp + "\nSession from " + sessID + " jids " + jidsList.get(sessID));
                    JingleSession incoming = null;
                    if (!tmp.contains("rgb") && !tmp.contains("jpeg") && !tmp.contains("mpeg")
                            && !tmp.contains("vivo") && !tmp.contains("mov") && !tmp.contains("avi") &&
                            jidsList.get(sessID).contains(request.getFrom().
                                    substring(0, request.getFrom().indexOf("/")))) {
                        CVM_Debug.getInstance().printDebugMessage("Audio detected");
                        try {
                            // Accept the call
                            incoming = request.accept();

                            // Start the call
                            synchronized (dataSync) {
                                incoming.startIncoming();
                            }
                            // Add to session mapping
                            iSessions_audio.get(sessID).add(incoming);
                        } catch (XMPPException e) {
                            e.printStackTrace();
                        }
                        return;
                    }
                }
            });
        } else if (mediamgr_name.equalsIgnoreCase("video_manager")) {
            jm = jManagers.get(sessID + "").get(1);
            jm.addJingleSessionRequestListener(new JingleSessionRequestListener() {
                public void sessionRequested(JingleSessionRequest request) {
                    String tmp = request.getJingle().toXML();
                    CVM_Debug.getInstance().printDebugMessage("TMP contains " + tmp + "\nSession from " + sessID + " jids " + jidsList.get(sessID));
                    JingleSession incoming = null;
                    if ((tmp.contains("rgb") || tmp.contains("jpeg") || tmp.contains("mpeg")
                            || tmp.contains("vivo") || tmp.contains("mov") || tmp.contains("avi")) &&
                            jidsList.get(sessID).contains(request.getFrom().
                                    substring(0, request.getFrom().indexOf("/")))) {
                        CVM_Debug.getInstance().printDebugMessage("Video detected");
                        try {
                            // Accept the call
                            incoming = request.accept();
                            CVM_Debug.getInstance().printDebugMessage("request accepted");
                            // Start the call
                            incoming.startIncoming();
                            CVM_Debug.getInstance().printDebugMessage("incoming started");
                            // Add to session mapping
                            iSessions_video.get(sessID).set(1, incoming);
                        } catch (XMPPException e) {
                            e.printStackTrace();
                        }
                        return;
                    }
                }
            });
        }
    }

    /**
     * This function notifies NCB of a schema received event.
     *
     * @param schema schema file.
     */
    private synchronized void dealWithSchema(String schema) {
        if (isLocal) {
            CVM_Debug.getInstance().printDebugMessage("SmackAdapter: dealWithSchema Called." + schema);
            Scanner sch = new Scanner(schema);
            String sessId = sch.next();
            createSession(sessId);
            String medium = sch.next();
            // add participants
            while (sch.hasNext()) {

                String name = sch.next();
                // remove yourself from list of participants
                if (name.equalsIgnoreCase(xmppConnection.getHost())) {
                    continue;
                }
                addParticipant(sessId, name);
            }
        } else {
            Event event = new Event("SchemaReceived");
            event.setParam("schema", schema);
            notify(event);
        }
    }

    /**
     * This function notifies NCB of a control event.
     *
     * @param ctl control file (connectionId ctlType ctlData sender).
     */
    private synchronized void dealWithNCBCtl(String ctl) {
        CVM_Debug.getInstance().printDebugMessage("SmackAdapter: dealWithNCBCtl Called." + ctl);
        Scanner sch = new Scanner(ctl);
        String connectionId = sch.next();
        String type = sch.next();
        if (type.equalsIgnoreCase("startReceiver")) {
            String medium = null;
            String sender = null;
            if (sch.hasNext()) {
                medium = sch.next();
                sender = sch.next();
            }
            if (medium != null && sender != null) {
                try {
                    enableMediumReceiver(connectionId, medium, sender);
                } catch (EventException e) {
                    e.printStackTrace();
                }
            }
        } else if (type.equalsIgnoreCase("ReceiverStarted")) {
            wait_for_reciever_setup_list.put(connectionId,
                    new Integer(wait_for_reciever_setup_list.get(connectionId).intValue() - 1));
            CVM_Debug.getInstance().printDebugMessage("wait_for_reciever_setup_list " +
                    wait_for_reciever_setup_list.get(connectionId));
            if (wait_for_reciever_setup_list.get(connectionId) <= 0) {
                try {
                    wait_for_reciever_setup.put(true);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        } else if (type.equalsIgnoreCase("startSendMedia")) {
            int sessID = sessMgr.indexOf(connectionId);
            String medium = sch.next();
            String sender = "";
            HashSet<String> jids = new HashSet<String>();
            while (sch.hasNext()) {
                String tmpName = sch.next();
                if (!sch.hasNext()) {
                    sender = tmpName;
                    continue; // this is the sender info, so skip it
                }
                CVM_Debug.getInstance().printDebugMessage("start sendmedia info " +
                        medium + " " + sender);
                if (!myJID.equals(tmpName)) {
                    jids.add(tmpName);
                }
            }
            if (jids.isEmpty()) {
                sendNCBCtl(connectionId + " remoteSendMediaStarted " + medium + " " + myJID,
                        sender);
            }
//			if(medium.equals(NCBBridgeCapability.AUDIO)){

            enableMedium(connectionId, medium, jids, sender);

/*				sendNCBCtl( connectionId+" remoteSendMediaStarted "+medium+" "+myJID, 
                        sender);
            }else if(medium.equals(NCBBridgeCapability.VIDEO)) {
                sendMedia(connectionId, medium,
                        jManagers.get(sessID+"").get(1),oSessions_video.get(sessID+""),
                        iSessions_video.get(sessID+""),jids, sender);
            }else if(medium.equals(NCBBridgeCapability.LIVE_AV)||medium.equals("liveAV"))
            {
                //initRecJmfMgr(jManagers.get(sessID).get(1),sessID, "AV_manager");
            }
*/
        } else if (type.equalsIgnoreCase("remoteSendMediaStarted")) {
            try {
                wait_for_sendMedia_call_back.put(true);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }

    /**
     * This function notifies NCB of a Chat received event.
     *
     * @param schema schema file.
     */
    private void dealWithChat(String chattext) {
        CVM_Debug.getInstance().printDebugMessage("SmackAdapter: dealWithChat Called." + chattext);
        //		NCBEventObjectManager.Instance().notifiySchemaReceivedEvent(schema);
    }

    public static void main(String args[]) {
        if (args.length == 2) {
            SmackAdapter sAdpt = new SmackAdapter(true);
            try {
                sAdpt.login(); //"test@squire.cs.fiu.edu", "test");
            } catch (EventException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(100000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else if (args.length == 4) {
            SmackAdapter sAdpt = new SmackAdapter(true);
            try {
                sAdpt.login(); //"test@squire.cs.fiu.edu", "test");
                String medium = args[2]; //"AUDIO";
                UUID conID = UUID.randomUUID();
                //String conID = "101";
                sAdpt.createSession(conID.toString());
                Scanner scan = new Scanner(args[3]);
                ArrayList<String> remoteParties = new ArrayList<String>();
                String pList = "";
                while (scan.hasNext()) {
                    String party = scan.next();
                    remoteParties.add(party);
                    pList = party + " " + pList;
                }
                for (String remoteParty : remoteParties) {
                    //String tmp = ""+remoteParties;
                    sAdpt.sendSchema(conID.toString() + " " + medium +
                            " " + args[0] + " " + pList, remoteParty); //"101 "+medium+" test@squire.cs.fiu.edu test2@squire.cs.fiu.edu", "test1@squire.cs.fiu.edu");
                }
                for (String remoteParty : remoteParties) {
                    sAdpt.addParticipant(conID.toString(), remoteParty);
                }
                sAdpt.enableMedium(conID.toString(), medium);
            } catch (EventException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(100000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void startFileTransferListener() {
        // Create the listener
        fmanager.addFileTransferListener(new FileTransferListener() {
            public void fileTransferRequest(final FileTransferRequest request) {
                CVM_Debug.getInstance().printDebugMessage("Receiving a transfer request :" + request.getDescription());

                new Thread() {
                    public void run() {
                        try {
                            // Check to see if the request should be accepted
                            if (request.getDescription().equals("schema")) {
                                // Accept schema
                                CVM_Debug.getInstance().printDebugMessage("Receiving Schema transfer :" + request.getDescription());
                                IncomingFileTransfer transfer = request.accept();
                                dealWithSchema((new BufferedReader(
                                        new InputStreamReader(
                                                transfer.recieveFile()))).readLine());
                            } else if (request.getDescription().equals("CVM_CHAT_CVM")) {
                                // Accept schema
                                CVM_Debug.getInstance().printDebugMessage("Receiving  Chat transfer :" + request.getDescription());
                                IncomingFileTransfer transfer = request.accept();
                                dealWithChat((new BufferedReader(
                                        new InputStreamReader(
                                                transfer.recieveFile()))).readLine());
                            } else if (request.getDescription().equals("NCB_CTL_NCB")) {
                                // Accept ctl
                                CVM_Debug.getInstance().printDebugMessage("Receiving CTL transfer :" + request.getDescription());
                                IncomingFileTransfer transfer = request.accept();
                                dealWithNCBCtl((new BufferedReader(
                                        new InputStreamReader(
                                                transfer.recieveFile()))).readLine());
                            } else if (!request.getDescription().equals("")) {
                                // Accept file, file name is description
                                IncomingFileTransfer transfer = request.accept();
                                transfer.recieveFile(
                                        new File(request.getDescription()));
                            } else {
                                // Reject it
                                request.reject();
                            }
                            CVM_Debug.getInstance().printDebugMessage("File transfer done");
                        } catch (XMPPException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();


            }
        });
    }

    private void startRosterListener() {
        // TODO: Retrieve contact list and populate UserObject 
        Roster roster = xmppConnection.getRoster();
        roster.addRosterListener(new RosterListener() {
            public void entriesAdded(Collection<String> addresses) {
            }

            public void entriesDeleted(Collection<String> addresses) {
            }

            public void entriesUpdated(Collection<String> addresses) {
            }

            public void presenceChanged(Presence presence) {
                // Send a new UserObject with the updated presence
                CVM_Debug.getInstance().printDebugMessage("Presence changed: " + presence.getFrom()
                        + " " + presence);
            }
        });
        Collection<RosterEntry> entries =
                (Collection<RosterEntry>) roster.getEntries();
        for (RosterEntry entry : entries) {
            // format is Alias: JID
            CVM_Debug.getInstance().printDebugMessage(entry.toString());
        }

    }

    private void init() {
        sessMgr = new ArrayList<String>();
        jidsList = new ArrayList<HashSet<String>>();
        jManagers = new TreeMap<String, ArrayList<JingleManager>>();
        iSessions_audio = new TreeMap<String, ArrayList<JingleSession>>();
        iSessions_video = new TreeMap<String, ArrayList<JingleSession>>();
        oSessions_audio = new TreeMap<String, ArrayList<JingleSession>>();
        oSessions_video = new TreeMap<String, ArrayList<JingleSession>>();
        wait_for_reciever_setup_list = new TreeMap<String, Integer>();
        ctlHelper = new CtlHelper();
        ctlHelper.start();
    }

    private void resetFW() {
        sessMgr = null;
        jidsList = null;
        jManagers = null;
        iSessions_audio = null;
        iSessions_video = null;
        oSessions_audio = null;
        oSessions_video = null;
    }

    private void initSession(int sessID) {
        jidsList.add(sessID, null);
        ArrayList<JingleManager> jm_arr = new ArrayList<JingleManager>();
        jm_arr.add(null);
        jm_arr.add(null);
        jManagers.put(sessID + "", jm_arr);
        iSessions_audio.put(sessID + "", new ArrayList<JingleSession>());
        iSessions_video.put(sessID + "", new ArrayList<JingleSession>());
        oSessions_audio.put(sessID + "", new ArrayList<JingleSession>());
        oSessions_video.put(sessID + "", new ArrayList<JingleSession>());
    }

    class CtlHelper extends Thread {
        public void run() {
            while (true) {
            }
        }

        public void handle(SmackAdptWaitEvent event) {
            if (event.type.equals(EventType.WAITFORCALLBACK)) {
                WaitForCallBack wcb = new WaitForCallBack(event.connectionID, event.medium, event.jm,
                        event.outgoingSessions, event.incomingSessions, event.jids,
                        event.sender);
                wcb.start();
            }
            if (event.type.equals(EventType.WAITFORRECEIVERS)) {
                WaitForSetup ws = new WaitForSetup(event.connectionID, event.medium, event.jm,
                        event.outgoingSessions, event.incomingSessions, event.jids,
                        event.sender);
                ws.start();
            }
        }

        class WaitForCallBack extends Thread {

            String connectionID;
            String medium;
            JingleManager jm;
            ArrayList<JingleSession> outgoingSessions;
            ArrayList<JingleSession> incomingSessions;
            HashSet<String> jids;
            String sender;

            public WaitForCallBack(String connectionID, String medium,
                                   JingleManager jm, ArrayList<JingleSession> outgoingSessions,
                                   ArrayList<JingleSession> incomingSessions, HashSet<String> jids,
                                   String sender) {
                this.connectionID = connectionID;
                this.medium = medium;
                this.jm = jm;
                this.outgoingSessions = outgoingSessions;
                this.incomingSessions = incomingSessions;
                this.jids = jids;
                this.sender = sender;
            }

            public void run() {
//				boolean timeout = false;
//				int tries = 100 * jids.size();
                CVM_Debug.getInstance().printDebugMessage("waiting for call back ...");
//				while( !timeout){
                try {
                    if (wait_for_sendMedia_call_back.take() == true) {
                        CVM_Debug.getInstance().printDebugMessage(".. got call back");
                        sendMedia2(connectionID, medium, jm, outgoingSessions,
                                incomingSessions, jids, sender);
//							break;
                    }
                    //Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
//					tries--;
//					if (tries <= 0) timeout = true;
//				}
            }
        }

        class WaitForSetup extends Thread {

            String connectionID;
            String medium;
            JingleManager jm;
            ArrayList<JingleSession> outgoingSessions;
            ArrayList<JingleSession> incomingSessions;
            HashSet<String> jids;
            String sender;

            public WaitForSetup(String connectionID, String medium,
                                JingleManager jm, ArrayList<JingleSession> outgoingSessions,
                                ArrayList<JingleSession> incomingSessions, HashSet<String> jids,
                                String sender) {
                this.connectionID = connectionID;
                this.medium = medium;
                this.jm = jm;
                this.outgoingSessions = outgoingSessions;
                this.incomingSessions = incomingSessions;
                this.jids = jids;
                this.sender = sender;
            }

            public void run() {
//				boolean timeout = false;
//				int tries = 150 * jids.size();
                int delay = 0 * jids.size();
                CVM_Debug.getInstance().printDebugMessage("waiting for reciever setup ...");
//				while( !timeout){
                try {
                    if (wait_for_reciever_setup.take() == true) {
                        CVM_Debug.getInstance().printDebugMessage("wait_for_reciever_setup "
                                + wait_for_reciever_setup);
                        Thread.sleep(delay);
                        sendMedia3(connectionID, medium, jm, outgoingSessions,
                                incomingSessions, jids, sender);
//							break;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
//					tries--;
//					if (tries <= 0) timeout = true;
//				}
            }
        }
    }

    class SmackAdptWaitEvent extends EventObject {

        private String connectionID = null;
        private String medium = null;
        private JingleManager jm = null;
        private ArrayList<JingleSession> outgoingSessions = null;
        private ArrayList<JingleSession> incomingSessions = null;
        private HashSet<String> jids = null;
        private String sender = null;
        private EventType type = null;

        public SmackAdptWaitEvent(Object source) {
            super(source);
        }

        public SmackAdptWaitEvent(Object source, String connectionID,
                                  String medium, JingleManager jm,
                                  ArrayList<JingleSession> outgoingSessions,
                                  ArrayList<JingleSession> incomingSessions, HashSet<String> jids,
                                  String sender, EventType type) {
            super(source);
            this.connectionID = connectionID;
            this.medium = medium;
            this.jm = jm;
            this.outgoingSessions = outgoingSessions;
            this.incomingSessions = incomingSessions;
            this.jids = jids;
            this.sender = sender;
            this.type = type;
        }
    }
}