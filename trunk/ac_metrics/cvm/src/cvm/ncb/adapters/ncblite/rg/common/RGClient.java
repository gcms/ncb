/* @author Mario J Lorenzo */ package cvm.ncb.adapters.ncblite.rg.common;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.List;

import cvm.model.*;


public class RGClient {

	private static Socket server;
	private static RGClient target;
	private static void connect() {

		try {
			CVM_Debug.getInstance().printDebugErrorMessage("Connecting to server....");
			server = new Socket("squire.cs.fiu.edu", 55556);
			CVM_Debug.getInstance().printDebugErrorMessage("Connected to server");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	private static void disconnect() {
		
		try {
			server.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public static Object callDB(String cmd, Object o) throws Exception {

		connect();
		
		Transaction tran = new Transaction(cmd, o, "");

		ObjectOutputStream out = new ObjectOutputStream(server
				.getOutputStream());

		out.writeObject(tran);

		Object obj =  handle();
		
		disconnect();
		
		return obj;
	}

	private static Object handle() throws Exception {

		ObjectInputStream in = new ObjectInputStream(server.getInputStream());

		Transaction tran;

		tran = (Transaction) in.readObject();

		return tran.response;
	}

	public void finalize() {
		try {
			server.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static RGClient getInstance() {
		if (target == null) 
			target = new RGClient();
		return target;
	}

	public static void main(String[] args) throws Exception {
		List lst = new LinkedList();

		lst.add("deng");
		lst.add("1234");
		lst.add("127.0.0.1");
		CVM_Debug.getInstance().printDebugMessage(RGClient.callDB("authUser", lst).toString());

		lst = new LinkedList();
		lst.add("deng");

		Thread.sleep(1000);
		
		CVM_Debug.getInstance().printDebugMessage(RGClient.callDB("refreshUI", lst).toString());

		
		System.gc();
	}

}
