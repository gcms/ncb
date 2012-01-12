package cvm.ncb.oem.pe;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class CallQueue {
    private static Log log = LogFactory.getLog(CallQueue.class);

    private Queue<Call> m_callList = null;

    CallQueue() {
        m_callList = new LinkedList<Call>();
    }

    public void add(String command, Map<String, Object> params) {
        Call call = new Call(command, params);
        m_callList.add(call);

        log.debug("Call added to the queue: " + call);
    }

    public Call next() {
        return m_callList.poll();
    }

    public boolean hasNext() {
        return !m_callList.isEmpty();
    }
}
