package edu.augustana;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;


public class TrainingScreenController {

    @FXML
    private Button backToMainButton;


    //All FXML stuff for the listening training tab. Will have to
    //delete a lot of this because it is repeated, and also we are
    //going to get rid of a lot of the radio in this tab.
    @FXML
    private Button backToMainButton1;

    @FXML
    private static TextField botCallSignTextField;

    @FXML
    private static TextField botMessageTextField;

    @FXML
    private static ScrollPane enteredGuessesScrollPane;

    @FXML
    private static VBox guessedMessagesVBox;


    @FXML
    private static Slider numBotsSlider;


    @FXML
    private Button startSimButton;

    @FXML
    private Button stopSimButton;

    @FXML
    private Button submitGuessButton;


    @FXML
    void initialize() {

        //Initializing the listening training tab. Need to add code here. Need to at least initialize the tune in slider
        startSimButton.setOnAction(evt -> {
            try {
                HandleListeningSim.startSim();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        submitGuessButton.setOnAction(evt -> HandleListeningSim.checkGuess());
        stopSimButton.setOnAction(evt -> HandleListeningSim.stopSim());


    }




    //All Methods that are for the listening training screen

    public static int getNumBots() {
        return (int) numBotsSlider.getValue();
    }

    public static String getGuessedCallSign() {
        return botCallSignTextField.getText();
    }

    public static String getGuessedMessage() {
        return botMessageTextField.getText();
    }

    public static VBox getGuessedMessagesVBox() {
        return guessedMessagesVBox;
    }

    public static TextField getBotCallSignTextField() {
        return botCallSignTextField;
    }

    public static TextField getBotMessageTextField() {
        return botMessageTextField;
    }
}
