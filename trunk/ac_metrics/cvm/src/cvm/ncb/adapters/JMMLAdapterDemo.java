package cvm.ncb.adapters;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

public class JMMLAdapterDemo {
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
            	sAdpt.login();
			} catch (EventException e) {
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
				sAdpt.login();
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
}
