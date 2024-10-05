package edu.augustana;

import java.io.IOException;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;

import javax.sound.sampled.LineUnavailableException;


import static edu.augustana.MorseTester.morseTester;
import static edu.augustana.Radio.*;
import static edu.augustana.CWHandler.*;

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
        frequencyDisplayLabel.setText(Double.toString(selectedFrequency));

        tapSoundBtn.setOnMousePressed(event -> {playTone(Math.abs(getSelectedTunningFrequency - selectedFrequency) * 1000000); startTimer();});
        tapSoundBtn.setOnMouseReleased(event -> {stopTone(); stopTimer();});

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
