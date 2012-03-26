package cvm.ncb.csm

import cvm.ncb.ks.StateHolder
import cvm.ncb.oem.pe.ContextProvider
import cvm.ncb.oem.pe.SignalInstance

import sb.base.Bindable
import sb.base.Binding
import sb.base.Signal
import sb.base.context.State
import cvm.ncb.oem.pe.actions.ManagerContext

class Binder {
    ManagerContext manager
    SignalLogger signalLogger

    public Binder(ManagerContext manager, SignalLogger signalLogger) {
        this.manager = manager;
        this.signalLogger = signalLogger;
    }

    public Map<String, Collection<ContextProvider>> getBindings(Collection<Binding> bindings) {
        Map<String, Collection<ContextProvider>> context = new LinkedHashMap<String, Collection<ContextProvider>>()

        for (Binding binding: bindings) {
            Collection<ContextProvider> values = getBindable(binding.getBindable())
            context.put(binding.getName(), values)
        }

        return context
    }

    public Collection<ContextProvider> getBindable(Bindable bindable) {
        doGetBindable(bindable)
    }

    private Collection<ContextProvider> doGetBindable(Bindable bindable) {
        []
    }

    private Collection<ContextProvider> doGetBindable(State bindable) {
        Collection<ContextProvider> result = new LinkedHashSet<ContextProvider>();
        for (StateHolder holder: manager.getStateManager().getType(bindable).getAll()) {
            result.add(holder);
        }
        return result;
    }

    private Collection<ContextProvider> doGetBindable(Signal bindable) {
        Collection<ContextProvider> result = new LinkedHashSet<ContextProvider>();
        SignalInstance instance = signalLogger.getSignalInstance(bindable.getName());
        if (instance != null)
            result.add(instance);

        return result;
    }
}
