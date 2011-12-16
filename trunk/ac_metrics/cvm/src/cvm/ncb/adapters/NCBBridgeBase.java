package cvm.ncb.adapters;

import cvm.model.Handles_Event;
import cvm.ncb.handlers.NCBEventObjectManager;
import cvm.ncb.handlers.exception.NoSessionException;
import cvm.ncb.handlers.exception.PartyNotAddedException;
import cvm.ncb.handlers.exception.PartyNotFoundException;
import cvm.ncb.ks.UserIDMappingTable;

/**
 * Target class of the Abstract design pattern.
 */
public abstract class NCBBridgeBase implements NCBBridge {
    private NCBEventObjectManager eventObjectManager;

    protected final NCBUserMapper getUserMapper() {
        return UserIDMappingTable.getInstance();
    }

    protected final NCBUserInfoStore getUserInfoStore() {
        return UserIDMappingTable.getInstance();
    }

    public void setEventObjectManager(NCBEventObjectManager eventObjectManager) {
        this.eventObjectManager = eventObjectManager;
    }

    protected final void notifyEvent(Handles_Event event) {
        eventObjectManager.fireUpEvent(event);
    }

    /**
     * Adds a participant to the session.
     *
     * @param sID           Id of the session to add.
     * @param participantID Id of the participant to add. i.e useranem: crinsomkairos.
     * @throws PartyNotAddedException
     * @throws NoSessionException
     */
    public void addAParticipant(String sID, String participantID) throws PartyNotAddedException, NoSessionException {
        addParticipant(sID, getUserMapper().lookupContact(this.getFWName(), participantID));
    }

    /**
     * Removes a participant to the session.
     *
     * @param sID           Id of the session to remove.
     * @param participantID Id of the participant to remove. i.e useranem: crinsomkairos.
     * @throws PartyNotFoundException
     * @throws NoSessionException
     */
    public void removeAParticipant(String sID, String participant) throws PartyNotFoundException, NoSessionException {
        removeParticipant(sID, getUserMapper().lookupContact(getFWName(), participant));
    }
}