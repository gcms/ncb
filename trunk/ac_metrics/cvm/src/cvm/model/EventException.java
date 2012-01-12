package cvm.model;

public class EventException extends RuntimeException {
    private Event event;

    public EventException(Event event) {
        this.event = event;
    }

    public Event getEvent() {
        return event;
    }

}
