package edu.augustana;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.io.IOException;


public class TrainingScreenController {

    //All FXML stuff for the listening training tab. Will have to
    //delete a lot of this because it is repeated, and also we are
    //going to get rid of a lot of the radio in this tab.

    @FXML
    private TextField botCallSignTextField;

    @FXML
    private TextField botMessageTextField;

    @FXML
    private ScrollPane enteredGuessesScrollPane;

    @FXML
    private VBox guessedMessagesVBox;


    @FXML
    private Button startSimButton;

    @FXML
    private Button stopSimButton;

    @FXML
    private Button submitGuessButton;

    @FXML
    private Label correctIncorrectLabel;

    @FXML
    private Button playLetterButton;

    @FXML
    private TextField guessTextField;

    @FXML
    private Button submitButton;



    @FXML
    void initialize() {

        //Initializing the listening training tab. Need to add code here. Need to at least initialize the tune in slider
        startSimButton.setOnAction(evt -> {
            try {
                HandleListeningSim.openBotView(guessedMessagesVBox);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        submitGuessButton.setOnAction(evt -> {HandleListeningSim.checkGuess(botCallSignTextField.getText(), botMessageTextField.getText(), guessedMessagesVBox);
            botCallSignTextField.clear();
            botMessageTextField.clear();});


        stopSimButton.setOnAction(evt -> HandleListeningSim.stopSim(guessedMessagesVBox));

        //CW Flashcards Section
        CWFlashcards.generateAlphabetList();
        submitButton.setOnAction(evt-> {
            if (CWFlashcards.handleGuess(guessTextField.getText().toUpperCase())) {
                correctIncorrectLabel.setText("Correct!");
                guessTextField.setText("");
            } else {
                correctIncorrectLabel.setText("Incorrect, try again!");
            }
            correctIncorrectLabel.setVisible(true);

        });
        correctIncorrectLabel.setVisible(false);
        playLetterButton.setOnAction(evt -> {
            correctIncorrectLabel.setVisible(false);
            try {
                CWFlashcards.playLetter();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        //End of CW Flashcards Section


        }

}
