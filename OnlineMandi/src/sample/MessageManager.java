package sample;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class MessageManager {
    public static File dbFile = new File("./src/sample/Resources");
    public static final String DB_NAME = "register.db";
    public static final String CONNECTION_STRING = "jdbc:sqlite:"+dbFile.getAbsolutePath()+"\\"+DB_NAME;
    public static final String MESSAGE_TABLE="MessageManager";

    public static final String COLUMN_SENDER_NAME="SenderName";
    public static final String COLUMN_SENDER_PHONE="SenderPhone";
    public static final String COLUMN_RECEIVER_NAME="ReceiverName";
    public static final String COLUMN_RECEIVER_PHONE="ReceiverPhone";
    public static final String COLUMN_CONTENT="Content";
    public static final String COLUMN_SEEN_TIME="SeenTime";
    public static final String COLUMN_SENT_TIME="SentTime";
    public static final String COLUMN_RECEIVED_TIME="ReceivedTime";
    public static final String COLUMN_SEEN="Seen";


    public static final String QUERY_ALL_CONVERSATIONS=" SELECT * FROM "+MESSAGE_TABLE+" WHERE "+COLUMN_SENDER_PHONE+" = ? OR "+COLUMN_RECEIVER_PHONE+" = ? ";
    public static final String QUERY_ALL_UNREAD_CONVERSATIONS=" SELECT * FROM "+MESSAGE_TABLE+" WHERE "+COLUMN_RECEIVER_PHONE+" = ? AND "+COLUMN_SEEN+" = ? ";
    public static final String QUERY_ADD_CONVO = " INSERT INTO " + MESSAGE_TABLE + " VALUES ( ?, ? , ? , ? , ? , ? , ?) ";

    public Connection conn;
    public PreparedStatement getAllConversationsStmt;
    public PreparedStatement getAllUnreadConversationsStmt;
    public PreparedStatement addConvo;
    private static MessageManager messageManager;
    //private constructor
    private MessageManager(){

    }

    public static MessageManager getInstance(){
        if(messageManager == null) messageManager = new MessageManager();
        return messageManager;
    }

    public boolean open(){
        try{
            conn= DriverManager.getConnection(CONNECTION_STRING);
            addConvo = conn.prepareStatement(QUERY_ADD_CONVO);
            getAllConversationsStmt=conn.prepareStatement(QUERY_ALL_CONVERSATIONS);
            getAllUnreadConversationsStmt=conn.prepareStatement(QUERY_ALL_UNREAD_CONVERSATIONS);
            return true;
        } catch (SQLException e) {
            System.out.println(" Error occured while opening the MessageManager table register database "+e.getMessage());
            return false;
        }
    }

    public boolean close(){
        try{
            if(getAllConversationsStmt!=null){
                getAllConversationsStmt.close();
            }
            if(addConvo!=null){
                addConvo.close();
            }
            if(getAllUnreadConversationsStmt!=null){
                getAllUnreadConversationsStmt.close();
            }
            if(conn!=null){
                conn.close();
            }
            return true;
        } catch (SQLException e) {
            System.out.println(" Error occured while closing the resources of the MessageManager table register database "+e.getMessage());
            return false;
        }
    }


    public boolean addConversation(String senderName, String senderPhone, String receiverName, String receiverPhone,
                            String content, Timestamp sentTime, int seen) {
        try {
            open();
            addConvo.setString(1, senderName);
            addConvo.setString(2, senderPhone);
            addConvo.setString(3, receiverName);
            addConvo.setString(4, receiverPhone);
            addConvo.setString(5, content);
            addConvo.setTimestamp(6, sentTime);
            addConvo.setInt(7, seen);
            addConvo.executeUpdate();
            close();
            return true;
        } catch (SQLException e) {
            System.out.println("Error occured while adding a conversation " + e.getMessage());
            return false;
        }
    }


    public ArrayList<Conversation> getAllConversations(String userPhone,String userName){
        try {
            open();
            getAllConversationsStmt.setString(1, userPhone);
            getAllConversationsStmt.setString(2, userPhone);
            ResultSet results=getAllConversationsStmt.executeQuery();
            ArrayList<Conversation>result=new ArrayList<>();
            HashMap<String,String> mp1=new HashMap<>();
            HashMap<String,Integer>mp2=new HashMap<>();
            HashMap<String,String> mp3=new HashMap<>();
            while(results.next()){
                if(results.getString(4).equals(userPhone)){
                    mp2.put(results.getString(2),results.getInt(7));
                    String str = null;
                    if(mp1.get(results.getString(2))==null){
                        str = results.getString(1)+"->"+results.getString(3)+"\n"+results.getTimestamp(6)+
                                " \n" + results.getString(5)+"\n";
                    }
                    else {
                        str = mp1.get(results.getString(2)) + results.getString(1) + "->" + results.getString(3) + "\n" + results.getTimestamp(6) +
                                "\n" + results.getString(5) + "\n";
                    }
                    mp1.put(results.getString(2),str);
                    mp3.put(results.getString(2),results.getString(1));
                }else{
                    mp2.put(results.getString(4),results.getInt(7));
                    String str = null;
                    if(mp1.get(results.getString(4))==null){
                        str = results.getString(1)+"->"+results.getString(3)+"\n"+results.getTimestamp(6)+
                                " \n" + results.getString(5)+"\n";
                    }
                    else {
                        str = mp1.get(results.getString(4)) + results.getString(1) + "->" + results.getString(3) + "\n" + results.getTimestamp(6) +
                                "\n" + results.getString(5) + "\n";
                    }
                    mp1.put(results.getString(4),str);
                    mp3.put(results.getString(4),results.getString(3));
                }

            }
            for(String str:mp1.keySet()){
                Conversation conversation=new Conversation(str,mp1.get(str),mp2.get(str),mp3.get(str));
                result.add(conversation);
            }
            close();
            return result;
        }catch (SQLException e){
            System.out.println("Error occured while fetching all the conversations from the MessageManager Table "+e.getMessage());
            return null;
        }
    }
    public ArrayList<Conversation> getUnreadConversations(String userPhone){
        try {
            open();
            getAllUnreadConversationsStmt.setString(1, userPhone);
            getAllUnreadConversationsStmt.setInt(2,0);
            ResultSet results=getAllUnreadConversationsStmt.executeQuery();
            ArrayList<Conversation>result=new ArrayList<>();
            HashMap<String,String> mp1=new HashMap<>();
            HashMap<String,Integer> mp2=new HashMap<>();
            HashMap<String,String> mp3=new HashMap<>();
            while(results.next()){
                if(results.getString(4).equals(userPhone)){
                    mp2.put(results.getString(2),results.getInt(9));
                    String str= mp1.get(results.getString(2))+results.getString(1)+"->"+results.getString(3)+" "+results.getTimestamp(6)+
                            "  :" + results.getString(5)+"\n";
                    mp1.put(results.getString(2),str);
                    mp3.put(results.getString(2),results.getString(1));
                }else{
                    mp2.put(results.getString(4),results.getInt(9));
                    String str= mp1.get(results.getString(4))+results.getString(1)+"->"+results.getString(3)+" "+results.getTimestamp(6)+
                            " :" + results.getString(5)+"\n";
                    mp1.put(results.getString(2),str);
                    mp3.put(results.getString(2),results.getString(1));
                }
            }
            for(String str:mp1.keySet()){
                Conversation conversation=new Conversation(str,mp1.get(str),mp2.get(str),mp3.get(str));
                result.add(conversation);
            }
            close();
            return result;
        }catch (SQLException e){
            System.out.println("Error occured while fetching all the unread conversations from the MessageManager Table "+e.getMessage());
            return null;
        }
    }

}
