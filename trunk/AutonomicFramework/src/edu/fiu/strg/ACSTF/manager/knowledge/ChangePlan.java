package edu.fiu.strg.ACSTF.manager.knowledge;

import java.util.ArrayList;

/**
 * @author A.C.I.D
 *
 * @param <T> Class representing touchpoint for managed resource
 * 
 */

public class ChangePlan
{
   private String id;
   private ArrayList<Action> actions;

    public ChangePlan() {
    	super();
    }
    
    public ChangePlan(ChangePlan cp) {
    	this.id=cp.getId();
    	this.actions = new ArrayList<Action>();
    	this.actions.addAll(cp.getActions());
    }
    
	public ChangePlan(String id, ArrayList<Action> actions)
	{
		super();
		this.id = id;
		this.actions = actions;
	}

	public String getId()
	{
		return id;
	}
	
	public void setId(String id)
	{
		this.id = id;
	}
	   
	public ArrayList<Action> getActions()
	{
		return actions;
	}

	public void setActions(ArrayList<Action> actions)
	{
		this.actions = actions;
	}
	
	public boolean equals(ChangePlan changePlan) {
		if (this.id==changePlan.getId()) return true;
		else return false;
	}
   
	
}
