package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import sample.*;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ProfilePageController implements Initializable {
    @FXML
    private BorderPane profilePane;

    @FXML
    public ImageView profilePic;

    public ImageView getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(Image profilePic) {
        this.profilePic.setImage(profilePic);
        this.profilePic.setVisible(true);
    }

    @FXML
    private Label nameLabel;

    @FXML
    private Button refreshButton;

    @FXML
    private Button buySectionButton;

    @FXML
    private Button sellSectionButton;

    @FXML
    private Button chatButton;

    @FXML
    private Button historyButton;

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

    private Image img;
    private String phoneNo;
    private String name;

    void createProfile(String phoneNo,Image image, String name) {
        nameLabel.setText(name);
        profilePic.setImage(image);
        profilePic.setVisible(true);
        this.phoneNo = phoneNo;
        this.name = name;
    }

    @FXML
    void buyButtonResponse(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("BuyPage.fxml"));
        Parent root = (Parent) loader.load();
        BuyPageController bpc = loader.getController();
        bpc.setName(name);
        bpc.setPhoneNo(phoneNo);
        Scene scene = new Scene(root, 600, 593);
        Main.primaryStage.setTitle("Buy Page");
        Main.primaryStage.setScene(scene);
        Main.primaryStage.show();
    }

    @FXML
    void sellButtonResponse(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("SellPage.fxml"));
        Parent root = (Parent) loader.load();
        SellPageController spc = loader.getController();
        spc.setName(name);
        spc.setPhoneNo(phoneNo);
        Scene scene = new Scene(root, 580, 790);
        Main.primaryStage.setTitle("Sell Page");
        Main.primaryStage.setScene(scene);
        Main.primaryStage.show();

    }
    @FXML
    void chatButtonResponse(ActionEvent e) throws IOException, InterruptedException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Chats.fxml"));
        Parent root = (Parent) loader.load();
        ChatsController cpc = loader.getController();
        cpc.first(name,phoneNo,cpc);
        Scene scene = new Scene(root, 681, 530);
        Main.primaryStage.setTitle("Chat Page");
        Main.primaryStage.setScene(scene);
        Main.primaryStage.show();
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Image image = new Image("file:./src/sample/Resources/farmProfilePage.jpg");
        BackgroundSize bSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false);
        Background background = new Background(new BackgroundImage(image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                bSize));
        profilePane.setBackground(background);
    }

    @FXML
    void historyButtonResponse(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("TransactionsPage.fxml"));
        Parent root = (Parent) loader.load();
        TransactionsPageController tpc = loader.getController();
        tpc.first(name,phoneNo);
        Scene scene = new Scene(root, 750, 600);
        Main.primaryStage.setTitle("Transactions History");
        Main.primaryStage.setScene(scene);
        Main.primaryStage.show();
    }
}
