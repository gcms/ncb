package edu.fiu.strg.ACSTF.manager.knowledge;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Knowledge<Touchpoint> implements KnowledgeInterface<Touchpoint> {

	private Touchpoint touchData;  // Data passed through sensor and effector
	
	// Locking Variables
	private volatile boolean running;
	private volatile boolean suspended;
	private volatile boolean dataLocked;
	
	// Symptoms and Change Plans
	private Map<String, Symptom> symptomsMap;
	private Map<String, ChangePlan> plansMap;

	// Queues
	private BlockingQueue<ChangeRequest> requestQ;
	private BlockingQueue<ChangePlan> planQ;
	
	private boolean debugFlag = true;
	
	public Knowledge() {
	  super();
	  this.running = true;
	  this.suspended = false;
	  this.dataLocked = false;
	  this.symptomsMap = new HashMap<String, Symptom>();
	  this.plansMap = new HashMap<String, ChangePlan>();
	  this.requestQ = new LinkedBlockingQueue<ChangeRequest>();
	  this.planQ = new LinkedBlockingQueue<ChangePlan>();
	}
	
	public void start() {
		this.running = true;
	}
	
	public void suspend() {
		suspended = true;
	}
	
	public void stop() {
		this.suspended = false;
		this.running = false;
	}

	public void resume() {
	  this.suspended=false; 	
	}
	
	public boolean isRunning() {
		return running;
	}
	
	public boolean isSuspended() {
		return this.suspended;
	}
  

	public Touchpoint getTouchData() {
    //      if (debugFlag2) { System.out.println(Thread.currentThread().getName() + " reads Touchpoint Data"); }
	      return this.touchData;
	}
	
    public void setTouchData(Touchpoint touchData) {
	    this.touchData=touchData;
  //  	if (debugFlag2) { System.out.println(Thread.currentThread().getName() + " writes Touchpoint Data"); }
	}
	  
      public boolean enqueueChangeRequest(ChangeRequest changeRequest) {   
    	try {
			this.requestQ.put(changeRequest);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if (debugFlag) { System.out.println(Thread.currentThread().getName() + ": New symptom recognized, queuing [ChangeRequest" 
			                	             + changeRequest.getId() + "]."); 
			    
	    }
		return true;
	  }

    public ChangeRequest dequeueChangeRequest() {
    	ChangeRequest readReq = null;
		try {
			readReq = this.requestQ.take();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        if (debugFlag) { System.out.println(Thread.currentThread().getName() + " : Dequeued [ChangeRequest" 
                                           + readReq.getId() + "], formulating change plan."); }
        return readReq;
      }
    
    
    public boolean enqueueChangePlan(ChangePlan changePlan) {   
    	try {
			this.planQ.put(changePlan);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		  if (debugFlag) { System.out.println(Thread.currentThread().getName() + " : Finalized [ChangePlan " 
                  + changePlan.getId() + "], queuing it for execution."); 
			    
	    }
		return true;
	  }
   
    public ChangePlan dequeueChangePlan() {
    	ChangePlan readPlan = null;
		try {
			readPlan = this.planQ.take();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        if (debugFlag) { System.out.println(Thread.currentThread().getName() + " : Dequeued [ChangeRequest" 
                                           + readPlan.getId() + "], formulating change plan."); }
        return readPlan;
      }
    
  

	public boolean hasDataLock() {
		return this.dataLocked;
	}


	public void lockData() {
		this.dataLocked = true;
		
	}


	public void unlockData() {
		this.dataLocked=false;
	}

	public Map<String, Symptom> getSymptoms() {
		return symptomsMap;
	}

	public void setSymptoms(Map<String, Symptom> symptomsMap) {
		this.symptomsMap = symptomsMap;
	}

	public Map<String, ChangePlan> getPlans() {
		return plansMap;
	}

	public void setPlans(Map<String, ChangePlan> plansMap) {
		this.plansMap = plansMap;
	}
	  
	
}
