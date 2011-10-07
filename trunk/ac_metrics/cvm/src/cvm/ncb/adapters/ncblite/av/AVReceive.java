/* @author Mario J Lorenzo */ package cvm.ncb.adapters.ncblite.av;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Label;
import java.awt.Panel;
import java.net.InetAddress;
import java.util.Vector;

import javax.media.ControllerErrorEvent;
import javax.media.ControllerEvent;
import javax.media.ControllerListener;
import javax.media.Player;
import javax.media.RealizeCompleteEvent;
import javax.media.control.BufferControl;
import javax.media.protocol.DataSource;
import javax.media.rtp.Participant;
import javax.media.rtp.RTPControl;
import javax.media.rtp.RTPManager;
import javax.media.rtp.ReceiveStream;
import javax.media.rtp.ReceiveStreamListener;
import javax.media.rtp.SessionAddress;
import javax.media.rtp.SessionListener;
import javax.media.rtp.event.ByeEvent;
import javax.media.rtp.event.NewParticipantEvent;
import javax.media.rtp.event.NewReceiveStreamEvent;
import javax.media.rtp.event.ReceiveStreamEvent;
import javax.media.rtp.event.RemotePayloadChangeEvent;
import javax.media.rtp.event.SessionEvent;
import javax.media.rtp.event.StreamMappedEvent;

import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

import cvm.model.*;

/**
 * AVReceive2 to receive RTP transmission using the new RTP API.
 */
public class AVReceive implements ReceiveStreamListener, SessionListener,
		ControllerListener {
	String sessions[] = null;

	RTPManager RTPmgrs[] = null;

	Vector playerWindows = null;

	boolean dataReceived = false;

	static Object dataSync = new Object();
	static Object videoSync = new Object();

	//private Composite parent = null;
	
	private static Composite videoComposites[] = null;

	private final int MAX_VIDEO_WINDOWS = 4;
	private int cw = -1;
	private int window = 4;
	private static AVReceive instance = null;
	private boolean sessionStarted = false;
	
	protected AVReceive() {
		videoComposites = new Composite[MAX_VIDEO_WINDOWS];
	}
	
	public static AVReceive getInstance() {
		if(instance == null) {
		instance = new AVReceive();
		}
		return instance;
	}
		
	public boolean addSessions(String sessions[]) {
		if (sessionStarted) return true;
		this.sessions = sessions;
		try {
			InetAddress ipAddr;
			SessionAddress localAddr = new SessionAddress();
			SessionAddress destAddr;

			RTPmgrs = new RTPManager[sessions.length];
			playerWindows = new Vector();

			SessionLabel session;

			// Open the RTP sessions.
			for (int i = 0; i < sessions.length; i++) {

				// Parse the session addresses.
				try {
					session = new SessionLabel(sessions[i]);
				} catch (IllegalArgumentException e) {
					System.err
							.println("Failed to parse the session address given: "
									+ sessions[i]);
					return false;
				}

				CVM_Debug.getInstance().printDebugErrorMessage("  - Open RTP session for: addr: "
						+ session.addr + " port: " + session.port + " ttl: "
						+ session.ttl);

				RTPmgrs[i] = (RTPManager) RTPManager.newInstance();
				RTPmgrs[i].addSessionListener(this);
				RTPmgrs[i].addReceiveStreamListener(this);

				ipAddr = InetAddress.getByName(session.addr);

				if (ipAddr.isMulticastAddress()) {
					// local and remote address pairs are identical:
					localAddr = new SessionAddress(ipAddr, session.port,
							session.ttl);
					destAddr = new SessionAddress(ipAddr, session.port,
							session.ttl);
				} else {
					localAddr = new SessionAddress(InetAddress.getLocalHost(),
							session.port);
					destAddr = new SessionAddress(ipAddr, session.port);
				}

				RTPmgrs[i].initialize(localAddr);

				// You can try out some other buffer size to see
				// if you can get better smoothness.
				BufferControl bc = (BufferControl) RTPmgrs[i]
						.getControl("javax.media.control.BufferControl");
				if (bc != null)
					bc.setBufferLength(350);

				RTPmgrs[i].addTarget(destAddr);
			}

		} catch (Exception e) {
			CVM_Debug.getInstance().printDebugErrorMessage("Cannot create the RTP Session: "
					+ e.getMessage());
			return false;
		}
		sessionStarted = true;
		return true;

	}
	
	/*public AVReceive(String sessions[]) {
		this.sessions = sessions;
	}*/

	public void addComposite(final Composite parent) {
		synchronized (videoSync) {
			for(int i=0;i<videoComposites.length;i++) {
				if(videoComposites[i]==null) continue;
				if(videoComposites[i].equals(parent)) return;
			}
			videoComposites[MAX_VIDEO_WINDOWS-window] = parent;
			window--;
			CVM_Debug.getInstance().printDebugErrorMessage("AVR - Composite added");
		}
	}
	
	public boolean initialize(Composite parent) {
		//videoComposites[MAX_VIDEO_WINDOWS-window] = parent;
		//window++;
/*		this.parent = parent;
		try {
			InetAddress ipAddr;
			SessionAddress localAddr = new SessionAddress();
			SessionAddress destAddr;

			RTPmgrs = new RTPManager[sessions.length];
			playerWindows = new Vector();

			SessionLabel session;

			// Open the RTP sessions.
			for (int i = 0; i < sessions.length; i++) {

				// Parse the session addresses.
				try {
					session = new SessionLabel(sessions[i]);
				} catch (IllegalArgumentException e) {
					System.err
							.println("Failed to parse the session address given: "
									+ sessions[i]);
					return false;
				}

				CVM_Debug.getInstance().printDebugErrorMessage("  - Open RTP session for: addr: "
						+ session.addr + " port: " + session.port + " ttl: "
						+ session.ttl);

				RTPmgrs[i] = (RTPManager) RTPManager.newInstance();
				RTPmgrs[i].addSessionListener(this);
				RTPmgrs[i].addReceiveStreamListener(this);

				ipAddr = InetAddress.getByName(session.addr);

				if (ipAddr.isMulticastAddress()) {
					// local and remote address pairs are identical:
					localAddr = new SessionAddress(ipAddr, session.port,
							session.ttl);
					destAddr = new SessionAddress(ipAddr, session.port,
							session.ttl);
				} else {
					localAddr = new SessionAddress(InetAddress.getLocalHost(),
							session.port);
					destAddr = new SessionAddress(ipAddr, session.port);
				}

				RTPmgrs[i].initialize(localAddr);

				// You can try out some other buffer size to see
				// if you can get better smoothness.
				BufferControl bc = (BufferControl) RTPmgrs[i]
						.getControl("javax.media.control.BufferControl");
				if (bc != null)
					bc.setBufferLength(350);

				RTPmgrs[i].addTarget(destAddr);
			}

		} catch (Exception e) {
			CVM_Debug.getInstance().printDebugErrorMessage("Cannot create the RTP Session: "
					+ e.getMessage());
			return false;
		}

		// Wait for data to arrive before moving on.

		long then = System.currentTimeMillis();
		long waitingPeriod = 60000; // wait for a maximum of 30 secs.
		CVM_Debug.getInstance().printDebugErrorMessage("  - Waiting for RTP data to arrive...");
		try {
			synchronized (dataSync) {
				while (!dataReceived
						&& System.currentTimeMillis() - then < waitingPeriod) {
					if (!dataReceived)
						System.err.print(".");
					dataSync.wait(1000);
				}
			}
		} catch (Exception e) {
		}

		if (!dataReceived) {
			CVM_Debug.getInstance().printDebugErrorMessage("No RTP data was received.");
			close();
			return false;
		}
*/
		return true;
	}

	public boolean isDone() {
		return playerWindows.size() == 0;
	}

	/**
	 * Close the players and the session managers.
	 */
	protected void close() {

		for (int i = 0; i < playerWindows.size(); i++) {
			try {
				((PlayerWindow) playerWindows.elementAt(i)).close();
			} catch (Exception e) {
			}
		}

		playerWindows.removeAllElements();

		// close the RTP session.
		for (int i = 0; i < RTPmgrs.length; i++) {
			if (RTPmgrs[i] != null) {
				RTPmgrs[i].removeTargets("Closing session from AVReceive2");
				RTPmgrs[i].dispose();
				RTPmgrs[i] = null;
			}
		}
	}

	PlayerWindow find(Player p) {
		for (int i = 0; i < playerWindows.size(); i++) {
			PlayerWindow pw = (PlayerWindow) playerWindows.elementAt(i);
			if (pw.player == p)
				return pw;
		}
		return null;
	}

	PlayerWindow find(ReceiveStream strm) {
		for (int i = 0; i < playerWindows.size(); i++) {
			PlayerWindow pw = (PlayerWindow) playerWindows.elementAt(i);
			if (pw.stream == strm)
				return pw;
		}
		return null;
	}

	/**
	 * SessionListener.
	 */
	public synchronized void update(SessionEvent evt) {
		if (evt instanceof NewParticipantEvent) {
			Participant p = ((NewParticipantEvent) evt).getParticipant();
			CVM_Debug.getInstance().printDebugErrorMessage("  - A new participant had just joined: "
					+ p.getCNAME());
		}
	}

	/**
	 * ReceiveStreamListener
	 */
	public synchronized void update(ReceiveStreamEvent evt) {
		//final int local_window;
		RTPManager mgr = (RTPManager) evt.getSource();
		Participant participant = evt.getParticipant(); // could be null.
		ReceiveStream stream = evt.getReceiveStream(); // could be null.

		if (evt instanceof RemotePayloadChangeEvent) {

			CVM_Debug.getInstance().printDebugErrorMessage("  - Received an RTP PayloadChangeEvent.");
			CVM_Debug.getInstance().printDebugErrorMessage("Sorry, cannot handle payload change.");
			return;

		}

		else if (evt instanceof NewReceiveStreamEvent) {

			try {
				stream = ((NewReceiveStreamEvent) evt).getReceiveStream();
				DataSource ds = stream.getDataSource();
				
				// Find out the formats.
				RTPControl ctl = (RTPControl) ds
						.getControl("javax.media.rtp.RTPControl");
				if (ctl != null) {
					CVM_Debug.getInstance().printDebugErrorMessage("  - Recevied new RTP stream: "
							+ ctl.getFormat());
					if(ctl.getFormat().toString().equalsIgnoreCase("JPEG/RTP")) {
						cw++;
					}
					
				} else {
					CVM_Debug.getInstance().printDebugErrorMessage("  - Recevied new RTP stream");
					return;
				}
				final int local_window = cw;	
				if (participant == null) {
					System.err
							.println("      The sender of this stream had yet to be identified.");
					if(ctl.getFormat().toString().equalsIgnoreCase("JPEG/RTP")) {
						CVM_Debug.getInstance().printDebugErrorMessage("AVR - checking if composite is ready");
						synchronized (dataSync) {
							//while(videoComposites[cw]==null) {
								dataSync.wait(5000);
								System.err.print(".");
							//}
						}
					}
				}
				else {
					CVM_Debug.getInstance().printDebugErrorMessage("      The stream comes from: "
							+ participant.getCNAME());
					return;
				}

				// create a player by passing datasource to the Media Manager
				if(ctl.getFormat().toString().equalsIgnoreCase("JPEG/RTP")) {
					
					final Player p = javax.media.Manager.createPlayer(ds);
					if (p == null)
						return;
						
					p.addControllerListener(this);
					p.realize();
					// TODO
					final ReceiveStream stm = stream;
					synchronized (videoSync) {
					Display.getDefault().syncExec(new Runnable() {
						public void run() {
							PlayerWindow pw = new PlayerWindow(videoComposites[local_window]);
							pw.populateData(p, stm);
							playerWindows.addElement(pw);

						}
					});
					}
				} else {
					final Player p = javax.media.Manager.createPlayer(ds);
					if (p == null)
						return;
						
					p.addControllerListener(this);
					p.realize();
					// TODO
					final ReceiveStream stm = stream;
					synchronized (dataSync) {
					Display.getDefault().syncExec(new Runnable() {
						public void run() {
							PlayerWindow pw = new PlayerWindow();
							pw.populateData(p, stm);
							playerWindows.addElement(pw);

						}
					});
					}
				}



				// frame.show();

				/*
				 * Component comp = p.getVisualComponent();
				 * 
				 * if(comp != null) { Frame frame = SWT_AWT.getFrame(parent);
				 * frame.add(comp);
				 * 
				 *  }
				 */

				// Notify intialize() that a new stream had arrived.
				synchronized (dataSync) {
					dataReceived = true;
					dataSync.notifyAll();
				}

			} catch (Exception e) {
				CVM_Debug.getInstance().printDebugErrorMessage("NewReceiveStreamEvent exception "
						+ e.getMessage());
				return;
			}

		}

		else if (evt instanceof StreamMappedEvent) {

			if (stream != null && stream.getDataSource() != null) {
				DataSource ds = stream.getDataSource();
				// Find out the formats.
				RTPControl ctl = (RTPControl) ds
						.getControl("javax.media.rtp.RTPControl");
				CVM_Debug.getInstance().printDebugErrorMessage("  - The previously unidentified stream ");
				if (ctl != null)
					CVM_Debug.getInstance().printDebugErrorMessage("      " + ctl.getFormat());
				CVM_Debug.getInstance().printDebugErrorMessage("      had now been identified as sent by: "
						+ participant.getCNAME());
			}
		}

		else if (evt instanceof ByeEvent) {

			CVM_Debug.getInstance().printDebugErrorMessage("  - Got \"bye\" from: "
					+ participant.getCNAME());
			PlayerWindow pw = find(stream);
			if (pw != null) {
				pw.close();
				playerWindows.removeElement(pw);
			}
		}

	}

	/**
	 * ControllerListener for the Players.
	 */
	public synchronized void controllerUpdate(ControllerEvent ce) {

		Player p = (Player) ce.getSourceController();

		if (p == null)
			return;

		// Get this when the internal players are realized.
		if (ce instanceof RealizeCompleteEvent) {
			PlayerWindow pw = find(p);
			if (pw == null) {
				// Some strange happened.
				CVM_Debug.getInstance().printDebugErrorMessage("Internal error!");
				return;
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
			CVM_Debug.getInstance().printDebugErrorMessage("AVReceive2 internal error: " + ce);
		}

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

	class PlayerWindow{

		Player player;
		ReceiveStream stream;
		final Composite parent;
		//private Shell sShell = null;
		//private Composite composite = null;

		public PlayerWindow() {
			parent = null;
		}
		
		public PlayerWindow(final Composite parent) {
			createShell();
			//CVM_Debug.getInstance().printDebugErrorMessage("AVR - "+videoComposites.length);
			this.parent = parent;
			
			
		}
		
		private void createShell() {
			
			//sShell = new Shell();
			//sShell.setLayout(new GridLayout());
			//composite = new Composite(sShell, SWT.EMBEDDED);
			
		}

		public void populateData(Player p, ReceiveStream strm) {
			player = p;
			stream = strm;
		}

		public void initialize() {

			if (parent==null) return;
			Display.getDefault().syncExec(new Runnable() {
				public void run() {
					Frame frame = SWT_AWT.new_Frame(parent);
					Component vis = player.getVisualComponent();
					if(vis != null)
						frame.add(vis);
					//frame.add(new PlayerPanel(player));
					//frame.setVisible(true);
					//sShell.open();
				}
			});
		}
		
		public void setVisible(final boolean value) {
		
			if(parent==null)return;
			Display.getDefault().syncExec(new Runnable() {
				public void run() {
					if(value) {
						GridData data = new GridData(GridData.FILL_HORIZONTAL);
						data.grabExcessVerticalSpace = true;
						data.grabExcessHorizontalSpace = true;
						data.horizontalAlignment = GridData.FILL;
						data.verticalAlignment = GridData.FILL;
						parent.setLayoutData(data);
			
					} else {
						parent.setVisible(false);
					}
				}
			});
			

			
		}

		public void close() {
			player.close();
		}

		/*
		 * public void addNotify() { super.addNotify(); pack(); }
		 */
	}

	/**
	 * GUI classes for the Player.
	 */
/*	class PlayerPanel extends Panel {

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
	}*/
	/*
	 * public static void main(String argv[]) { if (argv.length == 0) prUsage();
	 * 
	 * AVReceive avReceive = new AVReceive(argv); if (!avReceive.initialize()) {
	 * CVM_Debug.getInstance().printDebugErrorMessage("Failed to initialize the sessions.");
	 * System.exit(-1); }
	 *  // Check to see if AVReceive2 is done. try { while (!avReceive.isDone())
	 * Thread.sleep(1000); } catch (Exception e) {}
	 * 
	 * CVM_Debug.getInstance().printDebugErrorMessage("Exiting AVReceive2"); }
	 * 
	 * 
	 * static void prUsage() { CVM_Debug.getInstance().printDebugErrorMessage("Usage: AVReceive2 <session>
	 * <session> ..."); CVM_Debug.getInstance().printDebugErrorMessage(" <session>: <address>/<port>/<ttl>");
	 * System.exit(0); }
	 */
}// end of AVReceive2

