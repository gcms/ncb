package cvm.sb.expression

import cvm.sb.state.StateHolder
import cvm.sb.state.StateTypeManager

import sb.base.common.*
import cvm.sb.state.StateManager

class ValueEvaluator {
    // TODO: remove & fix this hack
    public static StateManager stateManager

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
        context.getVariable(value.parameter.name)
    }

    private static Object evaluate(ContextProvider context, SignalSource value) {
        context.getVariable("source")
    }

    private static Object evaluate(ContextProvider context, ExpressionValue value) {
        CombinedContextProvider params = new CombinedContextProvider(context)
        stateManager.types.each { StateTypeManager typeManager ->
            params[typeManager.name] = new StateTypeManagerContext(typeManager)
        }

        new GroovyShell(new ContextProviderBinding(context: params)).evaluate(value.value)
    }
}

class ContextProviderBinding extends groovy.lang.Binding {
  ContextProvider context

  public Object getParam(String name) {
    context.getVariable(name)
  }

  public Object getVariable(String name) {
    ContextProviderWrapper.wrap(getParam(name))
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