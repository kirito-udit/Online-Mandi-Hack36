package sample;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;

public class Main extends Application {
    public static Stage primaryStage;
    public static UserTable userTable;
    public static Socket socket;

    @Override
    public void start(Stage primary) throws Exception{
        userTable = UserTable.getInstance();
        userTable.open();
//        //Sign Up Page
//        Parent root = FXMLLoader.load(getClass().getResource("SignUpForm.fxml"));
//        primaryStage = primary;
//        primaryStage.setTitle("Sign up");
//        primaryStage.setScene(new Scene(root, 1023, 614));
//        primaryStage.show();

          //Profile Page
//        Parent root = FXMLLoader.load(getClass().getResource("ProfilePage.fxml"));
//        primaryStage = primary;
//        primaryStage.setTitle("Profile");
//        primaryStage.setScene(new Scene(root, 900, 600));
//        primaryStage.show();

            //Start Page
        //Start Page
        Parent root = FXMLLoader.load(getClass().getResource("Start.fxml"));
        primaryStage = primary;
        primaryStage.setTitle("Welcome to Online Mandi!");
        primaryStage.setScene(new Scene(root, 720, 540));
        primaryStage.show();
    }
    public static void main(String[] args) {
    public static void main(String[] args) throws IOException {
        socket = new Socket("localhost",6963);
        launch(args);
    }
}
