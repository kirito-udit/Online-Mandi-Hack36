package sample;

import javax.swing.*;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.*;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class WebBrowser extends JFrame implements Runnable{

    private WebEngine webEngine;
    private JFXPanel panel;
    public void run() {
        setTitle("Search");
        setVisible(true);
        setBounds(0,0,500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel = new JFXPanel();
        add(panel);
        Platform.runLater(() -> {
            WebView view = new WebView();
        view.getEngine().load("https://www.openstreetmap.org/search?whereami=1&query=28.5897%2C77.1450#map=12/28.5895/77.1450");
            panel.setScene(new Scene(view));
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new WebBrowser());
    }

}
