package sample;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class MessageManager {
    public static final String DB_NAME="register.DB";
    public static final String CONNECTION_STRING = "jdbc:sqlite:C:\\Users\\Kartikeya"+DB_NAME;
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

    public Connection conn;
    public PreparedStatement getAllConversationsStmt;
    public PreparedStatement getAllUnreadConversationsStmt;
    private static  MessageManager messageManager;
    //private constructor
    private MessageManager(){

    }

    public static MessageManager getInstance(){
        if(messageManager!=null) messageManager = new MessageManager();
        return messageManager;
    }

    public boolean open(){
        try{
            conn= DriverManager.getConnection(CONNECTION_STRING);
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

    public ArrayList<Conversation> getAllConversations(String userPhone,String userName){
        try {
            getAllConversationsStmt.setString(1, userPhone);
            getAllConversationsStmt.setString(2, userPhone);
            ResultSet results=getAllConversationsStmt.executeQuery();
            ArrayList<Conversation>result=new ArrayList<>();
            HashMap<String,String> mp1=new HashMap<>();
            HashMap<String,Integer>mp2=new HashMap<>();
            while(results.next()){
                if(results.getString(4).equals(userPhone)){
                    mp2.put(results.getString(2),results.getInt(9));
                    String str= mp1.get(results.getString(2))+results.getString(1)+"->"+results.getString(3)+" "+results.getTimestamp(6)+
                            "  :" + results.getString(5)+"\n";
                    mp1.put(results.getString(2),str);
                }else{
                    mp2.put(results.getString(4),results.getInt(9));
                    String str= mp1.get(results.getString(4))+results.getString(1)+"->"+results.getString(3)+" "+results.getTimestamp(6)+
                            " :" + results.getString(5)+"\n";
                    mp1.put(results.getString(2),str);
                }
            }
            for(String str:mp1.keySet()){
                Conversation conversation=new Conversation(str,mp1.get(str),mp2.get(str));
                result.add(conversation);
            }
            return result;
        }catch (SQLException e){
            System.out.println("Error occured while fetching all the conversations from the MessageManager Table "+e.getMessage());
            return null;
        }
    }
    public ArrayList<Conversation> getUnreadConversations(String userPhone){
        try {
            getAllUnreadConversationsStmt.setString(1, userPhone);
            getAllUnreadConversationsStmt.setInt(2,0);
            ResultSet results=getAllUnreadConversationsStmt.executeQuery();
            ArrayList<Conversation>result=new ArrayList<>();
            HashMap<String,String> mp1=new HashMap<>();
            HashMap<String,Integer>mp2=new HashMap<>();
            while(results.next()){
                if(results.getString(4).equals(userPhone)){
                    mp2.put(results.getString(2),results.getInt(9));
                    String str= mp1.get(results.getString(2))+results.getString(1)+"->"+results.getString(3)+" "+results.getTimestamp(6)+
                            "  :" + results.getString(5)+"\n";
                    mp1.put(results.getString(2),str);
                }else{
                    mp2.put(results.getString(4),results.getInt(9));
                    String str= mp1.get(results.getString(4))+results.getString(1)+"->"+results.getString(3)+" "+results.getTimestamp(6)+
                            " :" + results.getString(5)+"\n";
                    mp1.put(results.getString(2),str);
                }
            }
            for(String str:mp1.keySet()){
                Conversation conversation=new Conversation(str,mp1.get(str),mp2.get(str));
                result.add(conversation);
            }
            return result;
        }catch (SQLException e){
            System.out.println("Error occured while fetching all the unread conversations from the MessageManager Table "+e.getMessage());
            return null;
        }
    }

}
