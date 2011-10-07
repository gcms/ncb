package cvm.ncb.ks;
 import java.util.HashMap;
 import java.util.ArrayList;
 import java.util.concurrent.CopyOnWriteArraySet;
 import java.util.concurrent.PriorityBlockingQueue;
 import cvm.model.*;
 
 import cvm.ncb.manager.NCBCall;
 
public class Connection implements Comparable<Connection>{
	private String conId;
	private HashMap <String,String>mediumToComObj = null;
	private HashMap <String,String>oldMediumToComObj = null;
	private PriorityBlockingQueue<NCBCall> conCallList = null;
	private CopyOnWriteArraySet<String> partyList = null;
	private String defMedium = null;
	

	public Connection(String conID, String medium, String comObj){
		this.conId = conID;
		this.mediumToComObj = new HashMap<String,String>();
		this.oldMediumToComObj = new HashMap<String,String>();
		this.mediumToComObj.put(medium,comObj);
		conCallList = new PriorityBlockingQueue<NCBCall>();
		defMedium = medium;
		partyList = new CopyOnWriteArraySet<String>();
	}

	public Connection(String conID){
		this.conId = conID;
		this.mediumToComObj = new HashMap<String,String>();
		this.oldMediumToComObj = new HashMap<String,String>();
		conCallList = new PriorityBlockingQueue<NCBCall>();
		partyList = new CopyOnWriteArraySet<String>();
	}
	
	public String getConId(){
		return this.conId;
	}
	
	public String getComObj(String medium){
		CVM_Debug.getInstance().printDebugMessage("Connect Object getter: "+medium);
		if(medium == null || medium.equals("")) return this.mediumToComObj.get(defMedium);
		return this.mediumToComObj.get(medium);
	}
	
	public boolean setComObj(String medium, String comObj){
		CVM_Debug.getInstance().printDebugMessage("Connect Object setter: "+medium+" "+comObj);
		if(medium == null || medium.equals("")) return false;
		/*if(defMedium == null)*/ defMedium = medium;
		if(this.mediumToComObj.get(medium)!= null && 
				this.mediumToComObj.get(medium).equalsIgnoreCase(comObj)) {
			return false;
		}else {
			this.oldMediumToComObj.putAll(this.mediumToComObj);
			this.mediumToComObj.put(medium,comObj);
			//this.oldMediumToComObj.put(medium, this.mediumToComObj.put(medium,comObj));
			CVM_Debug.getInstance().printDebugMessage("Old comobj written as "
					+this.oldMediumToComObj.get(medium));
			CVM_Debug.getInstance().printDebugMessage("Medium mapping changed");
			return true;
		}
	}

	public String getPreviousComObj(String medium){
		return this.oldMediumToComObj.remove(medium);
	}
	
	public int compareTo(Connection other) {
		return this.conId.compareTo(other.getConId());
	}
	
	public void offerNCBCall(NCBCall call){
		if(contains(call)) return;
		conCallList.offer(call);
		if(call.getCallName().equalsIgnoreCase("addAParticipant"))
				partyList.add((String)call.getParams()[1]);
		else if(call.getCallName().equalsIgnoreCase("removeAParticipant"))
			partyList.remove((String)call.getParams()[1]);
	}
	
	private boolean contains(NCBCall call){
		for(NCBCall ncb_call: conCallList){
			if(ncb_call.equals(call)) return true;
		}
		return false;
	}

	public NCBCall pollCall(){
		return conCallList.poll();
	}
	
	public NCBCall peekCall(){
		return conCallList.peek();
	}

	public int getNumOfUsers(){
		return partyList.size() + 1; 
	}
	
	public ArrayList<String> getActiveMedia(){
		return new ArrayList<String>(mediumToComObj.keySet());
	}
	
	public ArrayList<String> getDeactivatedMedia(){
		return new ArrayList<String>(oldMediumToComObj.keySet());
	}

	public String getDefaultMedium(){
		return this.defMedium;
	}

	public ArrayList<String> getParticipants(){
		return new ArrayList<String>(partyList);
	}
	
	public boolean containsComObj(String fwName){
		if(this.mediumToComObj.containsValue(fwName)) return true;
		return false;
	}
	
	private boolean hasAComObj(String medium){
		if(medium == null) return false;
		if(mediumToComObj.get(medium)!=null) return true;
		else return false;
	}
}
