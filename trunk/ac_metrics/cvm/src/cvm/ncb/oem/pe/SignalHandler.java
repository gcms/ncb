package cvm.ncb.oem.pe;

public interface SignalHandler {
    boolean canHandle(Call call);
    void handle(Call call, PolicyEvalManager pem);
}
