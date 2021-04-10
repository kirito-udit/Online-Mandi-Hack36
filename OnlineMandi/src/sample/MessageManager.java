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

    public static final String COLUMN_SENDER_PHONE="SenderPhone";
    public static final String COLUMN_RECEIVER_PHONE="ReceiverPhone";
    public static final String COLUMN_CONTENT="Content";
    public static final String COLUMN_SENT_TIME="SentTime";
    public static final String COLUMN_SEEN="Seen";


    public static final String QUERY_ALL_CONVERSATIONS=" SELECT * FROM "+MESSAGE_TABLE+" WHERE "+COLUMN_SENDER_PHONE+" = ? OR "+COLUMN_RECEIVER_PHONE+" = ? ";
    public static final String QUERY_ALL_UNREAD_CONVERSATIONS=" SELECT * FROM "+MESSAGE_TABLE+" WHERE "+COLUMN_RECEIVER_PHONE+" = ? AND "+COLUMN_SEEN+" = ? ";
    public static final String QUERY_ADD_CONVO = " INSERT INTO " + MESSAGE_TABLE + " VALUES ( ?, ? , ? , ? , ? ) ";

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


    public boolean addConversation(String senderPhone,String receiverPhone,
                                   String content, Timestamp sentTime, int seen) {
        try {
            open();
            addConvo.setString(1, senderPhone);
            addConvo.setString(2, receiverPhone);
            addConvo.setString(3, content);
            addConvo.setTimestamp(4, sentTime);
            addConvo.setInt(5, seen);
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
            UserTable userTable=UserTable.getInstance();
            while(results.next()){
                if(results.getString(2).equals(userPhone)){//I am receiver
                    String senderName=userTable.getFullName(results.getString(1));
                    mp2.put(results.getString(1),results.getInt(5));//senderPhone mapped to seen variable
                    String str = null;
                    if(mp1.get(results.getString(1))==null){
                        str =senderName+"\n"+results.getTimestamp(4)+
                                " \n" + results.getString(3)+"\n";
                    }
                    else {
                        str = mp1.get(results.getString(1)) + senderName + "\n" + results.getTimestamp(4) +
                                "\n" + results.getString(3) + "\n";
                    }
                    mp1.put(results.getString(1),str);
                    mp3.put(results.getString(1),senderName);
                }else{
                    String senderName=userName;
                    String receiverName=userTable.getFullName(results.getString(2));
                    mp2.put(results.getString(2),results.getInt(5));//senderPhone mapped to seen variable
                    String str = null;
                    if(mp1.get(results.getString(2))==null){
                        str =senderName+"\n"+results.getTimestamp(4)+
                                " \n" + results.getString(3)+"\n";
                    }
                    else {
                        str = mp1.get(results.getString(2)) + senderName + "\n" + results.getTimestamp(4) +
                                "\n" + results.getString(3) + "\n";
                    }
                    mp1.put(results.getString(2),str);
                    mp3.put(results.getString(2),receiverName);
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
