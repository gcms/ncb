package cvm.ncb.ks;

import cvm.model.CVM_Debug;
import cvm.ncb.csm.BridgeCall;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.PriorityBlockingQueue;

public class Connection {
    private String conId;
    private Queue<BridgeCall> conCallList = new PriorityBlockingQueue<BridgeCall>();

    private HashMap<String, String> mediumToComObj = new HashMap<String, String>();
    private HashMap<String, String> oldMediumToComObj = new HashMap<String, String>();

    private CopyOnWriteArraySet<String> partyList = new CopyOnWriteArraySet<String>();
    private String defMedium = null;

    public Connection(String conID, String medium, String comObj) {
        this.conId = conID;
        defMedium = medium;
        mediumToComObj.put(medium, comObj);
    }

    public String getConId() {
        return conId;
    }

    public String getComObj(String medium) {
        CVM_Debug.getInstance().printDebugMessage("Connect Object getter: " + medium);

        if (medium == null || medium.equals(""))
            return mediumToComObj.get(defMedium);

        return mediumToComObj.get(medium);
    }

    public boolean setComObj(String medium, String comObj) {
        CVM_Debug.getInstance().printDebugMessage("Connect Object setter: " + medium + " " + comObj);

        if (medium == null || medium.equals(""))
            return false;

        /*if(defMedium == null)*/
        defMedium = medium;

        String currentCommObj = mediumToComObj.get(medium);
        if (currentCommObj != null && currentCommObj.equalsIgnoreCase(comObj))
            return false;

        oldMediumToComObj.putAll(mediumToComObj);
        mediumToComObj.put(medium, comObj);

        return true;
    }

    public String getPreviousComObj(String medium) {
        return oldMediumToComObj.get(medium);
    }

    public void offerNCBCall(BridgeCall call) {
        if (contains(call))
            return;

        CVM_Debug.getInstance().printDebugMessage("Offering call: " + call.getName() + " currentFw for " + call.getMedium() + ": " + getComObj(call.getMedium()));
        conCallList.offer(call);

        if (call.getName().equalsIgnoreCase("addAParticipant"))
            partyList.add((String) call.getParams()[1]);
        else if (call.getName().equalsIgnoreCase("removeAParticipant"))
            partyList.remove((String) call.getParams()[1]);
    }

    private boolean contains(BridgeCall call) {
        for (BridgeCall bridgeCall : conCallList) {
            if (bridgeCall.equals(call))
                return true;
        }

        return false;
    }

    public BridgeCall pollCall() {
        return conCallList.poll();
    }

    public BridgeCall peekCall() {
        return conCallList.peek();
    }

    public int getNumOfUsers() {
        return partyList.size() + 1;
    }

    public List<String> getActiveMedia() {
        return new ArrayList<String>(mediumToComObj.keySet());
    }

    public List<String> getDeactivatedMedia() {
        return new ArrayList<String>(oldMediumToComObj.keySet());
    }

    public String getDefaultMedium() {
        return defMedium;
    }

    public List<String> getParticipants() {
        return new ArrayList<String>(partyList);
    }

    public boolean containsComObj(String fwName) {
        return mediumToComObj.containsValue(fwName);
    }
}
