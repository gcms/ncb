package cvm.sb.emf

import cvm.ncb.csm.ContextProviderWrapper
import cvm.ncb.ks.StateHolder
import cvm.ncb.ks.StateTypeManager
import cvm.ncb.oem.pe.ContextProvider
import sb.base.*

class ValueEvaluator {
    // TODO: remove & fix this hack
    public static cvm.ncb.ks.StateManager stateManager

    public static Map<String, Object> getParams(ContextProvider context, Collection<ParameterBinding> bindings) {
        Map<String, Object> params = new LinkedHashMap<String, Object>();
        for (ParameterBinding binding: bindings) {
            String name = binding.getParameter().getName();
            Object value = ValueEvaluator.getValue(context, binding.getValue());
            params.put(name, value);
        }

        return params;
    }

    public static Object getValue(ContextProvider context, Value value) {
        evaluate(context, value)
    }

    private static Object evaluate(ContextProvider context, Value value) {
        null
    }

    private static Object evaluate(ContextProvider context, ParameterValue value) {
        context.getParams().get(value.parameter.name)
    }

    private static Object evaluate(ContextProvider context, SignalSource value) {
        context.getParams().get("source")
    }

    private static Object evaluate(ContextProvider context, ExpressionValue value) {
        Map params = context.params + [self: context.self]
        stateManager.types.each { StateTypeManager typeManager ->
            params[typeManager.name] = new StateTypeManagerContext(typeManager)
        }

        new GroovyShell(new groovy.lang.Binding(ContextProviderWrapper.wrap(params))).evaluate(value.value)
    }
}

class StateTypeManagerContext extends AbstractMap {
    private StateTypeManager typeManager

    public StateTypeManagerContext(StateTypeManager typeManager) {
        this.typeManager = typeManager
    }

    Set entrySet() {
        typeManager.all.collect { StateHolder holder ->
            new MapEntry(holder.id, holder)
        }
    }


}