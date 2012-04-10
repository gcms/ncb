package cvm.ncb.adapters;

import com.skype.*;
import com.skype.connector.*;
import com.skype.connector.TimeOutException;
import util.CVM_Debug;

import java.util.*;

/**
 * This is an adapter to the Skype interface. This class wraps around
 * the skype4java API calls.
 * For starters it performs the basic operations that are required by
 * the basic implementation of CVM but it can be extended to perform
 * all the operations allowed by Skype with the uses of Skype  call and chat
 * objects and the connector object.
 * <p/>
 * <p/>
 * Date: 01/13/2008
 *
 * @author Frank Hernandez
 * @see Connector
 */
public class SkypeAdapter extends NCBBridgeBase {

    //OLD Approach
    public boolean b_SendLock = false;
    private Random r = new Random(System.currentTimeMillis());
    private int count = 100000 + r.nextInt(100000);
    private volatile boolean b_mapLock;
    private Map<String, Chat> m_hmChatSessionMap;
    private Map<String, Call> m_hmCallSessionMap;
    private Map<String, ArrayList<Call>> m_hmCallListMap;
    private Map<String, Stream> m_hmStreamUserMap;
    private Connector m_kcConector;
    private String m_sWelcomeMess = "Help Code Monkey Ninjas Are After Me !!!! XP";
    private Application m_appSkypeApp;
    private String m_sAppName = "AuthentifiactionApp";
    private boolean m_bAppConnected = false;
    public boolean m_bConnectorLocked = false;
    private int nConnectorDelay = 2000;
    private SkypeAdapterDataContainer dataContainer;

    private enum MEDIUM {TEXT, AUDIO, VIDEO}

    ;
    private ArrayList<MEDIUM> m_MediumsEnabled = new ArrayList<MEDIUM>();

    private final String SchemaTagOpen = "<NEGOTIATION";
    private final String SchemaTagClose = "</NEGOTIATION>";
    private final String CallAddTagOpen = "<CALL>";
    private final String CallAddTagClose = "</CALL>";
    private final String CallRemTagOpen = "<CALLR>";
    private final String CallRemTagClose = "</CALLR>";
    private final String ChatAddTagOpen = "<CHAT>";
    private final String ChatAddTagClose = "</CHAT>";
    //NEW Approach
    //Maps Sessions to SignalInstance Ids
    private Map<String, ArrayList<String>> m_hmSessionCallIdMap;
    private Map<String, ArrayList<String>> m_hmSessionChatIdMap;
    private Map<String, ArrayList<String>> m_hmSessionIdMap;
    /*
      * These are use for the listening side of this application
      * There is a disagreement with CVM and Skype in the sense that
      * Skype Already adds the participants and creates the calls while
      * CVM has will try to recreate this calls and in turns cause trouble with
      * skype. With these update structures, CVM checks if the calls/chats
      * he is trying to stablish with this participant already exists
      * then rather than creating the new calls just copies the call/chat ids
      * to the main chat/call system.
      */
    private Map<String, ArrayList<String>> m_hmCallUpdateMap;
    private Map<String, ArrayList<String>> m_hmChatUpdateMap;
    private Map<String, ArrayList<String>> m_hmCallUpdateMapRem;
    private Map<String, ArrayList<String>> m_hmChatUpdateMapRem;

    private ArrayList<String> m_SchemasRecevied = new ArrayList<String>();
    //private SkypeAdapterThread schemaThread = null;

    public SkypeAdapter() {

        init();
    }

    private void init() {
        //OLD
        m_hmCallSessionMap = new HashMap<String, Call>();
        m_hmChatSessionMap = new HashMap<String, Chat>();
        m_hmCallListMap = new HashMap<String, ArrayList<Call>>();
        m_hmStreamUserMap = new HashMap<String, Stream>();
        m_kcConector = Connector.getInstance();

        //NEW
        m_hmSessionCallIdMap = new HashMap<String, ArrayList<String>>();
        m_hmSessionChatIdMap = new HashMap<String, ArrayList<String>>();
        m_hmCallUpdateMap = new HashMap<String, ArrayList<String>>();
        m_hmChatUpdateMap = new HashMap<String, ArrayList<String>>();
        m_hmCallUpdateMapRem = new HashMap<String, ArrayList<String>>();
        m_hmChatUpdateMapRem = new HashMap<String, ArrayList<String>>();

        m_hmSessionIdMap = new HashMap<String, ArrayList<String>>();
        dataContainer = new SkypeAdapterDataContainer(this);
        //Add Listener
        addMessageListener();
        //schemaThread = new SkypeAdapterThread(this);
        //schemaThread.start();
        //Add Listener
        //addMessageListener();
    }

    private void resetFW() {
        //OLD
        m_hmCallSessionMap = null;
        m_hmChatSessionMap = null;
        m_hmCallListMap = null;
        m_hmStreamUserMap = null;
        m_kcConector = null;

        //NEW
        m_hmSessionCallIdMap = null;
        m_hmSessionChatIdMap = null;
        m_hmCallUpdateMap = null;
        m_hmChatUpdateMap = null;
        m_hmCallUpdateMapRem = null;
        m_hmChatUpdateMapRem = null;

        m_hmSessionIdMap = null;
        //SkypeAdapterDataContainer. .getInstance();
        //Add Listener
        //addMessageListener();
        //schemaThread = new SkypeAdapterThread(this);
        //schemaThread.start();
        //Add Listener
        //addMessageListener();

    }

    /**
     * Returns the device capabilities.
     */
    public String getCapability() {
        String caps = null;
        try {
            String temp = null;
            temp = Skype.getAudioOutputDevice();
            caps = "Skype - ";
            caps = temp == null ? "null" : NCBBridgeCapability.AUDIO_OUTPUT;
            temp = Skype.getAudioInputDevice();
            caps = caps + ", " + (temp == "null" ? temp : NCBBridgeCapability.AUDIO_INPUT);
            temp = Skype.getVideoDevice();
            caps = caps + " , " + (temp == "null" ? temp : NCBBridgeCapability.VIDEO);
            Skype.setVideoDevice(null);
            Skype.setAudioInputDevice(null);

        } catch (SkypeException e) {

        }

        return caps;
    }

    /**
     * Returns a user object after login
     */
    @Method(name = "login", parameters = {})
    public void login() {
    }

    /**
     * Logs the user out.
     */
    @Method(name = "logout", parameters = {})
    public void logout() {

    }

    /**
     * Restarts the adapter.
     */
    public void restartService() {
        resetFW();
        init();
    }

    /**
     * This function creates a session in the Networks Bridge
     * for the specified session ID.
     *
     * @param session Id of the session to create.
     */
    @Method(name = "createSession", parameters = {"session"})
    public void createSession(String session) {
        System.out.println("Skype Start:" + System.currentTimeMillis());
        if (!(m_hmCallSessionMap.containsKey(session)
                && m_hmChatSessionMap.containsKey(session))) {
            m_hmChatSessionMap.put(session, null);
            m_hmCallSessionMap.put(session, null);
            m_hmCallListMap.put(session, null);

            //New Way
            m_hmSessionCallIdMap.put(session, null);
            m_hmSessionChatIdMap.put(session, null);
            CVM_Debug.getInstance().printDebugMessage("SkypeAdapter: Session created- " + session + ".");

        }
    }

    /**
     * Adds a participant to the session.
     *
     * @param session     Id of the session to add.
     * @param participant Id of the participant to add. i.e useranem: crinsomkairos.
     * @throws PartyNotAddedException
     * @throws NoSessionException
     * @throws ConnectorException
     */
    public void addParticipant(String session, String participant) {

        ArrayList<String> tempList;
        if (m_hmSessionIdMap.containsKey(session)) {

            tempList = m_hmSessionIdMap.get(session);
            if (!tempList.contains(participant)) {
                tempList.add(participant);
                CVM_Debug.getInstance().printDebugMessage("SkypeAdapter: Participant Added- " + participant + ". SessionID = " + session + ".");
                m_hmSessionIdMap.put(session, tempList);
            } else return;
        } else {
            tempList = new ArrayList<String>();
            tempList.add(participant);
            m_hmSessionIdMap.put(session, tempList);
            CVM_Debug.getInstance().printDebugMessage("SkypeAdapter: Participant Added- " + participant + ".");

        }

        //CVM_Debug.getInstance().printDebugMessage(sID+ ", "+ participantID);
        //this.addParticipantToCall(sID, participantID);
        this.addParticpantToApplication(participant);
        //this.addParticipantToChat(sID, participantID);

    }

    /**
     * This function is used to enable a communicaciot medium. This will activate the
     * audio in the communication or chat...
     *
     * @param session
     * @param medium
     * @throws PartyNotAddedException
     * @throws NoSessionException
     */
    @Method(name = "enableMedium", parameters = {"session", "medium"})
    public void enableMedium(String session, String medium) {
        String sID = session;//"S1";
        if (m_hmSessionIdMap.containsKey(sID)) {
            ArrayList<String> tempList;
            String participantID = null;
            tempList = m_hmSessionIdMap.get(sID);
            //For every perticipant add them to the new medium.
            for (int i = 0; tempList != null && i < tempList.size(); i++) {
                participantID = tempList.get(i);
                if (medium.equals(NCBBridgeCapability.LIVE_AV)
                        || medium.equals("AUDIO") || medium.equals("VIDEO")) {
                    //CVM_Debug.getInstance().printDebugMessage(tempList.size());
                    //CVM_Debug.getInstance().printDebugMessage(participantID);
                    m_MediumsEnabled.add(MEDIUM.AUDIO);
                    this.addParticipantToCall(sID, participantID);
                    if (medium.equals("VIDEO") || medium.equals(
                            NCBBridgeCapability.LIVE_AV)) switchToVideo(sID);
                } else if (medium.equals(NCBBridgeCapability.TEXT)) {
                    m_MediumsEnabled.add(MEDIUM.TEXT);
                    this.addParticipantToChat(sID, participantID);
                }
            }
            CVM_Debug.getInstance().printDebugMessage("SkypeAdapter: Media Enabled- " + medium + ".");

        }
    }

    /**
     * This function is used to disable a communicaciot medium. This will deactivate the
     * audio in the communication or chat...
     *
     * @param session
     * @param medium
     * @throws PartyNotAddedException
     * @throws NoSessionException
     */
    @Method(name = "disableMedium", parameters = {"session", "medium"})
    public void disableMedium(String session, String medium) {
        String sID = session;//"S1";
        if (m_hmSessionIdMap.containsKey(sID)) {
            ArrayList<String> tempList;
            String participantID = null;
            tempList = m_hmSessionIdMap.get(sID);
            //For every perticipant add them to the new medium.
            for (int i = 0; tempList != null && i < tempList.size(); i++) {
                participantID = tempList.get(i);
                if (medium.equals(NCBBridgeCapability.LIVE_AV) || medium.equals(NCBBridgeCapability.AUDIO)) {
                    try {
                        this.removeParticipantFromCall(sID, participantID);
                    } catch (SkypeException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    m_MediumsEnabled.remove(MEDIUM.AUDIO);
                } else if (medium.equals(NCBBridgeCapability.TEXT)) {
                    //this.addParticipantToChat(sID, participantID);
                    //Unable tpo remove participants from chat in Skype.
                    //If I remember correctly.
                    m_MediumsEnabled.remove(MEDIUM.TEXT);

                }
            }
            System.out.println("Skype Disabled:" + System.currentTimeMillis());
            CVM_Debug.getInstance().printDebugMessage("SkypeAdapter: Media Disabled- " + medium + ".");

        }
    }

    /**
     * Helper function adds a participant to a chat session.
     *
     * @param sID           session ID
     * @param participantID [articipant ID, like a username.
     * @throws PartyNotAddedException
     * @throws NoSessionException
     */
    private void addParticipantToChat(String sID, String participantID) {
        ArrayList m_cList = null;
        String chatID = null;

        if (!m_hmSessionChatIdMap.containsKey(sID)) {
            //Throw no session exception ???
            Map<String, Object> params = new LinkedHashMap<String, Object>();
            params.put("session", sID);
            throw new EventException("NoSession", params);
        } else if (m_hmChatUpdateMap.containsKey(sID)) {
            //If there is dat to update
            m_cList = m_hmSessionChatIdMap.get(sID);
            if (m_cList == null) {
                m_cList = new ArrayList<String>();
            }

            ArrayList m_cList2 = m_hmChatUpdateMap.get(sID);
            boolean bParticipantFound = false;
            for (int i = 0; i < m_cList2.size(); i++) {
                chatID = (String) m_cList2.get(i);
                if (!m_cList.contains(chatID)) {
                    m_cList.add(chatID);

                    if (this.skypeGetProperty("CHAT", chatID, "MEMBERS").contains(participantID)) {
                        bParticipantFound = true;
                    }
                }
            }
            //Clear the updates.
            m_hmChatUpdateMap.remove(sID);
            m_hmSessionChatIdMap.put(sID, m_cList);
            if (!bParticipantFound) {
                this.addParticipantToChat(sID, participantID);
            }
        } else {
            String tempResponse = null;
            //Chat tempChat = null;
            try {
                //Get the chats id already for this session.
                m_cList = m_hmSessionChatIdMap.get(sID);

                //If the session has no chat...
                if (m_cList == null) {

                    //Create the chat if none exist.
                    chatID = this.skypeCreateChat(participantID);
                    m_cList = new ArrayList<String>();
                    m_cList.add(chatID);
                    //Register the Chat.
                    m_hmSessionChatIdMap.put(sID, m_cList);

                }
                chatID = (String) m_cList.get(0);
                //Add participant to chat.
                tempResponse = this.skypeAddParticipantChat(chatID, participantID);//m_kcConector.executeWithId("ALTER CHAT "+chatID+" ADDMEMBERS "+participantID, "USER");

                this.skypeSendMessageThroughChat(chatID, m_sWelcomeMess);
                //Open chat window on the invoker's side.
                tempResponse = this.skypeOpenChatWhindow(chatID);//m_kcConector.executeWithId("OPEN CHAT "+chatID, "USER");

            } catch (Exception e) {
                if (e instanceof TimeOutException)
                    ;
                else {
                    e.printStackTrace();
                    Map<String, Object> params = new LinkedHashMap<String, Object>();
                    params.put("participant", participantID);
                    throw new EventException("PartyNotAdded", params);
                }

                // Throw PartyNotAdded NCB Exception.
                //throw new PartyNotAddedException(participantID);
            }
        }
        /*
          if(!m_hmChatSessionMap.containsKey(sID))
          {
              //Throw no session exception ???
              throw new NoSessionException(sID);
          }
          else
          {
              String tempResponse = null;
              Chat tempChat = null;
              String chatID = null;
              try
              {
                  //Get chat object for this section if created.
                  tempChat = m_hmChatSessionMap.get(sID);
                  //If thei session has no chat...
                  if(tempChat==null)
                  {
                      tempChat = m_hmChatSessionMap.put(sID, Skype.chat(participantID));
                      tempChat = m_hmChatSessionMap.get(sID);

                  }
                  chatID = tempChat.getId();
                  //Add participant to chat.

                  tempResponse = this.skypeAddParticipantChat(chatID, participantID);//m_kcConector.executeWithId("ALTER CHAT "+chatID+" ADDMEMBERS "+participantID, "USER");

                  tempChat.send(m_sWelcomeMess);
                  //Open chat window on the invoker's side.
                  tempResponse = this.skypeOpenChatWhindow(chatID);//m_kcConector.executeWithId("OPEN CHAT "+chatID, "USER");

              }
              catch (Exception e)
              {
                  if(e instanceof TimeOutException)
                      ;
                  else
                  {
                      e.printStackTrace();
                      throw new PartyNotAddedException(participantID);
                  }

                  // Throw PartyNotAdded NCB Exception.
                  //throw new PartyNotAddedException(participantID);
              }
          }*/
    }

    private String[] skypeGetChatMember(String sChatID) {
        String sMembers = this.skypeGetProperty("CHAT", sChatID, "MEMBERS");
        String[] memberList = sMembers.split(" ");
        return memberList;
    }

    /**
     * This function excecutes the Skype command to add a member to
     * an existing chat session. This function behaves exactly like
     * the ALTER CHAT command on Skype.
     *
     * @param sChatID       Skype ID of the chat to add the members to.
     * @param participantID
     * @return
     */
    private String skypeAddParticipantChat(String sChatID, String participantID) {
        try {
            m_kcConector = Connector.getInstance();
            if (m_kcConector == null)
                return null;

            //CVM_Debug.getInstance().printDebugMessage(m_kcConector.getCommandTimeout());
            //while(m_kcConector.getStatus() == Connector.Status.PENDING_AUTHORIZATION);

            String response = m_kcConector.executeWithId("ALTER CHAT " + sChatID + " ADDMEMBERS " + participantID, "USER");
            //Utils.checkErrors();
            //while(m_kcConector.getStatus() == Connector.Status.PENDING_AUTHORIZATION
            //		|| m_kcConector.getStatus()!= Connector.Status.REFUSED);


            return response;
        } catch (ConnectorException e) {
            if (e instanceof TimeOutException)
                ;
            //e.printStackTrace();
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * This function createss a skype chat.
     *
     * @param sParticipantID Skype Username of the participant.
     * @return Chat ID
     */
    private String skypeCreateChat(String sParticipantID) {
        try {
            m_kcConector = Connector.getInstance();
            if (m_kcConector == null)
                return null;
            String response = m_kcConector.executeWithId("CHAT CREATE " + sParticipantID, "CHAT");
            String[] temp = response.split(" ");
            response = temp[1];
            return response;
        } catch (ConnectorException e) {
            e.printStackTrace();
        } catch (Exception e) {
        }
        return null;

    }

    /**
     * This function sends a message to the Skype Chat.
     *
     * @param sChatID Id of the chat.
     * @param sMess   Message to send.
     * @return Skype related response (Ignore)
     */
    private String skypeSendMessageThroughChat(String sChatID, String sMess) {
        try {
            m_kcConector = Connector.getInstance();
            if (m_kcConector == null)
                return null;
            String response = m_kcConector.executeWithId("CHATMESSAGE " + sChatID + " " + sMess, "CHAT");
            return response;
        } catch (ConnectorException e) {
            e.printStackTrace();
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * Helper function adds a participant to a call session.
     *
     * @param sID           session ID
     * @param participantID [articipant ID, like a username.
     * @throws PartyNotAddedException
     * @throws NoSessionException
     */
    private void addParticipantToCall(String sID, String participantID) {
        ArrayList m_cList = null;

        if (!m_hmSessionCallIdMap.containsKey(sID)) {
            CVM_Debug.getInstance().printDebugMessage("No session");

            //Throw no session exception ???
            Map<String, Object> params = new LinkedHashMap<String, Object>();
            params.put("session", sID);
            throw new EventException("NoSession", params);
        } else if (m_hmCallUpdateMap.containsKey(sID)) {
            /*
                * If the call that we are trying to create was
                * already created then then the call already exist
                * in Skype and attempting to create it might lead
                * to some issues. Instead perform a fake 'add'.
                */
            //If there is data to update
            m_cList = m_hmSessionCallIdMap.get(sID);
            //If no call are registered in this client, register the new ones.
            if (m_cList == null) {
                m_cList = new ArrayList<String>();
            }

            ArrayList m_cList2 = m_hmCallUpdateMap.get(sID);
            boolean bParticipantFound = false;
            for (int i = 0; i < m_cList2.size(); i++) {
                String sCallID = (String) m_cList2.get(i);
                if (!m_cList.contains(sCallID)) {
                    m_cList.add(sCallID);

                    if (this.skypeGetPartnerHandle(sCallID).equals(participantID)) {
                        bParticipantFound = true;

                    }
                }
            }
            //Clear the updates.
            m_hmCallUpdateMap.remove(sID);
            m_hmSessionCallIdMap.put(sID, m_cList);
            if (!bParticipantFound) {
                //If there is no current call with the participant to add
                //then add it.
                this.addParticipantToCall(sID, participantID);
            }
        } else {
            String tempResponse;
            String conferenceID = null;
            m_cList = null;
            String tempID = null, tempID2 = null;
            try {
                //Add participant to call.
                m_cList = m_hmSessionCallIdMap.get(sID);

                //If no call object exist.
                if (m_cList == null) {
                    //SignalInstance the User
                    tempID = this.callParticipant(participantID);
                    if (tempID == null) {
                        m_hmSessionCallIdMap.put(sID, null);
                        Map<String, Object> params = new LinkedHashMap<String, Object>();
                        params.put("participant", participantID);
                        throw new EventException("PartyNotAdded", params);
                    } else {
                        m_cList = new ArrayList<String>();
                        //Register the call ID.
                        m_cList.add(tempID);
                        m_hmSessionCallIdMap.put(sID, m_cList);
                    }

                } else {
                    //Hold conference.
                    holdConferenceCalls(sID);


                    conferenceID = (String) m_cList.get(0);

                    tempID2 = this.callParticipant(participantID);
                    if (tempID2 == null) {
                        CVM_Debug.getInstance().printDebugMessage(participantID + " , " + tempID);
                        Map<String, Object> params = new LinkedHashMap<String, Object>();
                        params.put("participant", participantID);
                        throw new EventException("PartyNotAdded", params);
                    } else {
                        if (!tempID2.equals("101")) {
                            //Add call to conference.
                            addCallToCallList(tempID2, sID);
                            //Add call to conference;
                            tempResponse = this.skypeAddParticipantCall(tempID2, conferenceID);
                        }
                        resumeConferenceCalls(sID);
                    }

                }


                //Notify all the users that a call was added
                //to prevent adding them again.
                notifyUsersOfCallAdded(sID);
            } catch (Exception e) {
                e.printStackTrace();

                // Throw PartyNotAdded NCB Exception.
                Map<String, Object> params = new LinkedHashMap<String, Object>();
                params.put("participant", participantID);
                throw new EventException("PartyNotAdded", params);
            }

        }

        /*
          if(!m_hmCallSessionMap.containsKey(sID))
          {
              ;
          }
          else
          {

                      tempCall = m_hmCallSessionMap.put(sID, Skype.call(participantID));
                      tempCall = m_hmCallSessionMap.get(sID);

                      //Wait while call is stablished.
                      while(tempCall.getStatus() != SignalInstance.Status.INPROGRESS
                              && tempCall.getStatus() != SignalInstance.Status.CANCELLED
                              && tempCall.getStatus() != SignalInstance.Status.FAILED
                              && tempCall.getStatus() != SignalInstance.Status.REFUSED);

                      //If the call cannot be placed notify.
                      if(tempCall.getStatus() == SignalInstance.Status.CANCELLED
                              || tempCall.getStatus() == SignalInstance.Status.FAILED
                              || tempCall.getStatus() == SignalInstance.Status.REFUSED)
                      {
                          m_hmCallSessionMap.put(sID,null);
                          throw new PartyNotAddedException(participantID);
                      }

                      else
                      {

                          m_cList = m_hmCallListMap.get(sID);
                          if(m_cList == null)
                              m_cList = new ArrayList<SignalInstance>();

                          //Add call to conference/
                          addCallToCallList(tempCall, sID);
                      }
                  }
                  else
                  {
                      //Hold conference.
                      holdConferenceCalls(sID);

                      //CVM_Debug.getInstance().printDebugMessage("Callee ID:"+ participantID);
                      tempCall2 = Skype.call(participantID);

                      conferenceID = tempCall.getId();

                      while(tempCall2.getStatus() != SignalInstance.Status.INPROGRESS
                              && tempCall2.getStatus() != SignalInstance.Status.CANCELLED
                              && tempCall2.getStatus() != SignalInstance.Status.FAILED
                              && tempCall2.getStatus() != SignalInstance.Status.REFUSED);

                      // If the call cannot be placed notify.
                      if(tempCall2.getStatus() == SignalInstance.Status.CANCELLED
                              || tempCall2.getStatus() == SignalInstance.Status.FAILED
                              || tempCall2.getStatus() == SignalInstance.Status.REFUSED)
                      {
                          //CVM_Debug.getInstance().printDebugMessage("SignalInstance not added: "+ tempCall2.getPartnerDisplayName());
                          //m_hmCallSessionMap.put(sID,null);
                          //throw new PartyNotAddedException(participantID);
                      }
                      else
                      {
                          //Add call to conference.
                          addCallToCallList(tempCall2, sID);
                          //CVM_Debug.getInstance().printDebugMessage("Conference ID : "+ tempCall.getId());
                          //Add call to conference;
                          tempResponse = this.skypeAddParticipantCall(tempCall2.getId(),conferenceID);
                          //CVM_Debug.getInstance().printDebugMessage("Response: "+ tempResponse);
                          //resumeConferenceCalls(sID);
                      }

                  }




              }
              catch (Exception e)
              {
                  e.printStackTrace();

                  // Throw PartyNotAdded NCB Exception.
                  throw new PartyNotAddedException(participantID);
              }
          }*/
    }

    /**
     * This function attamps to place a call with the specified user.
     *
     * @param participantID Handle of the user to call.
     * @return Id of the call or null for fail
     */
    private String callParticipant(String participantID) {
        CVM_Debug.getInstance().printDebugMessage(participantID);
        String tempID = this.skypePlaceCall(participantID);
        if (tempID.equals("101")) return tempID;
        CVM_Debug.getInstance().printDebugMessage("====>>>>>>" + tempID);
        try {
            while (!this.skypeGetCallStatus(tempID).equals(Call.Status.INPROGRESS.toString())
                    && !this.skypeGetCallStatus(tempID).equals(Call.Status.CANCELLED.toString())
                    && !this.skypeGetCallStatus(tempID).equals(Call.Status.FAILED.toString())
                    && !this.skypeGetCallStatus(tempID).equals(Call.Status.REFUSED.toString())) {
                //CVM_Debug.getInstance().printDebugMessage("====>>>>>>"+this.skypeGetCallStatus(tempID));
            }
        } catch (Exception e) {
        }
//		If the call cannot be placed notify.
        if (this.skypeGetCallStatus(tempID).equals(Call.Status.CANCELLED.toString())
                || this.skypeGetCallStatus(tempID).equals(Call.Status.FAILED.toString())
                || this.skypeGetCallStatus(tempID).equals(Call.Status.REFUSED.toString())) {
            //m_hmCallSessionMap.put(sID,null);
            //throw new PartyNotAddedException(participantID);
            tempID = null;
        }
        return tempID;
    }
    //private skypeSetCallProperty(String sCallID, String sProp)

    /**
     * This function excecutes the Skype command to add a member to
     * an existing call session. This function behaves exactly like
     * the ALTER CALL command on Skype.
     *
     * @param sCallID      Skype ID of the chat to add the members to.
     * @param conferenceID
     * @return
     */
    private String skypeAddParticipantCall(String sCallID, String conferenceID) {
        try {
            m_kcConector = Connector.getInstance();
            if (m_kcConector == null)
                return null;

            return m_kcConector.executeWithId("SET CALL " + sCallID + " JOIN_CONFERENCE " + conferenceID, "CALL");

        } catch (ConnectorException e) {
            e.printStackTrace();
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * This function call returns the status of the specified call.
     *
     * @param sCallID SignalInstance id.
     * @return Status of the call.
     */
    private String skypeGetCallStatus(String sCallID) {
        return this.skypeGetCallProperty(sCallID, "STATUS");
    }

    /**
     * This function returns the partner handle of the person on the other side
     * of the call.
     *
     * @param sCallID ID of the call.
     * @return Handle of the use r on the other side.
     */
    private String skypeGetPartnerHandle(String sCallID) {
        return this.skypeGetCallProperty(sCallID, "PARTNER_HANDLE");
    }

    /**
     * This function returns the partner handle of the person on the other side
     * of the call.
     *
     * @param sCallID ID of the call.
     * @return Handle of the use r on the other side.
     */
    private String skypeGetConfPartnerHandle(String sCallID, int nTH_member) {
        //Scanner scan = new Scanner(
        return this.skypeGetCallProperty(sCallID, "" +
                "CONF_PARTICIPANT " + nTH_member);
        //);
        //return scan.next();
    }

    /**
     * This function returns the property value of the call for the specified property.
     *
     * @param sCallID Id of the call to ge tthe property from.
     * @param sProp   Property to get.
     * @return Property value.
     */
    private String skypeGetCallProperty(String sCallID, String sProp) {
        return this.skypeGetProperty("CALL", sCallID, sProp);
        /*
          try
          {
              m_kcConector = Connector.getInstance();
              if(m_kcConector == null)
                  return null;

              String response;
              response = m_kcConector.executeWithId("GET CALL "+sCallID+ " "+sProp,"CALL");
              int npropPos = response.indexOf(sProp);
              response = response.substring(npropPos+sProp.length()+1, response.length());
              return response;
          }
          catch (ConnectorException e)
          {
              e.printStackTrace();
          }
          catch(Exception e)
          {
          }
          return null;
          */
    }

    private String skypeGetProperty(String sWhat, String sID, String sProp) {
        try {
            m_kcConector = Connector.getInstance();
            if (m_kcConector == null)
                return null;

            String response;
            response = m_kcConector.executeWithId("GET " + sWhat + " " + sID + " " + sProp, sWhat);
            int npropPos = response.indexOf(sProp);
            response = response.substring(npropPos + sProp.length() + 1, response.length());
            return response;
        } catch (ConnectorException e) {
            //e.printStackTrace();
            return null;
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * This function places a call using the Skype API
     *
     * @param sParticipantID Handle name of the user.
     * @return The ID of the call placed.
     */
    private String skypePlaceCall(String sParticipantID) {
        try {
            m_kcConector = Connector.getInstance();
            if (m_kcConector == null)
                return null;
            String response = m_kcConector.executeWithId("CALL " + sParticipantID, "CALL");
            String[] temp = response.split(" ");
            response = temp[1];
            return response;
        } catch (ConnectorException e) {
            System.out.println("skypePlaceCall :::::" + e);//.printStackTrace());
        } catch (Exception e) {
        }
        return null;

    }

    /**
     * This function places the give call on hold.
     *
     * @param sCallID ID of the call to hold.
     * @return responsE from SKYPE
     */
    private String skypeHoldCall(String sCallID) {
        String result = this.skypeAlterCall(sCallID, "HOLD");
        try {
            Thread.sleep(750);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }

    /**
     * This function resumes a call.
     *
     * @param sCallID ID of the call to hold.
     * @return respons from SKYPE
     */
    private String skypeResumeCall(String sCallID) {
        return this.skypeAlterCall(sCallID, "RESUME");
    }

    /**
     * This function finishes the give call.
     *
     * @param sCallID ID of the call to finish.
     * @return response from Skype.
     */
    private String skypeFinishCall(String sCallID) {
        return this.skypeAlterCall(sCallID, "HANGUP");
    }

    /**
     * This function places the specifed call in the specified status.
     *
     * @param sCallID Id of the call to alter.
     * @param sStatus Status to set.
     * @return
     */
    private synchronized String skypeAlterCall(String sCallID, String sStatus) {
        try {
            wait(nConnectorDelay);
            m_kcConector = Connector.getInstance();
            while (m_kcConector.connect() != Connector.Status.ATTACHED) ;
            while (m_kcConector == null) ;
            CVM_Debug.getInstance().printDebugMessage(" new Call status " + sStatus);

            String response;
            response = m_kcConector.execute("ALTER CALL " + sCallID + " " + sStatus);
            return response;
        } catch (ConnectorException e) {
            CVM_Debug.getInstance().printDebugMessage("SkypeAdapter: Connector Isuses OOOC.");
            //e.printStackTrace();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NullPointerException e) {
            CVM_Debug.getInstance().printDebugMessage("SkypeAdapter: Connector Isuses OOOC.");
        }

        return null;

    }

    /**
     * This function opens the chat window on the invoker's side.
     *
     * @param sChatID
     * @return response from Skype
     */
    private String skypeOpenChatWhindow(String sChatID) {
        try {
            m_kcConector = Connector.getInstance();
            if (m_kcConector == null)
                return null;

            return m_kcConector.executeWithId("OPEN CHAT " + sChatID, "USER");

        } catch (ConnectorException e) {
            if (e instanceof TimeOutException)
                ;
            else
                e.printStackTrace();
        } catch (Exception e) {
            //Skype Issue with Null pointer... Until I figure it out.
        }
        return null;
    }

    /**
     * Helper function that adds a call to the conference list.
     *
     * @param sCallID SignalInstance to add.
     * @param sID     Session to which to add the call.
     */
    private void addCallToCallList(String sCallID, String sID) {
        //Get list of call in this conference for the session ID
        //ArrayList m_cList =m_hmCallListMap.get(sID);
        ArrayList m_cList = m_hmSessionCallIdMap.get(sID);

        if (m_cList == null)
            m_cList = new ArrayList<Call>();

        //Add the call if it is not already in the list.
        if (!m_cList.contains(sCallID)) {
            m_cList.add(sCallID);
            //m_hmCallListMap.put(sID, m_cList);
            m_hmSessionCallIdMap.put(sID, m_cList);
        }
    }

    /**
     * Helper function to place all the calls in a conference on hold.
     *
     * @param sID Id of the conference session.
     * @throws SkypeException
     */
    private void holdConferenceCalls(String sID) throws SkypeException {
        ArrayList m_cList = null;
        String tempID = null;
        //Get list of calls
        m_cList = m_hmSessionCallIdMap.get(sID);
        //Place every call on the list in hold.
        for (int i = 0; i < m_cList.size(); i++) {
            tempID = (String) m_cList.get(i);
            //if(this.skypeGetCallStatus(tempID).equals(SignalInstance.VideoStatus. RUNNING))
            //	this.skypeAlterCall(tempID, "STOP_VIDEO_RECEIVE");
            if (this.skypeGetCallStatus(tempID).equals(Call.Status.INPROGRESS.toString())) {
                this.skypeHoldCall(tempID);
            } else if (this.skypeGetCallStatus(tempID).equals(Call.Status.FINISHED.toString())) {
                m_cList.remove(i);
                i--;
                m_hmSessionCallIdMap.put(sID, m_cList);
                //m_hmCallListMap.put(sID, m_cList);
            }
        }
    }

    /**
     * Helper function to resume all the calls in a conference.
     *
     * @param sID Id of the conference session.
     * @throws SkypeException
     */
    private void resumeConferenceCalls(String sID) throws SkypeException {
        ArrayList m_cList = null;
        String tempId = null;
        //Get list of calls
        m_cList = m_hmSessionCallIdMap.get(sID);
        //Place every call on the list in hold.
        for (int i = 0; i < m_cList.size(); i++) {
            tempId = (String) m_cList.get(i);
            //CVM_Debug.getInstance().printDebugMessage("SignalInstance ID: "+ tempCall.getId());
            if (this.skypeGetCallStatus(tempId).toString().contains("HOLD"))
                this.skypeResumeCall(tempId);
        }
        //CVM_Debug.getInstance().printDebugMessage("End of conference resume");

        /*
          ArrayList m_cList = null;
          SignalInstance tempCall = null;
          //Get list of calls
          m_cList = m_hmCallListMap.get(sID);
          //Place every call on the list in hold.
          for(int i = 0; i<m_cList.size(); i++)
          {
              tempCall = (SignalInstance)m_cList.get(i);
              //CVM_Debug.getInstance().printDebugMessage("SignalInstance ID: "+ tempCall.getId());
              if(tempCall.getStatus() == SignalInstance.Status.ONHOLD)
                  tempCall.resume();
          }
          //CVM_Debug.getInstance().printDebugMessage("End of conference resume");

           */
    }

    /**
     * Removes a participant to the session.
     *
     * @param sID           Id of the session to remove.
     * @param participantID Id of the participant to remove. i.e useranem: crinsomkairos.
     * @throws PartyNotFoundException
     * @throws NoSessionException
     */
    @Method(name = "removeParticipant", parameters = {"session", "participant"})
    public void removeParticipant(String sID, String participantID) {
        //if(m_hmCallSessionMap.containsKey(sID)
        //&& m_hmChatSessionMap.containsKey(sID))
        //{
        try {

            ArrayList<String> tempList;
            if (m_hmSessionIdMap.containsKey(sID)) {
                tempList = m_hmSessionIdMap.get(sID);
                if (tempList.contains(participantID)) {
                    //Remove Participant
                    tempList.remove(participantID);
                    m_hmSessionIdMap.put(sID, tempList);
                }
            }

            /*
                   //Add participant to call.
                   SignalInstance tempCall = m_hmCallSessionMap.get(sID);
                   String tempResponse = m_kcConector.executeWithId("", "USER");

                   //Add participant to chat.
                   Chat tempChat = m_hmChatSessionMap.get(sID);
                   tempResponse = m_kcConector.executeWithId("ALTER CHAT "+tempChat.getId()+" ADDMEMBERS "+participantID, "USER");
                   */
            if (m_MediumsEnabled.contains(MEDIUM.AUDIO))
                removeParticipantFromCall(sID, participantID);
        } catch (SkypeException e) {


            // Throw PartyNotAdded NCB Exception.
            Map<String, Object> params = new LinkedHashMap<String, Object>();
            params.put("participant", participantID);
            throw new EventException("PartyNotAdded", params);


        }

        //}

    }

    private void remPartFrmCall(String sID, String participantID) throws SkypeException {
        /*
           * 1 - Find the call with the partne name = participantID.
           * 2 - Finish the call.
           * 3 - Remove the call from the list of calls.
           * 4 - If there was a conference and the call removed is the one that is
           * 		kept in the main map replace it by the first one in the list.
           * 5 - If there was no conference only a 2 way call, then instantiate
           * 		the main call to null
           */

        //Check if the session Exists.
        if (!m_hmSessionCallIdMap.containsKey(sID)) {
            Map<String, Object> params = new LinkedHashMap<String, Object>();
            params.put("session", sID);
            throw new EventException("NoSession", params);
        }

        //Retrieve the call list.
        ArrayList m_cList = m_hmSessionCallIdMap.get(sID);
        String tempId1 = null, tempId2 = null;
        boolean bCallFound = false;

        if (m_cList == null) {
            Map<String, Object> params = new LinkedHashMap<String, Object>();
            params.put("participant", participantID);
            throw new EventException("PartyNotAdded", params);
        }

        CVM_Debug.getInstance().printDebugMessage("Removing: " + participantID + "" +
                "from " + m_cList.size() + " way");
        //Check for 2 way call
        if (m_cList.size() == 1) {
            tempId1 = (String) m_cList.get(0);
            ArrayList<String> tempList = m_hmSessionIdMap.get(sID);
            for (int i = 0; i < tempList.size(); i++) {
                String partyID = tempList.get(i);
                CVM_Debug.getInstance().printDebugMessage(partyID + " mapping" +
                        this.skypeGetConfPartnerHandle(tempId1, i));
            }
            CVM_Debug.getInstance().printDebugMessage(this.skypeGetPartnerHandle(tempId1));
            //if(this.skypeGetPartnerHandle(tempId1).equals(participantID))
            //{
            //End SignalInstance
            this.skypeFinishCall(tempId1);
            Skype.setVideoDevice(null);
            Skype.setAudioInputDevice(null);
            //Remove SignalInstance From List
            if (this.skypeGetPartnerHandle(tempId1) == null) {
                //m_cList.remove(0);
                //m_hmSessionCallIdMap.put(sID, null);
            }
            bCallFound = true;
            //}
        } else if (m_cList.size() > 1) {
            tempId1 = (String) m_cList.get(0);

            //Find the call with the participant
            for (int i = 0; i < m_cList.size(); i++) {
                tempId2 = (String) m_cList.get(i);
                if (this.skypeGetPartnerHandle(tempId2).equalsIgnoreCase(participantID)) {

                    //CVM_Debug.getInstance().printDebugMessage("SignalInstance partner ID " + tempCall2.getPartnerId());
                    CVM_Debug.getInstance().printDebugMessage("Participant ID " + participantID);
                    bCallFound = true;
                    //Finish the call.
                    this.skypeFinishCall(tempId2);
                    Skype.setVideoDevice(null);
                    Skype.setAudioInputDevice(null);
                    //Remove From conference call.
                    m_cList.remove(i);
                    /*if(this.skypeGetPartnerHandle(tempId2).equals(this.skypeGetPartnerHandle(tempId2)))
                         {
                             //Replace main call with the first in the list.
                             m_cList.remove(i);
                             m_hmSessionCallIdMap.put(sID, m_cList);

                         }*/
                    notifyUsersOfCallRemoved(sID, tempId2);
                    break;
                }
            }
        }
        if (!bCallFound) {
            //Throw NCB Exception.
            Map<String, Object> params = new LinkedHashMap<String, Object>();
            params.put("participant", participantID);
            throw new EventException("PartyNotAdded", params);
        }
    }

    /**
     * Removes a participant from a call.
     *
     * @param sID           Id of the session
     * @param participantID participant to remove;
     * @throws SkypeException
     * @throws PartyNotFoundException
     * @throws NoSessionException
     */
    private void removeParticipantFromCall(String sID, String participantID) throws SkypeException {
        ArrayList m_cList = m_hmCallUpdateMapRem.get(sID);
        boolean bPartFound = false;
        if (m_cList != null) {
            ArrayList m_MainList = m_hmSessionCallIdMap.get(sID);
            for (int i = 0; i < m_cList.size(); i++) {
                if (m_MainList.contains(m_cList.get(i))) {
                    /*
                          * If the call that we are trying to remove was
                          * already removed by another user
                          * then the call does no longer exist in Skype
                          * and attempting to close it might lead to some
                          * issues. Instead perform a fake remove.
                          */
                    m_MainList.remove(m_cList.get(i));

                    bPartFound = true;
                }

            }
            //Clear Remove Information after removal is done.
            m_hmCallUpdateMapRem.remove(sID);

            if (!bPartFound) {
                //Then participant to remove is not in the removed
                //info received from other users. Revemve.
                this.remPartFrmCall(sID, participantID);
            }
        } else
            this.remPartFrmCall(sID, participantID);
        /*
          ArrayList m_cList = m_hmCallListMap.get(sID);
          SignalInstance tempCall = null, tempCall2 = null;
          boolean bCallFound = false;

          if(m_cList == null)
              throw new PartyNotFoundException(participantID);

          //Check for 2 way call
          if(m_cList.size()==1)
          {
              tempCall = (SignalInstance)m_cList.get(0);
              //CVM_Debug.getInstance().printDebugMessage("Partner ID:" + tempCall.getPartnerId());
              //CVM_Debug.getInstance().printDebugMessage("Participant ID: " + participantID);
              if(tempCall.getPartnerId().equals(participantID))
              {
                  //tempCall.finish();
                  //End SignalInstance
                  tempCall.finish();
                  m_hmCallSessionMap.put(sID, null);
              }
          }
          else if(m_cList.size()>1)
          {
              tempCall = m_hmCallSessionMap.get(sID);

              //Find the call with the participant
              for(int i = 0; i< m_cList.size(); i++)
              {
                  tempCall2 = (SignalInstance)m_cList.get(i);
                  if(tempCall2.getPartnerId().equalsIgnoreCase(participantID))
                  {

                      //CVM_Debug.getInstance().printDebugMessage("SignalInstance partner ID " + tempCall2.getPartnerId());
                      //CVM_Debug.getInstance().printDebugMessage("Participant ID " + participantID);
                      bCallFound = true;
                      //Finish the call.
                      tempCall2.finish();
                      //Remove From conference call.
                      m_cList.remove(i);
                      if(tempCall2.getId() == tempCall.getId())
                      {
                          //Replace main call with the first in the list.
                          m_hmCallSessionMap.put(sID, (SignalInstance)m_cList.get(0));
                      }
                      break;
                  }
              }
          }
          if(!bCallFound)
          {
              //Throw NCB Exception.
              throw new PartyNotFoundException(participantID);
          }
          */
    }

    /**
     * Executs the command for Skype to end a call.
     *
     * @param sCallID Skype Id of the call to end
     * @return response generated by the execution of the commnad.
     */
    private String skypeRemoveParticipantCall(String sCallID) {
        try {
            m_kcConector = Connector.getInstance();
            if (m_kcConector == null)
                return null;

            return m_kcConector.execute("SET CALL " + sCallID + " STATUS FINISHED");
            //return m_kcConector.executeWithId("SET CALL "+sCallID+ " STATUS FINISHED","USER");

        } catch (ConnectorException e) {
            e.printStackTrace();
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * This function switches the call to video call.
     * Skype does not allow for conference video at the moment so only
     * one to one calls are switched to video.
     *
     * @param sessionID Id of the session to change.
     */
    public void switchToVideo(String sessionID) {
        ArrayList m_cList = m_hmSessionCallIdMap.get(sessionID);
        if (m_cList == null) {
            //Do nothing for now.
            //Notify later.
            //Video Cannot be Switched
        } else {
            if (m_cList != null && m_cList.size() > 1) {
                //Do nothing for now.
                //Notify later.
                //Video Cannot be Switched.
                //Skype has no conference Video.
            } else {
                //if(this.skypeGetCallProperty((String)m_cList.get(0), "VIDEO_STATUS").equals("VIDEO_NONE"))
                {
                    //Set the video receive.
                    CVM_Debug.getInstance().printDebugMessage("Video on");
                    skypeSetSendVideo((String) m_cList.get(0));
                    skypeSetReceiveVideo((String) m_cList.get(0));
                }
            }


        }
        /*
          SignalInstance tempCall = m_hmCallSessionMap.get(sessionID);
          ArrayList m_cList = m_hmCallListMap.get(sessionID);
          if(tempCall == null)
          {
              //Do nothing for now.
              //Notify later.
              //Video Cannot be Switched
          }
          else
          {
              if(m_cList!=null && m_cList.size()>1)
              {
                  //Do nothing for now.
                  //Notify later.
                  //Video Cannot be Switched.
                  //Skype has no conference Video.
              }
              else
              {
                  try
                  {
                      if(tempCall.getReceiveVideoStatus() != SignalInstance.VideoStatus.AVAILABLE)
                      {
                          //Set the video receive.
                          CVM_Debug.getInstance().printDebugMessage("Video on");
                          //skypeSetReceiveVideo(tempCall.getId());
                          tempCall.setReceiveVideoEnabled(true);
                      }
                  }
                  catch (SkypeException e)
                  {
                      // TODO Auto-generated catch block
                      e.printStackTrace();
                  }
              }



          }
          */
    }

    /**
     * Executs the command for Skype to start receiving video.
     *
     * @param sCallID Skype Id of the call to end
     * @return response generated by the execution of the commnad.
     */
    private String skypeSetReceiveVideo(String sCallID) {
        try {
            m_kcConector = Connector.getInstance();
            if (m_kcConector == null)
                return null;

            return m_kcConector.execute("ALTER CALL " + sCallID + " START_VIDEO_RECEIVE");

        } catch (ConnectorException e) {
            //TimeOutExceptions---
            //e.printStackTrace();
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * Executs the command for Skype to start sending video.
     *
     * @param sCallID Skype Id of the call to end
     * @return response generated by the execution of the commnad.
     */
    private String skypeSetSendVideo(String sCallID) {
        try {
            m_kcConector = Connector.getInstance();
            if (m_kcConector == null)
                return null;

            return m_kcConector.execute("ALTER CALL " + sCallID + " START_VIDEO_SEND");

        } catch (ConnectorException e) {
            //TimeOutExceptions---
            //e.printStackTrace();
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * This function transfer all the members of a chat into a
     * conference call.
     *
     * @param sID
     */
    public void transferFromChatToConference(String sID) {
        String tempChatID = m_hmSessionChatIdMap.get(sID).get(0);
        if (tempChatID != null) {
            try {
                String[] tempList = this.skypeGetChatMember(tempChatID);
                String tempUser = null;
                for (int i = 0; i < tempList.length; i++) {
                    tempUser = tempList[i];
                    //CVM_Debug.getInstance().printDebugMessage(tempUser.getId() + " " + Skype.getProfile().getId());
                    //If the user is other than self, make a call.
                    if (!tempUser.equals(Skype.getProfile().getId()))
                        this.addParticipantToCall(sID, tempUser);
                }
                //Leave The Chat
                //tempChat.leave();
                //m_hmChatSessionMap.put(sID, null);
            } catch (SkypeException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (EventException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        /*
          Chat tempChat = m_hmChatSessionMap.get(sID);
          if(tempChat != null)
          {
              try
              {
                  User tempList[] = tempChat.getAllMembers();
                  User tempUser = null;
                  for(int i = 0; i < tempList.length; i++)
                  {
                      tempUser = tempList[i];
                      //CVM_Debug.getInstance().printDebugMessage(tempUser.getId() + " " + Skype.getProfile().getId());
                      if(!tempUser.getId().equals(Skype.getProfile().getId()))
                          this.addParticipantToCall(sID, tempUser.getId());
                  }
                  //Leave The Chat
                  //tempChat.leave();
                  //m_hmChatSessionMap.put(sID, null);
              }
              catch (SkypeException e)
              {
                  // TODO Auto-generated catch block
                  e.printStackTrace();
              }
              catch(PartyNotAddedException e)
              {
                  /*
                   * Do nothing for now. All the parties in the communication
                   * are there since we are extracting the names from the list of
                   * users in the chat. This exception will never be fired.
                   */
        /*	e.printStackTrace();
              }
          }*/

    }

    /**
     * This function transfer all the members of a conference into a
     * chat session.
     *
     * @param sID
     * @throws NoSessionException
     * @throws PartyNotAddedException
     */
    public void transferFromConferenceToChat(String sID) {
        /*
           * 1 - Get all the calls involved in this session.
           * 2 - Retrieve the partner.
           * 3 - Add partner to chat
           * 4 - Finish the call.
           * 5 - Set session to null.
           */

        ArrayList<String> tempList = m_hmSessionCallIdMap.get(sID);
        //String tempChatID = m_hmSessionChatIdMap.get(sID);
        String tempMember = null;
        String tempCallID = null;
        for (int i = 0; i < tempList.size(); i++) {
            try {
                tempCallID = tempList.get(i);
                tempMember = this.skypeGetPartnerHandle(tempCallID);
                CVM_Debug.getInstance().printDebugMessage("Transfering to Chat: " + tempMember);
                //if(tempChat == null)
                //tempChat = Skype.chat(tempMember);
                //else
                this.addParticipantToChat(sID, tempMember);

                this.removeParticipantFromCall(sID, tempMember);
            } catch (SkypeException e) {
                //Do nothing for now.

            } catch (EventException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        this.skypeOpenChatWhindow(sID);
        /*
          ArrayList<SignalInstance>tempList = m_hmCallListMap.get(sID);
          Chat tempChat = m_hmChatSessionMap.get(sID);
          String tempMember = null;
          SignalInstance tempCall = null;
          for(int i=0; i<tempList.size(); i++)
          {
              try
              {
                  tempCall = tempList.get(i);
                  tempMember = tempCall.getPartnerId();
                  //if(tempChat == null)
                      //tempChat = Skype.chat(tempMember);
                  //else
                      this.addParticipantToChat(sID, tempMember);

                  tempCall.finish();
              }
              catch(SkypeException e)
              {
                  //Do nothing for now.

              }
          }
          this.skypeOpenChatWhindow(sID);
          */
    }

    /**
     * This funtion adds the message listener to Skype.
     */
    private void addMessageListener() {
        try {
            //Add the listener to catch the schema from skype.
            Skype.addChatMessageListener(AdapterSkypeChatMessageListener.getInstance(this));
            //Add the appplication.
            m_appSkypeApp = Skype.addApplication(m_sAppName);//SkypeAdapter.class.getName());
            //Add the appplication listener.
            m_appSkypeApp.addApplicationListener(new AdapterSkypeApplicationListener(this));
            //Add the Connector listener.
            Connector.getInstance().addConnectorListener(new AdapterSkypeConnectorListener(this));
            //Add SignalInstance Listener
            Skype.addCallListener(new AdapterSkypeCallListener());
            //Add bridege for all friends.
            //CVM_Debug.getInstance().printDebugMessage("Adding App Bridges");
        } catch (SkypeException e) {
            // TODO Auto-generated catch block
            CVM_Debug.getInstance().printDebugMessage("SkypeAdapter: addMessageListener: Listener not Added.");
            e.printStackTrace();
        } catch (ConnectorException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * This function adds a participant to the current application.
     * At the moment there is only one application running at any time.
     * This application is used for dealing with the authorization of the
     * communication that is done via schemas.
     *
     * @param participantID Id of the participant to Add.
     */
    public void addParticpantToApplication(String participantID) {
        try {
            //m_appSkypeApp.connect(Skype.getContactList().getFriend("yingbo07"));

            m_appSkypeApp.connect(Skype.getContactList().getFriend(participantID));


        } catch (SkypeException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            //throw NCB Exception
        }
    }

    /**
     * This function notifies NCB of a schema received event.
     *
     * @param schema schema file.
     */
    private void dealWithSchema(String schema) {
        CVM_Debug.getInstance().printDebugMessage("SkypeAdapter: dealWithSchema Called.");
        Event event = new Event("SchemaReceived");
        event.setParam("schema", schema);
        notify(event);
    }

    /**
     * Private this method handles all the schemas that have been
     * received and have yet to be sent to the upper layers.
     *
     * @throws InterruptedException
     */
    public synchronized void handledReceviedSchemas() throws InterruptedException {
        String temp = null;
        if (m_SchemasRecevied.isEmpty()) {
            CVM_Debug.getInstance().printDebugMessage("No Schemas= Waiting now.");
            wait();
        } else {

            CVM_Debug.getInstance().printDebugMessage("Dealing With Schemas");

            while (!m_SchemasRecevied.isEmpty()) {
                temp = m_SchemasRecevied.remove(0);
                dealWithSchema(temp);
            }
        }
    }

    /**
     * Places the schemas in a container for processing.
     *
     * @param arg0
     */
    public void enqueueSchemaForProcessing(String arg0) {
        m_SchemasRecevied.add(arg0);
    }

    public synchronized void signalSchemaReceived() {
        notify();
    }

    /**
     * This function checks if what was recieved was a schema.
     *
     * @param message Information received.
     * @return Schema found or not.
     */
    private boolean isSchema(String message) {
        //String schemaTagOpen = "<schema>", schemaTagClose = "</schema>";

        CVM_Debug.getInstance().printDebugMessage("SkypeAdapter: isSchema Called.");
        //Check for schema delimiters.
        String tempMess = message;
        tempMess = tempMess.toUpperCase();
        if (tempMess.contains(this.SchemaTagOpen) && tempMess.contains(this.SchemaTagClose)) {
            CVM_Debug.getInstance().printDebugMessage("SkypeAdapter - isSchema: Yes");
            return true;
        }
        return false;
    }

    public boolean isAppConnected() {
        return m_bAppConnected;
    }

    public void setAppConnected(boolean conn) {
        m_bAppConnected = conn;
    }
    //public registerCallIdForUpdate(String sCallID)

    /**
     * This function is used for converting the uypdate data that is received from
     * other users into a map that can be used by the system.
     *
     * @param callUpdateMap This is to be the map that the data will be added to.
     * @param sInfoLine     Must be of the form SessionID#ID1,ID2,ID3...
     * @return New update Map, null if an error ocurred.
     */
    private Map<String, ArrayList<String>> createInfromationMapValues(Map<String, ArrayList<String>> callUpdateMap, String sInfoLine) {
        String[] tempData = sInfoLine.split("#");
        if (tempData.length == 0)
            return null;

        String sSessId = tempData[0];
        String sTemp = tempData[1];
        tempData = sTemp.split(",");

        if (tempData.length == 0)
            return null;

        ArrayList m_cList = m_cList = callUpdateMap.get(sSessId);

        for (int i = 0; i < tempData.length; i++) {

            if (m_cList == null) {
                m_cList = new ArrayList<String>();
                m_cList.add(tempData[i]);
                callUpdateMap.put(sSessId, m_cList);
            } else {
                m_cList.add(tempData[i]);

            }

        }
        callUpdateMap.put(sSessId, m_cList);

        return callUpdateMap;
    }

    /**
     * This function is used for converting the uypdate data that is received from
     * other users into a map that can be used by the system.
     *
     * @param callUpdateMap This is to be the map that the data will be added to.
     * @param sInfoLine     Must be of the form SessionID#ID1,ID2,ID3...@SessionID2#ID1,ID2,ID3...@
     * @return New update Map, null if an error ocurred.
     */
    private Map<String, ArrayList<String>> createInfromationMap(Map<String, ArrayList<String>> callUpdateMap, String sInfoLine) {
        String[] tempData = sInfoLine.split("@");
        for (int i = 0; i < tempData.length; i++) {
            callUpdateMap = this.createInfromationMapValues(callUpdateMap, tempData[i]);
        }
        return callUpdateMap;
    }

    private void handleUpdateIncomingInformation(String sData) {
        String tempData = sData.toUpperCase();
        if (tempData.startsWith(this.CallAddTagOpen) && tempData.endsWith(this.CallAddTagClose)) {
            //If it is a call update perform call update.
            sData = sData.substring(this.CallAddTagOpen.length(), sData.length() - this.CallAddTagClose.length());
            m_hmCallUpdateMap = this.createInfromationMap(m_hmCallUpdateMap, sData);
            //CVM_Debug.getInstance().printDebugMessage("Keys: "+m_hmCallUpdateMap.keySet().toString());
            //CVM_Debug.getInstance().printDebugMessage("Values: "+m_hmCallUpdateMap.values().toString());
        } else if (tempData.startsWith(this.CallRemTagOpen) && tempData.endsWith(this.CallRemTagClose)) {
            //IF it is a call remove perform remove update.
            sData = sData.substring(this.CallRemTagOpen.length(), sData.length() - this.CallRemTagClose.length());
            m_hmCallUpdateMapRem = this.createInfromationMap(m_hmCallUpdateMapRem, sData);
        } else if (tempData.startsWith(this.ChatAddTagOpen) && tempData.endsWith(this.ChatAddTagClose)) {
            //Perform chat update add.
            sData = sData.substring(this.ChatAddTagOpen.length(), sData.length() - this.ChatAddTagClose.length());
            m_hmChatUpdateMap = this.createInfromationMap(m_hmChatUpdateMap, sData);
        }

    }

    /**
     * This function sends the schema file to the specified user.
     *
     * @param schema      Schema File.
     * @param participant Id of the user.
     */
    @Method(name = "sendSchema", parameters = {"schema", "participant"})
    public synchronized void sendSchema(String schema, String participant) {
        b_SendLock = true;
        CVM_Debug.getInstance().printDebugMessage("Schema Sent: " + schema + " \nTo: " + participant);
        try {
            if (!m_hmStreamUserMap.containsKey(participant)) {
                //Add the participant to the application bridge.s
                //setAppConnected(false);

                addParticpantToApplication(participant);
                //while(!isAppConnected());
                //setAppConnected(false);
            }
            //##Change recursion for while.
            if (m_hmStreamUserMap.get(participant) == null || m_hmStreamUserMap.get(participant).getFriend().getId().equals("")) {
                CVM_Debug.getInstance().printDebugMessage("No User - Retry Connection");
                addParticpantToApplication(participant);
                return;
                //sendSchema(schema, participantID);
                //m_hmStreamUserMap..remove(participantID);
            }

            //Write to that participant.
            CVM_Debug.getInstance().printDebugMessage("User ID: " + "'" + m_hmStreamUserMap.get(participant).getFriend().getId() + "'");
            m_hmStreamUserMap.get(participant).write(schema);
            CVM_Debug.getInstance().printDebugMessage("Schema SENT!!");
            /**
             * Bandiad solution, in lue of a l33t solution this will have to
             * do for now, until I figure out what the dela is with the schema
             * sneding and event fire. Odd bug, needs more thought.
             * Bug: After the events for a schema received are fired, during the sending
             * of the next schema the program hanngs, need to find out y
             */
            if (schema.contains("controlSchema"))
                dataContainer.sentCount++;


            //else
            {

                //Throw Party not found exception.
            }
        } catch (SkypeException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            //Throw SchemaNotSentException
        } finally {
            b_SendLock = false;
            //handledReceviedSchemas();
        }

    }

    /**
     * This function notifies all the users in the session
     * that a call has been added to skype a user outside of
     * their environment.
     *
     * @param sID Session in which the call was added.
     */
    private String notifyUsersOfCallAdded(String sID) {

        ArrayList<String> m_cList = m_hmSessionCallIdMap.get(sID);
        if (m_cList == null)
            return null;

        String sCallIds = "";
        int i;
        for (i = 0; i < m_cList.size() - 1; i++) {
            sCallIds = sCallIds + m_cList.get(i) + ",";
        }
        sCallIds = sCallIds + m_cList.get(i) + "@";
        //Prepare information line info;
        sCallIds = sID + "#" + sCallIds;
        //Add the tags to indicate that this is a call addition.
        String sMess = this.CallAddTagOpen + sCallIds + this.CallAddTagClose;

        String userToSend;
        for (i = 0; i < m_cList.size() - 1; i++) {
            //For every call get the Skype Handler.
            userToSend = this.skypeGetPartnerHandle(m_cList.get(i));

            try {
                if (m_hmStreamUserMap.get(userToSend) == null) return null;
                //Send the active calls to the user.
                m_hmStreamUserMap.get(userToSend).write(sMess);
            } catch (SkypeException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return "";
    }

    /**
     * This function notifies all the users in the session
     * that a call has been removed in skype by a user outside of
     * their environment.
     *
     * @param sID     Session in which the call was added.
     * @param sCallID Id of the call removed.
     */
    private String notifyUsersOfCallRemoved(String sID, String sCallID) {
        //Get the list of all the active calls in this session.
        ArrayList<String> m_cList = m_hmSessionCallIdMap.get(sID);
        if (m_cList == null)
            return null;

        String sCallIds = "";
        String sSkypeHandle;

        String sMess = sID + "#" + sCallID + "@";
        //Add the tags to indicate that this is a call addition.
        sMess = this.CallRemTagOpen + sCallIds + this.CallRemTagClose;

        //Notify every user in this session that a call was removed.
        for (int i = 0; i < m_cList.size(); i++) {
            sCallIds = m_cList.get(i);
            sSkypeHandle = this.skypeGetPartnerHandle(sCallIds);
            try {
                if (m_hmStreamUserMap.get(sSkypeHandle) == null) return null;
                m_hmStreamUserMap.get(sSkypeHandle).write(sMess);
            } catch (SkypeException e) {
                // TODO Auto-generated catch block
                //e.printStackTrace();
                return null;
            }
        }
        return "";
    }

    private static class AdapterSkypeCallListener implements CallListener {


        public void callMaked(Call arg0) throws SkypeException {
            // TODO Auto-generated method stub

        }


        public void callReceived(Call call) throws SkypeException {
            call.answer();
            CVM_Debug.getInstance().printDebugMessage("SkypeAdapter - Callid " + call.getId() + " status " + call.getReceiveVideoStatus());
            CVM_Debug.getInstance().printDebugMessage("SkypeAdapter - Callid " + call.getId() + " status " + call.getSendVideoStatus());
            try {
                if (call.getReceiveVideoStatus().equals(Call.VideoStatus.valueOf("RUNNING")) ||
                        call.getReceiveVideoStatus().equals(Call.VideoStatus.valueOf("STARTING"))) {
                    Connector.getInstance().execute("ALTER CALL " + call.getId() + " START_VIDEO_SEND");
                    Connector.getInstance().execute("ALTER CALL " + call.getId() + " START_VIDEO_RECEIVE");
                }
            } catch (ConnectorException e) {
                System.out.println(e);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    /**
     * This class implements the required methods for a ChatMessageListener,
     * This is used by Skype4Java to notify that message has been received.
     *
     * @author Frank Hernandez
     */
    private static class AdapterSkypeChatMessageListener implements ChatMessageListener {
        private static AdapterSkypeChatMessageListener instance = null;
        private SkypeAdapter m_saAdapter = null;

        private AdapterSkypeChatMessageListener(SkypeAdapter adapt) {
            m_saAdapter = adapt;

        }

        public static AdapterSkypeChatMessageListener getInstance(SkypeAdapter adapt) {
            if (instance == null)
                instance = new AdapterSkypeChatMessageListener(adapt);

            return instance;
        }

        public void chatMessageSent(ChatMessage sentChatMessage) throws SkypeException {

        }

        public void chatMessageReceived(ChatMessage received) throws SkypeException {
            /*
                         //CVM_Debug.getInstance().printDebugMessage("Chat message received:" + received.getContent());
                         if (received.getType().equals(ChatMessage.Type.SAID))
                         {
                             String message = received.getContent();
                             if(m_saAdapter.isSchema(message))
                             {
                                 m_saAdapter.dealWithSchema(message);
                             }
                             else
                             {
                                 //Forward to our own chat interface
                                 //Close SkypeChat.
                                 CVM_Debug.getInstance().printDebugMessage("SkypeAdapter: - Not Schema - Forward to GIU");
                             }
                         }
                         */
        }
    }

    /**
     * This class implements the required methods for a ApplicationListener.
     * For information on the functions Ask Skype Developer...
     *
     * @author Frank Hernandez.
     */
    private class AdapterSkypeApplicationListener implements ApplicationListener {

        private SkypeAdapter adapt = null;
        boolean bConnected = false;

        public AdapterSkypeApplicationListener(SkypeAdapter inAdapt) {
            adapt = inAdapt;
        }

        public void connected(Stream arg0) throws SkypeException {

            //Add stream Listener here.
            CVM_Debug.getInstance().printDebugMessage("SkypeAdapter - ApplicationListener: Called");
            arg0.addStreamListener(new AdapterSkypeStreamListern(adapt));
            //arg0.write("<schema>Testing Schecam Misc Information</schema>");

            //Register schema under the username
            String participantID = arg0.getFriend().getId();
            m_hmStreamUserMap.put(participantID, arg0);
            CVM_Debug.getInstance().printDebugMessage("ApplicationListener: Added " +
                    m_hmStreamUserMap.get(participantID).getFriend().getId());

            //Testing Schema Sent ###
            //String schema = "<schema>Testing Schema Misc Information</schema>";
            //sendSchema(schema,participantID);
            //adapt.setAppConnected(true);
        }

        public void disconnected(Stream arg0) throws SkypeException {
            //Remove the communication with the user.
            String participantID = arg0.getFriend().getId();
            m_hmStreamUserMap.remove(participantID);
            //TODO Also might want to notify of the user's leave.
        }

    }

    /**
     * This class implements the required methods for a ConnectorListener.
     *
     * @author Frank Hernandez.
     */
    private class AdapterSkypeConnectorListener implements ConnectorListener {
        private SkypeAdapter adapt = null;

        public AdapterSkypeConnectorListener(SkypeAdapter inAdapt) {
            adapt = inAdapt;
        }

        public void messageReceived(ConnectorMessageEvent arg0) {


        }

        public void messageSent(ConnectorMessageEvent arg0) {
            // TODO Auto-generated method stub
            adapt.m_bConnectorLocked = false;
        }

        public void statusChanged(ConnectorStatusEvent arg0) {
            // TODO Auto-generated method stub


        }

    }

    /**
     * This class implements the required methods for a ConnectorListener.
     *
     * @author Frank Hernandez.
     */
    private class AdapterSkypeStreamListern implements StreamListener {

        private SkypeAdapter adapt = null;

        public AdapterSkypeStreamListern(SkypeAdapter inAdapt) {
            adapt = inAdapt;
        }

        public void datagramReceived(String arg0) throws SkypeException {
            // TODO Auto-generated method stub
            CVM_Debug.getInstance().printDebugMessage("SkypeAdapter - StreamListener - DatagramReceived: " + arg0);

        }

        public synchronized void textReceived(String arg0) throws SkypeException {
            // TODO Auto-generated method stub
            //while(b_SendLock);
            CVM_Debug.getInstance().printDebugMessage("SkypeAdapter - StreamListener - TextReceived: " + arg0);
            if (dataContainer.isSchema(arg0)) {
                //TRY COMMENTING TO TEST SCHEMA RECEIVE WITH OUT BLOCKING
                dataContainer.enqueueSchemaForProcessing(arg0);
                if (dataContainer.sentCount > 0)
                    dataContainer.sentCount--;

                if (dataContainer.sentCount == 0) {
                    dataContainer.signalSchemaReceived();
                    dataContainer.sentCount = 0;
                }
                CVM_Debug.getInstance().printDebugMessage("Schema Handled");
            } else {
                //dataContainer
            }
            /*	if(adapt.isSchema(arg0))
                    {
                   //adapt.dealWithSchema(arg0);
                   adapt.enqueueSchemaForProcessing(arg0);
                   adapt.signalSchemaReceived();
                    }
                    else if (arg0.contains("<"))
                    {
                        //adapt.handleUpdateIncomingInformation(arg0);
                    }
                    else
                    {
                        //Perform other operations
                    }*/
        }

    }

    @Method(name = "hasMediumFailed", parameters = {"session", "medium"})
    public boolean hasMediumFailed(String session, String medium_) {
        //return false;
        if (medium_ == null) return false;
/*		if(count == 0) {
			count--;
			System.out.println("Fault Injected: "+System.currentTimeMillis( ));
			return true;
		}
*/        //boolean result = false;
        count--;
        return false;
/*//		System.out.println("Count "+count);
		if(!m_hmSessionIdMap.containsKey(sessID)) { 
			count++;
			result = true;
		}
		else if(m_hmSessionIdMap.containsKey(sessID)) {
			try {
				if(!Skype.isInstalled() || !Skype.isRunning()) {
					count++;
					return true;
				}
			} catch (SkypeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				count++;
				return true;
			}
			if(m_hmSessionCallIdMap.containsKey(sessID) && 
					(medium_type.equalsIgnoreCase("audio")||(medium_type.equalsIgnoreCase("video")))){
				if(m_hmSessionCallIdMap.get(sessID) != null) {
					String callObjStr = m_hmSessionCallIdMap.get(sessID).get(0);
//					CVM_Debug.getInstance().printDebugErrorMessage("Skype Status is "+this.skypeGetCallProperty(callObjStr,"STATUS"));
					if(this.skypeGetCallProperty(callObjStr,"STATUS") == null
							|| this.skypeGetCallProperty(callObjStr,"STATUS")
						.equals("FAILED")) {
						count++;
						result = true;
					}
				}
			}else if(m_hmSessionChatIdMap.containsKey(sessID)){ 
				if(m_hmSessionChatIdMap.get(sessID) != null){
					String callObjStr = m_hmSessionChatIdMap.get(sessID).get(0);
//					CVM_Debug.getInstance().printDebugErrorMessage("Skype Status is "+this.skypeGetCallProperty(callObjStr,"STATUS"));
					if(this.skypeGetCallProperty(callObjStr,"STATUS") == null ||
							this.skypeGetCallProperty(callObjStr,"STATUS")
						.equals("FAILED")) {
						count++;
						result = true;
					}
				}
			}
		}
		return result;
*/
    }

    public String getName() {
        return "Skype";
    }

    @Method(name = "destroySession", parameters = {"session"})
    public void destroySession(String session) {
        ArrayList<SkypeAdapter.MEDIUM> temp = new ArrayList<SkypeAdapter.MEDIUM>();
        if ((m_hmCallSessionMap.containsKey(session)
                || m_hmChatSessionMap.containsKey(session))) {
            for (SkypeAdapter.MEDIUM mediumName : this.m_MediumsEnabled) {
                temp.add(mediumName);
            }
            for (SkypeAdapter.MEDIUM mediumName : temp) {
                try {
                    disableMedium(session, mediumName.name());
                } catch (EventException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            try {
                if (m_hmChatSessionMap.get(session) != null)
                    m_hmChatSessionMap.get(session).leave();
                m_hmChatSessionMap.remove(session);
                if (m_hmCallSessionMap.get(session) != null)
                    m_hmCallSessionMap.get(session).finish();
            } catch (SkypeException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            m_hmCallSessionMap.remove(session);
            m_hmCallListMap.remove(session);

            //New Way
            m_hmSessionCallIdMap.remove(session);
            m_hmSessionChatIdMap.remove(session);
            CVM_Debug.getInstance().printDebugMessage("SkypeAdapter: Session destroyed- " + session + ".");

        }
    }

    @Method(name = "enableMediumReceiver", parameters = {"session", "medium"})
    public void enableMediumReceiver(String session, String medium) {
        // TODO Auto-generated method stub
    }

    private void getLock() {
        while (b_mapLock) {
        }
        b_mapLock = true;
    }

    private void releaseLock() {
        b_mapLock = false;
    }


}
