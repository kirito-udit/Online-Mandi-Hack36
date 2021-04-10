package sample;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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

    @FXML
    private Conversation lastSelected;

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
        lastSelected = (Conversation) conversationTableView.getSelectionModel().getSelectedItem();
        setConvoTextArea();
        conversationTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            int idx = conversationTableView.getSelectionModel().getSelectedIndex();
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    ArrayList<Conversation> list2 = MessageManager.getInstance().getAllConversations(phoneNo,name);
                    observableConvoList= FXCollections.observableList(list2);
                }
            });
            phoneNoTableColumn.setCellValueFactory(
                    new PropertyValueFactory<Conversation,String>("client")
            );
            nameTableColumn.setCellValueFactory(
                    new PropertyValueFactory<Conversation,String>("nameOfClient")
            );
            conversationTableView.setItems(observableConvoList);
            conversationTableView.getSelectionModel().select(idx);
            setConvoTextArea();
        });
    }
//    @FXML
//    public void clickItem(MouseEvent event) {
//        setConvoTextArea();
//    }
    @FXML
    public void setConvoTextArea() {
        Conversation convo = (Conversation) conversationTableView.getSelectionModel().getSelectedItem();
        conversationTextArea.setText(convo.getConvo()+"\n");
    }
    @FXML
    public void sendButtonResponse(ActionEvent e) {
        if(sendTextField.getText().trim()=="")
            return;
        Conversation conversation = (Conversation) conversationTableView.getSelectionModel().getSelectedItem();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        MessageManager.getInstance().addConversation(this.phoneNo,conversation.getClient(),sendTextField.getText(),timestamp,0);
        conversationTextArea.setText(conversationTextArea.getText()+this.name+"\n"+timestamp.toString()+"\n"+sendTextField.getText()+"\n");
        sendTextField.setText("");
    }
    @FXML
    public void refreshButtonResponse(ActionEvent e) {
        first(name,phoneNo);
    }
}
