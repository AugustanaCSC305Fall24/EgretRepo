package edu.augustana;

import com.google.gson.Gson;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ScenarioBuilController {

    @FXML
    private Button addBotBtn;

    @FXML
    private ListView<TrainingListeningBot> botListTable;

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

    @FXML
    private Button loadBtn;

    @FXML
    private Button saveFileBtn;

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

        cancelBtn.setOnAction(evt -> {
            Stage stage = (Stage) ((Node) evt.getSource()).getScene().getWindow();
            stage.close();
        });

        saveScenarioBtn.setOnAction(event -> {

            createScenario();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();

        });

        saveFileBtn.setOnAction(event -> {
            scenario.saveToFile();
        });

        loadBtn.setOnAction(event -> {
            openFile();
        });

        deletebotBtn.setOnAction(event -> {
            deleteBot();
        });

    }

    public void loadScenario(){
        environment  = scenario.getEnvironment();
        botCollection  = scenario.getBotCollection();
        descripTionField.setText(scenario.getDescription());
        scenarioNameField.setText(scenario.getName());
        userMessageField.setText(scenario.getUserMessage());
        timeField.setText("00:00");
        tempField.setText(String.valueOf(environment.temperature));
        humidityField.setText(String.valueOf(String.valueOf(environment.humidity)));
        windSpeedField.setText(String.valueOf(environment.windSpeed));
        solarIndex.setText(String.valueOf(environment.solarActivity));
        scenarioTypeChoice.setValue(scenario.getType());
        updateBotListView();
    }

    public void setScenario(SimScenario scenario){
        this.scenario = scenario;
    }

    public void updateBotListView(){
        botListTable.getItems().clear();
        botListTable.getItems().addAll(scenario.getBotCollection().getBots());
    }

    public void deleteBot(){
        if(botListTable.getSelectionModel().getSelectedItem() !=  null){
            scenario.getBotCollection().deleteBot(botListTable.getSelectionModel().getSelectedItem());
            updateBotListView();
        }


    }


    private void openBotAdder() throws IOException {
        Stage scenarioBuildStage = new Stage();
        FXMLLoader loader = new FXMLLoader(App.class.getResource("botAdder.fxml"));

        scenarioBuildStage.setTitle("Scenario Builder");
        scenarioBuildStage.setScene(new Scene(loader.load()));
        scenarioBuildStage.show();

        adderController = loader.getController();
        adderController.setBotCollection(botCollection);
        adderController.setParentController(this);
    }

    public void editScenario(SimScenario scenario){

        isNewScenario = false;

        this.scenario = scenario;

        loadScenario();

    }

    public void loadFromFile(){
        isNewScenario =  false;
    }
    public void openFile() {
        Stage fileChooserStage = new Stage();

        // Set up the FileChooser
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON Files", "*.json"));

        // Open the FileChooser dialog
        File selectedFile = fileChooser.showOpenDialog(fileChooserStage);

        // If a file is selected, deserialize it
        if (selectedFile != null) {
            deserializeJson(selectedFile);
        }
    }

    public void deserializeJson(File file) {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(file)) {

            this.scenario = gson.fromJson(reader, SimScenario.class);

            loadScenario();
            // Use the simScenario object
        } catch (Exception e) {
            e.printStackTrace();
        }
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
            parentController.displayBots();
        }else{

            environment = new RadioEnviroment("scenarioNameField.getText()",
                    Double.parseDouble(solarIndex.getText()),
                    Double.parseDouble(windSpeedField.getText()),
                    Double.parseDouble(humidityField.getText()),
                    Double.parseDouble(tempField.getText()));

            scenario.setScenarioName(scenarioNameField.getText());
            scenario.setDescription(descripTionField.getText());
            scenario.setEnvironment(environment);
            scenario.setExpectedMesagge(userMessageField.getText());
            parentController.updateScenarioChoice();
            parentController.displayBots();
        }
    }


    public void setParentController(SandboxController controller){
        parentController = controller;
    }


}
