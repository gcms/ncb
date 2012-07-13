package cvm.sb.manager;

public interface SignalHandler {
    HandlingResult handle(SignalInstance signal, ManagerContext ctx);
}
