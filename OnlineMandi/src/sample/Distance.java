import javax.swing.*;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.*;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class Distance extends JFrame implements Runnable{

    private WebEngine webEngine;
    private JFXPanel panel;
    String l1;
    String l2;
    String d1;
    String d2;
    void getdes(String a, String b)
    {
        l1 = a;
        l2 = b;
    }
    void getdes2(String a, String b)
    {
        d1 = a;
        d2 = b;
    }
  

    public static void main(String[] args) {
        Distance objd  = new Distance();
        objd.getdes("27713","75564");
        objd.getdes2("28910","76457");
       // SwingUtilities.invokeLater(new sample.WebBrowser());
        objd.run();
    }

}
