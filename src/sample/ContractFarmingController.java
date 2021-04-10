package sample;

import java.io.IOException;

import javafx.event.*;
import javafx.fxml.*;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ContractFarmingController {
    @FXML
    private Button getContractButton;
    @FXML
    private Button giveContractButton;

    private String name;
    private String phoneNo;

    public void first(String name,String phoneNo){
        this.name = name;
        this.phoneNo = phoneNo;
    }

    @FXML
    public void getContractButtonResponse(ActionEvent e) {
        Label cropLabel = new Label("Enter the crop you grow");
        TextField cropTextField = new TextField();
        cropTextField.setPromptText("Enter name of crop");
        Label expectedPriceLable = new Label("Enter expected minimum price");
        TextField expectedPriceTextField = new TextField();
        expectedPriceTextField.setPromptText("Enter expected price");
        Label descriptionLabel = new Label("Enter the description (e.g your experience)");
        TextArea descriptionTextArea = new TextArea();
        descriptionTextArea.setPromptText("Enter description");
        Button submit = new Button("Submit");
        VBox vBox = new VBox();
        vBox.getChildren().addAll(cropLabel,cropTextField,expectedPriceLable,expectedPriceTextField,
        descriptionLabel,descriptionTextArea,submit);
        vBox.setPrefHeight(400);
        vBox.setPrefWidth(400);
        vBox.setSpacing(5);
        vBox.setAlignment(Pos.CENTER);

        Stage createStage = new Stage();
        AnchorPane root = new AnchorPane();
        root.getChildren().add(vBox);

        Scene canvasScene = new Scene(root);
        createStage.setTitle("Add yourself to ContractList");
        createStage.setScene(canvasScene);
        createStage.show();

        submit.setOnAction(actionEvent1->{
            String cropName = cropTextField.getText();
            int expectedPrice = Integer.parseInt(expectedPriceTextField.getText());
            String description = descriptionTextArea.getText();
            Contract.getInstance().addContract(cropName, expectedPrice, phoneNo, description);
            createStage.close();
        });

    }

    @FXML
    public void giveContractButtonResponse(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("GiveContracts.fxml"));
        Parent root = (Parent) loader.load();
        GiveContractsController gcc = loader.getController();
        gcc.first(name,phoneNo);
        Scene scene = new Scene(root, 580, 620);
        Main.primaryStage.setTitle("Available Contract Farmers");
        Main.primaryStage.setScene(scene);
        Main.primaryStage.show();
    }
}
