package edu.augustana;

import edu.augustana.Bots.ContinuousMessageBot;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class BotAdderController {

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

    private ScenarioBuildController parentController;

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

            clearBotAdderTextBoxes();

        });

        okBtn.setOnAction(evt -> {
            Stage stage = (Stage) ((Node) evt.getSource()).getScene().getWindow();

            parentController.updateBotListView();

            stage.close();
        });

        cancelBtn.setOnAction(evt -> {
            Stage stage = (Stage) ((Node) evt.getSource()).getScene().getWindow();
            stage.close();
        });


    }

    public void setParentController(ScenarioBuildController parentController) {
        this.parentController = parentController;
    }

    public void setBotCollection(BotCollection collection){
        botCollection = collection;
    }

    private void addBot() throws InterruptedException {
        ContinuousMessageBot bot = new ContinuousMessageBot(Radio.getBand(), botNameField.getText(), callSignField.getText(), messageField.getText());
        botCollection.addBot(bot);

    }

    //Clears the text boxes so that when you add it, it doesn't keep the same info
    private void clearBotAdderTextBoxes() {
        botNameField.clear();
        callSignField.clear();
        messageField.clear();
    }


}
