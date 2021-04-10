package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;

import javax.swing.*;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;

public class AddOfferController {
    @FXML
    private TextField cropTextField;

    @FXML
    private TextArea descriptionTextArea;

    @FXML
    private DatePicker startDatePicker;

    @FXML
    private DatePicker endDatePicker;

    @FXML
    private Spinner quantitySpinner;

    @FXML
    private TextField priceTextField;

    private String name,phoneNo;

    public String getName() {
        return name;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    @FXML
    void addOffer(ActionEvent e){
        String cropName = cropTextField.getText();
        String desc = descriptionTextArea.getText();
        java.sql.Date startDateValue = Date.valueOf(startDatePicker.getValue());
        java.sql.Date endDateValue = Date.valueOf(endDatePicker.getValue());
        int quantity = (int) quantitySpinner.getValue();
        int price = Integer.parseInt(priceTextField.getText());
        String description = descriptionTextArea.getText();
        if(SellerTable.getInstance().addOffer(cropName,quantity,price,startDateValue,endDateValue,phoneNo,description))
            JOptionPane.showMessageDialog(null,"Offer added successfully!");
        else
            JOptionPane.showMessageDialog(null,"Offer addition failed!");
    }
    @FXML
    public void backToProfileButtonResponse(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ProfilePage.fxml"));
        Parent root = (Parent) loader.load();
        ProfilePageController ppc = loader.getController();
        Image image = UserTable.getInstance().getProfilePic(phoneNo);
        ppc.createProfile(phoneNo, image, name);
        Scene scene = new Scene(root, 900, 620);
        Main.primaryStage.setTitle("My Profile");
        Main.primaryStage.setScene(scene);
        Main.primaryStage.show();
    }
}
