package cvm.ncb.tpm;

import java.util.ArrayList;
import edu.fiu.strg.ACSTF.resource.AbstractResource;
import cvm.ncb.csm.CommObject;

public class CommFWResource extends AbstractResource {

	private ArrayList<CommObject> cObjectList;
	
    public CommFWResource(ArrayList<CommObject> cObjectList) {
      	this.cObjectList=cObjectList;
    }
    
	public ArrayList<CommObject> getCObjectList() {
		return cObjectList;
	}

	public void setCObjectList(ArrayList<CommObject> cObjectList) {
		this.cObjectList = cObjectList;
	}

	public CommObject getObjectByName(String fwName) {
		for(CommObject comObj: cObjectList){
			if(comObj.getName().equalsIgnoreCase(fwName)){
				return comObj;
			}
		}
		return null;
		
	}
}
