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
    private DatePicker startDate;

    @FXML
    private DatePicker endDate;

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
        java.sql.Date startDateValue = Date.valueOf(startDate.getValue());
        java.sql.Date endDateValue = Date.valueOf(endDate.getValue());
        int quantity = (int) quantitySpinner.getValue();
        String price = priceTextField.getText();

        SellerTable sellerTable = SellerTable.getInstance();
        sellerTable.addOffer(cropName,quantity,price,startDateValue,endDateValue,name,phoneNo);

    }

}
