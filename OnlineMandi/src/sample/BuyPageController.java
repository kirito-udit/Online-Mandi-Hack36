package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.Window;

import javax.swing.*;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Stream;

public class BuyPageController implements Initializable {
    @FXML
    private Button refreshButton;
    @FXML
    private TableColumn cropNameTableColumn;
    @FXML
    private TableColumn priceTableColumn;
    @FXML
    private TableColumn quantityTableColumn;
    @FXML
    private TableView cropTableView;
    @FXML
    private TextArea descriptionTextArea;
    @FXML
    private Button buyButton;
    @FXML
    private ComboBox cropComboBox;

    ObservableList<Offer> data;
    private String phoneNo;
    private String name;
    String filter = "";
    private ObservableList<String> originalItems;
    public Trie t;
    private String pref = "";

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        data = FXCollections.observableList(SellerTable.getInstance().getAllOffers());
        final String[] crops = {"Wheat", "Rice","Barley","Jowar","Maize","Soybean","ChickPeas","Beans"
                ,"Sugarcane","Potatoes","Tomatoes","Coconut","Coffee","Tea",
                "SweetPotato","Radish","Carrot","SugarBeat","Olives","grapes","Cocoa Beans","Peas","Apples","Plantains","Mango",
                "Banana","Papaya","Strawberry","Lichi","PineApple","WaterMelon","MuskMelon","Yams","GroundNut","Mustard","Palm","SunflowerSeeds","FlexSeeds","EggPlant","Garlic","Onion"
                ,"BottleGourd","BitterGourd","ladyFinger","Coriander","GreenChilli","RedChilli"};
        t = new Trie();
        for(String crop : crops)
            t.insert(crop);

        cropComboBox.setItems(FXCollections.observableArrayList(crops));
        //originalItems = FXCollections.observableArrayList(cropComboBox.getItems());
        //new ComboBoxAutoComplete<String>(cropComboBox);
        cropNameTableColumn.setCellValueFactory(
                new PropertyValueFactory<Offer,Integer>("cropName")
        );
        priceTableColumn.setCellValueFactory(
                new PropertyValueFactory<Offer,Integer>("price")
        );
        quantityTableColumn.setCellValueFactory(
                new PropertyValueFactory<Offer,Integer>("quantity")
        );

        cropTableView.setItems(data);
        cropTableView.getSelectionModel().select(0);
        setDescriptionTextArea();
    }
    @FXML
    public void clickItem(MouseEvent event) {
        setDescriptionTextArea();
    }

    public void setDescriptionTextArea() {
        Offer offer = (Offer)cropTableView.getSelectionModel().getSelectedItem();
        String name = UserTable.getInstance().getFullName(offer.getSellerPhone());
        descriptionTextArea.setText("OfferID: "+offer.getOfferId()+"\n"+
                        "Seller Name: "+name+"\n"+
                        "Seller Phone Number: "+offer.getSellerPhone()+"\n"+
                        "Crop Name: "+offer.getCropName()+"\n"+
                        "Quantity: "+offer.getQuantity()+"\n"+
                        "Price/KG: "+offer.getPrice()+"\n"+
                        "Description: "+offer.getDescription()+"\n"+
                        "Sale start date: "+offer.getStartDate().toString()+"\n"+
                        "Sale end date: "+offer.getEndDate().toString()+"\n"
                );
    }

    @FXML
    public void refresh(ActionEvent e) {
        data = FXCollections.observableList(SellerTable.getInstance().getAllOffers());
        cropTableView.setItems(data);
        cropTableView.getSelectionModel().select(0);
        setDescriptionTextArea();
    }
    @FXML
    public void hiButtonAction(ActionEvent e) {
        MessageManager.getInstance().close();
        MessageManager.getInstance().open();
        Offer offer = (Offer) cropTableView.getSelectionModel().getSelectedItem();
        String name = UserTable.getInstance().getFullName(offer.getSellerPhone());
        MessageManager.getInstance().addConversation(this.phoneNo,offer.getSellerPhone(),"Hi!",new Timestamp(System.currentTimeMillis()),0);
        //MessageManager.getInstance().close();
    }
    @FXML
    public void buyButtonResponse(ActionEvent e) {
        Offer offer = (Offer) cropTableView.getSelectionModel().getSelectedItem();
        Spinner spinner = new Spinner();
        spinner.setEditable(true);
        spinner.setPromptText("Quantity:");
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, offer.getQuantity(), 1);
        spinner.setValueFactory(valueFactory);

        Button createButton = new Button();
        createButton.setText("Confirm order");

        VBox vBox = new VBox();
        vBox.getChildren().addAll(spinner,createButton);
        vBox.setPrefHeight(150);
        vBox.setPrefWidth(200);
        vBox.setSpacing(5);
        vBox.setAlignment(Pos.CENTER);

        Stage createStage = new Stage();
        AnchorPane root = new AnchorPane();
        root.getChildren().add(vBox);

        Scene canvasScene = new Scene(root);
        createStage.setTitle("Select Quantity");
        createStage.setScene(canvasScene);
        createStage.show();

        String sellerPhone = offer.getSellerPhone();
        String buyerPhone = phoneNo;
        String cropName = offer.getCropName();
        int price = offer.getPrice();

        createButton.setOnAction(actionEvent1 -> {
            Transactions.getInstance().processATransaction(buyerPhone,offer.getOfferId(),(int)spinner.getValue(),new Timestamp(System.currentTimeMillis()));
            createStage.close();
        });
    }
    @FXML
    public void handleOnKeyPressed(KeyEvent e) {
        pref = cropComboBox.getEditor().getText();
        List<String> cropList = t.autocomplete(pref);
        cropComboBox.setItems(FXCollections.observableArrayList(t.autocomplete(pref)));
    }
}
