package cvm.ncb.adapters;

import cvm.service.UserIDMappingTable;


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
    @Method(name = "addAParticipant", parameters = { "session", "participant"})
    public void addAParticipant(String session, String participant) {
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
    public void removeAParticipant(String session, String participant) {
        removeParticipant(session, getUserMapper().lookupContact(getName(), participant));
    }
}