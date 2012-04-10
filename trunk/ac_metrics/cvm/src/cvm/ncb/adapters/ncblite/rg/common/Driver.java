/* @author Mario J Lorenzo */ package cvm.ncb.adapters.ncblite.rg.common;

import java.util.LinkedList;
import java.util.List;

import cvm.ncb.adapters.ncblite.model.User;
import util.CVM_Debug;

public class Driver {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ObjectDispatcher d = new ObjectDispatcher();
		
		/* String userName = "ygant001";
		 String password = "1234";
		 
		 
		 List parms = new LinkedList();
		 
		 parms.add(userName);
		 parms.add(password);
		 
		 User user;
		 try
		 {
		 user = (User)d.callDB("authUser", parms);
		 
		 CVM_Debug.getInstance().printDebugMessage("GOT BACK RESULT: "+user);
		 } catch (Exception e)
		 {
		 // TODO Auto-generated catch block
		 e.printStackTrace();
		 }
		 
		 System.exit(-1);
		 */
		String userName = "usist001";
		String firstName = "uma";
		String lastName = "sista";
		String password = "123445";
		String voiceEnabled = "yes";
		String profilePic = "xyz";

		List parms = new LinkedList();

		parms.add(userName);
		parms.add(firstName);
		parms.add(lastName);
		parms.add(password);
		parms.add(voiceEnabled);
		parms.add(profilePic);

		User user;

		try {
			user = (User) d.callDB("updateProfile", parms);

			CVM_Debug.getInstance().printDebugMessage("GOT BACK RESULT:" + user);

		} catch (Exception e) {
			e.printStackTrace();

		}
		System.exit(-1);

		/*		String userName = "ygant001";
		 String contactUserName = "usist001";
		 
		 List parms = new LinkedList();
		 
		 parms.add(userName);
		 parms.add(contactUserName);
		 
		 Contact contact;
		 
		 try
		 {
		 d.callDB("addContactToContactList",parms);
		 }
		 catch(Exception e)
		 {
		 e.printStackTrace();
		 }
		 System.exit(-1);
		 */

		/*		String userName = "ygant001";
		 String contactUserName = "usist001";
		 
		 List parms = new LinkedList();
		 
		 parms.add(userName);
		 parms.add(contactUserName);
		 
		 Contact contact;
		 
		 try
		 {
		 d.callDB("adddeleteContactFromContactList",parms);
		 }
		 catch(Exception e)
		 {
		 e.printStackTrace();
		 }
		 System.exit(-1);
		 */

		/*		 String userName = "ygant001";
		 String formTypeName = "abc";
		 
		 List parms = new LinkedList();
		 
		 parms.add(userName);
		 parms.add(formTypeName);
		 
		 Form form;
		 
		 try
		 {
		 form=(Form)d.callDB("loadFormType",parms);
		 CVM_Debug.getInstance().printDebugMessage("GOT BACK RESULTS:"+form);
		 }
		 catch(Exception e)
		 {
		 e.printStackTrace();
		 }
		 System.exit(-1);
		 */

		/*		 String userName = "ygant001";
		 String formName = "abc";
		 
		 List parms = new LinkedList();
		 
		 parms.add(userName);
		 parms.add(formName);
		 
		 Form form;
		 
		 try
		 {
		 form=(Form)d.callDB("loadFormInstance",parms);
		 CVM_Debug.getInstance().printDebugMessage("GOT BACK RESULTS:"+form);
		 }
		 catch(Exception e)
		 {
		 e.printStackTrace();
		 }
		 System.exit(-1);
		 */

		/*		String userName = "ygant001";
		 String applicationName = "abc";
		 List params = new LinkedList();
		 parms.add(userName);
		 parms.add(applicationName);
		 Application application;
		 try
		 {
		 application = (Application)d.callDB("loadApplication",parms);
		 CVM_Debug.getInstance().printDebugMessage("GOT BACK RESULTS:"+application);
		 }
		 catch(Exception e)
		 {
		 e.printStackTrace();
		 }
		 System.exit(-1);
		 */
	}

}
