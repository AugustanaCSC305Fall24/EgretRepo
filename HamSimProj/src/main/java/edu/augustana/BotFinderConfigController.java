package edu.augustana;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;

import java.io.IOException;

public class BotFinderConfigController {

    @FXML
    private Button acceptBotsButton;

    @FXML
    private Slider numBotsSlider;

    @FXML
    private Slider wpmSlider;

    public void initialize() {

        acceptBotsButton.setOnAction(evt -> {
            try {
                HandleListeningSim.startSim(numBotsSlider.getValue(), wpmSlider.getValue());
                MorsePlayer.setBeatLength((int) wpmSlider.getValue()); //Need to change the wpm slider to 10-25 once we get that set up.
                HandleListeningSim.closeBotView();
            } catch (InterruptedException | IOException e) {
                throw new RuntimeException(e);
            }

        });
    }
}