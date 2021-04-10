package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;
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
    @FXML
    private ComboBox sortByComboBox;
    @FXML
    private ComboBox filterByComboBox;

    ObservableList<Offer> data;
    private String phoneNo;
    private String name;
    String filter = "";
    private ObservableList<String> originalItems;
    public Trie t;
    private String pref = "";
    private int minPrice = 0;
    private int maxPrice = 1000000000;
    private SortedList <Offer> offerSortedList;
    private Comparator <Offer> globalComparator;
    private FilteredList <Offer> filteredList;
    private Comparator<Offer> sortByPriceComparator = new Comparator<Offer>() {
        @Override
        public int compare(Offer o1, Offer o2) {
            return (o1.getPrice()<o2.getPrice())?0:1;
        }
    };

    private Comparator<Offer> sortByFinishDateComparator = new Comparator<Offer>() {
        @Override
        public int compare(Offer o1, Offer o2) {
            return (o1.getEndDate().compareTo(o2.getEndDate()));
        }
    };

    private Comparator<Offer> sortByStartDateComparator = new Comparator<Offer>() {
        @Override
        public int compare(Offer o1, Offer o2) {
            return (o1.getStartDate().compareTo(o2.getStartDate()));
        }
    };

    private Predicate<Offer> allOffersPredicate = new Predicate<Offer>() {
        @Override
        public boolean test(Offer offer) {
            return true;
        }
    };

    private Predicate<Offer> priceRangePredicate = new Predicate<Offer>() {
        @Override
        public boolean test(Offer offer) {
            if(offer.getPrice()>=minPrice && offer.getPrice()<=maxPrice)
                return true;
            return false;
        }
    };

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
        //Filling filter by combobox
        ArrayList <String> filterByArrayList = new ArrayList<>();
        filterByArrayList.add("Price Range");
        filterByComboBox.setItems(FXCollections.observableArrayList(filterByArrayList));

        //Filling sort by combobox
        ArrayList <String> sortByArrayList = new ArrayList<>();
        sortByArrayList.add("Price");
        sortByArrayList.add("End date");
        sortByArrayList.add("Start date");
        sortByComboBox.setItems(FXCollections.observableArrayList(sortByArrayList));

        //
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
        globalComparator = sortByPriceComparator;
        refresh();
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

        refresh();
    }

    @FXML
    public void refresh() {
        data = FXCollections.observableList(SellerTable.getInstance().getAllOffers());
        offerSortedList = new SortedList<Offer>(data,globalComparator);
        cropTableView.setItems(offerSortedList);
        cropTableView.getSelectionModel().select(0);
        setDescriptionTextArea();
    }

    @FXML
    public void hiButtonAction(ActionEvent e) {
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
    @FXML
    public void sortResponse(ActionEvent e) {
        if(((String)sortByComboBox.getSelectionModel().getSelectedItem()).equals("Price")) {
                globalComparator = sortByPriceComparator;
        }
        else if(((String)sortByComboBox.getSelectionModel().getSelectedItem()).equals("End date")) {
            globalComparator = sortByFinishDateComparator;
        }
        else if(((String)sortByComboBox.getSelectionModel().getSelectedItem()).equals("Start date")) {
            globalComparator = sortByStartDateComparator;
        }
        refresh();
    }
    @FXML
    private void filterResponse(ActionEvent e) {
        if(((String)filterByComboBox.getSelectionModel().getSelectedItem()).equals("Price Range")) {
            globalComparator = sortByPriceComparator;
            Spinner spinner = new Spinner();
            spinner.setEditable(true);
            spinner.setPromptText("Minimum price:");
            SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1000000000, 1);
            spinner.setValueFactory(valueFactory);

            Spinner spinner2 = new Spinner();
            spinner2.setEditable(true);
            spinner2.setPromptText("Maximum price:");
            SpinnerValueFactory<Integer> valueFactory2 = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1000000000, 1);
            spinner2.setValueFactory(valueFactory2);

            Button createButton = new Button();
            createButton.setText("Confirm order");

            VBox vBox = new VBox();
            vBox.getChildren().addAll(spinner,spinner2,createButton);
            vBox.setPrefHeight(200);
            vBox.setPrefWidth(200);
            vBox.setSpacing(5);
            vBox.setAlignment(Pos.CENTER);

            Stage createStage = new Stage();
            AnchorPane root = new AnchorPane();
            root.getChildren().add(vBox);

            Scene canvasScene = new Scene(root);
            createStage.setTitle("Select Range");
            createStage.setScene(canvasScene);
            createStage.show();

            createButton.setOnAction(actionEvent1 -> {
                minPrice = (Integer) spinner.getValue();
                maxPrice = (Integer) spinner2.getValue();
                data = FXCollections.observableList(SellerTable.getInstance().getAllOffers());
                offerSortedList = new SortedList<Offer>(data,globalComparator);
                filteredList = new FilteredList<>(offerSortedList,priceRangePredicate);
                cropTableView.setItems(filteredList);
                cropTableView.getSelectionModel().select(0);
                setDescriptionTextArea();
                createStage.close();
            });
        }
    }

}
