package cvm.ncb.drivers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import cvm.model.CVM_Debug;

public class Timer implements Runnable{
	public void run() {
	    try {
			System.out.println("Start Timer:"+System.currentTimeMillis( ));
			 BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			 br.readLine();
	    }catch (IOException ioe) {
	         CVM_Debug.getInstance().printDebugMessage("IO error ");
	    }
		System.out.println("End Timer:"+System.currentTimeMillis( ));
		System.exit(0);
	}
}
