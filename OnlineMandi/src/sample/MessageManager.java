package sample;


public class MessageManager {
    public static final String MESSAGE_TABLE="MessageManager";

    public static final String COLUMN_SENDER_PHONE="SenderPhone";
    public static final String COLUMN_RECEIVER_PHONE="ReceiverPhone";
    public static final String COLUMN_CONTENT="Content";
    public static final String COLUMN_SENT_TIME="SentTime";
    public static final String COLUMN_SEEN="Seen";


    public static final String QUERY_ALL_CONVERSATIONS=" SELECT * FROM "+MESSAGE_TABLE+" WHERE "+COLUMN_SENDER_PHONE+" = ? OR "+COLUMN_RECEIVER_PHONE+" = ? ";
    public static final String QUERY_ALL_UNREAD_CONVERSATIONS=" SELECT * FROM "+MESSAGE_TABLE+" WHERE "+COLUMN_RECEIVER_PHONE+" = ? AND "+COLUMN_SEEN+" = ? ";
    public static final String QUERY_ADD_CONVO = " INSERT INTO " + MESSAGE_TABLE + " VALUES ( ?, ? , ? , ? , ? ) ";

    private Comparator<Conversation> cmp = new Comparator<Conversation>() {
        @Override
        public int compare(Conversation o1, Conversation o2) {
            return 1;
        }
    };

    //private constructor
    private MessageManager(){

    }

    public static MessageManager getInstance(){
        if(messageManager == null) messageManager = new MessageManager();
        return messageManager;
    }


    public boolean addConversation(String senderPhone,String receiverPhone,
                                   String content, Timestamp sentTime, int seen) {
        try {
            conn=Server.getConnection();
            addConvo=conn.prepareStatement(QUERY_ADD_CONVO);
            addConvo.setString(1, senderPhone);
            addConvo.setString(2, receiverPhone);
            addConvo.setString(3, content);
            addConvo.setTimestamp(4, sentTime);
            addConvo.setInt(5, seen);
            addConvo.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error occured while adding a conversation " + e.getMessage());
            return false;
        }finally {
            try{
                addConvo.close();
            }catch (SQLException e){
                System.out.println("Error occured while closing the resources in addConversation method in Message Manager class "+e.getMessage());
            }
        }
    }


    public ArrayList<Conversation> getAllConversations(String userPhone,String userName){
        try {
            conn=Server.getConnection();
            getAllConversationsStmt=conn.prepareStatement(QUERY_ALL_CONVERSATIONS);
            getAllConversationsStmt.setString(1, userPhone);
            getAllConversationsStmt.setString(2, userPhone);
            ResultSet results=getAllConversationsStmt.executeQuery();
            ArrayList<Conversation>result=new ArrayList<>();
            HashMap<String,String> mp1=new HashMap<>();
            HashMap<String,Integer>mp2=new HashMap<>();
            HashMap<String,String> mp3=new HashMap<>();
            UserTable userTable=UserTable.getInstance();
                     for(String str:mp1.keySet()){
                Conversation conversation=new Conversation(str,mp1.get(str),mp2.get(str),mp3.get(str));
                result.add(conversation);
            }
            Collections.sort(result,cmp);
            return result;
        }catch (SQLException e){
            System.out.println("Error occured while fetching all the conversations from the MessageManager Table "+e.getMessage());
            return null;
        }finally {
            try{
                getAllConversationsStmt.close();
            }catch (SQLException e){
                System.out.println("Error occured while closing the resources of the getAllConversations Method of Message Manager class "+e.getMessage());
            }
        }
    }
}
