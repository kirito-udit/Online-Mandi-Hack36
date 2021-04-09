package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    public static Stage primaryStage;
    @Override
    public void start(Stage primary) throws Exception{
        //Sign Up Page
        Parent root = FXMLLoader.load(getClass().getResource("SignUpForm.fxml"));
        primaryStage = primary;
        primaryStage.setTitle("Sign up");
        primaryStage.setScene(new Scene(root, 1023, 614));
        primaryStage.show();

          //Profile Page
//        Parent root = FXMLLoader.load(getClass().getResource("ProfilePage.fxml"));
//        primaryStage = primary;
//        primaryStage.setTitle("Profile");
//        primaryStage.setScene(new Scene(root, 900, 600));
//        primaryStage.show();

            //Start Page
//        Parent root = FXMLLoader.load(getClass().getResource("Start.fxml"));
//        primaryStage = primary;
//        primaryStage.setTitle("Welcome to Online Mandi!");
//        primaryStage.setScene(new Scene(root, 720, 540));
//        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
