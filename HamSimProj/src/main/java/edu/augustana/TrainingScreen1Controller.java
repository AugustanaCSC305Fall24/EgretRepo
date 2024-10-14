package edu.augustana;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class TrainingScreen1Controller {

    @FXML
    private Button configButton;

    @FXML
    private Button newTrainingButton;

    @FXML
    private Button resumeTrainingButton;

    @FXML
    private AnchorPane tabAnchorPane;

    @FXML
    private TabPane TrainingScreen1TabPane;


    public void initialize() throws IOException {

        newTrainingButton.setOnAction(e -> {
            try {
                switchToTrainingScreen();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });



    }


    @FXML
    private void switchToTrainingScreen() throws IOException {

        try {
            // Load TrainingScreen1.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("TrainingScreen2.fxml"));
            TabPane demoTabPane = loader.load();
            System.out.println("Pressed BTN");
//            TrainingScreen1TabPane.getParent().getChildren().clear();
//            TrainingScreen1TabPane.getParent().getChildren().add(demoTabPane);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
