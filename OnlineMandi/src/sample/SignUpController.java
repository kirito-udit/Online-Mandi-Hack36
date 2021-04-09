package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.net.URL;
import java.util.ResourceBundle;

public class SignUpController implements Initializable {
    @FXML
    private GridPane gridPane;
    @FXML
    private BorderPane borderPane;
 public java.lang.String getname()
    {
        java.lang.String x = "k";
        return x;
    }
    public java.lang.String getpass()
    {
        return "t";
    }
    public java.lang.String getmail()
    {
        return "t";
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    Image image = new Image("file:///C:/Users/hp/Desktop/farm.jpg");
        BackgroundSize bSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false);
        Background background = new Background(new BackgroundImage(image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                bSize));

        borderPane.setBackground(background);
    }
}
