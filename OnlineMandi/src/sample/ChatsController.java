package sample;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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

    @FXML
    private TextField searchTextField;

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
        if(lastSelected == null)
                conversationTableView.getSelectionModel().select(0);
        else
                conversationTableView.getSelectionModel().select(lastSelected);
        lastSelected = (Conversation) conversationTableView.getSelectionModel().getSelectedItem();
        setConvoTextArea();

        conversationTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            lastSelected = (Conversation) newSelection;
            setConvoTextArea();
        });

        phoneNoTableColumn.setCellValueFactory(
                new PropertyValueFactory<Conversation,String>("client")
        );
        nameTableColumn.setCellValueFactory(
                new PropertyValueFactory<Conversation,String>("nameOfClient")
        );
        conversationTableView.setItems(observableConvoList);
        // conversationTableView.getSelectionModel().select(idx);
        setConvoTextArea();
//        });

        // Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<Conversation> filteredData = new FilteredList<>(observableConvoList, b -> true);

        // 2. Set the filter Predicate whenever the filter changes.
        searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(convo -> {
                // If filter text is empty, display all persons.

                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (convo.getNameOfClient().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
                    return true; // Filter matches first name.
                } else if (convo.getClient().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches last name.
                }
                else
                    return false; // Does not match.
            });
        });

        // 3. Wrap the FilteredList in a SortedList.
        SortedList<Conversation> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        // 	  Otherwise, sorting the TableView would have no effect.
        sortedData.comparatorProperty().bind(conversationTableView.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        conversationTableView.setItems(sortedData);


    }

    @FXML
    public void setConvoTextArea() {
        Conversation convo = (Conversation) conversationTableView.getSelectionModel().getSelectedItem();
        if(convo == null) {
            convo = lastSelected;
            conversationTableView.getSelectionModel().select(lastSelected);
        }
        try {
            conversationTextArea.setText(convo.getConvo());
        } catch(Exception e) {

        }
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
