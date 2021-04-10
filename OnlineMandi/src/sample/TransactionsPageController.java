package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class TransactionsPageController implements Initializable {
    @FXML
    private Button backToProfileButton;
    @FXML
    private TableColumn sTID;
    @FXML
    private TableColumn sBuyerPhone;
    @FXML
    private TableColumn sBuyerName;
    @FXML
    private TableColumn sPrice;
    @FXML
    private TableColumn sTimestamp;
    @FXML
    private TableColumn sCropName;
    @FXML
    private TableColumn bTID;
    @FXML
    private TableColumn bSellerPhone;
    @FXML
    private TableColumn bSellerName;
    @FXML
    private TableColumn bPrice;
    @FXML
    private TableColumn bTimestamp;
    @FXML
    private TableColumn bCropName;
    @FXML
    private TableView sellingTableView;
    @FXML
    private TableView buyingTableView;

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

    public void first(String name,String phoneNo) {
        this.name = name;
        this.phoneNo = phoneNo;

        //Fetching the transaction details from database and making observable array lists
        sellingTransactions = FXCollections.observableArrayList(Transactions.getInstance().getSellTransactions(phoneNo));
        buyingTransactions = FXCollections.observableArrayList(Transactions.getInstance().getBuyTransactions(phoneNo));
        
        //Populating the selling transactions table
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
        sellingTableView.setItems(sellingTransactions);
        sellingTableView.getSelectionModel().select(0);
        
        //Populating the buying transactions table
        bTID.setCellValueFactory(
                new PropertyValueFactory<Transaction,Integer>("transactionID")
        );
        bSellerPhone.setCellValueFactory(
                new PropertyValueFactory<Transaction,Integer>("sellerPhone")
        );
        bSellerName.setCellValueFactory(
                new PropertyValueFactory<Transaction,Integer>("sellerName")
        );
        bPrice.setCellValueFactory(
                new PropertyValueFactory<Transaction,Integer>("price")
        );
        bTimestamp.setCellValueFactory(
                new PropertyValueFactory<Transaction,Integer>("timestamp")
        );
        bCropName.setCellValueFactory(
                new PropertyValueFactory<Transaction,Integer>("cropName")
        );
        buyingTableView.setItems(buyingTransactions);
        buyingTableView.getSelectionModel().select(0);
    }

    @FXML
    private void backToProfileButtonResponse(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ProfilePage.fxml"));
        Parent root = (Parent) loader.load();
        ProfilePageController ppc = loader.getController();
        ppc.setName(name);
        ppc.setPhoneNo(phoneNo);
        Scene scene = new Scene(root, 900, 620);
        Main.primaryStage.setTitle("My Profile");
        Main.primaryStage.setScene(scene);
        Main.primaryStage.show();
    }
}
