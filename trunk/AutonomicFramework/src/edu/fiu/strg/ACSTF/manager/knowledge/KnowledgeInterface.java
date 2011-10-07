package edu.fiu.strg.ACSTF.manager.knowledge;

import java.util.Map;

public interface KnowledgeInterface<Touchpoint> {
	
	// Function Locking
	public void stop();
	public void start();
	public void suspend();	
	public void resume();
    public boolean isRunning();
    public boolean isSuspended();
    
    // Data Access
    public Touchpoint getTouchData();                 
    public void setTouchData(Touchpoint touchData);
    
    // Data Locking
    public boolean hasDataLock();
    public void lockData();
    public void unlockData();
    
    // Symptom Access
    public Map<String, Symptom> getSymptoms();
    public void setSymptoms(Map<String, Symptom> symptomsMap);
    
    // Change Plan Access
    public Map<String, ChangePlan> getPlans();
    public void setPlans(Map<String, ChangePlan> plansMap);
    
    // Queue Manipulation
    public boolean enqueueChangeRequest(ChangeRequest changeRequest);
    public ChangeRequest dequeueChangeRequest();
    public boolean enqueueChangePlan(ChangePlan changePlan);
    public ChangePlan dequeueChangePlan();
 

    

   
}
