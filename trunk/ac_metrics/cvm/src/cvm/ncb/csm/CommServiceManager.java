package cvm.ncb.csm;

import cvm.ncb.handlers.NCBExceptionHandler;
import cvm.ncb.ks.Connection;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

/**
 * Manages the communication objects of NCB.
 *
 * @author Frank Hernandez
 */
public class CommServiceManager {
    private static Log log = LogFactory.getLog(CommServiceManager.class);

    private CommObjectManager m_comCMan;

    public CommServiceManager(NCBExceptionHandler exceptionHandler) {
        log.info("Creating CommServiceManager instance");
        m_comCMan = new CommObjectManager(exceptionHandler.getObjectManager());
    }

    /**
     * This function returns the list of communication objects.
     *
     * @return
     */
    public ArrayList<CommObject> getCommObjectList() {
        return m_comCMan.getCommObjectList();
    }

    public void executeCall(Connection con, BridgeCall call) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        log.debug("Calling: " + call + " on " + con.getConId());

        String medium = call.getMedium();
        String commObjectName = call.getCommandType() == BridgeCall.CommandType.DESTROYSESSION ?
                con.getPreviousComObj(medium) :
                con.getComObj(medium);

        CommObject commObject = m_comCMan.getCommunicationObject(commObjectName);
        commObject.execute(call);
    }
}