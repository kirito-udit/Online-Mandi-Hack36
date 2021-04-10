package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ChatsController implements Initializable {

    private String name,phoneNo;
    private ArrayList<Conversation> convoList;

    public ArrayList<Conversation> getConvoList() {
        return convoList;
    }

    public void setConvoList(ArrayList<Conversation> convoList) {
        this.convoList = convoList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    @FXML
    private TableView conversationTableView;
    @FXML
    private TextArea conversationTextArea;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Conversation> observableConvoList = FXCollections.observableList(convoList);
        conversationTableView.setItems(observableConvoList);
        conversationTableView.setItems(observableConvoList);
        conversationTableView.getSelectionModel().select(0);
        setConvoTextArea();
    }

    @FXML
    public void clickItem(MouseEvent event) {
        setConvoTextArea();
    }

    public void setConvoTextArea() {
        Conversation convo = (Conversation) conversationTableView.getSelectionModel().getSelectedItem();
        conversationTextArea.setText(convo.getConvo()+"\n");
    }
}
