package sample;

import java.io.IOException;
import java.net.URL;
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

public class ViewOfferController implements Initializable {

    String name;
    String phoneNo;
    @FXML
    private TableView viewOfferTableView;
    @FXML
    private Button backButton;
    @FXML
    private Button updateOfferButton;
    @FXML
    private Button deleteOfferButton;
    @FXML
    private TableColumn cropNameTableColumn;
    @FXML
    private TableColumn priceTableColumn;
    @FXML
    private TableColumn startDateTableColumn;
    @FXML
    private TableColumn endDateTableColumn;
    @FXML
    private TableColumn descriptionTableColumn;

    private ObservableList observableOfferList;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

    }

    public void first(String name, String phoneNo) {
        this.name = name;
        this.phoneNo = phoneNo;
        System.out.println(name);
        System.out.println(phoneNo);
        cropNameTableColumn.setCellValueFactory(new PropertyValueFactory<Offer, String>("cropName"));
        priceTableColumn.setCellValueFactory(new PropertyValueFactory<Offer, String>("price"));
        startDateTableColumn.setCellValueFactory(new PropertyValueFactory<Offer, Date>("startDate"));
        endDateTableColumn.setCellValueFactory(new PropertyValueFactory<Offer, Date>("endDate"));
        descriptionTableColumn.setCellValueFactory(new PropertyValueFactory<Offer, String>("description"));
        ArrayList<Offer> list = SellerTable.getInstance().getMyOffers(phoneNo);

        observableOfferList = FXCollections.observableList(list);
        viewOfferTableView.setItems(observableOfferList);
        viewOfferTableView.getSelectionModel().select(0);
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
    public void updateOfferButtonResponse(ActionEvent e){
        
        Label enterNewPrice = new Label("Enter new price");
        Button updatePrice = new Button("UpdatePrice");
        TextField newPrice = new TextField();
        newPrice.setPromptText("Enter new Price");
        Label newStartDate = new Label("Enter new Start date");
        DatePicker startDate = new DatePicker();
        Button updateStartDate = new Button("UpdateStartDate");
        Label newEndDate = new Label("Enter new end date");
        DatePicker endDate = new DatePicker();
        Button updateEndDate = new Button("UpdateEndDate");
        Button updateDescription = new Button("UpdateDescription");
        TextField newDescription = new TextField();
        Label newDescLabel = new Label("Enter new description");
        newDescription.setPromptText("Enter new Description");
        Button close = new Button("Close");
        VBox vBox = new VBox();
        vBox.getChildren().addAll(enterNewPrice,newPrice,updatePrice,newStartDate,startDate,updateStartDate,newEndDate,endDate,updateEndDate,newDescLabel,newDescription,updateDescription,close);
        vBox.setPrefHeight(500);
        vBox.setPrefWidth(300);
        vBox.setSpacing(5);
        vBox.setAlignment(Pos.CENTER);

        Stage createStage = new Stage();
        AnchorPane root = new AnchorPane();
        root.getChildren().add(vBox);

        Scene canvasScene = new Scene(root);
        createStage.setTitle("UpdateOption");
        createStage.setScene(canvasScene);
        createStage.show();


        updatePrice.setOnAction(actionEvent1 -> {
            Offer offer = (Offer)viewOfferTableView.getSelectionModel().getSelectedItem();
            int offerId = offer.getOfferId();
            LocalDate now = LocalDate.now(); 
            Date input = new Date(offer.getStartDate().getTime());
            LocalDate date = input.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(); 
            if(now.isAfter(date)){
                JOptionPane.showMessageDialog(null, "Price can't be updated after the start date has passed");
            }
            else if(SellerTable.getInstance().updateOfferPrice(offerId,Integer.parseInt(newPrice.getText())))
                JOptionPane.showMessageDialog(null, "price Updated successfully");
            else{
                JOptionPane.showMessageDialog(null, "Price couldn't be updated");
            }
        });
        updateStartDate.setOnAction(actionEvent1 -> {
            Offer offer = (Offer)viewOfferTableView.getSelectionModel().getSelectedItem();
            int offerId = offer.getOfferId();
            if(SellerTable.getInstance().updateOfferStartDate(offerId, parseDate(startDate.getValue().toString())))
                JOptionPane.showMessageDialog(null, "Start Date Updated successfully");
        });
        updateEndDate.setOnAction(actionEvent1 -> {
            Offer offer = (Offer)viewOfferTableView.getSelectionModel().getSelectedItem();
            int offerId = offer.getOfferId();
            if(SellerTable.getInstance().updateOfferEndDate(offerId, parseDate(endDate.getValue().toString())))
            JOptionPane.showMessageDialog(null, "End Date Updated successfully");
        });
        
        updateDescription.setOnAction(actionEvent1 -> {
            Offer offer = (Offer)viewOfferTableView.getSelectionModel().getSelectedItem();
            int offerId = offer.getOfferId();
            if(SellerTable.getInstance().updateDescription(offerId,(newDescription.getText())))
            JOptionPane.showMessageDialog(null, "Description Updated successfully");
        });


        close.setOnAction(actionEven1->{
            createStage.close();
        });
    }
    @FXML
    public void deleteOfferButtonResponse(ActionEvent e){
        Offer offer = (Offer)viewOfferTableView.getSelectionModel().getSelectedItem();
        int offerId = offer.getOfferId();
        if(SellerTable.getInstance().deleteOffer(offerId))
            JOptionPane.showMessageDialog(null, "Offer deleted successfully");
    }

    java.sql.Date parseDate(String date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        java.sql.Date sqlDate = null;
        try {
            java.util.Date utilDate = format.parse(date);
            sqlDate = new java.sql.Date(utilDate.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sqlDate;
    }
}
