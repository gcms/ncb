package cvm.ncb.handlers.event;

import cvm.model.Handles_Event;

/**
 * This event notifies the logoff the system.
 * @author Frank Hernandez
 *
 */
public class NotifyLogoffReply_Event extends Handles_Event
{

	private boolean isLogedoff;
	public NotifyLogoffReply_Event(Object eventSource, boolean isLogedoff) {
		super(eventSource);
		this.isLogedoff = isLogedoff;
		// TODO Auto-generated constructor stub
	}
	/**
	 * Return wether or not logoff was successful.
	 * @return
	 */
	public boolean isLogedoff()
	{
		return isLogedoff;
	}

}
