package edu.augustana;

import java.io.File;
import java.util.ArrayList;

import com.google.gson.Gson;

import java.io.FileWriter;
import java.io.IOException;
import com.google.gson.annotations.Expose;
import edu.augustana.Bots.ContinuousMessageBot;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class SimScenario {

    @Expose
    private String description ;

    @Expose
    private String expectedMesagge ;

    @Expose
    private int numBots;

    @Expose
    private String scenarioName;

    @Expose
    private RadioEnviroment environment;

    @Expose
    private BotCollection botCollection;

    @Expose
    private String winMessage;

    @Expose
    private String failMessage;

    @Expose
    private int scenarioType;

    public boolean isPlaying;


    public SimScenario(String name, String description, String expectedMesagge, String failMessage, String winMessage, RadioEnviroment environment, BotCollection botCollection, int type){
        this.scenarioName = name;
        this.expectedMesagge = expectedMesagge;
        this.description = description;
        this.environment = environment;
        this.botCollection = botCollection;
        scenarioType = type;
        this.failMessage = failMessage;
        this.winMessage = winMessage;
        isPlaying = false;
    }

    public static SimScenario getDefaultScenario(){

        String defDescription = "";

        String defexpectedMessage = "";

        String deffailMessagge  = "";

        String defWinMessage= "";

        RadioEnviroment defRadioEnviroment = new RadioEnviroment("DEFAULT",0.1,0.1,0.1,0.1);
        ArrayList<ContinuousMessageBot> defBotList = new ArrayList<>();
        BotCollection defBotCollection = new BotCollection(defBotList);
        SimScenario defaultScenario = new SimScenario("DEFAULT",defDescription,defexpectedMessage,deffailMessagge, defWinMessage, defRadioEnviroment, defBotCollection, 0);

        return defaultScenario;

    }


    /*
     *For now we just need this method to be able to play the message and call sign of the bots
     * to have them continously play their message and and callsign with the different parameters in the scenario
     */
    public void startScenario() throws InterruptedException {
        Radio.setNoiseAmplitud(environment.getNoiseAmplitude());
        isPlaying = true;
        if(!botCollection.getBots().isEmpty()){
            for(ContinuousMessageBot bot: botCollection.getBots()){
                bot.playSound();
            }
        }

    }

    public void stopScenario(){
        isPlaying = false;
        if(!botCollection.getBots().isEmpty()){
            for(ContinuousMessageBot bot: botCollection.getBots()){
                bot.stopSound();
            }
        }
    }

    public boolean checkUserInput(String userInput){
        return false;
    }

    public String getDescription(){
        return description;
    }

    public String getName(){
        return scenarioName;
    }

    public RadioEnviroment getEnvironment(){
        return environment;
    }

    public int getType(){
        return scenarioType;
    }

    public BotCollection getBotCollection(){
        return botCollection;
    }

    @Override
    public String toString() {
        return this.scenarioName;
    }

    public String getUserMessage(){
        return expectedMesagge;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        // Check if the object is an instance of Agent
        if (obj instanceof SimScenario) {
            SimScenario other = (SimScenario) obj;
            // Define equality based on name and id
            return this.scenarioName == other.scenarioName;
        }

        return false;
    }

    public void setEnvironment(RadioEnviroment environment) {
        this.environment = environment;
    }

    public void setDescription(String newDescription){
        this.description = newDescription;
    }

    public void setExpectedMesagge(String newMessage){
        this.expectedMesagge = newMessage;
    }

    public void setScenarioName(String scenarioName) {
        this.scenarioName = scenarioName;
    }

    public void saveToFile(){
        Gson gson = new Gson();

        // Initialize the JavaFX FileChooser
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save JSON File");

        // Set a filter to only allow saving as JSON files
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON Files", "*.json"));

        // Show the Save dialog
        File fileToSave = fileChooser.showSaveDialog(new Stage());

        if (fileToSave != null) {
            // Ensure the file has a .json extension
            if (!fileToSave.getName().endsWith(".json")) {
                fileToSave = new File(fileToSave.getAbsolutePath() + ".json");
            }

            // Write JSON to the selected file
            try (FileWriter writer = new FileWriter(fileToSave)) {
                gson.toJson(this, writer);
                System.out.println("File saved to: " + fileToSave.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public int responseCorrectnessLevel(String userMessage){




        //Turn user message String into an ArrayList to be able to check for existence of individual characters in user message
        userMessage = userMessage.strip();
        char[] userCharArray = userMessage.toCharArray();
        ArrayList<Character> userCharList =  new ArrayList<>();
        for(char character: userCharArray){
            userCharList.add(character);
        }
        //Turn expected message String into an ArrayList to be able to check for existence of individual characters in user message
        char[] scenarioCharArray = expectedMesagge.toCharArray();
        ArrayList<Character> scenarioCharList =  new ArrayList<>();
        for(char character: scenarioCharArray){
            scenarioCharList.add(character);
        }

        int level1 = expectedMesagge.length() / 3;
        int level2 = level1 * 2;
        int level3 = scenarioCharList.size() - 1;
        int messageScore = 0;

        //Increments the score by one every time the user had the same character in the right place
        for(char character: scenarioCharList){
            if(userCharList.contains(character)){
                messageScore++;
            }
        }



        return 0;


    }
}
