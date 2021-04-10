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

        ArrayList<Conversation> list = MessageManager.getInstance().getAllConversations(phoneNo,name);
//        if(list == null) {
//            MessageManager.getInstance().addConversation("Yogiji","99", name, phoneNo, "Welcome to Online Mandi chat!", new Timestamp(System.currentTimeMillis()), 0);
//            list = MessageManager.getInstance().getAllConversations(phoneNo,name);
//        }
//        if(list == null)
//            System.out.println("NULL HAI");
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
        System.out.println("here");
        Conversation convo = (Conversation) conversationTableView.getSelectionModel().getSelectedItem();
        conversationTextArea.setText(convo.getConvo()+"\n");
    }
    @FXML
    public void sendButtonResponse(ActionEvent e) {
        Conversation conversation = (Conversation) conversationTableView.getSelectionModel().getSelectedItem();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        conversationTextArea.setText(conversationTextArea.getText()+
                this.name+"->"+conversation.getClient()+" "+timestamp.toString()+": "+
                sendTextField.getText());
        MessageManager.getInstance().addConversation(this.name,this.phoneNo,conversation.getNameOfClient(),conversation.getClient(),sendTextField.getText(),timestamp,0);
        sendTextField.setText("");
        setConvoTextArea();
    }
    @FXML
    public void refreshResponse(ActionEvent e) {
        MessageManager.getInstance().close();
        MessageManager.getInstance().open();
        ArrayList<Conversation> list = MessageManager.getInstance().getAllConversations(phoneNo,name);
        MessageManager.getInstance().close();
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
}
