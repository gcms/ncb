package cvm.ncb;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


import cvm.ncb.handlers.NCBEventObjectManager;
import cvm.ncb.handlers.exception.PartyNotAddedException;
import cvm.model.*;

/**
 * This is the NCB stub class.
 * @author Frank HErnandez
 *
 */
public class NetworkCommunicationBroker {
	private boolean sessionCreated;
	private Map<String,String> ncbConnMap;
	private Map<String, String>ncbPartyMap;
	private NCBEventObjectManager ncbNotifier;
	//SkypeAdapter testKA;
	private static NetworkCommunicationBroker instance;
	
	private NetworkCommunicationBroker()
	{
		ncbConnMap = new HashMap<String, String>();
		ncbPartyMap = new HashMap<String,String>();
		ncbNotifier = NCBEventObjectManager.Instance();
		//testKA = new SkypeAdapter();
		
	}

	/**
	 * This method returns whether the session was created or not.
	 * @return is session created.
	 */
	public boolean isCreatedSession()
	{
		CVM_Debug.getInstance().printDebugMessage("NCB isCreatedSession called.");
		return sessionCreated;
	}
	
	/**
	 * This method maps the ConnectionIds to the SessionIds.
	 * @param connID
	 * @param sID
	 */
	public void mapConnToSession(String connID, String sID)
	{
		CVM_Debug.getInstance().printDebugMessage("NCB connectionMapped.");
		ncbConnMap.put(connID, sID);
	}
	/**
	 * This method will return the sessionId for the given connection.
	 * @param connID
	 * @return session ID
	 */
	public String getSessionID(String connID)
	{
		CVM_Debug.getInstance().printDebugMessage("NCB getSession called with connID:\""+connID+"\".");
		return ncbConnMap.get(connID);
	}
	
	/**
	 * This is the Instnace implementation as in the 
	 * singleton pattern. 
	 * @return
	 */
	public static NetworkCommunicationBroker Instance()
	{
		if(instance==null)
			instance = new NetworkCommunicationBroker();
		
		return instance;
	}
	
	/**
	 * This method will attempt login the given user.
	 * @param userName
	 * @param password
	 * @return
	 */
	public UserObject login(String userName, String password)
	{
		UserObject usr = new UserObject(userName, password);
		CVM_Debug.getInstance().printDebugMessage("NCB Login called with userName:\""+userName+"\" and Password:\""+password+"\".");
		return usr;
	}
	
	/**
	 * This method creates the given session.
	 * @param sID
	 */
	public void createSession(String sID)
	{
		//testKA.createSession(sID);
		sessionCreated = true;
		CVM_Debug.getInstance().printDebugMessage("NCB createSession called with sID:"+sID);
	}

	/**
	 * This method returns the schemas for the given user.
	 * @param userName
	 * @param password
	 * @return
	 */
	public UserSchema retrieveSchemas(String userName, String password)
	{
		CVM_Debug.getInstance().printDebugMessage("NCB retrieve schema called with userName:\""+userName+"\" and Password:\""+password+"\".");
		return new UserSchema();
	}
	
	/**
	 * This method signals whether the sessin is created or not.
	 * @return
	 */
	public boolean createdSession()
	{
		CVM_Debug.getInstance().printDebugMessage("NCB createSession called.");
		sessionCreated = true;
		return sessionCreated;
	}
	
	/**
	 * This method generates a user profile for the give
	 * user.
	 * @param usr
	 * @param schema
	 * @return
	 */
	public UserProfile createUserProfile(UserObject usr, Object schema)
	{
		CVM_Debug.getInstance().printDebugMessage("NCB createUserProfile called.");
		return new UserProfile(usr, schema);
	}

	/**
	 * This method adds a participant. 
	 * to the given session.
	 * @param sID
	 * @param participant
	 * @return
	 */
	public boolean addParty(String sID, String participant)
	{
		//Test of the skype adapter.
		/*
		try {
			testKA.addParticipant(sID, participant);
		} catch (PartyNotAddedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		if(participant!=null && sID !=null)
		{
			//if(participant != null)
			//{
			//	String party = participant.replace(",", " ");
			//	Scanner scan = new Scanner (party);
		    //	while(scan.hasNext())
		    //	{
		    //		String p = scan.next();
		    		ncbPartyMap.put(participant, sID);
		    //	}
			//}
		}
		CVM_Debug.getInstance().printDebugMessage("NCB addParty called with sID:\""+sID+"\" and Participants:\""+participant+"\".");
	 return true;
	}

	/**
	 * This method adds the list of participants 
	 * to the given session. RIGHT WAY
	 * 
	 * @param sID
	 * @param participant
	 * @return
	 */
	public String removeParty(String sID, String participant)
	{
		boolean hasParticipant = false;
		String notAdded = null;
		if(participant!=null && sID !=null)
		{
			if(participant != null)
			{
				participant = participant.replace(",", " ");
				Scanner scan = new Scanner (participant);
		    	while(scan.hasNext())
		    	{
		    		String party = scan.next();
					hasParticipant = ncbPartyMap.containsKey(party);
					if(!hasParticipant)
					{
						notAdded = notAdded==null ? party:  notAdded+" "+party;
					}
					else
						ncbPartyMap.remove(participant);
		    	}
			}
		}
		CVM_Debug.getInstance().printDebugMessage("NCB removeParty called with sID:\""+sID+"\" and Participants:\""+participant+"\".");
		if(notAdded != null)
			notAdded = notAdded.replace(" ", ",");	
		
		return notAdded;
	}
	
	/***
	 * This methods send a schema to the given user in the
	 * given session.
	 * 
	 * @param sID
	 * @param senderID
	 * @param listReceiver
	 * @param control_xcml
	 * @param data_xcml
	 * @return
	 */
	public boolean sendSchema(String sID, String senderID, String listReceiver, String control_xcml,String data_xcml )
	{
	 CVM_Debug.getInstance().printDebugMessage("NCB sendSchema called with sID:\""+sID+"\" and senderID:\""+senderID+"\" and Control_XCML:\""+control_xcml+"\" and Data_XCML:\""+data_xcml+"\".");
	 return true;
	}

	/**
	 * This method will send the schema to all participants 
	 * in the specified connection.
	 * @param sID
	 * @param senderID
	 * @param listReceiver
	 * @param control_xcml
	 * @return
	 */
	public boolean sendSchema(String sID, String senderID, String listReceiver, Object control_xcml)
	{
	 CVM_Debug.getInstance().printDebugMessage("NCB sendSchema called with sID:\""+sID+"\" and senderID:\""+senderID+"\" for receiversIDs:\""+listReceiver+"\".");
	 return true;
	}
	/**
	 * This command will send the specified medium to all 
	 * the participants during the connection.
	 * @param sID
	 * @param medium
	 * @param mediumURL
	 * @return
	 */
	public boolean sendMedia(String sID, String medium, String mediumURL)
	{
	 CVM_Debug.getInstance().printDebugMessage("NCB sendMedia called with sID:\""+sID+"\" and Medium:\""+medium+"\" and MediumURL:\""+mediumURL+"\".");
	 return true;
	}

	/**
	 * Saves teh given schema.
	 * @param schema
	 */
	public boolean saveSchema(Object schema)
	{
	 CVM_Debug.getInstance().printDebugMessage("NCB saveSchema called.");
	 return true;
	}

	/**
	 * Logs the user out.
	 * @param userName
	 */
	public void logout(String userName)
	{
	 CVM_Debug.getInstance().printDebugMessage("NCB logout called with userName:\""+userName+"\".");
	}
	
	/**
	 * Resets the ncb instnace.
	 *
	 */
	public void resetNCB()
	{
		ncbConnMap = new HashMap<String, String>();
		ncbPartyMap = new HashMap<String,String>();
		ncbNotifier = NCBEventObjectManager.Instance();
	}


}
