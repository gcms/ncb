package cvm.ncb.ks;

import cvm.model.CVM_Debug;
import cvm.ncb.csm.BridgeCall;

import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.PriorityBlockingQueue;

public class Connection {
    private String id;
    private Queue<BridgeCall> queue = new PriorityBlockingQueue<BridgeCall>();

    private HashMap<String, String> currentMapping = new LinkedHashMap<String, String>();
    private HashMap<String, String> previousMapping = new LinkedHashMap<String, String>();

    private Set<String> participants = new CopyOnWriteArraySet<String>();
    private String defaultMedium = null;

    public Connection(String conID, String medium, String comObj) {
        this.id = conID;
        defaultMedium = medium;
        currentMapping.put(medium, comObj);
    }

    public String getId() {
        return id;
    }

    public String getFramework(String medium) {
        CVM_Debug.getInstance().printDebugMessage("Connect Object getter: " + medium);

        if (medium == null || medium.equals(""))
            return currentMapping.get(defaultMedium);

        return currentMapping.get(medium);
    }

    public void setFramework(String medium, String framework) {
        assert framework != null;

        defaultMedium = medium;

        String currentFramework = currentMapping.get(medium);
        if (currentFramework == null || !currentFramework.equals(framework)) {
            previousMapping.putAll(currentMapping);
            currentMapping.put(medium, framework);
        }
    }

    public String getPreviousFramework(String medium) {
        return previousMapping.get(medium);
    }

    public void enqueue(BridgeCall call) {
        if (contains(call))
            return;

//        CVM_Debug.getInstance().printDebugMessage("Offering call: " + call.getName() + " currentFw for " + call.getMedium() + ": " + getFramework(call.getMedium()));
        queue.offer(call);
    }

    private boolean contains(BridgeCall call) {
        for (BridgeCall bridgeCall : queue) {
            if (bridgeCall.equals(call))
                return true;
        }

        return false;
    }

    public BridgeCall pollCall() {
        return queue.poll();
    }

    public BridgeCall peekCall() {
        return queue.peek();
    }

    public List<String> getActiveMedia() {
        return new ArrayList<String>(currentMapping.keySet());
    }

    public String getDefaultMedium() {
        return defaultMedium;
    }

    public Collection<String> getParticipants() {
        return participants;
    }

    public boolean containsFramework(String fwName) {
        return currentMapping.containsValue(fwName);
    }

    public String getFramework(BridgeCall call) {
        return call.getCommandType() == BridgeCall.CommandType.DESTROYSESSION ?
                getPreviousFramework(call.getMedium()) :
                getFramework(call.getMedium());
    }
}
