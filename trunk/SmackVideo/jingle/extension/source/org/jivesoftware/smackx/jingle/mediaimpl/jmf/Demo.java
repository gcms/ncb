/**
 * $RCSfile$
 * $Revision: $
 * $Date: 28/12/2006
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

import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.Connection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smackx.jingle.JingleSession;
import org.jivesoftware.smackx.jingle.JingleManager;
import org.jivesoftware.smackx.jingle.JingleSessionRequest;
import org.jivesoftware.smackx.jingle.listeners.JingleSessionRequestListener;
import org.jivesoftware.smackx.jingle.nat.*;
import org.jivesoftware.smack.*;
import org.jivesoftware.smack.packet.*;
import org.jivesoftware.smackx.jingle.media.JingleMediaManager;

import javax.swing.*;
import java.awt.event.ActionEvent;

import java.util.ArrayList;

/**
 * Jingle Demo Application. It register in a XMPP Server and let users place calls using a full JID and auto-receive calls.
 * Parameters: Server User Pass.
 */
public class Demo extends JFrame {

    private JingleTransportManager transportManager = null;
    private Connection xmppConnection = null;

    private String server = null;
    private String user = null;
    private String pass = null;

    private JingleManager jm = null;
    private JingleSession incoming = null;
    private JingleSession outgoing = null;

    private JTextField jid = new JTextField(30);

    private ArrayList<JingleManager> jManagers = new ArrayList<JingleManager>();
    private ArrayList<JingleSession> iSessions = new ArrayList<JingleSession>();
    private ArrayList<JingleSession> oSessions = new ArrayList<JingleSession>();
    
    public Demo(String server, String user, String pass) {

        this.server = server;
        this.user = user;
        this.pass = pass;

        xmppConnection = new XMPPConnection(server);
        try {
            xmppConnection.connect();
            xmppConnection.login(user, pass);
            initJingleManager(xmppConnection,"audio_manager");
            initJingleManagerV(xmppConnection,"video_manager");
            initialize();
        }
        catch (XMPPException e) {
            e.printStackTrace();
        }
    }

/*    public void initialize() {
        ICETransportManager icetm0 = new ICETransportManager(xmppConnection, "jivesoftware.com", 3478);
        jm = new JingleManager(xmppConnection, icetm0, new JmfMediaManager("video_manager",2));
        jm.addCreationListener(icetm0);

        jm.addJingleSessionRequestListener(new JingleSessionRequestListener() {
            public void sessionRequested(JingleSessionRequest request) {

                if (incoming != null)
                    return;

                try {
                    // Accept the call
                    incoming = request.accept();

                    // Start the call
                    incoming.start();
                }
                catch (XMPPException e) {
                    e.printStackTrace();
                }

            }
        });

        createGUI();
    }
*/
    
    public void initJingleManager(Connection xmppConnection,final String mediamgr_name){
    	transportManager = new ICETransportManager(xmppConnection,
    			"jivesoftware.com", 3478);
    	JmfMediaManager jmm = new JmfMediaManager(mediamgr_name,transportManager);
    	ArrayList<JingleMediaManager> jingleMediaManagers = new ArrayList<JingleMediaManager>();
    	jingleMediaManagers.add(jmm);
    	JingleManager jm = new JingleManager(xmppConnection, jingleMediaManagers);
    	jm.addCreationListener((ICETransportManager)transportManager);
    	jManagers.add(jm);

    	jm.addJingleSessionRequestListener(new JingleSessionRequestListener() {
    		public void sessionRequested(JingleSessionRequest request) {

    			/*if (incoming != null)
    				return;
    				*/
    			String type = mediamgr_name;
    			String tmp = request.getJingle().toXML();
    			if(tmp.contains("rgb")) {
    				System.out.println("Video detected");
    				return;
    			}
    			System.out.println("Type is "+tmp);
    			
    			JingleSession incoming = null;
    			try {
    				// Accept the call
    				incoming = request.accept();

    				// Start the call
    				incoming.start();
    			}
    			catch (XMPPException e) {
    				e.printStackTrace();
    			}
    			iSessions.add(incoming);
    		}
    	});
    }
    public void initJingleManagerV(Connection xmppConnection,final String mediamgr_name){
    	transportManager = new ICETransportManager(xmppConnection,
    			"jivesoftware.com", 3478);
    	JmfMediaManager jmm = new JmfMediaManager(mediamgr_name,transportManager);
    	ArrayList<JingleMediaManager> jingleMediaManagers = new ArrayList<JingleMediaManager>();
    	jingleMediaManagers.add(jmm);
    	JingleManager jm = new JingleManager((Connection)xmppConnection, jingleMediaManagers);
    	jm.addCreationListener((ICETransportManager)transportManager);
    	jManagers.add(jm);

    	jm.addJingleSessionRequestListener(new JingleSessionRequestListener() {
    		public void sessionRequested(JingleSessionRequest request) {

    			/*if (incoming != null)
    				return;
    				*/
    			String type = mediamgr_name;
    			String tmp = request.getJingle().toXML();
    			if(tmp.contains("gsm")) {
    				System.out.println("Audio detected");
    				return;
    			
    			}
    			System.out.println("Type is "+tmp);
    			
    			JingleSession incoming = null;
    			try {
    				// Accept the call
    				incoming = request.accept();

    				// Start the call
    				incoming.start();
    			}
    			catch (XMPPException e) {
    				e.printStackTrace();
    			}
    			iSessions.add(incoming);
    		}
    	});
    }


    public void initialize(){
    	createGUI();
    }

    public void createGUI() {

        JPanel jPanel = new JPanel();

        jPanel.add(jid);

        jPanel.add(new JButton(new AbstractAction("Voice") {
            public void actionPerformed(ActionEvent e) {
                /*if (outgoing != null) return;
                */
            	JingleSession outgoing = null;
            	try {
                    outgoing = jManagers.get(0).
                    createOutgoingJingleSession(jid.getText());
                    outgoing.start();
                    oSessions.add(outgoing);
                }
                catch (XMPPException e1) {
                    e1.printStackTrace();
                }
            }
        }));

        jPanel.add(new JButton(new AbstractAction("Video") {
            public void actionPerformed(ActionEvent e) {
            	JingleSession outgoing = null;
/*                if (outgoing != null) return;
  */
            	try {
                    outgoing = jManagers.get(1).
                    createOutgoingJingleSession(jid.getText());
                     outgoing.start();
                     oSessions.add(outgoing);
            	}
                catch (XMPPException e1) {
                    e1.printStackTrace();
                }
            }
        }));

/*        jPanel.add(new JButton(new AbstractAction("Video") {
            public void actionPerformed(ActionEvent e) {
            	OutgoingJingleSession outgoing = null;
                if (outgoing != null) return;
  
            	try {
                    outgoing = jm.createOutgoingJingleSession(jid.getText());
                    outgoing.start();
                }
                catch (XMPPException e1) {
                    e1.printStackTrace();
                }
            }
        }));
*/
        jPanel.add(new JButton(new AbstractAction("Hangup") {
            public void actionPerformed(ActionEvent e) {
                if (outgoing != null)
                    try {
                        outgoing.terminate();
                    }
                    catch (XMPPException e1) {
                        e1.printStackTrace();
                    }
                    finally {
                        outgoing = null;
                    }
                if (incoming != null)
                    try {
                        incoming.terminate();
                    }
                    catch (XMPPException e1) {
                        e1.printStackTrace();
                    }
                    finally {
                        incoming = null;
                    }
            }
        }));

    jPanel.add(new JButton(new AbstractAction("SendMessage") {
        public void actionPerformed(ActionEvent e) {
//        	 Assume we've created an XMPPConnection name "connection".
        	System.out.println("here");
        	ChatManager chatmanager = xmppConnection.getChatManager();
        	Chat newChat = chatmanager.createChat("andrew.a.allen@gmail.com", new MessageListener() {
        	    public void processMessage(Chat chat, Message message) {
        	        System.out.println("Received message: " + message.getBody());
        	    }
        	});

        	try {
        	    newChat.sendMessage("Howdy!");
        	}
        	catch (XMPPException e2) {
        	    System.out.println("Error Delivering block");
        	}

   }}));

    this.add(jPanel);

}
    

    public static void main(String args[]) {

        Demo demo = null;

        if (args.length > 2) {
            demo = new Demo(args[0], args[1], args[2]);
            demo.pack();
            demo.setVisible(true);
            demo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }

    }

}
