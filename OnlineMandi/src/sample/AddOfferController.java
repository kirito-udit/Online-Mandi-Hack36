package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

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
        System.out.println(SellerTable.getInstance().addOffer(cropName,quantity,price,startDateValue,endDateValue,name,phoneNo));
    }

}
