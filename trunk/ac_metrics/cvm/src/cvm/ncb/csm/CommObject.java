package cvm.ncb.csm;

import cvm.ncb.adapters.NCBBridge;

/**
 * Communication object used by CSM
 * @author Frank Hernandez
 *
 */
public class CommObject 
{
	private String name;
	private NCBBridge m_ncbBridge = null;
	
	public CommObject()
	{}
	public CommObject(NCBBridge bridge)
	{
		setBridge(bridge);
		this.name = bridge.getFWName();
	}
	/**
	 * Sets the bride instance.
	 * @param bridge
	 */
	public void setBridge(NCBBridge bridge)
	{
		m_ncbBridge = bridge;
	}
	/**
	 * Gets the bridge instance.
	 * @return
	 */
	public NCBBridge getNCBBridge()
	{
		return m_ncbBridge;
	}
	
	public String getName() {
		return name;
	}
}
