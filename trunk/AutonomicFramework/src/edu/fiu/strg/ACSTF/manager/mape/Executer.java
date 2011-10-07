package edu.fiu.strg.ACSTF.manager.mape;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import edu.fiu.strg.ACSTF.manager.knowledge.ChangePlan;
import edu.fiu.strg.ACSTF.manager.knowledge.Action;
import edu.fiu.strg.ACSTF.manager.knowledge.KnowledgeInterface;
import edu.fiu.strg.ACSTF.touchpoint.AbstractTouchPoint;

public class Executer<Touchpoint> extends AbstractFunction<Touchpoint> {
	
	private AbstractTouchPoint effectorObject;
	
	public Executer(ThreadGroup mfGroup, String mfName, KnowledgeInterface<Touchpoint> knowledge, AbstractTouchPoint effectorObject) {
		super(mfGroup, mfName + " Executer", knowledge);
		this.effectorObject = effectorObject;
	}

	public void execute(ChangePlan changePlan) {
		  Class effectorClass=this.effectorObject.getClass();
		  for (Action ac : changePlan.getActions()) {
			  try {
				  Method method = effectorClass.getMethod(ac.getEffector()); 
				  method.invoke(this.effectorObject);
			  }
			  catch (NoSuchMethodException e) {
					System.out.println(e.getMessage());
			  }
			catch (IllegalArgumentException e)
			{
				e.printStackTrace();
			}
			catch (IllegalAccessException e)
			{
				e.printStackTrace();
			}
			catch (InvocationTargetException e)
			{
				e.printStackTrace();
			}
			  
		 }
	}
	
	public void doFunction() {
  		  ChangePlan cp = this.knowledge.dequeueChangePlan();
  		  execute(cp);
  		  this.knowledge.getSymptoms().get(cp.getId()).enable();
  		  System.out.println(this.getName() + ": Successfully implemented [ChangePlan" 
  				             + cp.getId() + "].");	  
	}	

}
