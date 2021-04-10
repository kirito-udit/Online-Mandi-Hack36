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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

public class Server {
    public static Connection conn=null;
    public static ArrayList<String> currentlyActiveUser = new ArrayList<>();
    public static ArrayList <HandleClientRequest> clientHandlers= new ArrayList<>();
    public static File dbFile = new File("./src/sample/Resources");
    public static final String DB_NAME = "register.db";
    public static final String CONNECTION_STRING = "jdbc:sqlite:"+dbFile.getAbsolutePath()+"\\"+DB_NAME;
    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        Socket socket;

        try {
            serverSocket=new ServerSocket(6963);
            System.out.println("Server started");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        while(true) {
            try {
                socket=serverSocket.accept();

//                System.out.println("Client Connected");
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                String phoneNumber = ois.readObject().toString();
                String password = ois.readObject().toString();
                System.out.println("Client Connected");

                //Authentication now
                FullNameProfilePic fnpc = UserTable.getInstance().authentication(password,phoneNumber);
                if(fnpc!=null)
                    oos.writeObject(phoneNumber);
                else
                    oos.writeObject(null);

                if(fnpc != null) {
                    currentlyActiveUser.add(phoneNumber);
                    HandleClientRequest clientHandler = new HandleClientRequest(fnpc.getFullName(),fnpc.getImage(),phoneNumber,socket,ois,oos);
                    clientHandlers.add(clientHandler);
                    Thread t = new Thread(clientHandler);
                    t.start(); //starting new thread to handle client request
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                
            }
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
    private static Connection getConnection(String databaseName, String UserName, String password) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost/" + databaseName + "?user=" + UserName + "&password=" + password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }
}
