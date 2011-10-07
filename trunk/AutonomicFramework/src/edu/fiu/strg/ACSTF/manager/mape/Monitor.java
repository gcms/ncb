package edu.fiu.strg.ACSTF.manager.mape;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import edu.fiu.strg.ACSTF.manager.knowledge.Knowledge;
import edu.fiu.strg.ACSTF.manager.knowledge.KnowledgeInterface;
import edu.fiu.strg.ACSTF.touchpoint.AbstractTouchPoint;


public class Monitor<Touchpoint> extends AbstractFunction<Touchpoint> {
	
	protected AbstractTouchPoint sensorObject;
	protected Method sensorMethod;

	public Monitor()
	{
		super();
	}

	public Monitor(ThreadGroup mfGroup, String mfName, KnowledgeInterface<Touchpoint> knowledge,
			       AbstractTouchPoint tp, String sensorMethodName) {
		
		super(mfGroup, mfName + " Monitor", knowledge);
		try
		{
			sensorObject = tp;
			sensorMethod = sensorObject.getClass().getMethod(sensorMethodName);
		}
		catch (SecurityException e)
		{
			e.printStackTrace();
		}
		catch (NoSuchMethodException e)
		{
			e.printStackTrace();
		}
		catch (IllegalArgumentException e)
		{
			e.printStackTrace();
		}
		this.knowledge = knowledge;
		init();
	}
		
	@SuppressWarnings("unchecked")
	public void init()
	{
		try
		{
			this.knowledge.setTouchData((Touchpoint) sensorMethod
					.invoke(sensorObject));
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
	}
	
	@Override
	public void doFunction() {
		while (this.knowledge.hasDataLock())
			; // wait if data locked
		this.knowledge.lockData();   // acquire lock on data
		init();
		this.knowledge.unlockData(); // release lock on touchData
/*		try {
			Thread.sleep(250);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
*/	}	
}
