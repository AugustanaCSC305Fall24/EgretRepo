package edu.augustana;

import edu.augustana.Bots.ContinuousMessageBot;
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

    private ScenarioBuilController parentController;

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

            parentController.updateBotListView();

            stage.close();
        });

        cancelBtn.setOnAction(evt -> {
            Stage stage = (Stage) ((Node) evt.getSource()).getScene().getWindow();
            stage.close();
        });


    }

    public void setParentController(ScenarioBuilController parentController) {
        this.parentController = parentController;
    }

    public void setBotCollection(BotCollection collection){
        botCollection = collection;
    }

    private void addBot() throws InterruptedException {
        ContinuousMessageBot bot = new ContinuousMessageBot(Radio.getBand(), botNameField.getText(), callSignField.getText(), messageField.getText());
        botCollection.addBot(bot);



    }


}
