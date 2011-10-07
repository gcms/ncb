package edu.fiu.strg.ACSTF.manager.mape;



public class MAPEGroup<Touchpoint> extends ThreadGroup {
	
	    private final static int MaxFunctions = 4;
	    
	    private final static int MonitorIndex = 0;
	    private final static int AnalyzeIndex = 1;
	    private final static int PlanIndex = 2;
	    private final static int ExecuteIndex = 3;
	    
	    private Thread[] functionList;
	    
	    public MAPEGroup(String name) {
	      super(name + " MAPEGroup");
	      this.functionList = new Thread[MaxFunctions];
	    }
	    
	    @SuppressWarnings("unchecked")
		public Monitor<Touchpoint> getMonitor() {
	    	return (Monitor<Touchpoint>) this.functionList[MonitorIndex];
	    }
	    
	    @SuppressWarnings("unchecked")
		public Analyzer<Touchpoint> getAnalyze() {
	    	return (Analyzer<Touchpoint>) this.functionList[AnalyzeIndex];
	    }
	    
	    @SuppressWarnings("unchecked")
		public Planner<Touchpoint> getPlan() {
	    	return (Planner<Touchpoint>) this.functionList[PlanIndex];
	    }
	    
	    @SuppressWarnings("unchecked")
		public Executer<Touchpoint> getExecute() {
	    	return (Executer<Touchpoint>)this.functionList[ExecuteIndex];
	    }
	    
	    public void setMonitor(Monitor<Touchpoint> m) {
	    	this.functionList[MonitorIndex] = m;
	    }
	    
	    public void setAnalyze(Analyzer<Touchpoint> a) {
	    	this.functionList[AnalyzeIndex] = a;
	    }
	    
	    public void setPlan(Planner<Touchpoint> p) {
	    	this.functionList[PlanIndex] = p;
	    }
	    
	    public void setExecute(Executer<Touchpoint> e) {
	    	this.functionList[ExecuteIndex] = e;
	    }
	       
	    public void start() {
            for (int i=0; i < MaxFunctions; i++) {
            	this.functionList[i].start();
            }
	    }
	    
	    @SuppressWarnings("unchecked")
		public boolean hasAllSuspended() {
	    	boolean result = true;
	    	for (int i=0; i<MaxFunctions; i++) {
	    		result = result && ((AbstractFunction<Touchpoint>)this.functionList[i]).isSuspended();
	    	}
	    	return result;
	    }
	    
	    public void joinAll() {
            for (int i=0; i < MaxFunctions; i++) {
            	try {
					this.functionList[i].join();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
	    }
}
