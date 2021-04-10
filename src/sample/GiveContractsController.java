package sample;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GiveContractsController implements Initializable {

    String name;
    String phoneNo;
    @FXML
    private TableView contractsTableView;
    @FXML
    private Button backButton;
    @FXML
    private Button approachForContractButton;
    @FXML
    private TableColumn cropNameTableColumn;
    @FXML
    private TableColumn priceTableColumn;
    @FXML
    private TableColumn phoneNoTableColumn;
    @FXML
    private TableColumn descriptionTableColumn;

    private ObservableList observableContractsList;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

    }

    public void first(String name, String phoneNo) {
        this.name = name;
        this.phoneNo = phoneNo;
        System.out.println(name);
        System.out.println(phoneNo);
        cropNameTableColumn.setCellValueFactory(new PropertyValueFactory<ContractDummy, String>("cropName"));
        priceTableColumn.setCellValueFactory(new PropertyValueFactory<ContractDummy, String>("price"));
        phoneNoTableColumn.setCellValueFactory(new PropertyValueFactory<ContractDummy, Date>("sellerPhone"));
        descriptionTableColumn.setCellValueFactory(new PropertyValueFactory<ContractDummy, String>("description"));
        ArrayList<ContractDummy> list = Contract.getInstance().getAllContracts(phoneNo);

        observableContractsList = FXCollections.observableList(list);
        contractsTableView.setItems(observableContractsList);
        contractsTableView.getSelectionModel().select(0);
    }

    @FXML
    public void backButtonResponse(ActionEvent e) throws IOException {
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
    @FXML
    public void approachForContractButtonResponse(ActionEvent e) throws InterruptedException, IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Chats.fxml"));
        Parent root = (Parent) loader.load();
        ContractDummy selectedContract = (ContractDummy)contractsTableView.getSelectionModel().getSelectedItem();
        String message = "Hi I am interested in signing a contract with you";
        MessageManager.getInstance().addConversation(phoneNo,selectedContract.getSellerPhone(), message, new Timestamp(System.currentTimeMillis()), 0);
        ChatsController cpc = loader.getController();
        cpc.first(name,phoneNo,cpc);
        Scene scene = new Scene(root, 681, 530);
        Main.primaryStage.setTitle("Chat Page");
        Main.primaryStage.setScene(scene);
        Main.primaryStage.show();
        
    }


}
