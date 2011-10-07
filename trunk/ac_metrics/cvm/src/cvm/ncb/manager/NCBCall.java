package cvm.ncb.manager;

public class NCBCall implements Comparable<NCBCall>{

	private String sessID =  null;
	private int count = -1;
	private Object[] params = null; 
	private String command = null;
	private String medium = null;
	private enum cmdType {LOGINALL, SENDSCHEMA, DESTROYSESSION, CREATESESSION,
		DISABLE, ENABLE, OTHER};
	private cmdType cType;
	
	public NCBCall(String sID, int cnt, String cmd, String medium, Object[] par){
		sessID = sID;
		count = cnt;
		command = cmd;
		params = par;
		this.medium = medium;
		if(this.getCallName().equalsIgnoreCase("sendSchema")) cType = cmdType.SENDSCHEMA;  
		else if(this.getCallName().equalsIgnoreCase("destroySession")) cType = cmdType.DESTROYSESSION; 
		else if(this.getCallName().equalsIgnoreCase("createSession")) cType = cmdType.CREATESESSION; 
		else if(this.getCallName().startsWith("disable")) cType = cmdType.DISABLE; 
		else if(!this.getCallName().startsWith("enable")) cType = cmdType.ENABLE; 
		else cType = cmdType.OTHER;
	}
		
	public Class [] getParamTypes() {
		Class [] out = new Class[params.length];
		int i=0;
		for (Object o: params ){
			out[i] = o.getClass();
			i++;
		}
		return out;
	}
		
	public String getCallName() {
		return command;
	}
	
	public Object[] getParams(){
		return params;
	}
	
	public String sessionID(){
		return sessID;
	}
	
	public int getCount() {
		return count;
	}
	
	public String getMedium(){
		return medium;
	}

	public int compareTo(NCBCall other) {
		return this.cType.compareTo(other.cType);
	}
	
	public boolean equals(NCBCall other){
		if(this.sessID.equals(other.sessID) &&
				(this.count == other.count) &&
				this.params.equals(other.params) && 
				this.command.equals(other.command) &&
				this.medium.equals(other.medium) )
			return true;
		else return false;

	}
}
