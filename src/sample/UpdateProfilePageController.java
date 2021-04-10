package sample;

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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.w3c.dom.Text;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class UpdateProfilePageController implements Initializable {
    @FXML
    private Button changePasswordButton;
    @FXML
    private Button changeProfilePicButton;

    private String name;
    private String phoneNo;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void first(String name, String phoneNo) {
        this.name = name;
        this.phoneNo = phoneNo;

    }

    @FXML
    public void changePasswordButtonResponse(ActionEvent e) {
        Label currentPasswordLabel = new Label("Enter Current Password");
        Label newPasswordLabel = new Label("Enter New Password");
        Label confirmNewPasswordLabel = new Label("Confirm New Password");
        PasswordField currentPasswordPasswordField = new PasswordField();
        currentPasswordPasswordField.setPromptText("Current Password");
        PasswordField newPasswordPasswordField = new PasswordField();
        newPasswordPasswordField.setPromptText("New Password");
        PasswordField confirmNewPasswordPasswordField = new PasswordField();
        confirmNewPasswordPasswordField.setPromptText("Confirm new Password");
        Button changeButton = new Button();
        changeButton.setText("Change Password");

        VBox vBox = new VBox();
        vBox.getChildren().addAll(currentPasswordLabel,currentPasswordPasswordField,newPasswordLabel,newPasswordPasswordField,confirmNewPasswordLabel,confirmNewPasswordPasswordField,changeButton);
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
            String currentPassword = currentPasswordPasswordField.getText();
            System.out.println(currentPassword);
            String newPassword = newPasswordPasswordField.getText();
            String confirmedNewPassword = confirmNewPasswordPasswordField.getText();
            if(LoginController.getMd5(currentPassword).equals(UserTable.getInstance().getPassword(phoneNo))) {
                if(newPassword.equals(confirmedNewPassword)) {
                    if(UserTable.getInstance().updatePassword(phoneNo,LoginController.getMd5(newPassword)))
                        JOptionPane.showMessageDialog(null,"Password updated successfully!");
                    else
                        JOptionPane.showMessageDialog(null,"Error updating password!");
                }
                else {
                    JOptionPane.showMessageDialog(null,"Confirmed password not matching!");
                }
            }
            else {
                JOptionPane.showMessageDialog(null,"Current password wrong!");
            }
            changeStage.close();
        });
    }

    @FXML
    public void changeProfilePicResponse(ActionEvent e) {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("image files","*.png","*.jpg","*.jpeg"));
        File selectedFile = fc.showOpenDialog(null);
        changeProfilePicButton.setText(selectedFile.getName());
        if(UserTable.getInstance().updateProfilePic(phoneNo,selectedFile.getAbsolutePath()))
            JOptionPane.showMessageDialog(null,"Profile Pic Updated!");
        else
            JOptionPane.showMessageDialog(null,"Profile Pic Update failed!");
    }

    @FXML
    public void backToProfileButtonResponse(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ProfilePage.fxml"));
        Parent root = (Parent) loader.load();
        ProfilePageController ppc = loader.getController();
        ppc.setName(name);
        ppc.setPhoneNo(phoneNo);
        Image image = UserTable.getInstance().getProfilePic(phoneNo);
        ppc.createProfile(phoneNo,image,name);
        Scene scene = new Scene(root, 900, 620);
        Main.primaryStage.setTitle("My Profile");
        Main.primaryStage.setScene(scene);
        Main.primaryStage.show();
    }
    @FXML
    public void updateAddressButtonResponse(ActionEvent e) {
        Label locationAccessLabel = new Label("Allow location access to automatically get coordinates: ");
        Button accessGrantedButton = new Button("Allow access");
        Label openMapLabel = new Label("Open Map and manually enter coordinates: ");
        Button openMapButton = new Button("Open Button");
        Label latitudeLabel = new Label("New Latitude: ");
        TextField latitudeTextField = new TextField();
        latitudeTextField.setPromptText("Latitude");
        Label longitudeLabel = new Label("New Longitude: ");
        TextField longitudeTextField = new TextField();
        longitudeTextField.setPromptText("Longitude");
        Label cityLabel = new Label("New Address: ");
        TextField cityTextField = new TextField();
        cityTextField.setPromptText("Address");
        Button changeButton = new Button("Confirm");

        VBox vBox = new VBox();
        vBox.getChildren().addAll(locationAccessLabel,accessGrantedButton,openMapLabel,openMapButton,latitudeLabel,latitudeTextField,longitudeLabel,longitudeTextField,cityLabel,cityTextField,changeButton);
        vBox.setPrefHeight(400);
        vBox.setPrefWidth(300);
        vBox.setSpacing(5);
        vBox.setAlignment(Pos.CENTER);

        Stage createStage = new Stage();
        AnchorPane root = new AnchorPane();
        root.getChildren().add(vBox);

        Scene canvasScene = new Scene(root);
        createStage.setTitle("Update Location");
        createStage.setScene(canvasScene);
        createStage.show();

        openMapButton.setOnAction(actionEvent1 -> {
            WebBrowser.main(null);
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
                        cityTextField.setText(arrayList.get(2));
                        return null;
                    }
                };

                new Thread(task).start();
            }
            catch (Exception e1) {
                e1.printStackTrace();
            }
        });

        changeButton.setOnAction(actionEvent1 -> {
            boolean flag = true;
            flag = UserTable.getInstance().updateCity(phoneNo,cityTextField.getText());
            flag = UserTable.getInstance().updateLatitude(phoneNo,Double.parseDouble(latitudeTextField.getText()));
            flag = UserTable.getInstance().updateLongitude(phoneNo,Double.parseDouble(longitudeTextField.getText()));
            if(!flag)
                JOptionPane.showMessageDialog(null,"Error in updating location!");
            else
                JOptionPane.showMessageDialog(null,"Location updated successfully!");
            createStage.close();
        });
    }
}
