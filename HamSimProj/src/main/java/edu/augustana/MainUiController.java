package edu.augustana;

import java.io.IOException;

import edu.augustana.Bots.ContinuousMessageBot;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.text.DecimalFormat;
import javafx.beans.value.ChangeListener;
import javafx.stage.Stage;

import javax.sound.sampled.LineUnavailableException;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class MainUiController {
    private boolean isMuted = false;
    private double savedVolume = 0.0;


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

    @FXML
    private Button closeButton;

    @FXML
    private Button fullScreenButton;

    @FXML
    private Button minimizeButton;

    @FXML
    private Button configButton;

    @FXML
    private Button sandboxButton;

    @FXML
    private Button trainingButton;

    @FXML
    private Label morseText;

    @FXML
    private Label englishText;

    @FXML
    private Slider frequencyFilterSlider;


    KnobControl volumeKnob;
    KnobControl filterKnob;
    KnobControl bandKnob;
    KnobControl toneKnob;
    private Boolean firstLoad = true;


    @FXML
    void initialize() throws IOException {
        displayLabel.setText("");

        assert mainHbox != null : "fx:id=\"mainHbox\" was not injected: check your FXML file 'mainUI.fxml'.";
        assert radioImage != null : "fx:id=\"radioImage\" was not injected: check your FXML file 'mainUI.fxml'.";

        //morseText = new Label();
        volumeKnob = new KnobControl();
        knobBox00.getChildren().add(volumeKnob);
        filterKnob = new KnobControl();
        knobBox10.getChildren().add(filterKnob);
        bandKnob = new KnobControl();
        knobBox01.getChildren().add(bandKnob);
        toneKnob = new KnobControl();
        knobBox11.getChildren().add(toneKnob);

        closeButton.setOnAction(evt -> Platform.exit());
        minimizeButton.setOnAction(evt -> App.windowStage.setIconified(true));
        fullScreenButton.setOnAction(evt -> handleFullScreenButtonPress(App.windowStage));
        configButton.setOnAction(evt -> {
            try {
                setConfigPane();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        sandboxButton.setOnAction(evt -> {
            try {
                setServerPane();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        trainingButton.setOnAction(evt -> {
            try {
                setTrainingPane();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        muteBtn.setOnAction(evt -> {
            isMuted = !isMuted;
            if (isMuted) {
                try {
                    mute();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                try {
                    unmute();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

        });

        freqSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
           int band = Radio.getBand();
           updateRadioFrequency(Radio.getBand(), newValue.doubleValue());
           updateDisplayText(Radio.getTime(), Radio.getSelectedTuneFreq(), Radio.getCwToneFreq(), band);

        });

        volumeKnob.valueProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("Volume value changed: " + newValue);
            Radio.updateGain(((newValue.doubleValue()/100) * 4));

        });


        filterKnob.valueProperty().addListener((observable, oldValue, newValue) -> {
            int val = (int)((newValue.doubleValue()/100)*6379);
            System.out.println("Filter value changed: " + val + 10);
            Radio.changeFilterValue(val + 10);

        });

        bandKnob.valueProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("Band value changed: " + newValue);
            double angle = (newValue.doubleValue() / 100)*360;
            Radio.setBand(chooseBand(angle));
            updateRadioFrequency(Radio.getBand(), freqSlider.getValue());
            updateDisplayText(Radio.getTime(), Radio.getSelectedTuneFreq(), Radio.getCwToneFreq(), chooseBand(angle));
        });


        toneKnob.valueProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("Tone value changed: " + newValue);

            double newFreq = (((newValue.doubleValue() / 100)*400) + 400);

            if(newFreq < 400)newFreq = 400;

            Radio.setCwToneFreq(newFreq);
            MorsePlayer.setSideTone();

            updateDisplayText(Radio.getTime(), Radio.getSelectedTuneFreq(), Radio.getCwToneFreq(), Radio.getBand());

        });

        powerBtn.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    try {
                        System.out.println("Radio Initialized");
                        Radio.initializeRadio();
                        updateKnobValues();
                    } catch (LineUnavailableException e) {
                        throw new RuntimeException(e);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println("RadioButton is selected (toggled on)");
                } else {
                    System.out.println("RadioButton is deselected (toggled off)");
                }
            }
        });


        //Loading the other fxml in the HBOX. Starting with the trainingscreen for now. Maybe make this a method so that we can have DRY coding. Just pass in the string for the fxml name

        setTrainingPane();
        firstLoad = false;

        frequencyFilterSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            MorsePlayer.setFrequencyFilter((double) newValue);
        });

    }

    private void updateRadioFrequency (int band, double frequencySliderValue){
        switch (band){
            case 10:
                Radio.setTunningRF(((frequencySliderValue/100)*1.7) + 28);
                break;

            case 17:
                Radio.setTunningRF(((frequencySliderValue/100)*(18.168 - 18.068) + 18.068));
                break;

            case 20:
                Radio.setTunningRF(((frequencySliderValue)/100)*(14.350 - 14.000) + 14.000);
                break;

            case 30:
                Radio.setTunningRF(((frequencySliderValue/100)*(10.15 - 10.1) + 10.1));
                break;

            case 40:
                Radio.setTunningRF(((frequencySliderValue/100)*(7.300 - 7.000) + 7.000));
                break;

            case 80:
                Radio.setTunningRF(((frequencySliderValue/100)*(4.0 - 3.5) + 3.5));
                break;
        }
    }

    private void setTrainingPane() throws IOException {
        if (!firstLoad) {
            mainHbox.getChildren().remove(mainHbox.getChildren().size() - 1);
        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource("TrainingScreen.fxml"));
        VBox trainingVbox = loader.load();
        mainHbox.getChildren().add(trainingVbox);
    }

    private void mute() throws IOException{
        savedVolume = volumeKnob.getValue();
        volumeKnob.setValue(0.0);
    }

    private void unmute() throws IOException{
        volumeKnob.setValue(savedVolume);
    }


    private void setServerPane() throws IOException {
        mainHbox.getChildren().remove(mainHbox.getChildren().size() - 1);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Sandbox.fxml"));
        VBox trainingVbox = loader.load();
        SandboxController controller = loader.getController();
        controller.setMainUIControllerController(this);
        mainHbox.getChildren().add(trainingVbox);
    }

    private void setConfigPane() throws IOException {
        mainHbox.getChildren().remove(mainHbox.getChildren().size() - 1);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Config.fxml"));
        VBox trainingVbox = loader.load();
        mainHbox.getChildren().add(trainingVbox);
    }


    private void updateKnobValues() throws InterruptedException {
        toneKnob.setValue(((Radio.getCwToneFreq()-400)/400)*100);
        toneKnob.skin.rotateToPosition((int)((Radio.getCwToneFreq()-400)/400)*100);
        bandKnob.skin.rotateToPosition(100);
        bandKnob.setValue(100);
        filterKnob.setValue(((Radio.getFilterValue()/6379)*100));
        filterKnob.skin.rotateToPosition((int)  ((Radio.getFilterValue()/6379)*100));
        volumeKnob.setValue((Radio.getSoundAmplitud()/4)*100);
    }

    void updateDisplayText(int time, double rFrequency, double tFrequency, int band){

        DecimalFormat df = new DecimalFormat("#.####"); // For one decimal place
        String formattedTFrequency = df.format(tFrequency);
        String formattedRFrequency = df.format(rFrequency);

        displayLabel.setText(formattedTFrequency + "Hz  "+ formattedRFrequency + "Mhz  " + time + "  " + band + "m ");
    }

    int chooseBand(double angle){
        if(angle >= 270){
            return 10;
        } else if (angle > 225) {
            return 17;
        } else if (angle > 180) {
            return 20;
        } else if(angle > 90){
            return 30;
        } else if(angle > 45){
            return 40;
        } else if(angle >= 0){
            return 80;
        } else{
            return 0;
        }

    }

    public void handleKeyPress(KeyEvent keyEvent) throws InterruptedException {
        if (keyEvent.getCode() == KeyCode.J) {
            new Thread(() -> {
                PaddleHandler.playContinuousDot();
            }).start();

        } else if (keyEvent.getCode() == KeyCode.K) {
            new Thread(() ->{
                PaddleHandler.playContinuousDash();
            }).start();
        } else if (keyEvent.getCode() == KeyCode.L) {
            CWHandler.startTimer();
        } else if (keyEvent.getCode() == KeyCode.N) {
            Radio.toggleNoise();
        }
    }

    public void handleKeyRelease(KeyEvent keyEvent) throws InterruptedException {
        if (keyEvent.getCode() == KeyCode.J || keyEvent.getCode() == KeyCode.K) {
            PaddleHandler.stopPaddlePress();
            addToMorseBox(PaddleHandler.getCwString()); // stops first paddle press on keyRelease of second paddle if both are held simultaneously
            addToEnglishBox(PaddleHandler.getCwString());
        } else if (keyEvent.getCode() == KeyCode.L) {
            CWHandler.stopTimer();
            addToMorseBox(CWHandler.getCwString());
            addToEnglishBox(CWHandler.getCwString());
        }

    }

    private void handleFullScreenButtonPress(Stage stage) {
        stage.setFullScreen(!stage.isFullScreen());
    }

    private void addToMorseBox(String morse) {
        morseText.setText(morse);
       // System.out.println("Label text: " + morseText.getText());
    }

    private void addToEnglishBox(String morse) {
        englishText.setText(TextToMorseConverter.morseToText(morse));
    }


    public void showMessageInTextBox(ContinuousMessageBot selectedBot) {
        String fullMessage = selectedBot.getMorseCallSign() + "/*//*/" + selectedBot.getMorseBotPhrase();
        addToEnglishBox(fullMessage.replace(' ', '/'));
        addToMorseBox(fullMessage.replace(' ', '/'));
    }
}