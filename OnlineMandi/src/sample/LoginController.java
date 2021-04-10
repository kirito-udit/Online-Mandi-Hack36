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
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.net.Socket;
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
    void signIn(ActionEvent e) throws IOException, ClassNotFoundException {
        String pass = getMd5(password.getText());
        String phoneNo = phoneNumber.getText();
        //ObjectOutputStream oos = new ObjectOutputStream(Main.socket.getOutputStream());
        //ObjectInputStream ois = new ObjectInputStream(Main.socket.getInputStream());
        Main.oos.writeObject(phoneNo);
        Main.oos.writeObject(pass);
        String check=Main.ois.readObject().toString();
        if(check.equals("Authentication Failed")){
            JOptionPane.showMessageDialog(null,"Incorrect credentials");
        }else{


        //creating an instance of UserTable class, if it already present then the same instance will be returned
            FullNameProfilePic fullNameProfilePic = Main.userTable.authentication(pass, phoneNo);

            //if authentication is successful then fetch the profile pic and name of the user
            Image image = fullNameProfilePic.getImage();
            String name = fullNameProfilePic.getFullName();

            //Parent root = FXMLLoader.load(getClass().getResource("ProfilePage.fxml"));
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ProfilePage.fxml"));
            Parent root = (Parent) loader.load();
            ProfilePageController ppc = loader.getController();
            ppc.createProfile(phoneNo, image, name);
            Scene scene = new Scene(root, 900, 620);
            Main.primaryStage.setTitle("My Profile");
            Main.primaryStage.setScene(scene);
            Main.primaryStage.show();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        try {
//            socket = new Socket("localhost", 6963);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        Image image = new Image("file:./src/sample/Resources/farmLogin.jpg");
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
    @FXML
    public void signUpResponse(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("SignUpForm.fxml"));
        Scene scene = new Scene(root, 1023, 614);
        Main.primaryStage.setTitle("Sign Up");
        Main.primaryStage.setScene(scene);
        Main.primaryStage.show();
    }
}
