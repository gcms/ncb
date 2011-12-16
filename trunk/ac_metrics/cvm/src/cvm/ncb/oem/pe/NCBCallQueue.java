package cvm.ncb.oem.pe;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.LinkedList;
import java.util.Queue;

public class NCBCallQueue{
    private static Log log = LogFactory.getLog(NCBCallQueue.class);

	private Queue<NCBCall> m_callList = null;

	NCBCallQueue() {
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
		return m_callList.poll();
	}
	
	public boolean hasNext(){
		return !m_callList.isEmpty();
	}
	
}
