package cvm.ncb.adapters;

import cvm.model.CVM_Debug;
import cvm.ncb.UserObject;
import cvm.ncb.handlers.NCBEventObjectManager;
import cvm.ncb.handlers.exception.LoginException;
import cvm.ncb.handlers.exception.NoSessionException;
import cvm.ncb.handlers.exception.PartyNotAddedException;
import cvm.ncb.handlers.exception.PartyNotFoundException;

import cvm.ncb.adapters.ncblite.av.AVReceive;
import cvm.ncb.adapters.ncblite.av.AVTransmit;
import cvm.ncb.adapters.ncblite.p2p.P2PChannel;
import cvm.ncb.adapters.ncblite.p2p.P2PHandler;
import cvm.ncb.adapters.ncblite.rg.common.RGClient;

import cvm.model.*;

/*import edu.fiu.scis.cvm.util.event_listener.Handles_Event;
import edu.fiu.scis.cvm.util.event_listener.Uses_Listener;
import edu.fiu.scis.cvm.util.event_listener.CVMAdapter.NotifyLoginReply_Event;
import se.ISynthesisEngine;
*/
import java.util.LinkedList;
import java.util.StringTokenizer;

import javax.media.MediaLocator;
import org.eclipse.swt.widgets.Composite;

/**
 * 
 * This is an adapter to the NCB interface. This class wraps around
 * the NCB API calls.
 * For starters it performs the basic operations that are required by 
 * the basic implementation of CVM but it can be extended to perform
 * all the operations allowed by Skype with the uses of Skype  call and chat
 * objects and the connector object.
 * 
 * Date: 01/13/2008
 * 
 * @see Connector 
 *  
 * @author Frank Hernandez
 *
 */
public class NCBNativeAdapter extends NCBBridge 
{
	
	private static String userName;
	private P2PChannel channel = null;
	private boolean connected = false;
	private RGClient rGClient = null;
	private String vMediaURL="192.192.192.1/3000";
	private String aMediaURL="192.192.192.1/3000";
	private static int last_port;
	private boolean REC_started;
	private Object dataSync = new Object();
	private Object dataSync1 = new Object();
	private Object loginObject = null;
	
	public NCBNativeAdapter() {
		connected = connect();
		rGClient = RGClient.getInstance();
		last_port = 3000;
		REC_started = false;
	}
	
	public void ServerConnection(String serverAddr, String serverPort) {
		//RGClient.ServerConnection(serverAddr, Integer.parseInt(serverPort));

	}

	public boolean connect() {
		if (connected) return true;
		try {
			channel = P2PChannel.getInstance();
			return true;
		}catch (Exception e0) {
			CVM_Debug.getInstance().printDebugMessage("Error in connecting "+e0);
		}
		return false;
	}

	public void notifyLogin(Object loginObject) {
		if(!connected) {
			connected = connect();
			//ServerConnection("squire.cs.fiu.edu","55555");
		}
		if (connected){
			try {
				synchronized(dataSync1){
					this.userName = ((LinkedList<String>)loginObject).getFirst();
					this.loginObject = rGClient.callDB("authUser",((LinkedList<String>)loginObject));
					dataSync1.notify();
				}
			}catch (Exception e0) {
				CVM_Debug.getInstance().printDebugMessage("Error logining in "+e0);
			}
		}
		NCBEventObjectManager.Instance().notifyLoginReply(loginObject);
		//use(new NotifyLoginReply_Event(this,this.loginObject));
	}

	public void notifyLogout() {
		try {
			rGClient.callDB("logoutUser", (LinkedList)loginObject);
		}catch (Exception e0) {
			CVM_Debug.getInstance().printDebugMessage("Error logging out");
			NCBEventObjectManager.Instance().notifyLogoffReply(false);
		}
		NCBEventObjectManager.Instance().notifyLogoffReply(true);
	}

	public void updateClientInfo(Object clientObject) {
		LinkedList lst = (LinkedList)clientObject;
		try {
			synchronized(dataSync1){
				loginObject = rGClient.callDB((String)lst.get(0),((LinkedList<String>)lst.get(1)));
			dataSync1.notify();
			}
			notifyUpdateClientInfo(loginObject);
		}catch (Exception e0) {
			CVM_Debug.getInstance().printDebugMessage("Error updating database in UCM");
		}
	}

	public String getUserName() {
		return this.userName;
	}
	
	public void shutDown() {
		// TODO Auto-generated method stub

	}
	public void send(String msg) {
		channel.send(msg);
	}

	/**
	 * This function notifies NCB of a schema received event.
	 * @param schema schema file.
	 */
	private void dealWithSchema(String schema)
	{
		CVM_Debug.getInstance().printDebugMessage("NCBNativeAdapterData: dealWithSchema Called.");
		NCBEventObjectManager.Instance().notifiySchemaReceivedEvent(schema);
		//Remove Tags.
		//Code HERE.
	}

	private void registerHandler() {
		if (!connected){
			connected = connect();
		}
		channel.registerHandler(new P2PHandler() {
	
			public void handle(String msg) {		
				StringTokenizer tkr = new StringTokenizer(msg,"||");
				//String connID = tkr.nextToken();		
				String name = tkr.nextToken();
				String senderSchema = tkr.nextToken();
				if (!name.equalsIgnoreCase(getUserName())) {
					reRegisterHandler(name,senderSchema);
					try {
						Thread.sleep(1000);
					}catch (InterruptedException e0){}
					return;
				}
				dealWithSchema(senderSchema);
			}
		});
	}

	/*	public void registerHandler() {
	//public void registerHandler(final ISynthesisEngine iSynEng) {
		if (!connected){
			connected = connect();
		}
		channel.registerHandler(new P2PHandler() {
	
			public void handle(String msg) {		
				StringTokenizer tkr = new StringTokenizer(msg,"||");
				String connID = tkr.nextToken();		
				String name = tkr.nextToken();
				String senderSchema = tkr.nextToken();
				if (!name.equalsIgnoreCase(getUserName())) {
					reRegisterHandler(connID,name,senderSchema);//sendSchema(connID, name, senderSchema);
//					reRegisterHandler(iSynEng,connID,name,senderSchema);//sendSchema(connID, name, senderSchema);
					try {
						Thread.sleep(1000);
					}catch (InterruptedException e0){}
					return;
				}
				//iSynEng.handleSchemaFromOther(name, senderSchema);
			}
		});
	}*/
	
	private void reRegisterHandler(String name, String schema) {
//	private void reRegisterHandler(final ISynthesisEngine iSynEng,String connID, String name, String schema) {
		channel.unRegisterHandler();
		sendSchema(schema, name);
		registerHandler();
//		registerHandler(iSynEng);
		
	}
	public void sendSchema(String schema, String name) {
		if (!connected) {
			connected = connect();
		}
		channel.send(name+"||"+schema);
		NCBEventObjectManager.Instance().notifySendSchemaReply(true);
	}
		
	public boolean addMedia(String sid, String media_type, String media_location) {
		return false;
	}

	public void addMedia(Object mediaObject) {
		if (mediaObject instanceof LinkedList) {
			String url = (String)((LinkedList)mediaObject).get(0);
			final Composite composite = (Composite)((LinkedList)mediaObject).get(1);
			recAV(url,composite);
		}
	}
	
	public boolean addParty(String sid, String name) {
		// TODO Auto-generated method stub
		return false;
	}

	public int adjustMediaQoS(String sid, String media_type, String media_location, int quality) {
		// TODO Auto-generated method stub
		return 0;
	}

	public void createSession(String uuid) {
		// TODO Auto-generated method stub
		
	}

	public void destroySession(String sid) {
		// TODO Auto-generated method stub
		
	}

	public Object getCapabilities() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean getCapability(String media_type, int count)
	{
		return true; 
	}
	public boolean getCapability(String user, String media_type) {
		// TODO Auto-generated method stub
		return false;
	}

	public String getUsername() {
		// TODO Auto-generated method stub
		return null;
	}

	public void joinSession(String userid, String sid) {
		// TODO Auto-generated method stub
		
	}

	public boolean removeMedia(String sid, String media_type, String media_location) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean removeParty(String sid, String name) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean sendMedia(String sid, String media_type, String media_location) {
		final boolean success = true;
		if(media_type.equalsIgnoreCase("video")) {
				Runnable v_run = new Runnable(){
							public void run() {
								AVTransmit atV = new AVTransmit(new MediaLocator("vfw://0"),
										"239.0.0.1",""+3008,null);//""+nextPort(), null);
								String result = atV.start();
								if (result != null) {
								   CVM_Debug.getInstance().printDebugErrorMessage("Error : " + result);
								   
								}
							}
				};			
				//if (!success) return success;
				Thread v_thread = new Thread(v_run);
				v_thread.setPriority(Thread.MAX_PRIORITY);
				v_thread.start();
				//return success;
		//}else if (media_type.equalsIgnoreCase("audio")) {
				Runnable a_run = new Runnable(){
					public void run() {
						AVTransmit atA = new AVTransmit(new MediaLocator("javasound://"),
								"239.0.0.1", ""+3012,null);//""+nextPort(), null);
						String result = atA.start();
						if (result != null) {
							CVM_Debug.getInstance().printDebugErrorMessage("Error : " + result);
							
						}
					}
				};			
				//if (!success) return success;
				Thread a_thread = new Thread(a_run);
				a_thread.setPriority(Thread.MAX_PRIORITY);
				a_thread.start();
				return success;
		}else {
			return false;
		}
	}

	private void recAV(final String URL, final Composite parent) {
		CVM_Debug.getInstance().printDebugErrorMessage("UCM - REC_started:"+REC_started);
		synchronized (dataSync){
		if(REC_started) {
			Runnable run = new Runnable() {
				public void run() {
					AVReceive avRec = AVReceive.getInstance();
					avRec.addComposite(parent);
					return;
				}};
				Thread thread = new Thread(run);
				thread.setPriority(Thread.MAX_PRIORITY);
				thread.start();
				return;
		}
		REC_started = true;
		Runnable run = new Runnable() {

			public void run() {
				final AVReceive avRec = AVReceive.getInstance();
				avRec.addComposite(parent);
				avRec.addSessions(new String[]{"239.0.0.1"+"/3000", "239.0.0.1"+"/3002","239.0.0.1"+"/3004", "239.0.0.1"+"/3006"});
				//final AVReceive avRec = new AVReceive(new String[]{"239.0.0.1"+"/"+nextPort(), "239.0.0.1"+"/"+nextPort()});
				//final AVReceive avRec = new AVReceive(new String[]{"239.0.0.1"+"/3000", "239.0.0.1"+"/3002"});
				
				//if (!avRec.initialize(parent)) {
				  //  CVM_Debug.getInstance().printDebugErrorMessage("Failed to initialize the sessions.");
				//}

			}};
		Thread thread = new Thread(run);
		thread.setPriority(Thread.MAX_PRIORITY);
		thread.start();
		}
	}
	
	
	public void shutdown() {
		// TODO Auto-generated method stub
		
	}

	public boolean stopMedia(String sid, String media_type, String media_location) {
		// TODO Auto-generated method stub
		return false;
	} 
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		NCBNativeAdapter ucm = new NCBNativeAdapter();
		//ucm.registerHandler();
		//ucm.connect();
		LinkedList<String> test = new LinkedList<String>();
		test.add("sanchez");
		test.add("1234");
		test.add("cadse.cs.fiu.edu");
		ucm.notifyLogin(test);
		//CVM_Debug.getInstance().printDebugMessage(ucm.notifyLoginReply(ucm.loginObject));
		//ucm.sendMedia("me", "audio", "somewhere");
		//ucm.send("Test || text || stuff");
		System.exit(0);
	}

	public String getMediaURL(String sid, String media_type, String media_location) {
		if (media_type.equalsIgnoreCase("video")) return vMediaURL;
		if (media_type.equalsIgnoreCase("audio")) return aMediaURL;
		return "";
	}

	private int nextPort() {
		int cur = last_port;
		last_port += 4;
		return cur;
		
	}

	public void notifyUpdateClientInfo(Object clientObject) {
		//TODO Auto-generated method stub
		//NCBController.getInstance().notifyUpdateClientInfo(clientObject);
	}

	public void notifyLoginReply(Object reply) {
		CVM_Debug.getInstance().printDebugErrorMessage(reply.toString());
		//NCBController.getInstance().notifyLoginReply(reply);
	}

	private void use(/*Handles_Event*/ Object event) {
		//	new RequestHandler(event).start();
	}
	class RequestHandler extends Thread{
	/*	private Handles_Event event = null;
		public RequestHandler(Handles_Event event)
		{
			this.event = event;
		}
		public void run(){	
			if(event instanceof NotifyLoginReply_Event)
			{
				notifyLoginReply(((NotifyLoginReply_Event) event).getContactList());
			}		
		}*/
	}


/*
package cvm.ncb.adapters;

import com.skype.SkypeException;
import com.skype.connector.Connector;

import cvm.ncb.UserObject;
import cvm.ncb.handlers.exception.LoginException;
import cvm.ncb.handlers.exception.NoSessionException;
import cvm.ncb.handlers.exception.PartyNotAddedException;
import cvm.ncb.handlers.exception.PartyNotFoundException;

public class NCBNativeAdapter extends NCBBridge 
{
*/
	@Override
	public void addParticipant(String sID, String participantID)
			throws PartyNotAddedException 
	{
		// TODO Auto-generated method stub

	}

	@Override
	public String getCapability() 
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isLoggedIn(String userName) 
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public UserObject login(String userName, String password)
			throws LoginException 
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void logout(String userName) 
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void removeParticipant(String sID, String participant)
			throws PartyNotFoundException 
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void restartService()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isSessionCreated(String sID) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void disableMedium(String connectionID, String mediumName) throws PartyNotFoundException, NoSessionException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enableMedium(String connectionID, String mediumName) throws PartyNotAddedException, NoSessionException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean hasMediumFailed(String sessID, String medium_type) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void enableMediumReceiver(String connectionID, String mediumName) throws PartyNotAddedException, NoSessionException {
		// TODO Auto-generated method stub
		
	}

}
