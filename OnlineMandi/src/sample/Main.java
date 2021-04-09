package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
//        Parent root = FXMLLoader.load(getClass().getResource("SignUpForm.fxml"));
//        primaryStage.setTitle("Sign up");
//        primaryStage.setScene(new Scene(root, 1023, 614));
//        primaryStage.show();
//        Parent root = FXMLLoader.load(getClass().getResource("LoginForm.fxml"));
//        primaryStage.setTitle("Login");
//        primaryStage.setScene(new Scene(root, 580, 790));
//        primaryStage.show();
        Parent root = FXMLLoader.load(getClass().getResource("Start.fxml"));
        primaryStage.setTitle("Welcome to Online Mandi");
        primaryStage.setScene(new Scene(root, 720, 540));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
