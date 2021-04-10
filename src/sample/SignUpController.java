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
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.mail.MessagingException;
import javax.swing.*;
import java.io.*;
import java.math.BigInteger;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;

public class SignUpController implements Initializable {
    @FXML
    private GridPane gridPane;

    @FXML
    private BorderPane borderPane;

    @FXML
    private Button submit;

    @FXML
    private Button upload;

    @FXML
    private TextField nameField;

    @FXML
    private TextField phoneNumber;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField cityName;

    @FXML
    private TextField aadharNumber;

    @FXML
    private DatePicker dateOfBirth;

    @FXML
    private TextField latitudeTextField;

    @FXML
    private TextField longitudeTextField;

    @FXML
    private Button selectLocationButton;

    @FXML
    private Button verifyEmailButton;

    @FXML
    private TextField otpTextField;

    @FXML
    private TextField emailTextField;

    @FXML
    private TextField verifyEmailTextField;

    @FXML
    private Button otpButton;

    @FXML
    private Button loginButton;
    File selectedFile;
    private String emailOTP;
    // JOptionPane.showMessageDialog(null,"Message!!!");
    public String getName() {
        String name = nameField.getText();
        //verify name
        return name;
    }
    public String getPhone() {
        String phoneNo = phoneNumber.getText();
        //verify correctness of phone
        return phoneNo;
    }
    public String getPassword() {
        String password = getMd5(passwordField.getText());
        return password;
    }
    public String getEmail() {
        String email = emailTextField.getText();
        return email;
    }
    public String getCity() {
        String city = cityName.getText();
        //here instead of taking input string from textfield we will try a combo box kind of thing
        //but for now return the city
        return city;
    }

    public String getAdhar() {
        String aadhar = aadharNumber.getText();
        //verify the correctness of adhaar
        //if not then show joptionpane with a message
        return aadhar;
    }

    public java.sql.Date getDob() {
        LocalDate dob = dateOfBirth.getValue();
        java.sql.Date date = Date.valueOf(dob);
        return date;
    }

    public double getLatitude(){
        return Double.parseDouble(latitudeTextField.getText());
    }

    public double getLongitude(){
       return Double.parseDouble(longitudeTextField.getText());
    }

    @FXML
    void upload(ActionEvent e) {
        FileChooser fc = new FileChooser();
        //set the initial directory which will be opened first
//        fc.setInitialDirectory(new File("C:\\Users\\HP\\Pictures\\Screenshots"));
        //adding filters to the type of file that will be opened
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("image files","*.png","*.jpg","*.jpeg"));
        selectedFile = fc.showOpenDialog(null);
        upload.setText(selectedFile.getName());
    }
    String originalOtp;
    //method to recieve OTP message
    @FXML
    public void getOTP(){
        String phoneNo = getPhone();
        originalOtp = SendSMS.OTP();
        System.out.println("OTP: "+originalOtp);
        //SendSMS.sendSms(originalOtp,phoneNo);
        System.out.println("Clicked.");

        Task task = new Task<Void>() {
            @Override public Void call() throws InterruptedException {
                long clickTime = System.currentTimeMillis();
                otpButton.setDisable(true);
                while(System.currentTimeMillis() - clickTime < 30000) {

                }
                otpButton.setDisable(false);
                return null;
            }
        };

        new Thread(task).start();
    }
    //this function is to be registered with the submit button in scene builder
    @FXML
    void submitResponse(ActionEvent e) throws Exception{

        //opens profilePage.fxml on successful register
        //Fetching all information from
        String name = getName();
        String phoneNo = getPhone();
        String password = getPassword();
        String city = getCity();
        String aadhar = getAdhar();
        java.sql.Date dob = getDob();
        String Email = getEmail();
        double latitude = getLatitude();
        double longitude = getLongitude();
        String emailOTPEntered = getEmailOTPEntered();
        //now sending an OTP to the user for verification
        //this OTP will be entered by the user in the OTP field

        String otp = otpTextField.getText();
//        ObjectOutputStream oos = new ObjectOutputStream(Main.socket.getOutputStream());
//        ObjectInputStream ois = new ObjectInputStream(Main.socket.getInputStream());
//        //writing the actual and the entered OTP to the server
//        oos.writeObject(originalOtp);
//        oos.writeObject(otp);
//
//        //getting the verification response from the server, if otps don't match, user will be prompted
//        String verification = (String) ois.readObject();
        if(originalOtp.equals(otp) && emailOTP.equals(emailOTPEntered)) {
            //creating the object of singleton class UserTable to avoid creating multiple objects for the same user table entity
            FileInputStream fis = new FileInputStream(selectedFile);
            Main.userTable.insertUser(name, phoneNo, password, city, fis, aadhar, dob,Email, latitude, longitude);


            //open a new Scene for Login Page
            Parent root = FXMLLoader.load(getClass().getResource("LoginForm.fxml"));
            Scene scene = new Scene(root, 580, 790);
            Main.primaryStage.setTitle("Login");
            Main.primaryStage.setScene(scene);
            Main.primaryStage.show();
        }
        else{
            JOptionPane.showMessageDialog(null,"OTPs don't match!!");
        }

    }

    private String getEmailOTPEntered() {
        return verifyEmailTextField.getText();
    }

    @FXML
    public void selectButtonResponse(ActionEvent e){
        Label locationAccessLabel = new Label("Allow location access to automatically get coordinates: ");
        Button accessGrantedButton = new Button("Allow access");
        Label openMapLabel = new Label("Open Map and manually enter coordinates: ");
        Button openMapButton = new Button("Open Button");

        VBox vBox = new VBox();
        vBox.getChildren().addAll(locationAccessLabel,accessGrantedButton,openMapLabel,openMapButton);
        vBox.setPrefHeight(300);
        vBox.setPrefWidth(300);
        vBox.setSpacing(5);
        vBox.setAlignment(Pos.CENTER);

        Stage createStage = new Stage();
        AnchorPane root = new AnchorPane();
        root.getChildren().add(vBox);

        Scene canvasScene = new Scene(root);
        createStage.setTitle("Get Location");
        createStage.setScene(canvasScene);
        createStage.show();

        openMapButton.setOnAction(actionEvent1 -> {
            WebBrowser.main(null);
            createStage.close();
        });
        accessGrantedButton.setOnAction(actionEvent1 -> {
            Address address = null;
            try {
//                address = GetLocationFromIP.getAddress();
                Task task = new Task<Void>() {
                    @Override public Void call() throws InterruptedException, IOException {
                        ArrayList<String> arrayList = GeoipifyAPIQuery.accessLocation();
                        latitudeTextField.setText(arrayList.get(0));
                        longitudeTextField.setText(arrayList.get(1));
                        cityName.setText(arrayList.get(2));
                        return null;
                    }
                };

                new Thread(task).start();
            }
            catch (Exception e1) {
                e1.printStackTrace();
            }
            createStage.close();
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Image image = new Image("file:./src/sample/Resources/farm.jpg");
        BackgroundSize bSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false);
        Background background = new Background(new BackgroundImage(image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                bSize));

        borderPane.setBackground(background);
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
    //an utility function to convert the date string from textfield into sql date
    java.sql.Date parseDate(String date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        java.sql.Date sqlDate = null;
        try {
            java.util.Date utilDate = format.parse(date);
            sqlDate = new java.sql.Date(utilDate.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sqlDate;
    }

    @FXML
    public void verifyEmailButtonResponse(ActionEvent e) {
        try {
            Task task = new Task<Void>() {
                @Override public Void call() throws InterruptedException, UnsupportedEncodingException, MessagingException {
                    Email email = new Email("onlinemandi0@gmail.com","KSSU$367");
                    email.setFrom("onlinemandi0@gmail.com","ONLINE MANDI");
                    email.setSubject("Verification Mail");
                    Random random = new Random();
                    emailOTP = ""+random.ints(1000, 9999).findFirst().getAsInt();
                    email.setContent("<h1>Welcome to Online Mandi</h1><br><br><p>Your Verification Code Is "+emailOTP+".</p>","text/html");
                    email.addRecipient(emailTextField.getText());
                    email.send();
                    return null;
                }
            };
            Task task2 = new Task<Void>() {
                @Override public Void call() throws InterruptedException, UnsupportedEncodingException, MessagingException {
                    long clickTime = System.currentTimeMillis();
                    verifyEmailButton.setDisable(true);
                    while(System.currentTimeMillis() - clickTime < 30000) {

                    }
                    verifyEmailButton.setDisable(false);
                    return null;
                }
            };
            new Thread(task).start();
            new Thread(task2).start();
        } catch(Exception e1) {
            JOptionPane.showMessageDialog(null,"Enter a valid email address.");
            e1.printStackTrace();
        }
    }

    @FXML
    public void loginButtonResponse(ActionEvent e) throws IOException {
         //open a new Scene for Login Page
         Parent root = FXMLLoader.load(getClass().getResource("LoginForm.fxml"));
         Scene scene = new Scene(root, 580, 790);
         Main.primaryStage.setX(450);
         Main.primaryStage.setY(20);
         Main.primaryStage.setTitle("Login");
         Main.primaryStage.setScene(scene);
         Main.primaryStage.show();
    }
}