package edu.augustana.UI;

import edu.augustana.HamRadioServerClient;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class ServerBuildController {

    @FXML
    private Button cancelBtn;

    @FXML
    private Button createServerBtn;

    @FXML
    private TextField humidityField;

    @FXML
    private TextField scenarioNameField;

    @FXML
    private TextField solarIndex;

    @FXML
    private TextField tempField;

    @FXML
    private TextField timeField;

    @FXML
    private TextArea userMessageField;

    @FXML
    private TextField windSpeedField;

    public SandboxController parentController;


    @FXML
    void initialize(){


        cancelBtn.setOnAction(evt -> {
            parentController.setCreateServerVisible(true);
            Stage stage = (Stage) ((Node) evt.getSource()).getScene().getWindow();
            stage.close();
        });


        createServerBtn.setOnAction(event -> {

            try {
                HamRadioServerClient.createServer(scenarioNameField.getText(),Double.valueOf(solarIndex.getText()),Double.valueOf(humidityField.getText()));
                parentController.setConnected();
                parentController.updateListOfServer();
                parentController.setCreateServerVisible(true);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.close();

            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        });

    }



}
