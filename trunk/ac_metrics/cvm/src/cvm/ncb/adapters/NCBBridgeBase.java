package cvm.ncb.adapters;

import cvm.model.Event;
import cvm.ncb.handlers.exception.NoSessionException;
import cvm.ncb.handlers.exception.PartyNotAddedException;
import cvm.ncb.handlers.exception.PartyNotFoundException;
import cvm.ncb.ks.UserIDMappingTable;

/**
 * Target class of the Abstract design pattern.
 */
public abstract class NCBBridgeBase implements NCBBridge {
    private EventNotifier eventNotifier;

    protected final NCBUserMapper getUserMapper() {
        return UserIDMappingTable.getInstance();
    }

    protected final NCBUserInfoStore getUserInfoStore() {
        return UserIDMappingTable.getInstance();
    }

    public void setEventNotifier(EventNotifier eventNotifier) {
        this.eventNotifier = eventNotifier;
    }

    public void notify(Event event) {
        eventNotifier.notify(event);
    }


    /**
     * Adds a participant to the session.
     *
     * @param session Id of the session to add.
     * @param participant Id of the participant to add. i.e useranem: crinsomkairos.
     * @throws PartyNotAddedException
     * @throws NoSessionException
     */
    public void addAParticipant(String session, String participant) throws PartyNotAddedException, NoSessionException {
        addParticipant(session, getUserMapper().lookupContact(this.getName(), participant));
    }

    /**
     * Removes a participant to the session.
     *
     * @param session           Id of the session to remove.
     * @param participantID Id of the participant to remove. i.e useranem: crinsomkairos.
     * @throws PartyNotFoundException
     * @throws NoSessionException
     */
    public void removeAParticipant(String session, String participant) throws PartyNotFoundException, NoSessionException {
        removeParticipant(session, getUserMapper().lookupContact(getName(), participant));
    }
}