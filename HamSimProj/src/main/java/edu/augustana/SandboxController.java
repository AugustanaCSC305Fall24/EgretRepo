package edu.augustana;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.util.ArrayList;

public class SandboxController {

    @FXML
    private ListView<TrainingListeningBot> agentList;

    @FXML
    private Button editScenarioBtn;

    @FXML
    private Tab enviromentTab;

    @FXML
    private Button filterBtn;

    @FXML
    private Button joinBtn;

    @FXML
    private ChoiceBox<SimScenario> scenarioChoiceBox;

    @FXML
    private Label scenarioDescription;

    @FXML
    private Label scenarioDialog;

    @FXML
    private Button servInfoBtn;

    @FXML
    private HBox serverhbox;

    @FXML
    private Button startScenarioBtn;

    @FXML
    private Button newScenarioBtn;


    @FXML
    void initialize(){

        ScenarioCollection.addScenario(SimScenario.getDefaultScenario());
        scenarioChoiceBox.getItems().addAll(ScenarioCollection.getCollection());
        scenarioChoiceBox.setValue(ScenarioCollection.getCollection().get(0));
        scenarioDescription.setText(scenarioChoiceBox.getValue().getDescription());
        agentList.getItems().addAll(scenarioChoiceBox.getValue().getBotCollection().getBots());

//        startScenarioBtn.setOnAction((evt -> {
//
//        });


    }


}
