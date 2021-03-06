/**
 * $RCSfile$
 * $Revision: $
 * $Date: 08/11/2006
 * <p/>
 * Copyright 2003-2006 Jive Software.
 * <p/>
 * All rights reserved. Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jivesoftware.smackx.jingle.mediaimpl.jmf;

import java.awt.Dimension;
import java.io.IOException;
import java.net.InetAddress;
//import java.text.Normalizer.Form;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.media.CaptureDeviceInfo;
import javax.media.CaptureDeviceManager;
//import javax.media.Codec;
import javax.media.Controller;
import javax.media.ControllerClosedEvent;
import javax.media.ControllerEvent;
import javax.media.ControllerListener;
import javax.media.Format;
import javax.media.Manager;
import javax.media.NoDataSourceException;
import javax.media.NoProcessorException;
import javax.media.Processor;
//import javax.media.UnsupportedPlugInException;
import javax.media.control.BufferControl;
//import javax.media.control.FormatControl;
//import javax.media.control.PacketSizeControl;
import javax.media.control.TrackControl;
import javax.media.format.AudioFormat;
//import javax.media.format.RGBFormat;
import javax.media.format.VideoFormat;
//import javax.media.protocol.CaptureDevice;
import javax.media.protocol.ContentDescriptor;
import javax.media.protocol.DataSource;
import javax.media.protocol.PushBufferDataSource;
import javax.media.protocol.PushBufferStream;
import javax.media.rtp.InvalidSessionAddressException;
import javax.media.rtp.RTPManager;
import javax.media.rtp.SendStream;
import javax.media.rtp.SessionAddress;

import org.jivesoftware.smackx.jingle.media.JingleMediaSession;

/**
 * An Easy to use Video Channel implemented using JMF.
 * It sends and receives jmf for and from desired IPs and ports.
 * Also has a rport Symetric behavior for better NAT Traversal.
 * It send data from a defined port and receive data in the same port, making NAT binds easier.
 * <p/>
 * Send from portA to portB and receive from portB in portA.
 * <p/>
 * Sending
 * portA ---> portB
 * <p/>
 * Receiving
 * portB ---> portA
 * <p/>
 * <i>Transmit and Receive are interdependents. To receive you MUST trasmit. </i>
 *
 * @author Thiago Camargo
 */
public class VideoChannel {

    private DataSource ds;
    private String localIpAddress;
    private String remoteIpAddress;
    private int localPort;
    private int portBase;
    private Format format;
    private final int mode;
    private boolean success = false;

    private Processor processor = null;
    private RTPManager rtpMgrs[];
    private DataSource dataOutput = null;
    private VideoReceiver videoReceiver;
    
    private List<SendStream> sendStreams = new ArrayList<SendStream>();

    private JingleMediaSession jingleMediaSession;

    private boolean started = false;
    
    

    /**
     * Creates an Audio Channel for a desired jmf locator. For instance: new MediaLocator("dsound://")
     *
     * @param locator         media locator
     * @param localIpAddress  local IP address
     * @param remoteIpAddress remote IP address
     * @param localPort       local port number
     * @param remotePort      remote port number
     * @param format          audio format
     */
    public VideoChannel(
    		String localIpAddress,
            String remoteIpAddress,
            int localPort,
            int remotePort,
            JingleMediaSession jingleMediaSession,
            int mode) {
//    	System.out.println("Mode is "+mode);
    	this.mode = mode;
    	if (mode == JmfMediaManager.SERVER_MODE || mode == JmfMediaManager.BIDIRECTIONAL_MODE) {
    		CloneDS.getInstance();
			this.ds = CloneDS.getCloneDataSource(); //initDevice();
    	}
        this.localIpAddress = localIpAddress;
        this.remoteIpAddress = remoteIpAddress;
        this.localPort = localPort;
        this.portBase = remotePort;
        this.jingleMediaSession = jingleMediaSession;
    }

    /**
     * Starts the transmission. Returns null if transmission started ok.
     * Otherwise it returns a string with the reason why the setup failed.
     * Starts receive also.
     *
     * @return result description
     */
    
	public DataSource initDevice()
	{
/*		  Vector formatsCatalog = new Vector();
	      Vector audioDevices = new Vector();
	      Vector videoDevices = new Vector();
	      Vector devices = new Vector();

	      // r�cup�ration des p�riph�riques disponibles

	      devices = CaptureDeviceManager.getDeviceList (null);
  		System.out.println("Initializing device \n");
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

	            // on s�pare les periph�riques audio et vid�o
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
	      // on recup�re l'unique device video
	      cdiVideo = (CaptureDeviceInfo) (videoDevices).elementAt(0);      
	      // on recupere les formats disponibles
	      Format[] video = cdiVideo.getFormats();

	      // on s�l�ctionne un format
	      format = video[3];
	      System.out.println("Type: " + video[3].getEncoding() + " Data: " + video[3].getDataType());
	    
	      try {     
		    // we are getting a clone of the DataSource   
			ds = CloneDS.createDataSource(cdiVideo.getLocator());      
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
	      
*/	    System.err.println("Wrong initDevice called ");  
		return null;
	 }    

    public synchronized String start() {
        if (started) return null;

        if(mode == JmfMediaManager.SERVER_MODE || mode == JmfMediaManager.BIDIRECTIONAL_MODE)
        {
	        // Create a processor for the specified jmf locator
	        String result = createProcessor();
	        if (result != null) {
	            started = false;
	        }
	
	        // Create an RTP session to transmit the output of the
	        // processor to the specified IP address and port no.
	        result = createTransmitter();
	        if (result != null) {
	            processor.close();
	            processor = null;
	            started = false;
	        } else {
	            started = true;
	        }
	
	        // Start the transmission
	        processor.start();
        } if(mode == JmfMediaManager.CLIENT_MODE || mode == JmfMediaManager.BIDIRECTIONAL_MODE) {
            // Create a Receiver for the specified jmf locator
            
            String result = createReceiver();
            if (result != null) {
                 started = false;
            }
            else {
                started = true;
            }
            return null;

        } if (started == false ) {
        	System.err.println("Mode: " + mode + " not suported");
            started = false;
        }
        	
        return null;
    }

    /**
     * Stops the transmission if already started.
     * Stops the receiver also.
     */
    public void stop() {
        if (!started) return;
        synchronized (this) {
            try {
                started = false;
                if (processor != null) {
                    processor.stop();
                    processor = null;

                    for (RTPManager rtpMgr : rtpMgrs) {
                        rtpMgr.removeReceiveStreamListener(videoReceiver);
                        rtpMgr.removeSessionListener(videoReceiver);
                        rtpMgr.removeTargets("Session ended.");
                        rtpMgr.dispose();
                    }

                    sendStreams.clear();
                    success = false;

                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
  
    private String createProcessor() {
        
    			
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

    private String createReceiver() 
    {
    	
        if(success == true) return null;
    	SessionAddress localAddr, destAddr;
        InetAddress ipAddr;
        
        videoReceiver = new VideoReceiver(this, jingleMediaSession);
        int port;

        
        try {
        	RTPManager manager = RTPManager.newInstance();
            port = portBase + 2;
            ipAddr = InetAddress.getByName(remoteIpAddress);

            localAddr = new SessionAddress(InetAddress.getByName(this.localIpAddress), localPort);

            destAddr = new SessionAddress(ipAddr, port);

            manager.addReceiveStreamListener(videoReceiver);
            manager.addSessionListener(videoReceiver);

            BufferControl bc = (BufferControl) manager.getControl("javax.media.control.BufferControl");
            if (bc != null) {
                int bl = 160;
                bc.setBufferLength(bl);
            }

            try {

                manager.initialize(localAddr);

            }
            catch (InvalidSessionAddressException e) {
                // In case the local address is not allowed to read, we user another local address
                SessionAddress sessAddr = new SessionAddress();
                localAddr = new SessionAddress(sessAddr.getDataAddress(), localPort);
                manager.initialize(localAddr);
            }

            manager.addTarget(destAddr);

            System.err.println("Receiver: Created RTP session at " + localPort + " to: " + remoteIpAddress + " " + port);
        }
        catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
        
        return null;
    }

     /**
     * Use the RTPManager API to create sessions for each jmf
     * track of the processor.
     *
     * @return description
     */
    private String createTransmitter() {

        // Cheated.  Should have checked the type.
        PushBufferDataSource pbds = (PushBufferDataSource) dataOutput;
        PushBufferStream pbss[] = pbds.getStreams();

        rtpMgrs = new RTPManager[pbss.length];
        SessionAddress localAddr, destAddr;
        InetAddress ipAddr;
        SendStream sendStream;
        videoReceiver = new VideoReceiver(this, jingleMediaSession);
        int port;

        for (int i = 0; i < pbss.length; i++) {
            try {
                rtpMgrs[i] = RTPManager.newInstance();

                port = portBase + 2 * i;
                ipAddr = InetAddress.getByName(remoteIpAddress);

                localAddr = new SessionAddress(InetAddress.getByName(this.localIpAddress),
                        localPort);

                destAddr = new SessionAddress(ipAddr, port);

                rtpMgrs[i].addReceiveStreamListener(videoReceiver);
                rtpMgrs[i].addSessionListener(videoReceiver);

                BufferControl bc = (BufferControl) rtpMgrs[i].getControl("javax.media.control.BufferControl");
                if (bc != null) {
                    int bl = 160;
                    bc.setBufferLength(bl);
                }

                try {

                    rtpMgrs[i].initialize(localAddr);

                }
                catch (InvalidSessionAddressException e) {
                    // In case the local address is not allowed to read, we user another local address
                    SessionAddress sessAddr = new SessionAddress();
                    localAddr = new SessionAddress(sessAddr.getDataAddress(),
                            localPort);
                    rtpMgrs[i].initialize(localAddr);
                }

                rtpMgrs[i].addTarget(destAddr);

//                System.err.println("Transmitter: Created RTP session at " + localPort + " to: " + remoteIpAddress + " " + port);

                sendStream = rtpMgrs[i].createSendStream(dataOutput, i);

                sendStreams.add(sendStream);

                sendStream.start();
                success = true;
            }
            catch (Exception e) {
                e.printStackTrace();
                return e.getMessage();
            }
        }

        return null;
    }

    /**
     * Set transmit activity. If the active is true, the instance should trasmit.
     * If it is set to false, the instance should pause transmit.
     *
     * @param active active state
     */
    public void setTrasmit(boolean active) {
        for (SendStream sendStream : sendStreams) {
            try {
                if (active) {
                    sendStream.start();
                    System.out.println("START");
                }
                else {
                    sendStream.stop();
                    System.out.println("STOP");
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * *************************************************************
     * Convenience methods to handle processor's state changes.
     * **************************************************************
     */

    private Integer stateLock = 0;
    private boolean failed = false;

    Integer getStateLock() {
        return stateLock;
    }

    void setFailed() {
        failed = true;
    }

    private synchronized boolean waitForState(Processor p, int state) {
        p.addControllerListener(new StateListener());
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

    public static void main(String args[]) {

    	/*
        InetAddress localhost;
        try {
            localhost = InetAddress.getLocalHost();

           // VideoChannel audioChannel0 = new VideoChannel(new MediaLocator("javasound://8000"), localhost.getHostAddress(), localhost.getHostAddress(), 7002, 7020, new AudioFormat(AudioFormat.GSM_RTP), null);
           // VideoChannel audioChannel1 = new VideoChannel(new MediaLocator("javasound://8000"), localhost.getHostAddress(), localhost.getHostAddress(), 7020, 7002, new AudioFormat(AudioFormat.GSM_RTP), null);

            audioChannel0.start();
            audioChannel1.start();

            try {
                Thread.sleep(5000);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }

            audioChannel0.setTrasmit(false);
            audioChannel1.setTrasmit(false);

            try {
                Thread.sleep(5000);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }

            audioChannel0.setTrasmit(true);
            audioChannel1.setTrasmit(true);

            try {
                Thread.sleep(5000);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }

            audioChannel0.stop();
            audioChannel1.stop();
			
        }
        catch (UnknownHostException e) {
            e.printStackTrace();
        }
		*/
    }
}