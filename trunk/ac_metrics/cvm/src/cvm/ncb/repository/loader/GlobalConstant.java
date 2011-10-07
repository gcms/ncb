package cvm.ncb.repository.loader;

public class GlobalConstant {
	public static enum RequestedType { Video, Audio, Chat, Emoticon,Voicemail,PC2Phone,Payment,Bandwidth, Lossrate}
	public static enum OperationType {greaterThanOrEqualTo, lessThanOrEqualTo, equalTo, 
		lessThan, greaterThan,existIn, request, selection, reselection}
	public static enum policyType {goalPolicy, actionPolicy}
	
}
