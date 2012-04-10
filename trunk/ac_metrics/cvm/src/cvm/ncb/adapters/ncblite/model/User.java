/* @author Mario J Lorenzo */
package cvm.ncb.adapters.ncblite.model;

import util.CVM_Debug;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

public class User extends MutableObject
{
	private String userName;
	private String password;
	private String firstName;
	private String lastName;

	private String profilePic;
	private String voiceEnable;
	private String hostURL;
	private RoleList roleList;
	private List contactList;
	private List applicationList;
	private Form formTypeSchema;
	private Form formInstanceSchema;
	
	public User(){
		
	}
	
	public User(String name, String name2, String password, String pic, String name3, String enable, String URL ) {
		super();
		// TODO Auto-generated constructor stub
		firstName = name;
		lastName = name2;
		this.password = password;
		profilePic = pic;
		userName = name3;
		voiceEnable = enable;
		hostURL = URL;
	}
	
	/*
	public User(List list, String name, String name2, String password, String pic, RoleList list2, String name3, String enable, String URl, List list3, Form type, Form instance) {
		super();
		// TODO Auto-generated constructor stub
		contactList = list;
		firstName = name;
		lastName = name2;
		this.password = password;
		profilePic = pic;
		videoStreamURL = URl;
		roleList = list2;
		userName = name3;
		voiceEnable = enable;
		applicationList = list3;
		formTypeSchema = type;
		formInstanceSchema = instance;
	}*/

	/* LIST OF SETTER AND GETTER FOR THE THE ABOVE CONSTRUCTOR*/
	
	public List getApplicationList() {
		return applicationList;
	}

	public void setApplicationList(List applicationList) {
		this.applicationList = applicationList;
	}

	public List getContactList() {
		return contactList;
	}

	public void setContactList(List contactList) {
		this.contactList = contactList;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		
		this.firstName = firstName;
	}

	public Form getFormInstanceSchema() {
		return formInstanceSchema;
	}

	public void setFormInstanceSchema(Form formInstanceSchema) {
		this.formInstanceSchema = formInstanceSchema;
	}

	public Form getFormTypeSchema() {
		return formTypeSchema;
	}

	public void setFormTypeSchema(Form formTypeSchema) {
		this.formTypeSchema = formTypeSchema;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getProfilePic() {
		return profilePic;
	}

	public void setProfilePic(String profilePic) {
		this.profilePic = profilePic;
	}

	public RoleList getRoleList() {
		return roleList;
	}

	public void setRoleList(RoleList roleList) {
		this.roleList = roleList;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getVoiceEnable() {
		return voiceEnable;
	}

	public void setVoiceEnable(String voiceEnable) {
		this.voiceEnable = voiceEnable;
	}


	public String getHostURL() {
		return hostURL;
	}

	public void setHostURL(String hostURL) {
		this.hostURL = hostURL;
	}

	public String toString()
	{
		return userName;
		
	}
	
	public static void main(String[] args) {
		
		User user = new User();
		user.setFirstName("Hello");
		
		
		user.addPropertyChangeListener(new PropertyChangeListener() {

			public void propertyChange(PropertyChangeEvent evt) {
				CVM_Debug.getInstance().printDebugMessage("WAS: "+evt.getOldValue()+" NOW: "+evt.getNewValue());
			}			
		
		});
		
		user.setFirstName("good bye");
		
		user.setFirstName("hi");
		
/*		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(User.class);
						
			PropertyDescriptor[] props = beanInfo.getPropertyDescriptors();
			
			for (PropertyDescriptor descriptor : props) {
				
				CVM_Debug.getInstance().printDebugMessage(descriptor.getName());
						
				descriptor.getReadMethod().invoke(user, null);
				
				//descriptor.getWriteMethod().invoke(user, new Object[] {"Good Bye"});
				
			}
			
			
		
			
			
		} catch (IntrospectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
*/		
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof User) {
			
			if(((User)obj).getUserName().equalsIgnoreCase(this.getUserName())) {
				return true;
			}
		}
		
		return false;
	}

	@Override
	public int hashCode() {
		return userName.hashCode();
	}
	
}