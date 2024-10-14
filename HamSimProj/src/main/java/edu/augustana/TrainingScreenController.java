package edu.augustana;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;


import java.io.IOException;




public class TrainingScreenController {



    @FXML
    private HBox mainHbox;

    @FXML
    private BorderPane trainingBorderPane;







    @FXML
    public void initialize() throws IOException {

        try {
            // Load TrainingScreen1.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("TrainingScreen1.fxml"));
            TabPane trainingTabPane = loader.load();

            // Remove the old TabPane and add the new one
            mainHbox.getChildren().add(trainingTabPane);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }



}
