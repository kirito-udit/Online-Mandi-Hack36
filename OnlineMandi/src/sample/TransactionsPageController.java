package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

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
    ObservableList<Transaction> sellingTransactions;
    ObservableList<Transaction> buyingTransactions;

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
        sellingTransactions = FXCollections.observableArrayList(Transactions.getInstance().getSellingTransactions(phoneNo));
        buyingTransactions = FXCollections.observableArrayList(Transactions.getInstance().getBuyingTransactions(phoneNo));
        sTID.setCellValueFactory(
                new PropertyValueFactory<Transaction,Integer>("transactionID")
        );
        sBuyerPhone.setCellValueFactory(
                new PropertyValueFactory<Transaction,Integer>("buyerPhone")
        );
        sBuyerName.setCellValueFactory(
                new PropertyValueFactory<Transaction,Integer>("buyerName")
        );
        sPrice.setCellValueFactory(
                new PropertyValueFactory<Transaction,Integer>("price")
        );
        sTimestamp.setCellValueFactory(
                new PropertyValueFactory<Transaction,Integer>("timestamp")
        );
        sCropName.setCellValueFactory(
                new PropertyValueFactory<Transaction,Integer>("cropName")
        );
        sTID.setCellValueFactory(
                new PropertyValueFactory<Transaction,Integer>("transactionID")
        );
        sBuyerPhone.setCellValueFactory(
                new PropertyValueFactory<Transaction,Integer>("buyerPhone")
        );
        sBuyerName.setCellValueFactory(
                new PropertyValueFactory<Transaction,Integer>("buyerName")
        );
        sPrice.setCellValueFactory(
                new PropertyValueFactory<Transaction,Integer>("price")
        );
        sTimestamp.setCellValueFactory(
                new PropertyValueFactory<Transaction,Integer>("timestamp")
        );
        sCropName.setCellValueFactory(
                new PropertyValueFactory<Transaction,Integer>("cropName")
        );
    }

}
