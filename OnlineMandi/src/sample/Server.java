package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import java.util.ArrayList;
import java.util.Vector;

public class Server implements  Runnable {
    public static Connection conn=null;
    //public static ArrayList<String> currentlyActiveUser = new ArrayList<>();
    //public static ArrayList <HandleClientRequest> clientHandlers= new ArrayList<>();
    public static File dbFile = new File("./src/sample/Resources");
    public static final String DB_NAME = "register.db";
    public static final String CONNECTION_STRING = "jdbc:sqlite:"+dbFile.getAbsolutePath()+"\\"+DB_NAME;
    private Socket socket;
    public static ArrayList<ClientDetails>clients = new ArrayList<>();


    Server(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        String phoneNumber = null;
        String password = null;
        try {
            oos = new ObjectOutputStream((socket.getOutputStream()));
            ois = new ObjectInputStream((socket.getInputStream()));
        } catch (Exception e) {
            System.out.println("Error in run method OOS OIS ");
            e.printStackTrace();
        }
        while(true) {
            try {
                phoneNumber = (String) ois.readObject();//this is the first string read by the server
                password = (String) ois.readObject();
                FullNameProfilePic fnpc = UserTable.getInstance().authentication(password, phoneNumber);
                if (fnpc == null) {
                    oos.writeObject("Authentication Failed");
                } else {
                    oos.writeObject("Authentication Successful");
                    clients.add(new ClientDetails(phoneNumber, oos));
                    break;
                }
            } catch (Exception e) {
                e.printStackTrace();
                for (ClientDetails client : clients) {
                    if (client.getPhoneNo().equals(phoneNumber)) {
                        clients.remove(client);
                        break;
                    }
                }
                return;
            }
        }

        while(true){
            String data = null, message=null;
            Timestamp t = null;
            try {
                data = (String) ois.readObject();//this will be the phone No of the reciever whom the sender wants to send msg to
                t = (Timestamp) ois.readObject();
                message = (String)ois.readObject();
                MessageManager mm=MessageManager.getInstance();
                mm.addConversation(phoneNumber,data, message,t, 0);
            } catch (Exception e) {
                e.printStackTrace();
                for(ClientDetails client : clients) {
                    if(client.getPhoneNo().equals(phoneNumber)) {
                        clients.remove(client);
                        break;
                    }
                }
                return;
            }

            //now server will check in the array list of clients for the given phone No and send message to hi
            for(ClientDetails d : clients){
                if(d.getPhoneNo().equals(data)){
                    try {
                        UserTable userTable = UserTable.getInstance();
                        d.getOos().writeObject(phoneNumber);
                        d.getOos().writeObject(t);
                        d.getOos().writeObject(message);
                        d.getOos().flush();
                        break;
                    } catch (IOException e) {
                        e.printStackTrace();
                        return;
                    }
                }
            }
        }
    }
    public static void main(String[] args) throws IOException {
        ServerSocket ss = null;
        //Socket socket;

        try {
            ss=new ServerSocket(6963);
            System.out.println("Server started");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        while (true) {
            Socket s = ss.accept();
            Server server = new Server(s);
            Thread t = new Thread(server);
            t.start();
        }
    }

    public static Connection getConnection()  {
        if (conn != null) return conn;
        try {
            conn = DriverManager.getConnection(CONNECTION_STRING);
            return conn;
        }catch (SQLException e){
            System.out.println("Unable to establish connection with Database "+e.getMessage());
            return null;
        }
    }
    /*private static Connection getConnection(String databaseName, String UserName, String password) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost/" + databaseName + "?user=" + UserName + "&password=" + password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }*/
}
