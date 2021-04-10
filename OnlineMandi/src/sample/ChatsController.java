package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ChatsController implements Initializable {

    private String name,phoneNo;
    private ArrayList<Conversation> convoList;
    public ObservableList<Conversation> observableConvoList;

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
    private TextField sendTextField;
    @FXML
    private TableView conversationTableView;
    @FXML
    private TextArea conversationTextArea;
    @FXML
    private Button sendButton;
    @FXML
    private TableColumn phoneNoTableColumn;
    @FXML
    private TableColumn nameTableColumn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public void first(String name, String phoneNo) {
        this.name = name;
        this.phoneNo = phoneNo;
        System.out.println(name);
        ArrayList<Conversation> list = MessageManager.getInstance().getAllConversations(phoneNo,name);

        observableConvoList= FXCollections.observableList(list);
        phoneNoTableColumn.setCellValueFactory(
                new PropertyValueFactory<Conversation,String>("client")
        );
        nameTableColumn.setCellValueFactory(
                new PropertyValueFactory<Conversation,String>("nameOfClient")
        );
        conversationTableView.setItems(observableConvoList);
        conversationTableView.getSelectionModel().select(0);
        setConvoTextArea();
    }
    @FXML
    public void clickItem(MouseEvent event) {
        setConvoTextArea();
    }
    @FXML
    public void setConvoTextArea() {
        Conversation convo = (Conversation) conversationTableView.getSelectionModel().getSelectedItem();
        conversationTextArea.setText(convo.getConvo()+"\n");
    }
    @FXML
    public void sendButtonResponse(ActionEvent e) {
        Conversation conversation = (Conversation) conversationTableView.getSelectionModel().getSelectedItem();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        MessageManager.getInstance().addConversation(this.name,this.phoneNo,conversation.getNameOfClient(),conversation.getClient(),sendTextField.getText(),timestamp,0);
        first(name,phoneNo);
        sendTextField.setText("");
    }
}
