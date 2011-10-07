/*
 * @(#)Clone.java	1.2 01/03/13
 *
 * Copyright (c) 1999-2001 Sun Microsystems, Inc. All Rights Reserved.
 *
 * Sun grants you ("Licensee") a non-exclusive, royalty free, license to use,
 * modify and redistribute this software in source and binary code form,
 * provided that i) this copyright notice and license appear on all copies of
 * the software; and ii) Licensee does not utilize the software in a manner
 * which is disparaging to Sun.
 *
 * This software is provided "AS IS," without a warranty of any kind. ALL
 * EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND WARRANTIES, INCLUDING ANY
 * IMPLIED WARRANTY OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE OR
 * NON-INFRINGEMENT, ARE HEREBY EXCLUDED. SUN AND ITS LICENSORS SHALL NOT BE
 * LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING
 * OR DISTRIBUTING THE SOFTWARE OR ITS DERIVATIVES. IN NO EVENT WILL SUN OR ITS
 * LICENSORS BE LIABLE FOR ANY LOST REVENUE, PROFIT OR DATA, OR FOR DIRECT,
 * INDIRECT, SPECIAL, CONSEQUENTIAL, INCIDENTAL OR PUNITIVE DAMAGES, HOWEVER
 * CAUSED AND REGARDLESS OF THE THEORY OF LIABILITY, ARISING OUT OF THE USE OF
 * OR INABILITY TO USE SOFTWARE, EVEN IF SUN HAS BEEN ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGES.
 *
 * This software is not designed or intended for use in on-line control of
 * aircraft, air traffic, aircraft navigation or aircraft communications; or in
 * the design, construction, operation or maintenance of any nuclear
 * facility. Licensee represents and warrants that it will not use or
 * redistribute the Software for such purposes.
 */

package org.jivesoftware.smackx.jingle.mediaimpl.jmf;

import java.util.StringTokenizer;
import java.util.Vector;

import javax.media.*;
import javax.media.control.TrackControl;
import javax.media.format.*;
import javax.media.protocol.*;
import javax.media.rtp.SessionAddress;
import javax.media.NoDataSourceException;

import java.awt.Dimension;
import java.io.IOException;

import javax.media.CaptureDeviceInfo;
import javax.media.CaptureDeviceManager;
import javax.media.Controller;
import javax.media.ControllerClosedEvent;
import javax.media.ControllerEvent;
import javax.media.ControllerListener;
import javax.media.Format;
import javax.media.Manager;
import javax.media.Processor;
import javax.media.NoProcessorException;


/**
 * Sample program to clone a data source using the
 * cloneable DataSource and playback the result. 
 */
public class CloneDS  {

    private static DataSource ds = null;
    private static CloneDS instance = null;
    private static Processor processor = null;
    private static Format format =  null;
    private static DataSource dataOutput = null;
    private static Integer stateLock = 0;
    private static boolean failed = false;
    private static SessionAddress[] localAddrs;
    private static boolean firstCloneUsed = false;
    
    /**
     * Given a MediaLocator, return create a player and use that player
     * as a player to playback the media.
     */
    public synchronized static DataSource createDataSource(MediaLocator ml) throws IOException, NoDataSourceException{
//    	System.err.println("Begining create data source ...");
/*    	if(instance == null){
    		instance = new CloneDS(ml);
    	}
*/    	if (ds != null){
    		return (((SourceCloneable)ds).createClone());
    	}
//    	System.err.println("End create data source ...");
    	return ds;
    }

    public synchronized static DataSource getCloneDataSource() {
/*    	if(instance == null){
    		try {
				instance = new CloneDS();
			} catch (NoDataSourceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
*/    	if (ds != null){
    		if(firstCloneUsed == false ){
    			firstCloneUsed = true;
    			return getClonableDataSource();
    		}else {
    			return (((SourceCloneable)ds).createClone());
    		}
    	}
//    	System.err.println("End create data source ...");
    	return ds;
    }

    public synchronized static CloneDS getInstance() {
//    	System.err.println("Begining get Instance ...");
    	if(instance == null){
    		try {
				instance = new CloneDS();
			} catch (NoDataSourceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	return instance;
    }
    /**
     * Given a MediaLocator, return create a player and use that player
     * as a player to playback the media.
     */
    public synchronized static boolean isInstantiated() {
    	if(instance == null){
    		return false;
    	}
    	return true;
    }

 /*   *//**
     * Given a MediaLocator, return create a player and use that player
     * as a player to playback the media.
     *//*
    public synchronized static Processor getProcessor() throws NoProcessorException,IOException{
    	System.err.println("Begining get processor ...");
		if (processor == null){
			processor = javax.media.Manager.createProcessor(ds);
	        processor.configure();
		}
    	System.err.println("End get Processor ...");
    	return processor;
    }
*/
    /**
     * Given a MediaLocator, return create a player and use that player
     * as a player to playback the media.
     */
    public synchronized static DataSource getClonableDataSource() {
    	return ds;
    }

    private CloneDS(MediaLocator ml) throws IOException, NoDataSourceException{
//    	System.err.println("Begining constructor ...");
    	// create a new datasource
    	ds = null;
//    	try {
    		ds = Manager.createDataSource(ml);
//    	} catch (Exception e) {
//    		System.err.println("Cannot create DataSource from: " + ml);
//    	}

		ds = Manager.createCloneableDataSource(ds);
		if (ds == null) {
			System.err.println("Cannot clone the given DataSource");
		}
//    	System.err.println("End constructor");
    }
    
    private CloneDS() throws IOException, NoDataSourceException{
//    	System.err.println("Begining constructor2 ...");
    	// create a new datasource
    	ds = null;
//    	try {
    		ds = initDevice();
//    	} catch (Exception e) {
//    		System.err.println("Cannot create DataSource from: " + ml);
//    	}

		ds = Manager.createCloneableDataSource(ds);
		if (ds == null) {
			System.err.println("Cannot clone the given DataSource");
		}
//    	System.err.println("End constructor");
    }

    /**
     * Starts the transmission. Returns null if transmission started ok.
     * Otherwise it returns a string with the reason why the setup failed.
     * Starts receive also.
     *
     * @return result description
     */
    
	private DataSource initDevice()
	{
		Vector formatsCatalog = new Vector();
	      Vector audioDevices = new Vector();
	      Vector videoDevices = new Vector();
	      Vector devices = new Vector();

	      // récupération des périphériques disponibles

	      devices = CaptureDeviceManager.getDeviceList (null);
	      CaptureDeviceInfo cdi;

	      if (devices != null && devices.size() > 0) 
	      {
	          int devicesNum = devices.size();
	          Format[] formats;
	          
	          for ( int i = 0; i < devicesNum; i++) 
	          {
	            cdi = (CaptureDeviceInfo) devices.elementAt (i);
	            formats = cdi.getFormats();
	            formatsCatalog.addElement(formats);      

	            // on sépare les periphériques audio et vidéo
	            for (int j = 0; j < formats.length; j++) 
	            {
	                if (formats[j] instanceof AudioFormat) 
	                {
	                  audioDevices.addElement(cdi);
	                  break;
	                }
	                else if (formats[j] instanceof VideoFormat) 
	                {
	                  videoDevices.addElement(cdi);            
	                  break;
	                }
	            }
	         }
	      }     

	      CaptureDeviceInfo cdiVideo;
	      // on recupère l'unique device video
	      cdiVideo = (CaptureDeviceInfo) (videoDevices).elementAt(0);      
	      // on recupere les formats disponibles
	      Format[] video = cdiVideo.getFormats();

	      // on séléctionne un format
	      format = video[3];
	      //System.out.println("Type: " + video[3].getEncoding() + " Data: " + video[3].getDataType());
	    
	      try {     
	    	// on créé le DataSource   
			ds = Manager.createDataSource(cdiVideo.getLocator());      
			// on lui applique le format choisi                 
			
			//FormatControl[] fmtControl =  ((CaptureDevice)ds).getFormatControls();
			//fmtControl[0].setFormat(format);
			return ds;
	      }
	      catch (IOException ioe) {
	          System.out.println(ioe);
	      }
	      catch (NoDataSourceException ndse) {
	          System.out.println(ndse);
	      }     
	      
	      return null;
	 }    

    public static String createProcessor() {
        
    	getInstance();
    	// Try to create a processor to handle the input jmf locator
        try {
            processor = javax.media.Manager.createProcessor(ds);
        }
        catch (NoProcessorException npe) {
            npe.printStackTrace();
            return "Couldn't create processor";
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
            return "IOException creating processor";
        }

        processor.configure();
        // Wait for it to configure
        boolean result = waitForState(processor, Processor.Configured);
        if (!result){
            return "Couldn't configure processor";
        }
        
        
        // Get the tracks from the processor
        TrackControl[] tracks = processor.getTrackControls();

        // Do we have atleast one track?
        if (tracks == null || tracks.length < 1){
            return "Couldn't find tracks in processor";
        }

        // Set the output content descriptor to RAW_RTP
        // This will limit the supported formats reported from
        // Track.getSupportedFormats to only valid RTP formats.
        ContentDescriptor cd = new ContentDescriptor(ContentDescriptor.RAW_RTP);
        processor.setContentDescriptor(cd);

        Format supported[];
        Format chosen = null;
        boolean atLeastOneTrack = false;

        // Program the tracks.
        for (int i = 0; i < tracks.length; i++) {
               Format format = tracks[i].getFormat();
               Dimension size = ((VideoFormat)format).getSize();
               float frameRate = ((VideoFormat)format).getFrameRate();
               
               int w = (size.width % 8 == 0 ? size.width : (size.width /8) * 8);
               int h = (size.height % 8 == 0 ? size.height : (size.height /8) * 8);
               
                
            	tracks[i].setFormat(new VideoFormat(VideoFormat.JPEG_RTP,new Dimension(w,h),Format.NOT_SPECIFIED,Format.byteArray,frameRate));                
            	tracks[i].setEnabled(true);
            	            	       
        }


        result = waitForState(processor, Controller.Realized);
        if (!result)
            return "Couldn't realize processor";

        // Get the output data source of the processor
        dataOutput = processor.getDataOutput();

        return null; 
    }

    private static synchronized boolean waitForState(Processor p, int state) {
        p.addControllerListener(CloneDS.getInstance().new StateListener());
        failed = false;

        // Call the required method on the processor
        if (state == Processor.Configured) {
            p.configure();
        }
        else if (state == Processor.Realized) {
            p.realize();
        }

        // Wait until we get an event that confirms the
        // success of the method, or a failure event.
        // See StateListener inner class
        while (p.getState() < state && !failed) {
            synchronized (getStateLock()) {
                try {
                    getStateLock().wait();
                }
                catch (InterruptedException ie) {
                    return false;
                }
            }
        }

        return !failed;
    }

    static Integer getStateLock() {
        return stateLock;
    }

    static void setFailed() {
        failed = true;
    }

    public static void closeProcessor() {
    	processor.close();
    }
    
    public static void startProcessor(){
    	processor.start();
    }
    
    public static boolean isProcessorNull(){
    	if(processor == null) return true;
    	else return false;
    }
    
    public static void stopProcessor(){
    	processor.stop();
    }
    
    /**
     * *************************************************************
     * Inner Classes
     * **************************************************************
     */

    class StateListener implements ControllerListener {

        public void controllerUpdate(ControllerEvent ce) {

            // If there was an error during configure or
            // realize, the processor will be closed
            if (ce instanceof ControllerClosedEvent)
                setFailed();

            // All controller events, send a notification
            // to the waiting thread in waitForState method.
            if (ce != null) {
                synchronized (getStateLock()) {
                    getStateLock().notifyAll();
                }
            }
        }
    }
    
    public static void addLocalSessionAddr(SessionAddress local){
    	SessionAddress[] tmp = new SessionAddress[localAddrs.length + 1];
    	System.arraycopy(localAddrs, 0, tmp, 0, localAddrs.length);
    	localAddrs = tmp;
    	localAddrs[localAddrs.length - 1] = local;
    }
    
    public static void removeLocalSessionAddr(SessionAddress local){
    	SessionAddress[] tmp = new SessionAddress[localAddrs.length - 1];
    	for(int i=0; i < localAddrs.length; i++){
    		if(localAddrs[i].equals(local)){
    	    	if(i == 0){
        			System.arraycopy(localAddrs, i+1, tmp, 0, tmp.length);
    	    	}else {
    	    		System.arraycopy(localAddrs, 0, tmp, 0, i);
        			System.arraycopy(localAddrs, i+1, tmp, i, tmp.length - 1);
    	    	}
    	    }
    	}
    	localAddrs = tmp;
    }

    public static SessionAddress[] getLocalSessionAddr(){
    	return localAddrs;
    }
}

