package sample;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.math.BigInteger;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
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
    private TextField poBoxNumber;
    File selectedFile;
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
    public String getPIN() {
        String pin = (poBoxNumber.getText());
        //verify correctness of PIN whether it is of six digits or not
        return pin;
    }
    @FXML
    void upload(ActionEvent e) {
        FileChooser fc = new FileChooser();
        //set the initial directory which will be opened first
//        fc.setInitialDirectory(new File("C:\\Users\\HP\\Pictures\\Screenshots"));
        //adding filters to the type of file that will be opened
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("image files","*.png","*.jpg","*.jpeg"));
        selectedFile = fc.showOpenDialog(null);
    }
    //this function is to be registered with the submit button in scene builder
    @FXML
    void submitResponse(ActionEvent e) throws Exception{
        //opens profilePage.fxml on successful register
        String name = getName();
        String phoneNo = getPhone();
        String password = getPassword();
        String city = getCity();
        String aadhar = getAdhar();
        java.sql.Date dob = getDob();
        String poBoxNumber = getPIN();
        //fetching the path of the image selectd from fileChooser
        //but before that check if file size exceeds  307200 bytes, if so then error is to be shown
        String local = null;
//        if(selectedFile.length() > 307200)
//             JOptionPane.showMessageDialog(null,"File size exceeds 300KB!!!");
//        else
//        local = selectedFile.toURI().toString();
        //creating the object of singleton class UserTable to avoid creating multiple objects for the same user table entity
        FileInputStream fis = new FileInputStream(selectedFile);
        Main.userTable.insertUser(name,phoneNo,password,city,fis,aadhar,dob,poBoxNumber);
        //open a new Scene for Login Page
        Parent root = FXMLLoader.load(getClass().getResource("LoginForm.fxml"));
        Scene scene = new Scene(root, 580, 790);
        Main.primaryStage.setTitle("Login");
        Main.primaryStage.setScene(scene);
        Main.primaryStage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Image image = new Image("file:///C:/Users/hp/Desktop/farm.jpg");
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
}
