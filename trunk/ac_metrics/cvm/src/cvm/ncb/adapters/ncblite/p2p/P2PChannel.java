/* @author Mario J Lorenzo */ package cvm.ncb.adapters.ncblite.p2p;

import java.util.LinkedList;
import java.util.List;

import org.mr.api.blocks.ScalableFactory;
import org.mr.api.blocks.ScalableHandler;
import org.mr.api.blocks.ScalableStage;

import cvm.ncb.adapters.ncblite.rg.common.RGClient;
import util.CVM_Debug;

public class P2PChannel
{
	//STATIC VARS
	private static P2PChannel target;

	//INSTANCE VARS
	private static ScalableStage toPeer;
	private static ScalableStage fromPeer;
	private String channelID;
	private String channelDst;
	private P2PHandler handler;
	private ScalableHandler sh;
	
	public static P2PChannel getInstance()
	{
		if (target == null) target = new P2PChannel();
		
		return target;
		
	}
	
	private P2PChannel()
	{
	

		String[] keyPair = requestSipKeyPair();
		channelID = keyPair[0];
		channelDst = keyPair[1];
	
		toPeer = ScalableFactory.getStage(channelDst, true);
		fromPeer = ScalableFactory.getStage(channelID, true);
	
		sh = new ScalableHandler(){

			public void handle(Object arg0) {
				handler.handle((String)arg0);
			}
			
		};
		fromPeer.addHandler(sh);
		
	}
	
	public void registerHandler(P2PHandler handler)
	{
		this.handler = handler;
	}
	
	public void send(String msg)
	{
		toPeer.queue(msg);
		CVM_Debug.getInstance().printDebugMessage("SENT: "+msg);
	
	}
	
	public void unRegisterHandler() {
		fromPeer.removeHandler(sh);
		sh = new ScalableHandler(){

			public void handle(Object arg0) {
				handler.handle((String)arg0);
			}
			
		};
		fromPeer.addHandler(sh);
	}
	
	private String[] requestSipKeyPair()
	{	
	//	return new String[]{"IPCOMM01","IPCOMM02"};
		
		String[] keyPair = new String[] {"",""};
		
		String method = "requestSipKeyPair";
		List parms = new LinkedList();
		
		try
		{
			keyPair = (String[])RGClient.callDB(method, parms);

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return keyPair;
	}

	
	

	public static void main(String[] args)
	{
		P2PChannel channel = P2PChannel.getInstance();
		
		/*channel.registerHandler(new P2PHandler(){
			
			public void handle(String msg)
			{
				CVM_Debug.getInstance().printDebugMessage("REC: "+msg);
			}
			
		}
		);
		
		channel.send("Hello other computer.");*/
	}
	
	
	
}
