package edu.fiu.strg.ACSTF.manager.mape;

import edu.fiu.strg.ACSTF.manager.knowledge.KnowledgeInterface;


public abstract class AbstractFunction<Touchpoint> extends Thread {
	protected boolean suspended;
	protected KnowledgeInterface<Touchpoint> knowledge;
	
	public AbstractFunction(ThreadGroup mfgroup, String mfid, KnowledgeInterface<Touchpoint> knowledge) {
		super(mfgroup, mfid);
		this.suspended = false;
		this.knowledge = knowledge;
	}
	
	public AbstractFunction() {
		// TODO Auto-generated constructor stub
	}

	public abstract void doFunction();
	
	public void run() {
		System.out.println(this.getName() + " is now running...");
		while(knowledge.isRunning()) { 
	           doFunction();
/*	           try {
	        	   Thread.sleep(250);
	           } catch (InterruptedException e) {
	        	   // TODO Auto-generated catch block
	        	   e.printStackTrace();
	           }
*/		       while(knowledge.isSuspended()) {
	        	   // wait if suspended
	        	   this.suspended = true;	   
	           }
	           if (this.suspended) { this.suspended=false; }
		}
		System.out.println(this.getName() + " Stopped");
	}
	
	public boolean isSuspended() {
		return this.suspended;
	}
}

