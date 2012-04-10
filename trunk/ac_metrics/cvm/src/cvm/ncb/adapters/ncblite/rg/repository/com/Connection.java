/* @author Mario J Lorenzo */ package cvm.ncb.adapters.ncblite.rg.repository.com;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;

import cvm.ncb.adapters.ncblite.rg.common.Transaction;
import cvm.ncb.adapters.ncblite.rg.repository.Gateway;
import util.CVM_Debug;

public class Connection implements Runnable {

	private Socket socket;

	Gateway gateway;

	public Connection(Socket socket, Gateway gateway) {
		this.socket = socket;
		this.gateway = gateway;
	}

	public void run() {

		try {
			ObjectInputStream in = new ObjectInputStream(socket
					.getInputStream());
			
			Object obj;

//			while (true) {

				try {
					obj = in.readObject();
					

						if (obj instanceof Transaction) {

							Transaction tran = (Transaction) obj;

							process(tran);

						}
				} catch (EOFException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			
				
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			
		}

		catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
		}

		
		try { socket.close(); } catch (IOException e) { 
		 }
		 

	}

	public void process(Transaction tran) {

		System.out.print("[Transaction "
				+ new Timestamp(System.currentTimeMillis()) + "]" + "\t [ID: "
				+ tran.responsePipe + "]");


		String methodName = tran.cmd;
		List parmList = (List) tran.parms;

		int size = parmList.size();

		Class[] parmTypes = new Class[size];
		Object[] parmArgs = new Object[size];

		Iterator itr = parmList.iterator();

		int pos = 0;

		while (itr.hasNext()) {
			parmArgs[pos] = itr.next();

			parmTypes[pos] = parmArgs[pos].getClass();

			pos++;
		}

		Method methLst;

		try {
			methLst = gateway.getClass().getMethod(methodName, parmTypes);
		} catch (SecurityException e) {
			processError(tran, e);
			return;
		} catch (NoSuchMethodException e) {
			processError(tran, e);
			return;
		}

		Object result;

		try {
			result = methLst.invoke(gateway, parmArgs);

		} catch (IllegalArgumentException e) {
			processError(tran, e);
			return;
		} catch (IllegalAccessException e) {
			processError(tran, e);
			return;
		} catch (InvocationTargetException e) {
			processError(tran, e);
			return;
		}

		if (result instanceof Exception) {
			tran.errors = true;
			tran.errorMsg = ((Exception) result).getMessage();
			CVM_Debug.getInstance().printDebugMessage("\t[Failure] (0003)");
		} else {
			tran.response = result;

			System.out.print("\t [Transaction Summary: " + tran + "]");

			System.out.print("\t[Success] (0004)");

		}

		CVM_Debug.getInstance().printDebugMessage("");

		dispatch(tran);

	}

	public void processError(Transaction tran, Exception e) {
		tran.errors = true;
		tran.errorMsg = e.getMessage();
		CVM_Debug.getInstance().printDebugMessage("\t[Failure] (0005)");
		if(e instanceof InvocationTargetException) {
			((InvocationTargetException)e).getTargetException().printStackTrace();
		}
		dispatch(tran);
	}

	public void close() {
		try {
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// ------------>

	private void dispatch(Transaction tran) {

		try {
			ObjectOutputStream out = new ObjectOutputStream(socket
					.getOutputStream());

			out.writeObject(tran);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
