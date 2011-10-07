package cvm.ncb.handlers.event;

import cvm.model.Handles_Event;

/**
 * This class is usd for notifiying a MediaInitiatorEnableReply_Event to the SE.
 * @author Frank Hernandez
 *
 */
public class MediaInitiatorEnableReply_Event  extends Handles_Event 
{

	/**
	 * the variable holding whether the media is enabled successfully or not 
	 */
	private boolean notifyEnableMediaReply = false;
	
	public MediaInitiatorEnableReply_Event(Object eventSource, boolean notifyEnableMedia) {
		super(eventSource);
		this.notifyEnableMediaReply = notifyEnableMedia;
	}

	public boolean getNotifyMedia() {
		return notifyEnableMediaReply;
	}

	

}
