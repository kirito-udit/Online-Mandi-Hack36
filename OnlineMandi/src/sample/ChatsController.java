package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public void first() {
        MessageManager.getInstance().close();
        MessageManager.getInstance().open();
        ArrayList<Conversation> list = MessageManager.getInstance().getAllConversations(phoneNo,name);
        MessageManager.getInstance().close();
        if(list!=null)
            observableConvoList= FXCollections.observableList(list);
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
        conversationTextArea.setText(conversationTextArea.getText()+"\n"+
                this.name+"->"+conversation.getClient()+" "+timestamp.toString()+": "+
                sendTextField.getText());
        MessageManager.getInstance().addConversation(this.name,this.phoneNo,conversation.getClient(),conversation.getNameOfClient(),sendTextField.getText(),timestamp,0);
        sendTextField.setText("");
    }
}
