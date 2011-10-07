package edu.fiu.strg.ACSTF.manager.knowledge;

/**
 * @author A.C.I.D
 *
 */
public class ChangeRequest
{
   private String id;

    public ChangeRequest() {
    	super();
    }
    
	public ChangeRequest(String id)
	{
		super();
		this.id = id;
	}
	
	public ChangeRequest(ChangeRequest cr) {
		this.id=cr.id;
	}

	public String getId()
	{
		return id;
	}
	
	public void setId(String id)
	{
		this.id = id;
	}
	   
	public boolean equals(ChangeRequest changeRequest) {
		if (this.id==changeRequest.getId()) return true;
		else return false;
	}
  
}
