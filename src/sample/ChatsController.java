package sample;

import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ChatsController implements Initializable, Runnable{

    private String name,phoneNo;
    private String sellerPhone = null;
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
    private TextField searchTextField;
    private FilteredList<Conversation> filteredData;
    private SortedList<Conversation> sortedData;
    private ChatsController cpc;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public String getSellerPhone() {
        return sellerPhone;
    }

    public void setSellerPhone(String sellerPhone) {
        this.sellerPhone = sellerPhone;
    }

    //    public ChatsController(ChatsController cpc){
//        this.cpc = cpc;
//    }
    public void first(String name, String phoneNo, ChatsController cpc) throws InterruptedException {
        this.name = name;
        this.phoneNo = phoneNo;
        this.cpc = cpc;
        System.out.println(name);
        System.out.println(phoneNo);
        phoneNoTableColumn.setCellValueFactory(
                new PropertyValueFactory<Conversation,String>("client")
        );
        nameTableColumn.setCellValueFactory(
                new PropertyValueFactory<Conversation,String>("nameOfClient")
        );
        ArrayList<Conversation> list = MessageManager.getInstance().getAllConversations(phoneNo,name);

        observableConvoList= FXCollections.observableList(list);
        conversationTableView.setItems(observableConvoList);
        if(sellerPhone == null)
            conversationTableView.getSelectionModel().select(0);
        else {
            for(Conversation conversation : observableConvoList) {
                if(conversation.getClient().equals(sellerPhone)) {
                    conversationTableView.getSelectionModel().select(conversation);
                    break;
                }
            }
        }
        setConvoTextArea();

        conversationTextArea.textProperty().addListener((observableValue, s, t1) -> conversationTextArea.setScrollTop(Double.MAX_VALUE));
        filteredData = new FilteredList<>(observableConvoList, b -> true);

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
        sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        // 	  Otherwise, sorting the TableView would have no effect.
        sortedData.comparatorProperty().bind(conversationTableView.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        conversationTableView.setItems(sortedData);
        //now the thread for receiving messages will start separately
        Thread t = new Thread(cpc);
        t.start();

    }
    @Override
    public  void run() {
        while(true) {
            try {

                //if the current selected item's phone No is same as the phone No if the incoming message
                //we simply display it on the text area
                //otherwise we will send a message to the the sender that his message has not been seen yet
                //and highlight that msg
                String senderPhoneNo = (String) Main.ois.readObject();
                Timestamp timestamp = (Timestamp) Main.ois.readObject();
                String message = (String) Main.ois.readObject();
                System.out.println(message);
                UserTable userTable = UserTable.getInstance();
                String senderName = userTable.getFullName(senderPhoneNo);
                Conversation convo = (Conversation) this.conversationTableView.getSelectionModel().getSelectedItem();

                if (convo.getClient().equals(senderPhoneNo)) {
                    this.conversationTextArea.setText(this.conversationTextArea.getText() +"\n"+ senderName + "\n" + timestamp + "\n" + message);
                    this.conversationTextArea.appendText("");
                }
                updateTable();
            }catch(Exception e){
                e.printStackTrace();
                return;
            }
        }
    }

    public void updateTable(){
        int index = conversationTableView.getSelectionModel().getSelectedIndex();
        ArrayList<Conversation> list = MessageManager.getInstance().getAllConversations(this.phoneNo,this.name);
        this.observableConvoList= FXCollections.observableList(list);
        filteredData = new FilteredList<>(observableConvoList, b -> true);
        sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(conversationTableView.comparatorProperty());
        conversationTableView.setItems(sortedData);
        this.conversationTableView.getSelectionModel().select(index);
    }

    @FXML
    public void clickItem(MouseEvent event) {
        updateTable();
        setConvoTextArea();
    }

    public void setConvoTextArea() {
        if(conversationTableView.getSelectionModel().getSelectedItem() == null)
            return;
        Conversation convo = (Conversation)this.conversationTableView.getSelectionModel().getSelectedItem();
        this.conversationTextArea.setText(convo.getConvo());
        this.conversationTextArea.appendText("");
    }
    @FXML
    public void sendButtonResponse(ActionEvent e) throws IOException {
        Conversation convo = (Conversation) conversationTableView.getSelectionModel().getSelectedItem();
        Main.oos.writeObject(convo.getClient());
        Main.oos.writeObject(new Timestamp(System.currentTimeMillis()));
        Main.oos.writeObject(sendTextField.getText());
        conversationTextArea.setText(conversationTextArea.getText()+
                "\n"+name+"\n"+new Timestamp(System.currentTimeMillis())+
                "\n"+sendTextField.getText());
        conversationTextArea.appendText("");
        Main.oos.flush();
        sendTextField.setText("");
    }
    @FXML
    public void backToProfileResponse(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ProfilePage.fxml"));
        Parent root = (Parent) loader.load();
        ProfilePageController ppc = loader.getController();
        ppc.setName(name);
        ppc.setPhoneNo(phoneNo);
        Image image = UserTable.getInstance().getProfilePic(phoneNo);
        ppc.createProfile(phoneNo,image,name);
        Scene scene = new Scene(root, 900, 620);
        Main.primaryStage.setTitle("My Profile");
        Main.primaryStage.setScene(scene);
        Main.primaryStage.show();
    }
}
