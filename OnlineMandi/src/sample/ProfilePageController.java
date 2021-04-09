package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.net.URL;
    @FXML
    private BorderPane profilePane;

    @FXML
    private ImageView profilePic;

    @FXML
    private Label nameLabel;


    void createProfile(Image image, String name)
    {
        profilePic.setImage(image);
        nameLabel.setText(name);
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
}
