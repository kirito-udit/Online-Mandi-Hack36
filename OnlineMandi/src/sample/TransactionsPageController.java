package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class TransactionsPageController extends Initializable {
    @FXML
    public Button backToProfileButton;
    @FXML
    public TableColumn sTID;
    @FXML
    public TableColumn sBuyerPhone;
    @FXML
    public TableColumn sBuyerName;
    @FXML
    public TableColumn sPrice;
    @FXML
    public TableColumn sTimestamp;
    @FXML
    public TableColumn sCropName;
    @FXML
    public TableColumn bTID;
    @FXML
    public TableColumn bSellerPhone;
    @FXML
    public TableColumn bSellerName;
    @FXML
    public TableColumn bPrice;
    @FXML
    public TableColumn bTimestamp;
    @FXML
    public TableColumn bCropName;

    private String phoneNo;
    private String name;

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void first() {
        ArrayList <Transaction> sellingTransactions = Transactions.getInstance().getSellingTransactions(phoneNo);
        ArrayList <Transaction> buyingTransactions = Transactions.getInstance().getBuyingTransactions(phoneNo);

    }

}
