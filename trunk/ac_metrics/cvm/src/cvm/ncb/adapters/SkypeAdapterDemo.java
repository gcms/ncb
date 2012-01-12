package cvm.ncb.adapters;

import cvm.ncb.handlers.exception.LoginException;
import cvm.ncb.handlers.exception.NoSessionException;
import cvm.ncb.handlers.exception.PartyNotAddedException;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

/**
 * Created by IntelliJ IDEA.
 * User: gustavosousa
 * Date: 04/01/12
 * Time: 15:37
 * To change this template use File | Settings | File Templates.
 */
public class SkypeAdapterDemo {
    public static void main(String args[]) {
        if (args.length == 2) {
            SkypeAdapter sAdpt = new SkypeAdapter();
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
        } else if (args.length == 4) {
            SkypeAdapter sAdpt = new SkypeAdapter();
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
