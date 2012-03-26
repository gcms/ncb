package cvm.ncb.adapters;

import java.util.ArrayList;
import java.util.Scanner;

public class SIPAdapterDemo {

    public static void main(String args[]) {
        if (args.length == 2){
            SIPAdapter sAdpt = new SIPAdapter();
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
            } catch (EventException e) {
                e.printStackTrace();
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

}
