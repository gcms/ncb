/* @author Mario J Lorenzo */ package cvm.ncb.adapters.ncblite.rg.repository;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import cvm.ncb.adapters.ncblite.rg.repository.com.ConnectionManager;



public class RGService {
	
	public void start() {
		
		ServerSocket server = null;
		try {
			 server = new ServerSocket(55556);
			
			ConnectionManager mgr = new ConnectionManager();
			
			while(true)
			{
				Socket client = server.accept();
				mgr.addConnection(client);
			}
			
		} catch (IOException e) {
			try {
				server.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
		
	}
	
	public static void main(String[] args) {
		new RGService().start();
	}
}
