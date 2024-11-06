package edu.augustana;

import java.util.ArrayList;

public class SimScenario {

    private String description = "Hello World";

    private String expectedMesagge = "Sample";

    private int numBots;

    private String scenarioName;

    private RadioEnviroment environment;

    private BotCollection botCollection;

    private String winMessage;

    private String failMessage;

    private int scenarioType;


    public SimScenario(String name, String description, String expectedMesagge, String failMessage, String winMessage, RadioEnviroment environment, BotCollection botCollection, int type){
        this.scenarioName = name;
        this.expectedMesagge = expectedMesagge;
        this.description = description;
        this.environment = environment;
        this.botCollection = botCollection;
        scenarioType = type;
        this.failMessage = failMessage;
        this.winMessage = winMessage;
    }

    public static SimScenario getDefaultScenario(){

        String defDescription = "";

        String defexpectedMessage = "";

        String deffailMessagge  = "";

        String defWinMessage= "";

        RadioEnviroment defRadioEnviroment = new RadioEnviroment("DEFAULT",0.1,0.1,0.1,0.1);
        ArrayList<TrainingListeningBot> defBotList = new ArrayList<>();
        BotCollection defBotCollection = new BotCollection(defBotList);
        SimScenario defaultScenario = new SimScenario("DEFAULT",defDescription,defexpectedMessage,deffailMessagge, defWinMessage, defRadioEnviroment, defBotCollection, 0);

        return defaultScenario;

    }


    /*
     *For now we just need this method to be able to play the message and call sign of the bots
     * to have them continously play their message and and callsign with the different parameters in the scenario
     */
    public void startScenario(){

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
}
