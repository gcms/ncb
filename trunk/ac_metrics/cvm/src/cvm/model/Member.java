/* @author Mario J Lorenzo */ package cvm.model;

import java.io.Serializable;

public class Member implements Serializable {
	

	private String userName;
	private String firstName;
	private String lastName;
	private boolean active;
	private String profilePic;
	private String videoStreamURL;
	
		
	
	public Member(String userName, String firstName, String lastName, boolean active, String profilePic) {
		super();
		this.userName = userName;
		this.firstName = firstName;
		this.lastName = lastName;
		this.active = active;
		this.profilePic = profilePic;
	}
	
	
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getProfilePic() {
		return profilePic;
	}
	public void setProfilePic(String profilePic) {
		this.profilePic = profilePic;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getVideoStreamURL() {
		return videoStreamURL;
	}
	public void setVideoStreamURL(String videoStreamURL) {
		this.videoStreamURL = videoStreamURL;
	}
	public boolean equals(Object obj) {
		
		assert(obj instanceof Member);
		Member mem = (Member) obj;
		
		return this.userName.equals(mem.userName);
		
	}
	
	

}
