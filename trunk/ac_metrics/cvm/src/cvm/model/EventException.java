package cvm.model;

import cvm.ncb.oem.pe.SignalInstance;

public class EventException extends RuntimeException {
    private SignalInstance event;

    public EventException(SignalInstance event) {
        this.event = event;
    }

    public SignalInstance getEvent() {
        return event;
    }

}
