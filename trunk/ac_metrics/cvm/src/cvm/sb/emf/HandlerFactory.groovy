package cvm.sb.emf


import cvm.sb.manager.actions.ActionSignalHandler
import cvm.sb.manager.SignalHandler
import cvm.sb.manager.SignalHandlerManager
import cvm.sb.manager.actions.ActionCaller
import cvm.sb.manager.actions.ActionInstance
import cvm.sb.manager.actions.CallActionInstance
import cvm.sb.manager.actions.SequenceActionInstance
import sb.base.Handler
import sb.base.common.*

class HandlerFactory {
    public SignalHandlerManager createSignalHandlerManager(Collection<Handler> handlers) {
        SignalHandlerManager manager = new SignalHandlerManager()
        handlers.findAll { it.enabled }.each { Handler handler ->
            manager.register(createSignalHandler(handler))
        }

        manager
    }

    public SignalHandler createSignalHandler(Handler handler) {
        ActionCaller action = createActionCaller(handler.action)

        action ? new ActionSignalHandler(handler.signal, action) : null
    }

    private ActionInstance createActionInstance(Action action) {
        null
    }

    private ActionInstance createActionInstance(MacroAction action) {
        Class.forName(action.impl).newInstance()
    }

    private ActionInstance createActionInstance(EnqueueCall action) {
        new CallActionInstance(action)
    }

    private ActionInstance createActionInstance(SequenceAction action) {
        def callers = action.children.collect { ActionExecution execution ->
            createActionCaller(execution)
        }

        new SequenceActionInstance(callers)
    }

    public ActionCaller createActionCaller(ActionExecution action) {
        ActionInstance instance = createActionInstance(action.action)
        instance ? new ActionCaller(action, instance) : null
    }

}
