package edu.augustana;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import javax.sound.sampled.LineUnavailableException;

import static edu.augustana.CWHandler.*;
import static edu.augustana.MorsePlayer.playMorseString;
import static edu.augustana.Radio.*;
import static edu.augustana.Radio.setSelectedRF;
import static edu.augustana.TextToMorseConverter.morseToText;
import static edu.augustana.TextToMorseConverter.textToMorse;


public class trainingScreen2Controller {

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

}
