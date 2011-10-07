package cvm.ncb.handlers.exception;

/**
 * This exception occurs when the adding of a party fails.
 * @author Frank Hernandez
 *
 */
public class PartyNotAddedException extends Exception{

	/**
	 * the name of the participant that coursed this exception
	 */
	private String partyName;
	
	public PartyNotAddedException(String name) 
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

