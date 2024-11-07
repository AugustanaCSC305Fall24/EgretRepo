package edu.augustana;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

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
    private ChoiceBox<Integer> scenarioTypeChoice;

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

    private SimScenario scenario;

    private botAdderController adderController;

    private BotCollection botCollection;

    private RadioEnviroment environment;

    private SandboxController parentController;

    private boolean isNewScenario = true;


    @FXML
    void initialize(){

        scenarioTypeChoice.setValue(0);

        addBotBtn.setOnAction(event -> {
            try {
                openBotAdder();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        cancelBtn.setOnAction(evt -> Platform.exit());

        saveScenarioBtn.setOnAction(event -> {

            createScenario();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();

        });

    }

    public void setScenario(SimScenario scenario){
        this.scenario = scenario;
    }


    private void openBotAdder() throws IOException {
        Stage scenarioBuildStage = new Stage();
        FXMLLoader loader = new FXMLLoader(App.class.getResource("botAdder.fxml"));

        scenarioBuildStage.setTitle("Scenario Builder");
        scenarioBuildStage.setScene(new Scene(loader.load()));
        scenarioBuildStage.show();

        adderController = loader.getController();
        adderController.setBotCollection(botCollection);
    }

    public void editScenario(SimScenario scenario){

        isNewScenario = false;

        this.scenario = scenario;
        environment  = scenario.getEnvironment();
        botCollection  = scenario.getBotCollection();

        scenarioNameField.setText(scenario.getName());
        userMessageField.setText(scenario.getUserMessage());
        timeField.setText("00:00");
        tempField.setText(String.valueOf(environment.temperature));
        humidityField.setText(String.valueOf(String.valueOf(environment.humidity)));
        windSpeedField.setText(String.valueOf(environment.windSpeed));
        solarIndex.setText(String.valueOf(environment.solarActivity));
        scenarioTypeChoice.setValue(scenario.getType());




    }

    public void newScenario(){
        botCollection = new BotCollection(new ArrayList<TrainingListeningBot>());
    }

    private void createScenario(){

        if(isNewScenario){

            environment = new RadioEnviroment("scenarioNameField.getText()",
                    Double.parseDouble(solarIndex.getText()),
                    Double.parseDouble(windSpeedField.getText()),
                    Double.parseDouble(humidityField.getText()),
                    Double.parseDouble(tempField.getText()));
            SimScenario newScenario = new SimScenario(scenarioNameField.getText(),descripTionField.getText(),userMessageField.getText(),"That is not right!", "You Win!",
                    environment, botCollection, scenarioTypeChoice.getValue());

            ScenarioCollection.addScenario(newScenario);
            parentController.updateScenarioChoice();
        }else{

        }


    }


    public void setParentController(SandboxController controller){
        parentController = controller;
    }


}
