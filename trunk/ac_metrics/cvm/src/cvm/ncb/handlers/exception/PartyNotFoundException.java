package cvm.ncb.handlers.exception;
/**
 * This class represents an exception that is thrown when the 
 * program tries to remove a party that is not in the connection 
 * @author Guangqiang Zhao
 *
 */

public class PartyNotFoundException extends Exception
{
	/**
	 * the name of the participant that coursed this exception
	 */
	private String partyName;
	
	public PartyNotFoundException(String name) 
	{
		super();
		partyName= name;
	}
	/**
	 * This method returns the name of the participant
	 * @return the name of the participant
	 */
	public String getPartyName() 
	{
		return partyName;
	}
	

}
