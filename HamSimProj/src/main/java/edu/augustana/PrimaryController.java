package edu.augustana;

import java.io.IOException;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;

import javax.sound.sampled.LineUnavailableException;


import static edu.augustana.MorsePlayer.playMorseString;
import static edu.augustana.MorseTester.morseTester;
import static edu.augustana.Radio.*;
import static edu.augustana.CWHandler.*;
import static edu.augustana.TextToMorseConverter.morseToText;
import static edu.augustana.TextToMorseConverter.textToMorse;

public class PrimaryController {

    private double selectedFrequency;
    private double getSelectedTunningFrequency;

    @FXML
    private Label frequencyDisplayLabel;

    @FXML
    private Slider frequencySlider;

    @FXML
    private Slider gainSlider;

    @FXML
    private Slider noiseSlider;

    @FXML
    private Button playContBtn;

    @FXML
    private Button tapSoundBtn;

    @FXML
    private Label tuneFrequency;

    @FXML
    private Slider tuningFrequencySlider;

    @FXML private TextField inputTextfield;

    @FXML
    private Button playMorseBtn;

    @FXML
    private Slider filterSlider;



    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }

    @FXML
    private void initialize(){
        selectedFrequency = 7.030;
        getSelectedTunningFrequency = 7.030500;
        frequencySlider.setValue(selectedFrequency);
        frequencySlider.setMin(7.000);   // Minimum value
        frequencySlider.setMax(7.035);
        tuningFrequencySlider.setMin(7.000);   // Minimum value
        tuningFrequencySlider.setMax(7.035);
        filterSlider.setMin(40);
        filterSlider.setMax(800);
        filterSlider.setValue(800);

        frequencyDisplayLabel.setText(Double.toString(selectedFrequency));

        tapSoundBtn.setOnMousePressed(event -> {playTone(Math.abs(getSelectedTunningFrequency - selectedFrequency) * 1000000); startTimer(); System.out.println(morseToText(getCwArray()));});
        tapSoundBtn.setOnMouseReleased(event -> {stopTone(); stopTimer();});
        playMorseBtn.setOnAction(event -> {
            try {
                playMorseString(textToMorse(inputTextfield.getText()));

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        frequencySlider.valueProperty().addListener(new ChangeListener<Number>() {
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

        gainSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                // Update the label with the new slider value


                updateGain(newValue.doubleValue());

            }
        });

        tuningFrequencySlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                // Update the label with the new slider value
                tuneFrequency.setText(Double.toString(newValue.doubleValue()));
                getSelectedTunningFrequency = newValue.doubleValue();
                setTunningRF(getSelectedTunningFrequency);

                // Trigger your custom function or behavior
                //onSliderValueChanged(newValue.doubleValue());
            }
        });

    }


    @FXML
    private void playContinuous() throws LineUnavailableException, InterruptedException {
        initializeRadio();
        setTunningRF(getSelectedTunningFrequency);
        setSelectedRF(selectedFrequency);
//        morseTester();

    }


}
