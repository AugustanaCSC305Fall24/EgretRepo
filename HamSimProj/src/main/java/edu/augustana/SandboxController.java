package edu.augustana;

import edu.augustana.Bots.ContinuousMessageBot;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;

public class SandboxController {

    @FXML
    private ListView<ContinuousMessageBot> agentList;

    @FXML
    private Button editScenarioBtn;

    @FXML
    private Tab enviromentTab;

    @FXML
    private Button filterBtn;

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
    private Button startStopScenarioBtn;

    @FXML
    private Button newScenarioBtn;

    private ScenarioBuildController buildController;

    @FXML
    private Slider wpmSlider;

    @FXML
    private Button showBotMessageButton;

    private MainUiController mainUIController;

    @FXML
    private Button serverJoinLeaveBtn;

    @FXML
    private ListView<String> serverListView;

    @FXML
    private Button createServerBtn;

    @FXML
    private Tab serverInfoTab;


    @FXML
    private Button stopScenario;

    @FXML
    private Button updateServersBtn;


    @FXML
    void initialize(){

        ScenarioCollection.addScenario(SimScenario.getDefaultScenario());
        scenarioChoiceBox.getItems().addAll(ScenarioCollection.getCollection());
        scenarioChoiceBox.setValue(ScenarioCollection.getCollection().get(0));
        scenarioDescription.setText(scenarioChoiceBox.getValue().getDescription());
        agentList.getItems().addAll(scenarioChoiceBox.getValue().getBotCollection().getBots());


        startStopScenarioBtn.setOnAction(evt -> {
            if(!scenarioChoiceBox.getValue().isPlaying){
                try {
                    scenarioChoiceBox.getValue().startScenario();
                    startStopScenarioBtn.textProperty().set("Stop");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }else{
                scenarioChoiceBox.getValue().stopScenario();
                startStopScenarioBtn.textProperty().set("Start");
            }

        });

        createServerBtn.setOnAction(event -> {
            Stage serverBuildStage = new Stage();
            FXMLLoader loader = new FXMLLoader(App.class.getResource("serverBuildUI.fxml"));
            serverBuildStage.setTitle("Server Builder");
            try {
                serverBuildStage.setScene(new Scene(loader.load()));
                ServerBuildController controller = loader.getController();
                controller.parentController = this;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            serverBuildStage.show();
            createServerBtn.setVisible(false);

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

        showBotMessageButton.setOnAction(evt -> mainUIController.showMessageInTextBox(agentList.getSelectionModel().getSelectedItem()));

        MorsePlayer.setWordsPerMinuteMultiplier((int) wpmSlider.getValue());



        serverJoinLeaveBtn.setOnAction(event -> {
            if(HamRadioServerClient.isConnected == true){
                try {
                    HamRadioServerClient.disconnectServer();
                    serverJoinLeaveBtn.setText("Connect");
                    updateListOfServer();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }else{
                Stage serverConnectStage = new Stage();
                FXMLLoader loader = new FXMLLoader(App.class.getResource("serverConnect.fxml"));
                serverConnectStage.setTitle("Connect To server");
                try {
                    serverConnectStage.setScene(new Scene(loader.load()));
                    ServerConnectUI controller = loader.getController();
                    controller.parentController = this;
                    controller.setServerID(serverListView.getSelectionModel().getSelectedItem());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                serverConnectStage.show();
                try {
                    updateListOfServer();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

        updateServersBtn.setOnAction(event -> {
            try {
                updateListOfServer();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

    }

    public void setMainUIControllerController(MainUiController controller) {
        mainUIController = controller;
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


    public void setConnected() {
        serverJoinLeaveBtn.setText("Disconnect");
    }

    public void updateListOfServer() throws Exception {
        serverListView.getItems().clear();
        serverListView.getItems().addAll( HamRadioServerClient.getAvailableServers().keySet());
    }

    public void setCreateServerVisible(boolean bool){
        createServerBtn.setVisible(bool);
    }
}
