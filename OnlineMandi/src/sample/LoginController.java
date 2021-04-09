package sample;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javax.swing.*;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;
public class LoginController implements Initializable {
    @FXML
    private BorderPane loginPane;
    @FXML
    private Button signIn;
    @FXML
    private TextField phoneNumber;
    @FXML
    private PasswordField password;
    @FXML
    void signIn(ActionEvent e) throws IOException {
        String pass = getMd5(password.getText());
        String phoneNo = phoneNumber.getText();
        //creating an instance of UserTable class, if it already present then same instance will be returned
        UserTable userTable = UserTable.getInstance();
        userTable.open();
        boolean status = authenticate(phoneNo,pass);
        if(status){
        FullNameProfilePic fullNameProfilePic = userTable.authentication(pass,phoneNo);
        if(fullNameProfilePic!=null){
            //if authentication is successful then fetch the profile pic and name of the user
            Image image = userTable.getImage();
            String name = userTable.getName();
            Image image = fullNameProfilePic.getImage();
            String name = fullNameProfilePic.getFullName();
            Parent root = FXMLLoader.load(getClass().getResource("ProfilePage.fxml"));
            Scene scene = new Scene(root, 580, 790);
            Main.primaryStage.setTitle("My Profile");
            Main.primaryStage.setScene(scene);
            Main.primaryStage.show();
            ProfilePageController ppc = new ProfilePageController();
            ppc.createProfile(image, name);
            Main.primaryStage.show();
        }
        else{
            JOptionPane.showMessageDialog(null,"Invalid Phone Number or password!!!");
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Image image = new Image("file:///C:/Users/hp/Desktop/farmLogin.jpg");
        BackgroundSize bSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false);
        Background background = new Background(new BackgroundImage(image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                bSize));
        loginPane.setBackground(background);
    }
    public static String getMd5(String input) {
        try {
            // Static getInstance method is called with hashing MD5
            MessageDigest md = MessageDigest.getInstance("MD5");
            // digest() method is called to calculate message digest
            //  of an input digest() return array of byte
            byte[] messageDigest = md.digest(input.getBytes());
            // Convert byte array into signum representation
            BigInteger no = new BigInteger(1, messageDigest);
            // Convert message digest into hex value
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }
        // For specifying wrong message digest algorithms
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
