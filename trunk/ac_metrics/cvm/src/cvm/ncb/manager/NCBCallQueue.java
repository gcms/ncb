package cvm.ncb.manager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.LinkedList;
public class NCBCallQueue{
    private static Log log = LogFactory.getLog(NCBCallQueue.class);


	private static NCBCallQueue instance = null;
	private LinkedList<NCBCall> m_callList = null;
	/**
	 * Singleton implementation.
	 * @return instance of NCBCallQueue
	 */
	public static NCBCallQueue getInstance()
	{
		if(instance==null)
			instance = new NCBCallQueue();
		
		return instance;
	}

	private NCBCallQueue() {
		m_callList = new LinkedList<NCBCall>();
	}
	
/*	public void add(String sessID, int count, String command){
		m_callList.add(new NCBCall(sessID, count, command));
	}
*/	
	public void add(String sessID, int count, String command, String medium, Object[] params){
        NCBCall call = new NCBCall(sessID, count, command, medium, params);
		m_callList.add(call);

        log.debug("Call added to the queue: " + call);
	}

	public NCBCall next(){
		return m_callList.removeFirst();
	}
	
	public boolean hasNext(){
		return !m_callList.isEmpty();
	}
	
}
