public class SmackAdapter implements Manageable {
    private EventNotifier eventNotifier;
    public void setEventNotifier(EventNotifier eventNotifier) {
        this.eventNotifier = eventNotifier;
    }

    @Call(name = "sendSchema", parameters = {"schema", "participant"})
    public void sendSchema(String schema, String participant) {
        if (!isOnline(participant))
            return;
        try {
            sendStringAsFile(participant, "schema", schema);
        } catch (XMPPException e) {
            Event event = new Event("SchemaFailed");
            event.setParam("receiver", participant);
            event.setParam("schema", schema);
            throw new EventException(event);
        }
    }
    
    /* ... */
    private void dealWithSchema(String participant, String schema) {
        Event event = new Event("SchemaReceived");
        event.setParam("sender", participant);
        event.setParam("schema", schema);
        eventNotifier.notify(event);
    }
    /* ... */
}

