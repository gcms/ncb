package cvm.ncb.handlers;

import java.util.EventListener;
import java.util.Vector;

import cvm.model.Handles_Event;
import cvm.model.Uses_Listener;
import cvm.ncb.UserProfile;
import cvm.ncb.handlers.event.ConnectionCreatedReply_Event;
import cvm.ncb.handlers.event.ConnectionDeclinedReply_Event;
import cvm.ncb.handlers.event.ControlSchemaNotSentException_Event;
import cvm.ncb.handlers.event.DataSchemaNotSentException_Event;
import cvm.ncb.handlers.event.LoginExcetption_Event;
import cvm.ncb.handlers.event.MediaInitiatorEnableReply_Event;
import cvm.ncb.handlers.event.NoSessionException_Event;
import cvm.ncb.handlers.event.NotifyLoginReply_Event;
import cvm.ncb.handlers.event.NotifyLogoffReply_Event;
import cvm.ncb.handlers.event.PartyAddedReply_Event;
import cvm.ncb.handlers.event.PartyNotAddedException_Event;
import cvm.ncb.handlers.event.PartyRemovedReply_Event;
import cvm.ncb.handlers.event.SchemaNotSavedException_Event;
import cvm.ncb.handlers.event.SchemaReceived_Event;
import cvm.ncb.handlers.event.SendSchemaReply_Event;
import cvm.ncb.handlers.event.UnavailableMedia_Event;
import cvm.ncb.handlers.event.UserProfileCreatedEvent;

/**
 * This class handles the notification of events to the 
 * regisetered listeners. This is class specifically signals
 * events to the NCB layer listener. 
 * @author fhern006_1
 *
 */
public class NCBEventObjectManager 
{
	private static Vector listeners;
	private static NCBEventObjectManager instance;
	private static Object syncObject_ = new Object();
		
	private NCBEventObjectManager()
	{
		
	}
		
	/**
	 * Implementation of the Instance method as in the 
	 * singleton design pattern.
	 * @return
	 */
	public static NCBEventObjectManager Instance()
	{
		if(instance==null)
		{	synchronized(syncObject_)
			{
				if(instance ==null)
					instance = new NCBEventObjectManager();
			}
		}
		return instance;
	}
	public synchronized void addUpListener(Uses_Listener listener)
	{
		if(listeners == null)
			listeners = new Vector();
		listeners.addElement(listener);
	}
	public synchronized void addDownListener(EventListener listener)
	{
		
	}
	
	/**
	 * Removes an event from the list.
	 */
	public synchronized void removeUpListener(Uses_Listener listener)
	{
		if(listeners == null)
			listeners = new Vector();
		listeners.removeElement(listener);
	}
	/**
	 * This method will notify UCM of a LoginReply.
	 * @param contactlist
	 */
	public void notifyLoginReply(Object contactlist)
	{	
		NotifyLoginReply_Event event = new NotifyLoginReply_Event(this, contactlist);
		fireUpEvent(event);
		//fireUCMUpEventLoginReply(contactlist);
	}
	/**
	 * This method will notify UCM of a LogoffReply.
	 * @param contactlist
	 */
	public void notifyLogoffReply(boolean success)
	{	
		NotifyLogoffReply_Event event = new NotifyLogoffReply_Event(this, success);
		fireUpEvent(event);
		//fireUCMUpEventLoginReply(contactlist);
	}
	/**
	 * This methods notifies UCM of a PartyAddedReply.
	 * @param partyAdded
	 */
	public void notifyPartyAddedReply(boolean partyAdded)
	{
		PartyAddedReply_Event event = new PartyAddedReply_Event(this,partyAdded);
		fireUpEvent(event);		
	}
	
	/**
	 * This methods notifies UCM of a PartyRemovedReply.
	 * @param partyRemoved
	 */
	public void notifyPartyRemovedReply_Event(boolean partyRemoved)
	{
		PartyRemovedReply_Event event = new PartyRemovedReply_Event(this,partyRemoved);
		fireUpEvent(event);		
	}
	
	/**
	 * This methods notifies UCM of a MediaInitiatorEnableReply.
	 * @param partyRemoved
	 */
	public void notifyMediaInitiatorEnableReply_Event(boolean partyRemoved)
	{
		MediaInitiatorEnableReply_Event event = new MediaInitiatorEnableReply_Event(this,partyRemoved);
		fireUpEvent(event);		
	}
	/**
	 * This method notifies UCM of a SendSchemaReply.
	 * @param sendStatus
	 */
	public void notifySendSchemaReply_Event(boolean sendStatus)
	{
		SendSchemaReply_Event event = new SendSchemaReply_Event(this,sendStatus);
		fireUpEvent(event);		
	}
	
	/**
	 * This methods notifies UCM of a ConnectionCreatedReply.
	 * @param notifyConnectionReply
	 */
	public void notifyConnectionCreatedReply_Event(boolean notifyConnectionReply)
	{
		ConnectionCreatedReply_Event event = new ConnectionCreatedReply_Event(this,notifyConnectionReply);
		fireUpEvent(event);		
	}
	/**
	 * This methods notifies UCM of a ConnectionDeclineReply.
	 * @param notifyConnectionReply
	 */
	public void notifyConnectionDeclinedReply_Event(boolean notifyConnectionReply)
	{
		ConnectionDeclinedReply_Event event = new ConnectionDeclinedReply_Event(this,notifyConnectionReply);
		fireUpEvent(event);		
	}
	
	/**
	 * Event notification function. This function wil fire the event to
	 * notify the SE.
	 * @param usrProfile
	 */
	public void notifyUserProfileCreatedEvent(UserProfile usrProfile)
	{
		UserProfileCreatedEvent event = new UserProfileCreatedEvent(this, usrProfile);
		fireUpEvent(event);
		//fireUpEventSEUserProfileCreate(usrProfile);
	}
	
	/**
	 * Notify SE of a loginException
	 *
	 */
	public void notifyLoginExceptionEvent ()
	{
		LoginExcetption_Event event = new LoginExcetption_Event(this);
		fireUpEvent(event);
		
	}
	/**
	 * Notify SE of a noSessionException
	 *
	 */
	public void notifyNoSessionException_Event (String sID)
	{
		NoSessionException_Event event = new NoSessionException_Event(this, sID);
		fireUpEvent(event);
		
	}
	/**
	 * Notify SE of a PartyNotAddedException
	 *
	 */
	public void notifyPartyNotAddedException_Event (String userID)
	{
		PartyNotAddedException_Event event = new PartyNotAddedException_Event(this, userID);
		fireUpEvent(event);
		
	}
	
	/**
	 * Notify SE of a DataSchemaNotSentException
	 *
	 */
	public void notifyDataSchemaNotSentException_Event ()
	{
		DataSchemaNotSentException_Event event = new DataSchemaNotSentException_Event(this);
		fireUpEvent(event);
		
	}
	/**
	 * Notify SE of a ControlSchemaNotSentException
	 *
	 */
	public void notifyControlSchemaNotSentException_Event ()
	{
		ControlSchemaNotSentException_Event event = new ControlSchemaNotSentException_Event(this);
		fireUpEvent(event);
		
	}
	/**
	 * Notify SE of UnavailableMedia
	 */
	public void notifyUnavailableMedia_Event ()
	{
		UnavailableMedia_Event event = new UnavailableMedia_Event(this);
		fireUpEvent(event);
		
	}
	/**
	 * Notify SE of SchemaNotSavedException
	 *
	 */
	public void notifySchemaNotSavedException_Event()
	{
		SchemaNotSavedException_Event event = new SchemaNotSavedException_Event(this);
		fireUpEvent(event);
	}
	
	public void notifiySchemaReceivedEvent(String schema)
	{
		SchemaReceived_Event event = new SchemaReceived_Event(this, schema);
		fireUpEvent(event);
	}
	public void notifyUnrecognizedEvent()
	{
		fireUpEvent(new Handles_Event(this));
	}
	/**
	 * General Event Trigger. This method will fire the given event.
	 * @param event
	 */
	private void fireUpEvent(Handles_Event event)
	{
		//No listeners do nothing
		if (listeners != null && !listeners.isEmpty())
		{
			//UserProfileCreatedEvent event = new UserProfileCreatedEvent(this, usrProfile);
			Vector list;
			synchronized(this) { list = (Vector)listeners.clone(); }
			for (int i=0; i < list.size(); i++)
			{
				Uses_Listener listener = (Uses_Listener) list.elementAt(i);
				//if((listener instanceof cvm.ucm.handlers.UCMEventHandler))
				listener.use(event);
			}
		}
	}

	public void notifySaveSchemaReply(boolean b) {
		// TODO Auto-generated method stub
		
	}

	public void notifySendMediaReply(boolean b) {
		// TODO Auto-generated method stub
		
	}

	public void notifySendSchemaReply(boolean b) {
		// TODO Auto-generated method stub
		
	}

	public void notifyIsCreatedSessionReply(boolean b) {
		// TODO Auto-generated method stub
		
	}
	

}
