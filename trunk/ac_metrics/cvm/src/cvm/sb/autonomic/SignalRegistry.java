package cvm.sb.autonomic;

import com.sun.xml.internal.bind.v2.util.QNameMap;
import cvm.sb.expression.SignalLogger;
import cvm.sb.manager.SignalInstance;

import java.util.*;

public class SignalRegistry implements SignalLogger {
    private Map<String, Set<SignalEntry>> registry = new LinkedHashMap<String, Set<SignalEntry>>();

    private static class SignalEntry {
        private String name;
        private Object source;
        private SignalInstance signal;

        public SignalEntry(SignalInstance signal) {
            name = signal.getName();
            source = signal.getSource();
            this.signal = signal;
        }

        public int hashCode() {
            return name.hashCode() + (source == null ? 0 : source.hashCode());
        }

        public boolean equals(Object o) {
            SignalEntry e = (SignalEntry) o;

            return name.equals(e.name)
                    && (source == e.source || source != null && source.equals(e.source));
        }

        public SignalInstance getSignal() {
            return signal;
        }
    }

    public Collection<SignalInstance> getSignalInstances(String name) {
        Collection<SignalInstance> result = new LinkedList<SignalInstance>();
        for (SignalEntry e : getSignalEntries(name)) {
            result.add(e.getSignal());
        }
        return result;
    }

    private Set<SignalEntry> getSignalEntries(String name) {
        Set<SignalEntry> entries = registry.get(name);
        if (entries == null) {
            entries = new LinkedHashSet<SignalEntry>();
            registry.put(name, entries);
        }        
        return entries;
    }

    public void registerSignal(SignalInstance signal) {
        getSignalEntries(signal.getName()).add(new SignalEntry(signal));
    }
}
