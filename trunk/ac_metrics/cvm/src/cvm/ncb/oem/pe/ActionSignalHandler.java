package cvm.ncb.oem.pe;

import cvm.ncb.oem.pe.actions.ActionInstance;

/**
 * Created by IntelliJ IDEA.
 * User: gustavosousa
 * Date: 17/01/12
 * Time: 17:36
 * To change this template use File | Settings | File Templates.
 */
public class ActionSignalHandler implements SignalHandler {
    private String signal;
    public ActionInstance action;

    public ActionSignalHandler(String signal, ActionInstance action) {
        this.signal = signal;
        this.action = action;
    }

    public boolean canHandle(Call call) {
        return call.getName().equals(signal);
    }

    public void handle(Call call, PolicyEvalManager pem) {
        action.execute(pem, call);
    }
}
