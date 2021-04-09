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

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ProfilePageController implements Initializable {
    @FXML
    private BorderPane profilePane;

    @FXML
    private ImageView profilePic;

    @FXML
    private Label nameLabel;

    @FXML
    private Button refreshButton;

    @FXML
    private Button sellSectionButton;
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
    void sellButtonResponse(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("SellPage.fxml"));
        Parent root = (Parent) loader.load();
        SellPageController  spc = loader.getController();
        spc.setName(name);
        spc.setPhoneNo(phoneNo);
        Scene scene = new Scene(root, 580, 790);
        Main.primaryStage.setTitle("Login");
        Main.primaryStage.setScene(scene);
        Main.primaryStage.show();

    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Image image = new Image("file:///C:/Users/hp/Desktop/farmProfilePage.jpg");
        BackgroundSize bSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false);
        Background background = new Background(new BackgroundImage(image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                bSize));

    }
}
