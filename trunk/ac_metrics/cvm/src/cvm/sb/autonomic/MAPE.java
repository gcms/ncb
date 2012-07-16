package cvm.sb.autonomic;


import cvm.sb.manager.ManagerContext;
import sb.base.AutonomicManager;

public class MAPE {
    private MonitorImpl monitor;
    private AnalyzerImpl analyzer;
    private PlannerImpl planner;
    private ExecutorImpl executor;

    public MAPE(AutonomicManager am, ManagerContext context) {
        monitor = new MonitorImpl(am.getIdentifies());
        analyzer = new AnalyzerImpl(am.getRequests());
        planner = new PlannerImpl(am.getPlans());
        executor = new ExecutorImpl();

        monitor.setAnalyzer(analyzer);
        analyzer.setPlanner(planner);
        planner.setExecutor(executor);

        setContext(context);
    }

    public void setContext(ManagerContext context) {
        monitor.setContext(context);
        executor.setContext(context);
    }

    public MonitorImpl getMonitor() {
        return monitor;
    }
}
