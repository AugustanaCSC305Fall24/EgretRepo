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
    private TextField botCallSignTextField;

    @FXML
    private TextField botMessageTextField;

    @FXML
    private ScrollPane enteredGuessesScrollPane;

    @FXML
    private VBox guessedMessagesVBox;


    @FXML
    private Slider numBotsSlider;


    @FXML
    private Button startSimButton;

    @FXML
    private Button stopSimButton;

    @FXML
    private Button submitGuessButton;

    @FXML
    private Slider playbackSpeedSlider;


    @FXML
    void initialize() {

        //Initializing the listening training tab. Need to add code here. Need to at least initialize the tune in slider
        startSimButton.setOnAction(evt -> {
            try {
                MorsePlayer.setBeatLength( (int) playbackSpeedSlider.getValue());
                HandleListeningSim.startSim(numBotsSlider.getValue(), playbackSpeedSlider.getValue(), guessedMessagesVBox);

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        submitGuessButton.setOnAction(evt -> {HandleListeningSim.checkGuess(botCallSignTextField.getText(), botMessageTextField.getText(), guessedMessagesVBox);
            botCallSignTextField.clear();
            botMessageTextField.clear();});


        stopSimButton.setOnAction(evt -> HandleListeningSim.stopSim(guessedMessagesVBox));


        numBotsSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
//            numBots = newValue.intValue();
        });



    }
}
