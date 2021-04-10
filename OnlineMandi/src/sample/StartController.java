package sample;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
public class StartController implements Initializable {
    @FXML
    private BorderPane startPane;
    @FXML
    private Button login;
    @FXML
    private Button signUp;
    @FXML
    void loginResponse(ActionEvent e) throws IOException {
        //open a new Scene for Login Page
        Parent root = FXMLLoader.load(getClass().getResource("LoginForm.fxml"));
        Scene scene = new Scene(root, 580, 790);
        Main.primaryStage.setTitle("Login");
        Main.primaryStage.setScene(scene);
        Main.primaryStage.show();
    }
    @FXML
    void signUpResponse(ActionEvent e) throws IOException {
        //open a new Scene for SignUp Page
        Parent root = FXMLLoader.load(getClass().getResource("SignUpForm.fxml"));
        Scene scene = new Scene(root, 580, 790);
        Main.primaryStage.setTitle("Login");
        Main.primaryStage.setScene(scene);
        Main.primaryStage.show();
    }
    @FXML
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Image image = new Image("file:///C:/Users/hp/Desktop/farmStart.jpg");
        System.out.println(image.getHeight()+"\n"+image.getWidth()+"\n"+image.getUrl());
        BackgroundSize bSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false);
        Background background = new Background(new BackgroundImage(image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                bSize));
        startPane.setBackground(background);
    }
}
