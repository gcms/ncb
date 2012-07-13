package cvm.sb.manager;

import java.util.Map;

public abstract class ManagerFacade {
    private MainManager manager;

    public ManagerFacade(MainManager manager) {
        this.manager = manager;
        manager.start();
    }

    public MainManager getManager() {
        return manager;
    }

    protected void enqueue(String name, Map<String, Object> params) {
        getManager().enqueue(null, name, params);
    }

    protected void enqueue(String name, Object... params) {
        getManager().enqueue(null, name, params);
    }
}
