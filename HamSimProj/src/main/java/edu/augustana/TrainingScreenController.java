package edu.augustana;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;


import java.io.IOException;




public class TrainingScreenController {



    @FXML
    private AnchorPane tabAnchorPane;

    @FXML
    private Button configButton;

    @FXML
    private Button newTrainingButton;

    @FXML
    private Button resumeTrainingButton;

    @FXML
    private TabPane tabPane;

    @FXML
    private Tab trainingTab;





    @FXML
    public void initialize() throws IOException {
       tabAnchorPane = (AnchorPane) loadFXML("TrainingScreen1.fxml");
    }

    @FXML
    private void switchToTrainingScreen() throws IOException {

    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }



}
