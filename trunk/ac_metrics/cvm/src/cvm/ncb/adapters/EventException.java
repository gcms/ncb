package cvm.ncb.adapters;

import java.util.LinkedHashMap;
import java.util.Map;

public class EventException extends RuntimeException {
    private Event event;

    public EventException(Event event) {
        this.event = event;
    }

    public EventException(String name, Map<String, Object> params) {
        this(new Event(name, params));
    }

    public EventException(String name) {
        this(name, new LinkedHashMap<String, Object>());
    }

    public Event getEvent() {
        return event;
    }

}
