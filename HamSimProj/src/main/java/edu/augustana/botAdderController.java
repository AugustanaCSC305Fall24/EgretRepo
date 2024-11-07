package edu.augustana;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class botAdderController {

    @FXML
    private Button addBtn;

    @FXML
    private TextField botNameField;

    @FXML
    private TextField callSignField;

    @FXML
    private Button cancelBtn;

    @FXML
    private TextField messageField;

    private SimScenario scenario;

    private BotCollection botCollection;

    @FXML
    private Button okBtn;

    @FXML
    void initialize(){

        addBtn.setOnAction( event -> {
            try {
                addBot();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        });

        okBtn.setOnAction(evt -> {
            Stage stage = (Stage) ((Node) evt.getSource()).getScene().getWindow();
            stage.close();
        });

        cancelBtn.setOnAction(evt -> {
            Stage stage = (Stage) ((Node) evt.getSource()).getScene().getWindow();
            stage.close();
        });

    }


    public void setBotCollection(BotCollection collection){
        botCollection = collection;
    }

    private void addBot() throws InterruptedException {
        TrainingListeningBot bot = new TrainingListeningBot(Radio.getBand());

        botCollection.addBot(bot);



    }


}
