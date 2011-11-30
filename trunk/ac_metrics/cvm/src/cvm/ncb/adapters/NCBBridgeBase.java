package cvm.ncb.adapters;

import cvm.ncb.UserObject;
import cvm.ncb.handlers.exception.LoginException;
import cvm.ncb.handlers.exception.NoSessionException;
import cvm.ncb.handlers.exception.PartyNotAddedException;
import cvm.ncb.handlers.exception.PartyNotFoundException;

import cvm.ncb.ks.UserIDMappingTable;

/**
 * Target class of the Abstract design pattern.
 *
 */
public abstract class NCBBridgeBase implements NCBBridge {

	protected String username, password, fwName;


    /**
	 * Adds a participant to the session.
	 * @param sID Id of the session to add.
	 * @param participantID Id of the participant to add. i.e useranem: crinsomkairos. 
	 * @throws PartyNotAddedException 
	 * @throws NoSessionException 
	 */
	public void addAParticipant(String sID, String participantID) throws PartyNotAddedException, NoSessionException {
		addParticipant(sID, UserIDMappingTable.getInstance().lookupContact(this.fwName, participantID));
	}

    /**
	 * Removes a participant to the session.
	 * @param sID Id of the session to remove.
	 * @param participantID Id of the participant to remove. i.e useranem: crinsomkairos. 
	 * @throws PartyNotFoundException 
	 * @throws NoSessionException 
	 */
	public void removeAParticipant(String sID, String participant) throws PartyNotFoundException, NoSessionException{
		removeParticipant(sID, UserIDMappingTable.getInstance().lookupContact(fwName, participant));
	}


    public String getFWName(){
		return fwName;
	}

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}



