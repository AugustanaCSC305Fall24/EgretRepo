package edu.augustana;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.skin.SliderSkin;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.text.DecimalFormat;
import javafx.beans.value.ChangeListener;

import javax.sound.sampled.LineUnavailableException;

import static edu.augustana.Radio.setTunningRF;
import static java.lang.Math.max;
import static java.lang.Math.min;

public class MainUiController {

    @FXML
    private Label displayLabel;

    @FXML
    private Tab enviromentTab;

    @FXML
    private Button filterBtn;

    @FXML
    private Slider freqSlider;

    @FXML
    private Button joinBtn;

    @FXML
    private VBox knobBox00;

    @FXML
    private VBox knobBox01;

    @FXML
    private VBox knobBox10;

    @FXML
    private VBox knobBox11;

    @FXML
    private GridPane knobGridPane;

    @FXML
    private HBox mainHbox;

    @FXML
    private RadioButton muteBtn;

    @FXML
    private RadioButton powerBtn;

    @FXML
    private ImageView radioImage;

    @FXML
    private ChoiceBox<?> scenarioChoiceBox;

    @FXML
    private Button servInfoBtn;

    @FXML
    private HBox serverhbox;

    KnobControl volumeKnob;
    KnobControl filterKnob;
    KnobControl bandKnob;
    KnobControl toneKnob;






    @FXML
    void initialize() {
        assert mainHbox != null : "fx:id=\"mainHbox\" was not injected: check your FXML file 'mainUI.fxml'.";
        assert radioImage != null : "fx:id=\"radioImage\" was not injected: check your FXML file 'mainUI.fxml'.";

        volumeKnob = new KnobControl();
        knobBox00.getChildren().add(volumeKnob);
        filterKnob = new KnobControl();
        knobBox10.getChildren().add(filterKnob);
        bandKnob = new KnobControl();
        knobBox01.getChildren().add(bandKnob);
        toneKnob = new KnobControl();
        knobBox11.getChildren().add(toneKnob);




        volumeKnob.valueProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("Volume value changed: " + newValue);
            Radio.updateGain(newValue.doubleValue());
            Radio.updateNoiseGain((newValue.doubleValue()/100) * 4);

        });

        filterKnob.valueProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("Filter value changed: " + newValue);
            Radio.changeFilterValue((int)(max(0.01,newValue.doubleValue())/100)*6379);

        });

        bandKnob.valueProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("Band value changed: " + newValue);
            double angle = (newValue.doubleValue() / 100)*360;
            Radio.setBand(chooseBand(angle));
            updateDisplayText(Radio.getTime(), Radio.getSelectedTuneFreq(), Radio.getCwToneFreq(), chooseBand(angle));
        });

        toneKnob.valueProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("Tone value changed: " + newValue);
            double angle = (newValue.doubleValue() / 100)*360;
            Radio.setCwToneFreq((angle/360) * 800);
            updateDisplayText(Radio.getTime(), Radio.getSelectedTuneFreq(), Radio.getCwToneFreq(), Radio.getBand());
        });

        powerBtn.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    try {
                        System.out.println("Radio Initialized");
                        Radio.initializeRadio();

                    } catch (LineUnavailableException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println("RadioButton is selected (toggled on)");
                } else {
                    System.out.println("RadioButton is deselected (toggled off)");
                }
            }
        });

    }

    void updateDisplayText(int time, double rFrequency, double tFrequency, int band){

        DecimalFormat df = new DecimalFormat("#.#"); // For one decimal place
        String formattedValue = df.format(tFrequency);

        displayLabel.setText(formattedValue + "Hz  "+ rFrequency + "Mhz  " + time + "  " + band + "m ");
    }

    int chooseBand(double angle){
        if(angle > 180){
            return 10;
        } else if (angle > 150) {
            return 17;
        } else if (angle > 120) {
            return 20;
        }else if(angle > 90){
            return 30;
        }else if(angle > 60){
            return 40;
        }else if(angle > 30){
            return 40;
        }else{
            if(angle > 220){
                return 10;
            }else{
                return 40;
            }
        }

    }


}