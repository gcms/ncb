package org.jivesoftware.smackx.jingle.mediaimpl.jmf;

import javax.media.ControllerErrorEvent;
import javax.media.ControllerEvent;
import javax.media.ControllerListener;
import javax.media.Player;
import javax.media.RealizeCompleteEvent;
import javax.media.protocol.DataSource;
import javax.media.rtp.Participant;
import javax.media.rtp.RTPControl;
import javax.media.rtp.ReceiveStream;
import javax.media.rtp.ReceiveStreamListener;
import javax.media.rtp.SessionListener;
import javax.media.rtp.event.ByeEvent;
import javax.media.rtp.event.NewParticipantEvent;
import javax.media.rtp.event.NewReceiveStreamEvent;
import javax.media.rtp.event.ReceiveStreamEvent;
import javax.media.rtp.event.RemotePayloadChangeEvent;
import javax.media.rtp.event.SessionEvent;
import javax.media.rtp.event.StreamMappedEvent;

import org.jivesoftware.smackx.jingle.media.JingleMediaSession;

import java.io.*;
import java.awt.*;
import java.net.*;
import java.awt.event.*;
import java.util.Vector;

import javax.media.*;
import javax.media.rtp.*;
import javax.media.rtp.event.*;
import javax.media.rtp.rtcp.*;
import javax.media.protocol.*;
import javax.media.protocol.DataSource;
import javax.media.format.AudioFormat;
import javax.media.format.VideoFormat;
import javax.media.Format;
import javax.media.format.FormatChangeEvent;
import javax.media.control.BufferControl;


public class VideoReceiver implements ReceiveStreamListener, SessionListener,
        ControllerListener {

    boolean dataReceived = false;
	protected static javax.swing.event.EventListenerList m_ListenerList = new javax.swing.event.EventListenerList();
	
    Object dataSync;
    JingleMediaSession jingleMediaSession;
    Vector playerWindows = null;
    
    public VideoReceiver(final Object dataSync, final JingleMediaSession jingleMediaSession) {
        this.dataSync = dataSync;
        this.jingleMediaSession = jingleMediaSession;
        playerWindows = new Vector();
    }

    /**
     * JingleSessionListener.
     */
    public synchronized void update(SessionEvent evt) {
        if (evt instanceof NewParticipantEvent) {
            Participant p = ((NewParticipantEvent) evt).getParticipant();
            System.err.println("  - A new participant had just joined: " + p.getCNAME());
        }
    }

    /**
     * ReceiveStreamListener
     */
    public synchronized void update(ReceiveStreamEvent evt) {

        Participant participant = evt.getParticipant(); // could be null.
        ReceiveStream stream = evt.getReceiveStream();  // could be null.

        if (evt instanceof RemotePayloadChangeEvent) {
            System.err.println("  - Received an RTP PayloadChangeEvent.");
            System.err.println("Sorry, cannot handle payload change.");

        }
        else if (evt instanceof NewReceiveStreamEvent) {

            try {
                stream = evt.getReceiveStream();
                DataSource ds = stream.getDataSource();
                
                jingleMediaSession.mediaReceived(participant != null ? participant.getCNAME() : "");

                // Notify intialize() that a new stream had arrived.
                synchronized (dataSync) {
                    dataReceived = true;
                    dataSync.notifyAll();
                }
                
                fireImageEventReady(ds);
        		// Find out the formats.
        		RTPControl ctl = (RTPControl)ds.getControl("javax.media.rtp.RTPControl");
        		if (ctl != null){
        		    System.err.println("  - Recevied new RTP stream: " + ctl.getFormat());
        		} else
        		    System.err.println("  - Recevied new RTP stream");

        		if (participant == null)
        		    System.err.println("      The sender of this stream had yet to be identified.");
        		else {
        		    System.err.println("      The stream comes from: " + participant.getCNAME()); 
        		}

        		// create a player by passing datasource to the Media Manager
        		Player p = javax.media.Manager.createPlayer(ds);
        		if (p == null)
        		    return;

        		p.addControllerListener(this);
        		p.realize();
        		PlayerWindow pw = new PlayerWindow(p, stream);
        		playerWindows.addElement(pw);
            }
            catch (Exception e) {
                System.err.println("NewReceiveStreamEvent exception " + e.getMessage());
                return;
            }

        }
        else if (evt instanceof StreamMappedEvent) {

            if (stream != null && stream.getDataSource() != null) {
                DataSource ds = stream.getDataSource();
                // Find out the formats.
                RTPControl ctl = (RTPControl) ds.getControl("javax.jmf.rtp.RTPControl");
                System.err.println("  - The previously unidentified stream ");
                if (ctl != null)
                    System.err.println("      " + ctl.getFormat());
                System.err.println("      had now been identified as sent by: " + participant.getCNAME());
            }
        }
        else if (evt instanceof ByeEvent) {
            System.err.println("  - Got \"bye\" from: " + participant.getCNAME());
        }
    }

    /**
     * ControllerListener for the Players.
     */
/*    public synchronized void controllerUpdate(ControllerEvent ce) {

        Player p = (Player) ce.getSourceController();

        if (p == null)
            return;

        // Get this when the internal players are realized.
        if (ce instanceof RealizeCompleteEvent) {
            p.start();
        }

        if (ce instanceof ControllerErrorEvent) {
            p.removeControllerListener(this);
            System.err.println("Receiver internal error: " + ce);
        }

    }
 */  
    //  This methods allows classes to register for MyEvents
    public static void addImageEventReadyListener(ImageEventReadyListener p_Listener) {
    	m_ListenerList.add(ImageEventReadyListener.class, p_Listener);
    }

    // This methods allows classes to unregister for MyEvents
    public static void removeImageEventReadyListener(ImageEventReadyListener p_Listener) {
    	m_ListenerList.remove(ImageEventReadyListener.class, p_Listener);
    }

    // This private class is used to fire MyEvents
    public void fireImageEventReady(DataSource ds) {
    	Object[] lListeners = m_ListenerList.getListenerList();
    	
        for (int i=0; i<lListeners.length; i+=2) {
            if (lListeners[i]==ImageEventReadyListener.class) {
                ((ImageEventReadyListener)lListeners[i+1]).ImageEventReady(ds);
            }
        }
    }
    
    /**
     * ControllerListener for the Players.
     */
    public synchronized void controllerUpdate(ControllerEvent ce) {

	Player p = (Player)ce.getSourceController();

	if (p == null)
	    return;

	// Get this when the internal players are realized.
	if (ce instanceof RealizeCompleteEvent) {
	    PlayerWindow pw = find(p);
	    if (pw == null) {
		// Some strange happened.
		System.err.println("Internal error!");
		System.exit(-1);
	    }
	    pw.initialize();
	    pw.setVisible(true);
	    p.start();
	}

	if (ce instanceof ControllerErrorEvent) {
	    p.removeControllerListener(this);
	    PlayerWindow pw = find(p);
	    if (pw != null) {
		pw.close();	
		playerWindows.removeElement(pw);
	    }
	    System.err.println("AVReceive3 internal error: " + ce);
	}

    }

    PlayerWindow find(Player p) {
    	for (int i = 0; i < playerWindows.size(); i++) {
    	    PlayerWindow pw = (PlayerWindow)playerWindows.elementAt(i);
    	    if (pw.player == p)
    		return pw;
    	}
    	return null;
        }


        PlayerWindow find(ReceiveStream strm) {
    	for (int i = 0; i < playerWindows.size(); i++) {
    	    PlayerWindow pw = (PlayerWindow)playerWindows.elementAt(i);
    	    if (pw.stream == strm)
    		return pw;
    	}
    	return null;
        }

    /**
     * A utility class to parse the session addresses.
     */
    class SessionLabel {

	public String addr = null;
	public int port;
	public int ttl = 1;

	SessionLabel(String session) throws IllegalArgumentException {

	    int off;
	    String portStr = null, ttlStr = null;

	    if (session != null && session.length() > 0) {
		while (session.length() > 1 && session.charAt(0) == '/')
		    session = session.substring(1);

		// Now see if there's a addr specified.
		off = session.indexOf('/');
		if (off == -1) {
		    if (!session.equals(""))
			addr = session;
		} else {
		    addr = session.substring(0, off);
		    session = session.substring(off + 1);
		    // Now see if there's a port specified
		    off = session.indexOf('/');
		    if (off == -1) {
			if (!session.equals(""))
			    portStr = session;
		    } else {
			portStr = session.substring(0, off);
			session = session.substring(off + 1);
			// Now see if there's a ttl specified
			off = session.indexOf('/');
			if (off == -1) {
			    if (!session.equals(""))
				ttlStr = session;
			} else {
			    ttlStr = session.substring(0, off);
			}
		    }
		}
	    }

	    if (addr == null)
		throw new IllegalArgumentException();

	    if (portStr != null) {
		try {
		    Integer integer = Integer.valueOf(portStr);
		    if (integer != null)
			port = integer.intValue();
		} catch (Throwable t) {
		    throw new IllegalArgumentException();
		}
	    } else
		throw new IllegalArgumentException();

	    if (ttlStr != null) {
		try {
		    Integer integer = Integer.valueOf(ttlStr);
		    if (integer != null)
			ttl = integer.intValue();
		} catch (Throwable t) {
		    throw new IllegalArgumentException();
		}
	    }
	}
    }

    /**
     * GUI classes for the Player.
     */
    class PlayerWindow extends Frame {

	Player player;
	ReceiveStream stream;

	PlayerWindow(Player p, ReceiveStream strm) {
	    player = p;
	    stream = strm;
	}

	public void initialize() {
	    add(new PlayerPanel(player));
	}

	public void close() {
	    player.close();
	    setVisible(false);
	    dispose();
	}

	public void addNotify() {
	    super.addNotify();
	    pack();
	}
    }

    /**
     * GUI classes for the Player.
     */
    class PlayerPanel extends Panel {

	Component vc, cc;

	PlayerPanel(Player p) {
	    setLayout(new BorderLayout());
	    if ((vc = p.getVisualComponent()) != null)
		add("Center", vc);
	    if ((cc = p.getControlPanelComponent()) != null)
		add("South", cc);
	}

	public Dimension getPreferredSize() {
	    int w = 0, h = 0;
	    if (vc != null) {
		Dimension size = vc.getPreferredSize();
		w = size.width;
		h = size.height;
	    }
	    if (cc != null) {
		Dimension size = cc.getPreferredSize();
		if (w == 0)
		    w = size.width;
		h += size.height;
	    }
	    if (w < 160)
		w = 160;
	    return new Dimension(w, h);
	}
    }

}
