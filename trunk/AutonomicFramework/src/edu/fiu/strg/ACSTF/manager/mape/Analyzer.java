package edu.fiu.strg.ACSTF.manager.mape;

import edu.fiu.strg.ACSTF.manager.knowledge.ChangeRequest;
import edu.fiu.strg.ACSTF.manager.knowledge.KnowledgeInterface;
import edu.fiu.strg.ACSTF.manager.knowledge.Symptom;

public class Analyzer<Touchpoint> extends AbstractFunction<Touchpoint> {

	public Analyzer(ThreadGroup mfGroup, String mfName, KnowledgeInterface<Touchpoint> knowledge) {
		super(mfGroup, mfName + " Analyzer", knowledge);
	}

	@Override	
	public void doFunction() {
  		  while(this.knowledge.hasDataLock());  // wait until data updated  
  		  this.knowledge.lockData();  
  		  this.knowledge.getTouchData(); 
  		  this.knowledge.unlockData();
  		  for (Symptom s : this.knowledge.getSymptoms().values()) {
  			  if (s.isSatisfied(this.knowledge.getTouchData())) {
  				  s.disable();
  				  this.knowledge.enqueueChangeRequest(new ChangeRequest(s.getId()));
  				  System.out.println("Symptom Recognized: " + s.getId());
  			  }
  		  }
	}			
}
