package cvm.ncb.adapters;

import cvm.model.CVM_Debug;
import cvm.ncb.handlers.exception.LoginException;
import cvm.ncb.handlers.exception.NoSessionException;
import cvm.ncb.handlers.exception.PartyNotAddedException;
import cvm.ncb.handlers.exception.PartyNotFoundException;
import net.sourceforge.peers.Logger;
import net.sourceforge.peers.sip.Utils;
import net.sourceforge.peers.sip.core.useragent.SipEvent;
import net.sourceforge.peers.sip.core.useragent.SipEvent.EventType;
import net.sourceforge.peers.sip.core.useragent.UserAgent;
import net.sourceforge.peers.sip.core.useragent.handlers.InviteHandler;
import net.sourceforge.peers.sip.syntaxencoding.SipUriSyntaxException;
import net.sourceforge.peers.sip.transactionuser.Dialog;
import net.sourceforge.peers.sip.transport.SipMessage;
import net.sourceforge.peers.sip.transport.SipRequest;
import net.sourceforge.peers.sip.transport.SipResponse;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.*;

// for Socket classes


/**
 * 
 * This is an adapter to the GoogleTalk interface. This class wraps around
 * the GTalk API calls.
 * For starters it performs the basic operations that are required by 
 * the basic implementation of CVM but it can be extended to perform
 * all the operations allowed by Skype with the uses of Skype  call and chat
 * objects and the connector object.
 * 
 * Date: 5/5/2010
 * 
 * @see Connector 
 *  
 * @author Andrew A Allen
 *
 */
public class SIPAdapter extends NCBBridgeBase implements Observer
{

	public String fwName = "Asterisk";
	private TreeMap<String, Integer> sessMap;
	private TreeMap<String, HashSet<String>> partyMap;
	private TreeMap<String, String> callMap;

	private UserAgent userAgent = null;
	private InviteHandler inviteHandler = null;
    private String myURL = null;
    private String pass = null;
    private Thread thread = null;
    private HashMap<String, String> peersMap = null;
    private CtlMsgServer ctlMsgSrv = null;
    
    public SIPAdapter(){
        userAgent = new UserAgent();
        thread = new Thread(new Runnable() {
            public void run() {
                userAgent.getUas().getInitialRequestManager()
                    .getInviteHandler().addObserver(SIPAdapter.this);
            }
         });
         thread.start();
    	sessMap = new TreeMap<String, Integer>();
    	partyMap = new TreeMap<String, HashSet<String>>();
    	callMap = new TreeMap<String, String>();
    	peersMapInit();
    	ctlMsgSrv = new CtlMsgServer();

    }
    
    private void peersMapInit() {
    	peersMap = new HashMap<String, String>();
    	peersMap.put("demo3@asterisk.cs.fiu.edu","Bamboozle.cs.fiu.edu");
		peersMap.put("demo2@asterisk.cs.fiu.edu","Garfield.cs.fiu.edu");
		peersMap.put("demo5@asterisk.cs.fiu.edu","Taffy.cs.fiu.edu");
		peersMap.put("demo1@asterisk.cs.fiu.edu","Richard.cs.fiu.edu");
		peersMap.put("demo4@asterisk.cs.fiu.edu","Ash.cs.fiu.edu");
		
	}

	public void addParticipant(String session, String participant)
			throws PartyNotAddedException 
	{
    	if(partyMap.containsKey(session)){
			if(!partyMap.get(session).contains(participant)){
				partyMap.get(session).add(participant);
				return;
			}
		}
		//throw new PartyNotAddedException("Already added or invalid session");
	}

	public void createSession(String session)
	{
		if(!sessMap.containsKey(session)){
			// assuming we will be getting a UUID string
			sessMap.put(session, Integer.parseInt(session));
			partyMap.put(session, new HashSet<String>());
		}

	}

	public String getCapability()
	{
		return null;
	}

    public void login()
			throws LoginException 
	{
        String userName = getUserInfoStore().getFwUserName(getName());
        String password = getUserInfoStore().getFwPassword(getName());
		//provider = userName.substring(userName.indexOf("@")+1);
		myURL = "sip:"+userName;
		pass = password;
/*        if(this.userAgent == null){
        	this.userAgent = new UserAgent();
        }
        if (this.inviteHandler == null){
        	inviteHandler = userAgent.getUas()
        		.getInitialRequestManager().getInviteHandler();
        	inviteHandler.addObserver(this);
        }
*/      CVM_Debug.getInstance().printDebugMessage("Done Asterisk login");
	}

	public void logout()
	{
		
	}

	public void removeParticipant(String sID, String participantID)
			throws PartyNotFoundException 
	{
		if(partyMap.containsKey(sID)){
			if(partyMap.get(sID).contains("sip:"+participantID)){
				partyMap.get(sID).remove("sip:"+participantID);
				return;
			}
		}
		throw new PartyNotFoundException("Already removed or invalid session");

	}

	public void restartService()
	{
		// TODO Auto-generated method stub

	}

    public void sendSchema(String schema, String participant) {
		// TODO Auto-generated method stub
	}

	public void disableMedium(String session, String medium) throws PartyNotFoundException, NoSessionException {
		if(medium.equalsIgnoreCase("AUDIO")){
			Dialog dialog = userAgent.getDialogManager().getDialog(
					callMap.get(session));
			hangup(dialog);
			callMap.remove(session);
		}
	}

	public void enableMedium(String session, String medium) throws PartyNotAddedException, NoSessionException {
/*		if(mediumName.equalsIgnoreCase("AUDIO") && 
				partyMap.get(connectionID).size()== 1){
	        if(this.userAgent == null){
	        	this.userAgent = new UserAgent();
	        }
	        if (this.inviteHandler == null){
	        	inviteHandlerUac = userAgent.getUac().getInviteHandler();
	        	inviteHandlerUac.addObserver(this);
	        }
	    	
	        String sipUri = partyMap.get(connectionID).iterator().next();
	        String callId = Utils.generateCallID(userAgent.getMyAddress());
            System.out.println("sipURI "+ sipUri);
            System.out.println("callId "+ callId);
            try {	
            	userAgent.getUac().invite(sipUri, callId);
            } catch (SipUriSyntaxException e) {
	            System.out.println("syntax issue "+ e);
	            return;
	        }
			callMap.put(connectionID,callId );
		}
		// we want a conference with more than 2 persons
		else if(mediumName.equalsIgnoreCase("AUDIO") && 
				partyMap.get(connectionID).size() > 1){
*//*			if(this.userAgent == null){
	        	this.userAgent = new UserAgent();
	        }
	        if (this.inviteHandlerUac == null){
	        	inviteHandlerUac = userAgent.getUac().getInviteHandler();
	        	inviteHandlerUac.addObserver(this);
	        }
*/	        
			for(String participant: partyMap.get(session)){
				if (peersMap.containsKey(participant)){
					ctlMsgSrv.sendCtlMsg(peersMap.get(participant), session +" enable");
				}
			}
			String sipUri = getConfId(session);
	        String callId = Utils.generateCallID(this.userAgent.getMyAddress());
            try {	
            	userAgent.getUac().invite(sipUri, callId);
            } catch (SipUriSyntaxException e) {
	            System.out.println("syntax issue "+ e);
	            return;
	        }
			callMap.put(session,callId );
/*		}
*/	}

   
    //for uas
    private void handleResponse(SipResponse sipResponse) {
        Dialog dialog = userAgent.getDialogManager().getDialog(sipResponse);
        dialog.addObserver(SIPAdapter.this);
    }

    private String getConfId(String connectionID) {
		return "sip:999"+sessMap.get(connectionID).toString();
	}

	private String getMyURL() {
		return myURL;
	}

	public boolean hasMediumFailed(String session, String medium_) {
		// TODO Auto-generated method stub
		return false;
	}

    public String getName() {
        return "Asterisk";
    }

    public void destroySession(String session) {
		if(sessMap.containsKey(session)){
			sessMap.remove(session);
		}
	}
	
    //////////////////////////////////////////////////////////
    // Observer methods
    //////////////////////////////////////////////////////////
    
    public void update(Observable o, Object arg) {
        if (o.equals(inviteHandler)) {
        	if (arg instanceof SipEvent) {
                SipEvent sipEvent = (SipEvent) arg;
                SipMessage sipMessage = sipEvent.getSipMessage();
                //if (Utils.getMessageCallId(sipMessage.).equals(callId)) {
                    manageInviteHandlerEvent(sipEvent);
                //}
                //if event is not for this frame (conversation) simply discard it
            } else {
                Logger.error("invite handler notification unknown");
            }
        } /*else if (o instanceof Dialog) {
            if (dialog == null) {
                dialog = (Dialog) o;
            }
        }
*/        else if (arg instanceof SipEvent) {
            SipEvent sipEvent = (SipEvent) arg;
            if (sipEvent.getEventType() == EventType.INCOMING_CALL) {
                final SipMessage sipMessage = sipEvent.getSipMessage();
                if (sipMessage instanceof SipResponse) {
                	handleResponse((SipResponse)sipMessage);
                }
            }
        }
    }

    private void manageInviteHandlerEvent(SipEvent sipEvent) {
        EventType eventType = sipEvent.getEventType();
        Dialog dialog;
        switch (eventType) {
        
        case RINGING:
            dialog = userAgent.getDialogManager().getDialog(sipEvent.getSipMessage());
            //sipRequest = userAgent.getSipRequest(sipEvent.getSipMessage());
            dialog.addObserver(this);
            break;
            
        case CALLEE_PICKUP:
            dialog = userAgent.getDialogManager().getDialog(sipEvent.getSipMessage());
            dialog.addObserver(this);
            break;
            
        case ERROR:
            break;

        default:
            System.out.println("unknown sip event: " + sipEvent);
            break;
        }
    }

    private void acceptCall(final Dialog dialog, final SipRequest sipRequest) {
        SwingWorker<Void, Void> swingWorker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                inviteHandler.acceptCall(sipRequest, dialog);
                return null;
            }
        };
        swingWorker.execute();
    }
    
    private void rejectCall(SipRequest sipRequest) {
                inviteHandler.rejectCall(sipRequest);
    }
    
    private void cancel(Dialog dialog, SipRequest sipRequest) {
                userAgent.getUac().terminate(dialog, sipRequest);
    }
    
    private void hangup(Dialog dialog) {
                userAgent.getUac().terminate(dialog);
    }

    public void enableMediumReceiver(String session, String medium) throws PartyNotAddedException, NoSessionException {
		if(medium.equalsIgnoreCase("AUDIO")) {

		}
	}

	public static void main(String args[]) {
		if (args.length == 2){
        	SIPAdapter sAdpt = new SIPAdapter();
            try {
				sAdpt.login(); //"test@squire.cs.fiu.edu", "test");
			} catch (LoginException e) {
				e.printStackTrace();
			}
            try {
				Thread.sleep(100000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
       }else if (args.length == 4){
        	SIPAdapter sAdpt = new SIPAdapter();
            try {
				sAdpt.login(); //"test@squire.cs.fiu.edu", "test");
				String medium = args[2]; //"AUDIO";
				//UUID conID = UUID.randomUUID();
				String conID = "101";
				sAdpt.createSession(conID.toString());
				Scanner scan = new Scanner(args[3]);
				ArrayList<String> remoteParties = new ArrayList<String>();
				String pList = "";
				while (scan.hasNext()){
					String party = scan.next();
					remoteParties.add(party);
					pList = party+" "+pList;
				}
				for (String remoteParty: remoteParties){
					//String tmp = ""+remoteParties;
			        sAdpt.sendSchema(conID.toString()+" "+medium+
	        			" "+args[0]+" "+pList,remoteParty); //"101 "+medium+" test@squire.cs.fiu.edu test2@squire.cs.fiu.edu", "test1@squire.cs.fiu.edu");
				}
				for (String remoteParty: remoteParties){
					sAdpt.addParticipant(conID.toString(), remoteParty);
				}
				sAdpt.enableMedium(conID.toString(), medium);
			} catch (LoginException e) {
				e.printStackTrace();
			} catch (PartyNotAddedException e1) {
				e1.printStackTrace();
			} catch (NoSessionException e1) {
				e1.printStackTrace();
			}
            try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
/*			try {
				sAdpt.disableMedium("101", "AUDIO");
			} catch (PartyNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSessionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
*/       }
    }

	class CtlMsgServer implements Runnable {
	    private ServerSocket server;
	    private int port = 7777;

	    public CtlMsgServer() {
	        try {
	            server = new ServerSocket(port);
		        Thread t = new Thread(this);
		        t.start();

	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }

	    public void sendCtlMsg(String remote, String msg){
	    	try {
	    		//
	            // Create a connection to the server socket on the server application
	            //
	            InetAddress host = InetAddress.getByName(remote);
	            Socket socket = new Socket(host.getHostName(), 7777);
	            //
	            // Send a message to the client application
	            //
	            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
	            oos.writeObject(msg);
	            oos.close();
	        } catch (UnknownHostException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	    
	    public void run() {

	        //
	        // The server do a loop here to accept all connection initiated by the
	        // client application.
	        //
	        while (true) {
	            try {
	                Socket socket = server.accept();
	                new ConnectionHandler(socket);
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	    }
	}

	class ConnectionHandler implements Runnable {
	    private Socket socket;

	    public ConnectionHandler(Socket socket) {
	        this.socket = socket;

	        Thread t = new Thread(this);
	        t.start();
	    }

	    public void run() {
	        try
	        {
	            //
	            // Read a message sent by client application
	            //
	            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
	            String msg = (String) ois.readObject();
	    		Scanner sch = new Scanner(msg);
	    		if(!sch.hasNext()){
	    			return;
	    		}
	            String conID = sch.next();
	            String state = sch.next();
	    		ois.close();
	            socket.close();
	            
	            if (state.equalsIgnoreCase("enable")){
	            	createSession(conID);
	            	try {
						enableMedium(conID, "AUDIO");
					} catch (PartyNotAddedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (NoSessionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	            }
	            else if (state.equalsIgnoreCase("disable")){
	            	try {
						disableMedium(conID, "AUDIO");
					} catch (PartyNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (NoSessionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	            }
	            //System.out.println("Waiting for client message...");
	        } catch (IOException e) {
	            e.printStackTrace();
	        } catch (ClassNotFoundException e) {
	            e.printStackTrace();
	        }
	    }
	}

/*	class ClientSocketExample {
//	    public static void main(String[] args) {
//        ServerSocketExample example = new ServerSocketExample();
//        example.handleConnection();
//    }
	    public static void main(String[] args) {
	        try {
	            //
	            // Create a connection to the server socket on the server application
	            //
	            InetAddress host = InetAddress.getLocalHost();
	            Socket socket = new Socket(host.getHostName(), 7777);

	            //
	            // Send a message to the client application
	            //
	            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
	            oos.writeObject("Hello There...");

	            //
	            // Read and display the response message sent by server application
	            //
	            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
	            String message = (String) ois.readObject();
	            System.out.println("Message: " + message);

	            ois.close();
	            oos.close();
	        } catch (UnknownHostException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        } catch (ClassNotFoundException e) {
	            e.printStackTrace();
	        }
	    }
	}
*/}


