package cvm.ncb.adapters;

public class JMMLAdapter extends NCBBridgeBase
{

	public void addParticipant(String session, String participant)
	{
		// TODO Auto-generated method stub

	}

	public void createSession(String session)
	{
		// TODO Auto-generated method stub

	}

	public String getCapability()
	{
		// TODO Auto-generated method stub
		return null;
	}

    public void login()
	{
		// TODO Auto-generated method stub
	}

	public void logout()
	{
		// TODO Auto-generated method stub

	}

	public void removeParticipant(String sID, String participant)
	{
		// TODO Auto-generated method stub

	}

	public void restartService()
	{
		// TODO Auto-generated method stub

	}

    public void sendSchema(String schema, String participant) {
		// TODO Auto-generated method stub
		
	}

	public void disableMedium(String session, String medium) {
		// TODO Auto-generated method stub
		
	}

	public void enableMedium(String session, String medium) {
		// TODO Auto-generated method stub
		
	}

	public boolean hasMediumFailed(String session, String medium_) {
		// TODO Auto-generated method stub
		return false;
	}

    public String getName() {
        return "JMML";
    }

    public void destroySession(String session) {
		// TODO Auto-generated method stub
		
	}

	public void enableMediumReceiver(String session, String medium) {
		// TODO Auto-generated method stub
		
	}
}
