package cvm.sb.manager;

public class HandlingResult {
    private boolean handled;
    private Object result;
    public static final HandlingResult NOT_HANDLED = new HandlingResult();

    public HandlingResult(boolean handled, Object result) {
        this.handled = handled;
        this.result = result;
    }

    public HandlingResult() {
        this.handled = false;
    }

    public HandlingResult(Object result) {
        this(true, result);
    }

    public boolean isHandled() {
        return handled;
    }

    public Object getResult() {
        return result;
    }
}
