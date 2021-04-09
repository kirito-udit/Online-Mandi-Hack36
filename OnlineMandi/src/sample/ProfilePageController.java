package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

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
    private Image img;

    void createProfile(Image image, String name)
    {
        nameLabel.setText(name);
        this.img = image;
        profilePic.setImage(image);
        profilePic.setVisible(true);
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
        profilePane.setBackground(background);
    }

    @FXML
    public void refresh() {
        profilePic.setImage(img);
        profilePic.setVisible(true);
        System.out.println("BUMTON PREMSED");
    }
}
