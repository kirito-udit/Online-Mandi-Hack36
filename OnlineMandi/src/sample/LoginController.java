package sample;

import com.email.durgesh.Email;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import javax.mail.MessagingException;
import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.Socket;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private BorderPane loginPane;

    @FXML
    private Button forgotPasswordButton;

    @FXML
    private Button signIn;

    @FXML
    private TextField phoneNumber;

    @FXML
    private PasswordField password;

    private String mailOTP;
    private String originalOtp;

    @FXML
    void signIn(ActionEvent e) throws IOException, ClassNotFoundException {
        String pass = getMd5(password.getText());
        String phoneNo = phoneNumber.getText();
        //ObjectOutputStream oos = new ObjectOutputStream(Main.socket.getOutputStream());
        //ObjectInputStream ois = new ObjectInputStream(Main.socket.getInputStream());
        Main.oos.writeObject(phoneNo);
        Main.oos.writeObject(pass);
        String check = Main.ois.readObject().toString();
        if (check.equals("Authentication Failed")) {
            JOptionPane.showMessageDialog(null, "Incorrect credentials");
        } else {


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

    @FXML
    public void forgotPasswordButtonResponse(ActionEvent e) {
        Label changeUsingEmailLabel = new Label("First verify one of these two continue: ");
        Label phoneNumberLabel = new Label("Enter Phone Number");
        TextField phoneNumberTextField = new TextField();
        Button changeUsingEmailButton = new Button("Email");
        Button changeUsingPhoneNoButton = new Button("Phone");

        VBox vBox = new VBox();
        vBox.getChildren().addAll(changeUsingEmailLabel, phoneNumberLabel,phoneNumberTextField,changeUsingEmailButton, changeUsingPhoneNoButton);
        vBox.setPrefHeight(300);
        vBox.setPrefWidth(300);
        vBox.setSpacing(10);
        vBox.setAlignment(Pos.CENTER);

        Stage changeStage = new Stage();
        AnchorPane root = new AnchorPane();
        root.getChildren().add(vBox);

        Scene canvasScene = new Scene(root);
        changeStage.setTitle("Forgot Password?");
        changeStage.setScene(canvasScene);
        changeStage.show();

        changeUsingEmailButton.setOnAction(actionEvent1 -> {
            enterOTPFromEmail(phoneNumberTextField.getText());
            changeStage.close();
        });
        changeUsingPhoneNoButton.setOnAction(actionEvent1 -> {
            enterOTPFromPhone(phoneNumberTextField.getText());
            changeStage.close();
        });
    }

    private void enterOTPFromPhone(String phoneNumber) {
        try {
            originalOtp = SendSMS.OTP();
            System.out.println("OTP: " + originalOtp);
            //SendSMS.sendSms(originalOtp,phoneNo);
            System.out.println("Clicked.");
        } catch (Exception e1) {
            JOptionPane.showMessageDialog(null, "Authentication code not sent, check phone number!");
            e1.printStackTrace();
        }

        Label authenticationLabel = new Label("Enter OTP: ");
        TextField authenticationCodeTextField = new TextField();
        authenticationCodeTextField.setPromptText("OTP here");
        Button confirmButton = new Button("Confirm");

        VBox vBox = new VBox();
        vBox.getChildren().addAll(authenticationLabel, authenticationCodeTextField, confirmButton);
        vBox.setPrefHeight(150);
        vBox.setPrefWidth(200);
        vBox.setSpacing(5);
        vBox.setAlignment(Pos.CENTER);

        Stage changeStage = new Stage();
        AnchorPane root = new AnchorPane();
        root.getChildren().add(vBox);

        Scene canvasScene = new Scene(root);
        changeStage.setTitle("Verify OTP");
        changeStage.setScene(canvasScene);
        changeStage.show();

        confirmButton.setOnAction(actionEvent1 -> {
            if (authenticationCodeTextField.getText().equals(originalOtp)) {
                setNewPassword(phoneNumber);
            } else {
                JOptionPane.showMessageDialog(null, "Wrong authentication code!");
            }
            changeStage.close();
        });
    }


    public void enterOTPFromEmail(String phoneNumber) {
        try {
            Task task = new Task<Void>() {
                @Override
                public Void call() throws InterruptedException, UnsupportedEncodingException, MessagingException {
                    Email email = new Email("onlinemandi0@gmail.com", "KSSU$367");
                    email.setFrom("onlinemandi0@gmail.com", "ONLINE MANDI");
                    email.setSubject("Verification Mail");
                    Random random = new Random();
                    String emailOTP = "" + random.ints(1000, 9999).findFirst().getAsInt();
                    mailOTP = emailOTP;
                    email.setContent("<h1>Welcome to Online Mandi</h1><br><br><p>Your Verification Code Is " + emailOTP + ".</p>", "text/html");
                    email.addRecipient(UserTable.getInstance().getPoBox(phoneNumber));
                    email.send();
                    return null;
                }
            };
            new Thread(task).start();
        } catch (Exception e1) {
            JOptionPane.showMessageDialog(null, "Authentication code not sent, check phone number!");
            e1.printStackTrace();
        }

        Label authenticationLabel = new Label("Enter authentication code: ");
        TextField authenticationCodeTextField = new TextField();
        authenticationCodeTextField.setPromptText("Authentication code here");
        Button confirmButton = new Button("Confirm");

        VBox vBox = new VBox();
        vBox.getChildren().addAll(authenticationLabel, authenticationCodeTextField, confirmButton);
        vBox.setPrefHeight(150);
        vBox.setPrefWidth(200);
        vBox.setSpacing(5);
        vBox.setAlignment(Pos.CENTER);

        Stage changeStage = new Stage();
        AnchorPane root = new AnchorPane();
        root.getChildren().add(vBox);

        Scene canvasScene = new Scene(root);
        changeStage.setTitle("Authentication");
        changeStage.setScene(canvasScene);
        changeStage.show();

        confirmButton.setOnAction(actionEvent1 -> {
            if (authenticationCodeTextField.getText().equals(mailOTP)) {
                setNewPassword(phoneNumber);
            } else {
                JOptionPane.showMessageDialog(null, "Wrong authentication code!");
            }
            changeStage.close();
        });
    }

    public void setNewPassword(String phoneNo) {
        Label newPasswordLabel = new Label("Enter New Password");
        Label confirmNewPasswordLabel = new Label("Confirm New Password");
        PasswordField newPasswordPasswordField = new PasswordField();
        newPasswordPasswordField.setPromptText("New Password");
        PasswordField confirmNewPasswordPasswordField = new PasswordField();
        confirmNewPasswordPasswordField.setPromptText("Confirm new Password");
        Button changeButton = new Button();
        changeButton.setText("Change Password");

        VBox vBox = new VBox();
        vBox.getChildren().addAll(newPasswordLabel, newPasswordPasswordField, confirmNewPasswordLabel, confirmNewPasswordPasswordField, changeButton);
        vBox.setPrefHeight(350);
        vBox.setPrefWidth(200);
        vBox.setSpacing(5);
        vBox.setAlignment(Pos.CENTER);

        Stage changeStage = new Stage();
        AnchorPane root = new AnchorPane();
        root.getChildren().add(vBox);

        Scene canvasScene = new Scene(root);
        changeStage.setTitle("New Password");
        changeStage.setScene(canvasScene);
        changeStage.show();

        changeButton.setOnAction(actionEvent1 -> {
            String newPassword = newPasswordPasswordField.getText();
            String confirmedNewPassword = confirmNewPasswordPasswordField.getText();
            if (newPassword.equals(confirmedNewPassword)) {
                if (UserTable.getInstance().updatePassword(phoneNo, LoginController.getMd5(newPassword)))
                    JOptionPane.showMessageDialog(null, "Password updated successfully!");
                else
                    JOptionPane.showMessageDialog(null, "Error updating password!");
            } else {
                JOptionPane.showMessageDialog(null, "Confirmed password not matching!");
            }
            changeStage.close();
        });
    }
}
