package cvm.ncb.tpm;

import java.net.URI;
import java.net.URL;
import java.util.Set;

import cvm.ncb.ks.ConIDMappingTable;
import cvm.ncb.ks.Connection;
import edu.fiu.strg.ACSTF.manager.GenericManager;
import edu.fiu.strg.ACSTF.resource.AbstractResource;

public class CommTPManager<Touchpoint> extends GenericManager {

	private ConIDMappingTable m_conToFwTable = null;

	
	public CommTPManager(String name, URI policyFileName, AbstractResource aResource) {
		super(name, policyFileName, aResource);
		m_conToFwTable = ConIDMappingTable.getInstance();
	}
	
	public boolean updateConTable(String conID){
		boolean result;
		m_conToFwTable.setLockState(true);
		result = m_conToFwTable.setMapping(conID);
		m_conToFwTable.setLockState(false);
		return result;
	}

	public boolean updateConTable(String conID, String medium, String fwName){
		boolean result;
		m_conToFwTable.setLockState(true);
		result = m_conToFwTable.setMapping(conID,medium,fwName);
		m_conToFwTable.setLockState(false);
		return result;
	}
	
	public Connection getConnection(String conID){
		return m_conToFwTable.getConnection(conID);
	}

	public boolean removeConnection(String conID){
		return m_conToFwTable.remove(conID);
	}

	public boolean hasConnection(String conID){
		return m_conToFwTable.hasConnection(conID);
	}
	
	public Set<String> getResetFWTableIterator() {
		return ((CommFWTouch)this.touchpoint).getResetFWTable();
	}

	public boolean removeFWFromResetFWTable(String fwName) {
		return ((CommFWTouch)this.touchpoint).getResetFWTable().remove(fwName);
	}

}
