package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Main extends Application {
    public static Stage primaryStage;
    public static UserTable userTable;
    public static Socket socket;
    public static ObjectInputStream ois;
    public static ObjectOutputStream oos;
    @Override
    public void start(Stage primary) throws Exception{
        userTable = UserTable.getInstance();
//        userTable.open();
//        Server.conn=UserTable.getInstance().conn;
//        SellerTable.getInstance().open();

        //Start Page
        Parent root = FXMLLoader.load(getClass().getResource("Start.fxml"));
        primaryStage = primary;
        primaryStage.setTitle("Welcome to Online Mandi!");
        primaryStage.setScene(new Scene(root, 720, 540));
        primaryStage.show();
    }
    public static void main(String[] args) throws IOException {
        try {
            socket = new Socket("localhost", 6963);
            oos= new ObjectOutputStream(socket.getOutputStream());
            ois= new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        launch(args);
    }
}
