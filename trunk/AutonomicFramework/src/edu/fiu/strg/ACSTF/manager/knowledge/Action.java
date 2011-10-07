package edu.fiu.strg.ACSTF.manager.knowledge;
import java.util.ArrayList;

/**
 * @author A.C.I.D
 *
 * @param <T> Class representing touchpoint for managed resource
 * 
 */

public class Action
{
    private String effector;
    private ArrayList<Parameter> parameters;
    
	public Action(String effector, ArrayList<Parameter> parameters)
	{
		super();
		this.effector = effector;
		this.parameters = parameters;
	}

	public String getEffector()
	{
		return effector;
	}

	public void setEffector(String effector)
	{
		this.effector = effector;
	}

	public ArrayList<Parameter> getParameters()
	{
		return parameters;
	}

	public void setParameters(ArrayList<Parameter> parameters)
	{
		this.parameters = parameters;
	}
    
    




  
}
