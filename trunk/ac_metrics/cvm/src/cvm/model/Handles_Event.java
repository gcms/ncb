package cvm.model;
import java.util.*;
/**
 * This class is the parent class for all classes that represent  
 * an event coming up from a lower level to a higher level
 *
 */
public class Handles_Event extends EventObject
{
	/**
	 * Constructor for Handles_Event
	 * @param eventSource the object which would fire this event
	 */
	public Handles_Event(Object eventSource) 
	{
		super(eventSource);
	}
}
