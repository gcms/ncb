package cvm.ncb.ks;

import cvm.ncb.oem.policy.Framework;
import java.util.HashMap;
import java.util.HashSet;
import cvm.model.*;

public class ConIDCandidateSetMappingTable {
	
	private static ConIDCandidateSetMappingTable instance = null;
	private static HashMap<String, HashSet<Framework>> conIDToCandididateSetMap = null;
	private boolean lock = false;
	
	private ConIDCandidateSetMappingTable()
	{
		conIDToCandididateSetMap = new HashMap<String, HashSet<Framework>>();
		CVM_Debug.getInstance().printDebugMessage("ConIDCandidateSetMappintTable created!");
		
	}
	
	public static ConIDCandidateSetMappingTable getInstance()
	{
		if (instance == null)
			instance = new ConIDCandidateSetMappingTable();
		return instance;
	}
	
	
	public boolean isTableLocked(){
		return lock;
	}

	public void setLockState(boolean state){
		lock = state;
	}
	
	public void setConIDCandidateSetMapping(String cID, HashSet<Framework> hs)
	{
		conIDToCandididateSetMap.put(cID, hs);
	}
	
	public HashSet<Framework> getConIDCandidateSetMapping(String cID)
	{
		if (conIDToCandididateSetMap.containsKey(cID))
			return conIDToCandididateSetMap.get(cID);
		else	
			return null;
	}
	
	
}
