/* @author Mario J Lorenzo */ package cvm.ncb.adapters.ncblite.rg.repository.com;

import java.net.Socket;
import java.util.Iterator;
import java.util.Vector;

import cvm.ncb.adapters.ncblite.rg.repository.Gateway;
import cvm.model.*;

public class ConnectionManager {

	private Vector connections = null;
	
	private Gateway gateway = new Gateway();
	
	public void addConnection(Socket client) {
		
		Connection con = new Connection(client, gateway);
		
		Thread t = new Thread(con);
		
		t.start();
		
	}
	
	
	public void finalize() {
		
		Iterator itr = connections.iterator();
		
		while (itr.hasNext()) {
			Connection con = (Connection) itr.next();
			
			con.close();
			
		}
		
	}
	
	
}
