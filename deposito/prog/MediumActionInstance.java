public class MediumAction implements MacroActionInstance {
  public Object execute(ManagerContext ctx, Map<String, Object> params) {
    Object session = params.get("session");
    StateHolder connection = ctx.getStateManager()
        .getType("Connection").get(session);
    Resource framework = connection.getResource("framework");
    SignalInstance signal = createSignal(framework, params);
    framework.enqueue(signal);
    return null;
  }
  private SignalInstance createSignal(Resource resource,
        Map<String, Object> params) {
    Object action = params.get("action");
    if (action.equals("enable"))
      return resource.createSignal("EnableMedium", params); 
    if (action.equals("receive"))
      return resource.createSignal("EnableMediumReceiver", params);
    if (action.equals("disable"))
      return resource.createSignal("DisableMedium", params);
    return null;
  }
}

