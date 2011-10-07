package cvm.ncb.adapters;
/**
 * This class contains all the possible capabilities of NCB.
 * This is used to encapsulate API specific Capabilities into
 * a general type that is understood by NCB.
 * 
 * @author Raidel Batista, Eddie Incer, Frank Hernandez
 *
 */
public abstract class NCBBridgeCapability 
{
	/**
	 * Live video
	 */
	public static final String VIDEO = "VIDEO";
	/**
	 * Live audio
	 */
	public static final String AUDIO = "AUDIO";
	/**
	 * Live Audio Out
	 */
	public static final String AUDIO_OUTPUT = "AUDIO_OUT";
	/**
	 * Live Audio in.
	 */
	public static final String AUDIO_INPUT = "AUDIO_IN";
	/**
	 * Live Audio and video
	 */
	public static final String LIVE_AV = "LiveAV";
	/**
	 * Live text... Chat
	 */
	public static final String TEXT = "Text";

}
