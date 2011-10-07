package cvm.ncb.ks;

import java.util.HashSet;
import java.util.Iterator;
import cvm.model.*;

/**
 * @author aalle004
 *
 */
public class ConIDMappingTable {

	private static ConIDMappingTable instance = null;
	private boolean lock = false;
	private HashSet<Connection> conIDs = null;
	
	
	/**
	 *  Singleton Constructor
	 */
	private ConIDMappingTable(){
		conIDs = new HashSet<Connection>();
		CVM_Debug.getInstance().printDebugMessage("New instance of ConID Table");
	}
	
	public static ConIDMappingTable getInstance(){
		if(instance == null) {
			instance = new ConIDMappingTable();
		}
		return instance;
	}
	
	public boolean isTableLocked(){
		return lock;
	}
	
	public boolean hasConnection(String conID){
		for(Connection con: this.conIDs){
			if(con.getConId().equals(conID)){
				return true;
			}
		}
		return false;
	}

	public void setLockState(boolean state){
		lock = state;
	}
	
	public String getMapping(String conID, String medium){
		for(Connection con: this.conIDs){
			if(con.getConId().equals(conID)){
				return con.getComObj(medium);
			}
		}
		return null; 
	}
	
	public boolean setMapping(String conID, String medium, String comObj){
		for(Connection con: this.conIDs){
			if(con.getConId().equals(conID)){
				return con.setComObj(medium, comObj);
			}
		}
		this.conIDs.add(new Connection(conID,medium,comObj));
		return false;
	}
	
	public boolean setMapping(String conID){
		if(conIDs.contains(new Connection(conID))) return false;
		return setMapping(conID, null, null); 
	}

	public Connection getConnection(String conID) {
		for(Connection con: this.conIDs){
			if(con.getConId().equals(conID)){
				return con;
			}
		}
		return null;
	}


	public boolean remove(String conID){
		for(Connection con: this.conIDs){
			if(con.getConId().equals(conID)){
				return conIDs.remove(con);
			}
		}
		return false;
	}
	public Iterator<Connection> getAllConnections() {
		return conIDs.iterator();
	}
}


