package edu.augustana.UI;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import edu.augustana.App;

public class InstructionsController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button okButton;

    @FXML
    void initialize() {
        assert okButton != null : "fx:id=\"okButton\" was not injected: check your FXML file 'Instructions.fxml'.";
        okButton.setOnAction(evt -> {
            try {
                App.setRoot("MainUI");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }


}
