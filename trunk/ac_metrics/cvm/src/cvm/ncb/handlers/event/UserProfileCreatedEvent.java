package cvm.ncb.handlers.event;

import cvm.model.Handles_Event;
import cvm.ncb.UserProfile;

/**
 * Event used to notify the occurance of a UserProfileCreatedEvent
 * @author Frank Hernandez
 *
 */
public class UserProfileCreatedEvent extends Handles_Event {

	private UserProfile usrProfile;
	
	public UserProfileCreatedEvent(Object eventSource, UserProfile usrProfile)
	{
		super(eventSource);
		this.usrProfile= usrProfile;
	}
	/**
	 * This method returns the user profile object.
	 * @return user profile object
	 */
	public UserProfile getUserProfile()
	{
		return usrProfile;
	}
	
}
