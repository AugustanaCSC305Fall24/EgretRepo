package edu.augustana;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.skin.SliderSkin;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;

public class MainUiController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private Label displayLabel;

    @FXML
    private URL location;

    @FXML
    private Slider freqSlider;

    @FXML
    private HBox mainHbox;

    @FXML
    private RadioButton muteBtn;

    @FXML
    private RadioButton powerBtn;

    @FXML
    private ImageView radioImage;

    @FXML
    private Slider tuneSlider;

    @FXML
    private GridPane knobGridPane;

    @FXML
    private VBox knobBox00;

    @FXML
    private VBox knobBox01;

    @FXML
    private VBox knobBox10;

    @FXML
    private VBox knobBox11;

    KnobControl volumeKnob;
    KnobControl filterKnob;
    KnobControl bandKnob;
    KnobControl toneKnob;






    @FXML
    void initialize() {
        assert mainHbox != null : "fx:id=\"mainHbox\" was not injected: check your FXML file 'mainUI.fxml'.";
        assert radioImage != null : "fx:id=\"radioImage\" was not injected: check your FXML file 'mainUI.fxml'.";

        VBox[] controlVBoxes = {knobBox00, knobBox10, knobBox01, knobBox11};
        KnobControl[] knobArray = {volumeKnob,filterKnob,bandKnob,toneKnob};

        for(int i = 0; i < knobArray.length; i++){
            KnobControl knob = knobArray[i];
            knob = new KnobControl();
            controlVBoxes[i].getChildren().add(knob);
        }

        volumeKnob.valueProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("Slider value changed: " + newValue);
        });

    }

    void updateDisplayText(int time, double rFrequency, double tFrequency, int band){
        displayLabel.setText(tFrequency + "Hz  "+ rFrequency + "Mhz  " + time + "  " + band + "m ");
    }


}