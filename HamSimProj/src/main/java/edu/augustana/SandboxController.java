package edu.augustana;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

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

    private ScenarioBuilController buildController;

    @FXML
    private Slider wpmSlider;


    @FXML
    void initialize(){

        ScenarioCollection.addScenario(SimScenario.getDefaultScenario());
        scenarioChoiceBox.getItems().addAll(ScenarioCollection.getCollection());
        scenarioChoiceBox.setValue(ScenarioCollection.getCollection().get(0));
        scenarioDescription.setText(scenarioChoiceBox.getValue().getDescription());
        agentList.getItems().addAll(scenarioChoiceBox.getValue().getBotCollection().getBots());


        startScenarioBtn.setOnAction(evt -> {
            try {
                scenarioChoiceBox.getValue().startScenario();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        newScenarioBtn.setOnAction(evt -> {
            try {
                Stage scenarioBuildStage = new Stage();
                FXMLLoader loader = new FXMLLoader(App.class.getResource("ScenarioBuildUI.fxml"));
                scenarioBuildStage.setTitle("Scenario Builder");
                scenarioBuildStage.setScene(new Scene(loader.load()));
                scenarioBuildStage.show();
                Thread.sleep(500);
                buildController = loader.getController();
                buildController.newScenario();
                buildController.setParentController(this);

            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        editScenarioBtn.setOnAction(evt -> {
            try {
                Stage scenarioBuildStage = new Stage();
                FXMLLoader loader = new FXMLLoader(App.class.getResource("ScenarioBuildUI.fxml"));

                scenarioBuildStage.setTitle("Scenario Builder");
                scenarioBuildStage.setScene(new Scene(loader.load()));
                scenarioBuildStage.show();
                Thread.sleep(500);
                buildController = loader.getController();
                buildController.setParentController(this);
                buildController.editScenario(scenarioChoiceBox.getValue());

            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        scenarioChoiceBox.setOnAction(event -> {
            scenarioChoiceBox.setValue(ScenarioCollection.getCollection().get(ScenarioCollection.getCollection().size() - 1));
            displayCurrentScenario();
        });

        wpmSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            MorsePlayer.setWordsPerMinuteMultiplier((int) wpmSlider.getValue());
        });


    }

    public void updateScenarioChoice(){
        scenarioChoiceBox.getItems().clear();
        scenarioChoiceBox.getItems().addAll(ScenarioCollection.getCollection());

    }

    public void setCurrentScenario(SimScenario scenario){
        scenarioChoiceBox.setValue(scenario);
    }

    public void displayCurrentScenario(){
        scenarioDescription.setText(scenarioChoiceBox.getValue().getDescription());

    }

    public void displayBots(){
        agentList.getItems().clear();
        agentList.getItems().addAll(scenarioChoiceBox.getValue().getBotCollection().getBots());
    }


    private void openScenarioBuilder() throws IOException {
        //load fxml
        Stage scenarioBuildStage = new Stage();
        FXMLLoader loader = new FXMLLoader(App.class.getResource("ScenarioBuildUI.fxml"));
        buildController = loader.getController();
        scenarioBuildStage.setTitle("Scenario Builder");
        scenarioBuildStage.setScene(new Scene(loader.load()));
        scenarioBuildStage.show();
    }


}
