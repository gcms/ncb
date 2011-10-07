package edu.fiu.strg.ACSTF.manager.knowledge;

import java.util.ArrayList;


/**
 * @author A.C.I.D
 *
 */
public class Symptom
{
    private String id;
    private boolean enabled;
    
    private ArrayList<Mapping> mappings;

	public Symptom(String id, ArrayList<Mapping> mappings)
	{
		super();
		this.id = id;
		this.enabled = true;
		this.mappings = mappings;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public ArrayList<Mapping> getMappings()
	{
		return mappings;
	}

	public void setMappings(ArrayList<Mapping> mappings)
	{
		this.mappings = mappings;
	}

	public boolean isSatisfied(Object touchData) {
      if (this.enabled) {
        for (Mapping m : mappings) {
    	    if (!m.isSatisfied(touchData)) {
    		    return false;
    	    }
        }
        return true;
      }
      else {
    	  return false;
      }
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void disable() {
		this.enabled = false;
	}
	
	public void enable() {
		this.enabled = true;
	}
	
	
}
