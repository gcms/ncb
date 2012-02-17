/* @author Mario J Lorenzo */
package cvm.ncb.adapters.ncblite.model;

import java.io.Serializable;
import java.util.List;

public class RoleList implements Serializable{

	
	List roles;

	
	
	
	
	public RoleList(List roles) {
		super();
		// TODO Auto-generated constructor stub
		this.roles = roles;
	}

	public List getRoles() {
		return roles;
	}

	public void setRoles(List roles) {
		this.roles = roles;
	}
	
	
	
	
}
