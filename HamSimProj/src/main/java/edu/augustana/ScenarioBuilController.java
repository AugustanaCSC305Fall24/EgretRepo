package edu.augustana;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class ScenarioBuilController {

    @FXML
    private Button addBotBtn;

    @FXML
    private TableView<?> botListTable;

    @FXML
    private Button cancelBtn;

    @FXML
    private Button deletebotBtn;

    @FXML
    private TextArea descripTionField;

    @FXML
    private TextField humidityField;

    @FXML
    private Button saveScenarioBtn;

    @FXML
    private TextField scenarioNameField;

    @FXML
    private ChoiceBox<?> scenarioTypeChoice;

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


    @FXML
    void initialize(){

        addBotBtn.setOnAction(event -> {
            try {
                openBotAdder();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

    }


    private void openBotAdder() throws IOException {
        Stage scenarioBuildStage = new Stage();
        FXMLLoader loader = new FXMLLoader(App.class.getResource("botAdder.fxml"));
        scenarioBuildStage.setTitle("Scenario Builder");
        scenarioBuildStage.setScene(new Scene(loader.load()));
        scenarioBuildStage.show();
    }
}
