package cvm.ncb.adapters;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

import cvm.ncb.UserObject;
import cvm.ncb.handlers.exception.LoginException;
import cvm.ncb.handlers.exception.NoSessionException;
import cvm.ncb.handlers.exception.PartyNotAddedException;
import cvm.ncb.handlers.exception.PartyNotFoundException;

public class JMMLAdapter extends NCBBridge 
{

	@Override
	public void addParticipant(String sID, String participantID)
			throws PartyNotAddedException 
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void createSession(String sessionID) 
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
	public void sendSchema(String schema, String participantID) {
		// TODO Auto-generated method stub
		
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
	public void destroySession(String sessionID) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enableMediumReceiver(String connectionID, String mediumName) throws PartyNotAddedException, NoSessionException {
		// TODO Auto-generated method stub
		
	}
	
	public static void main(String args[]) {
		/* 
		 *  If we have only 2 arguments then we are setting up the receiver
		 *  first argument is user name
		 *  second argument is password
		 *  example: test@squire.cs.fiu.edu test
		 */
		if (args.length == 2){
        	SmackAdapter sAdpt = new SmackAdapter();
            try {

            	// simulate request to login
            	sAdpt.login(args[0],args[1]); 
			} catch (LoginException e) {
				e.printStackTrace();
			}
            try {
            	// sleep while we setup the other receivers / initiator
            	Thread.sleep(100000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
       }
		/* 
		 *  If we have  4 arguments then we are setting up the initiator
		 *  first argument is user name
		 *  second argument is password
		 *  third argument is the media type to transmit
		 *  fourth argument is a space delimited string of the list of 
		 *  receivers 
		 *  example: test@squire.cs.fiu.edu test AUDIO test1@squire.cs.fiu.edu test2@squire.cs.fiu.edu
		 */
		else if (args.length == 4){
        	SmackAdapter sAdpt = new SmackAdapter();
            try {
				sAdpt.login(args[0],args[1]); 
				String medium = args[2]; 
				UUID conID = UUID.randomUUID();
				// simulates the request to create a new connection
				sAdpt.createSession(conID.toString());
				
				// simulate the request to add parties
				Scanner scan = new Scanner(args[3]);
				ArrayList<String> remoteParties = new ArrayList<String>();
				String pList = "";
				while (scan.hasNext()){
					String party = scan.next();
					remoteParties.add(party);
					pList = party+" "+pList;
				}

				// simulate the request to send schema to parties in connection
				for (String remoteParty: remoteParties){
			        sAdpt.sendSchema(conID.toString()+" "+medium+
	        			" "+args[0]+" "+pList,remoteParty); 
				}
				for (String remoteParty: remoteParties){
					sAdpt.addParticipant(conID.toString(), remoteParty);
				}
				
				// simulate request to start media
				sAdpt.enableMedium(conID.toString(), medium);
			} catch (LoginException e) {
				e.printStackTrace();
			} catch (PartyNotAddedException e1) {
				e1.printStackTrace();
			} catch (NoSessionException e1) {
				e1.printStackTrace();
			}
            try {
				Thread.sleep(100000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
       }
    }
}
