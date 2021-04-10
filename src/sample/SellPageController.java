package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import java.io.IOException;

public class SellPageController {
    @FXML
    private Button addOffersButton;

    @FXML
    private Button viewOffersButton;

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

    @FXML
    void addOffer(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddOfferPage.fxml"));
        Parent root = (Parent) loader.load();
        AddOfferController  aopc = loader.getController();
        aopc.setName(name);
        aopc.setPhoneNo(phoneNo);
        Scene scene = new Scene(root, 580, 790);
        Main.primaryStage.setTitle("AddOfferPage");
        Main.primaryStage.setScene(scene);
        Main.primaryStage.show();
    }

    @FXML
    void viewOffer(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ViewOffer.fxml"));
        Parent root = (Parent) loader.load();
        ViewOfferController  vopc = loader.getController();
        vopc.first(name,phoneNo);
        Scene scene = new Scene(root, 580, 600);
        Main.primaryStage.setTitle("ViewOfferPage");
        Main.primaryStage.setScene(scene);
        Main.primaryStage.show();
    }


}
