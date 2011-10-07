package edu.fiu.strg.ACSTF.manager.mape;

import edu.fiu.strg.ACSTF.manager.knowledge.ChangeRequest;
import edu.fiu.strg.ACSTF.manager.knowledge.KnowledgeInterface;


public class Planner<Touchpoint> extends AbstractFunction<Touchpoint> {
	
	public Planner(ThreadGroup mfGroup, String mfName, KnowledgeInterface<Touchpoint> knowledge) {
		super(mfGroup, mfName + " Planner", knowledge);
	}
	
	public void doFunction() {
		  ChangeRequest cr = this.knowledge.dequeueChangeRequest();
		  if (this.knowledge.getPlans().containsKey(cr.getId())) {
		     this.knowledge.enqueueChangePlan(this.knowledge.getPlans().get(cr.getId()));	  
		  }
		  else {
			  System.out.println("Planner: Error Change Plan not Found");
		  } 
	}	
}
