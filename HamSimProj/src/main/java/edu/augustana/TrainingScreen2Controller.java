package edu.augustana;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;

import javax.sound.sampled.LineUnavailableException;

import static edu.augustana.CWHandler.*;
import static edu.augustana.MorsePlayer.playMorseString;
import static edu.augustana.MorsePlayer.setBeatLength;
import static edu.augustana.Radio.*;
import static edu.augustana.Radio.setSelectedRF;
import static edu.augustana.TextToMorseConverter.morseToText;
import static edu.augustana.TextToMorseConverter.textToMorse;


public class TrainingScreen2Controller {

    private double selectedFrequency;
    private double getSelectedTunningFrequency;

    @FXML
    private ResourceBundle resources;

    @FXML
    private Label tuneFrequencyLabel;

    @FXML
    private Label frequencyDisplayLabel;


    @FXML
    private Text englishText;

    @FXML
    private Text cwText;

    @FXML
    private URL location;

    @FXML
    private TextField cwTextBox;

    @FXML
    private Slider filterSlider;

    @FXML
    private Slider playbackSpeedSlider;

    @FXML
    private Slider noiseSlider;

    @FXML
    private Button sendButton;

    @FXML
    private Button showMessageButton;

    @FXML
    private Button tapSoundButton;

    @FXML
    private TabPane trainingScreenDemoTab;

    @FXML
    private Slider tuneInSlider;

    @FXML
    private Slider tuneOutSlider;

    @FXML
    private Button turnOnRadioButton;

    @FXML
    private Slider volumeSlider;

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
    private ListView<?> enteredGuessesListView;

    @FXML
    private Slider filterSlider1;

    @FXML
    private Label frequencyDisplayLabel1;

    @FXML
    private Slider noiseSlider1;

    @FXML
    private static Slider numBotsSlider;

    @FXML
    private Slider playbackSpeedSlider1;

    @FXML
    private Button startSimButton;

    @FXML
    private Button stopSimButton;

    @FXML
    private Button submitGuessButton;

    @FXML
    private Label tuneFrequencyLabel1;

    @FXML
    private Slider tuneInSlider1;

    @FXML
    private Slider tuneOutSlider1;

    @FXML
    private Button turnOnRadioButton1;

    @FXML
    private Slider volumeSlider1;

    @FXML
    void initialize() {
        assert cwTextBox != null : "fx:id=\"cwTextBox\" was not injected: check your FXML file 'TrainingScreen2.fxml'.";
        assert filterSlider != null : "fx:id=\"filterSlider\" was not injected: check your FXML file 'TrainingScreen2.fxml'.";
        assert noiseSlider != null : "fx:id=\"noiseSlider\" was not injected: check your FXML file 'TrainingScreen2.fxml'.";
        assert sendButton != null : "fx:id=\"sendButton\" was not injected: check your FXML file 'TrainingScreen2.fxml'.";
        assert showMessageButton != null : "fx:id=\"showMessageButton\" was not injected: check your FXML file 'TrainingScreen2.fxml'.";
        assert tapSoundButton != null : "fx:id=\"tapSoundButton\" was not injected: check your FXML file 'TrainingScreen2.fxml'.";
        assert trainingScreenDemoTab != null : "fx:id=\"trainingScreenDemoTab\" was not injected: check your FXML file 'TrainingScreen2.fxml'.";
        assert tuneInSlider != null : "fx:id=\"tunInSlider\" was not injected: check your FXML file 'TrainingScreen2.fxml'.";
        assert tuneOutSlider != null : "fx:id=\"tuneOutSlider\" was not injected: check your FXML file 'TrainingScreen2.fxml'.";
        assert turnOnRadioButton != null : "fx:id=\"turnOnRadioButton\" was not injected: check your FXML file 'TrainingScreen2.fxml'.";
        assert volumeSlider != null : "fx:id=\"volumeSlider\" was not injected: check your FXML file 'TrainingScreen2.fxml'.";

        selectedFrequency = 7.030;
        getSelectedTunningFrequency = 7.030500;
        tuneOutSlider.setValue(selectedFrequency);
        tuneOutSlider.setMin(7.000);   // Minimum value
        tuneOutSlider.setMax(7.035);
        tuneInSlider.setMin(7.000);   // Minimum value
        tuneInSlider.setMax(7.035);
        filterSlider.setMin(40);
        filterSlider.setMax(6379);
        filterSlider.setValue(800);
        noiseSlider.setMin(0);
        noiseSlider.setMax(4);
        cwText.setText("");
        englishText.setText("");

        frequencyDisplayLabel.setText(Double.toString(selectedFrequency));

        turnOnRadioButton.setOnAction(evt -> {
            try {
                playContinuous();
            } catch (LineUnavailableException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        tapSoundButton.setOnMousePressed(event -> {playTone(Math.abs(getSelectedTunningFrequency - selectedFrequency) * 1000000); startTimer(); System.out.println(morseToText(getCwArray()));});
        tapSoundButton.setOnMouseReleased(event -> {stopTone(); stopTimer();});
        sendButton.setOnAction(event -> {
            try {
                setBeatLength( (int) playbackSpeedSlider.getValue());
                playMorseString(textToMorse(cwTextBox.getText()));

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });



        tuneOutSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                // Update the label with the new slider value
                frequencyDisplayLabel.setText("RF: " + newValue.doubleValue());

                selectedFrequency = newValue.doubleValue();
                setSelectedRF(selectedFrequency);


            }
        });

        noiseSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {

                updateNoiseGain(newValue.doubleValue());


            }
        });

        filterSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {

                changeFilterValue(newValue.intValue());
            }
        });

        volumeSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                // Update the label with the new slider value


                updateGain(newValue.doubleValue());

            }
        });

        tuneInSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                // Update the label with the new slider value
                tuneFrequencyLabel.setText(Double.toString(newValue.doubleValue()));
                getSelectedTunningFrequency = newValue.doubleValue();
                setTunningRF(getSelectedTunningFrequency);

                // Trigger your custom function or behavior
                //onSliderValueChanged(newValue.doubleValue());
            }
        });



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

    //showMessageButton.setOnAction(evt -> setCwText());

    @FXML
    private void showMessageText(){
        String message = TextToMorseConverter.morseToText(CWHandler.getCwArray());
        englishText.setText(message);
        String message2 = "";
        for (int i = 3; i < CWHandler.getCwArray().size(); i++) {
            if (getCwArray().get(i).equals("*")) {
                message2 += " ";
            } else {
                message2 += CWHandler.getCwArray().get(i);
            }
        }

        cwText.setText(message2);
        CWHandler.getCwArray().clear();
    }


    @FXML
    private void playContinuous() throws LineUnavailableException, InterruptedException {
        initializeRadio();
        setTunningRF(getSelectedTunningFrequency);
        setSelectedRF(selectedFrequency);
//        morseTester();

    }

    @FXML
    private void switchToMainScreen() {
        try {
            // Load the TrainingScreen.fxml (or whichever FXML represents the main screen)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("TrainingScreen.fxml"));
            Parent mainScreenParent = loader.load();

            // Get the current scene and replace the root node with the new one
            backToMainButton.getScene().setRoot(mainScreenParent);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //All Methods that are for the listening training screen

    public static int getNumBots() {
        return (int) numBotsSlider.getValue();
    }

    public static String getGuessedCallSign() {
        return botCallSignTextField.getText();
    }

    public static String getGuessedMessage() {
        return botCallSignTextField.getText();
    }


    public void handleKeyPress(KeyEvent keyEvent) throws InterruptedException {
        CWHandler.startPaddleTimer();
    }

    public void handleKeyRelease(KeyEvent keyEvent) throws InterruptedException {
        CWHandler.stopPaddleTimer();
        CWHandler.handlePaddle(keyEvent);
    }
}
