package cvm.ncb.tpm;

import java.util.Iterator;
import java.util.HashSet;
import java.util.concurrent.CopyOnWriteArraySet;

import cvm.ncb.csm.CommObject;
import cvm.ncb.ks.*;
import cvm.ncb.manager.NCBCallQueue;

import edu.fiu.strg.ACSTF.touchpoint.AbstractTouchPoint;
import cvm.model.*;

	public class CommFWTouch extends AbstractTouchPoint<CommFWResource> {
		private CopyOnWriteArraySet<String> failedTable = null;
		private CopyOnWriteArraySet<String> resetFWTable = null;

		public CommFWTouch(CommFWResource commFWs) {
			super(commFWs);
			failedTable = new CopyOnWriteArraySet<String>();
			resetFWTable = new CopyOnWriteArraySet<String>();
		}
		
		public boolean hasFailed() {
			//CVM_Debug.getInstance().printDebugMessage("Checking for failures "+!failedTable.isEmpty());
			if(!failedTable.isEmpty()) return true;
			return false;
		}
		
		public boolean checkFrameworkForFailure() {
			ConIDMappingTable m_conToFwTable = ConIDMappingTable.getInstance();
			Iterator<Connection> itr = m_conToFwTable.getAllConnections();
			while(itr.hasNext()) {
				Connection con = itr.next();
				for(CommObject comObj: this.getResource().getCObjectList()){
					if(con.containsComObj(comObj.getNCBBridge().getFWName())) {
						for(String medium: con.getActiveMedia()){
							if (comObj.getNCBBridge().hasMediumFailed(con.getConId(),
								medium)) updateFailedTable(comObj.getName());
						}
					}
				}
			}
			return true;
		}

		public void resetFramework() {
			for(String fwName: failedTable){
				this.getResource().getObjectByName(fwName).getNCBBridge().restartService();
				resetFWTable.add(fwName);
				failedTable.remove(fwName);
				CVM_Debug.getInstance().printDebugMessage("Removed "+fwName+" from failed table");
			}
		}

		public void notifyFailedFramework() {
			CVM_Debug.getInstance().printDebugMessage("Detected:"+System.currentTimeMillis( ));
			for(String fwName: failedTable){
				CommFWCapabilitySet.getInstance().removeFramework(fwName);
				Object[] obj = null;
				NCBCallQueue.getInstance().add(fwName, 0, "failedFramework","", obj);
				CVM_Debug.getInstance().printDebugMessage(fwName+" is no longer available");
				failedTable.remove(fwName);
			}
		}

		public boolean hasFailedReset() {
			return false;
		}
		
		public void reInstantiate() {
			  CVM_Debug.getInstance().printDebugMessage("Restored Number to 5.");
		}
			
		private void updateFailedTable(String fwName){
			failedTable.add(fwName);
		}
		
		private void doPause(int iTimeInSeconds){
			long t0, t1;
			CVM_Debug.getInstance().printDebugMessage("timer start");
			t0=System.currentTimeMillis( );
			t1=System.currentTimeMillis( )+(iTimeInSeconds*1000);

			CVM_Debug.getInstance().printDebugMessage("T0: "+t0);
			CVM_Debug.getInstance().printDebugMessage("T1: "+t1);

			do {
			t0=System.currentTimeMillis( );

			} while (t0 < t1);

			CVM_Debug.getInstance().printDebugMessage("timer end");

		}
/*<<<<<<< .mine

		public HashSet<String> getFailedTable() {
			return failedTable;
		}

		public void setFailedTable(HashSet<String> failedTable) {
			this.failedTable = failedTable;
		}
=======
*/
		public CopyOnWriteArraySet<String> getResetFWTable() {
			return resetFWTable;
		}
/*>>>>>>> .r2006*/
}
