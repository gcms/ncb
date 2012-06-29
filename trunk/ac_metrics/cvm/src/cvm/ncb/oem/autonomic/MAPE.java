package cvm.ncb.oem.autonomic;


import cvm.ncb.oem.pe.actions.ManagerContext;
import sb.base.AutonomicManager;

public class MAPE {
    private MonitorImpl monitor;
    private AnalyzerImpl analyzer;
    private PlannerImpl planner;

    public MAPE(AutonomicManager am, ManagerContext context) {
        monitor = new MonitorImpl(am.getIdentifies());
        analyzer = new AnalyzerImpl(am.getRequests());
        planner = new PlannerImpl(am.getPlans());

        monitor.setAnalyzer(analyzer);
        analyzer.setPlanner(planner);

        setContext(context);
    }

    public void setContext(ManagerContext context) {
        monitor.setContext(context);
        planner.setContext(context);
    }

    public MonitorImpl getMonitor() {
        return monitor;
    }
}