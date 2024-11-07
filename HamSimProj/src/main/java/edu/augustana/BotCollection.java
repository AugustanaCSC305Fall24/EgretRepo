package edu.augustana;

import java.util.ArrayList;

public class BotCollection {


    //Replace this with appropriate classes
    private ArrayList<TrainingListeningBot> botCollection;

    public BotCollection(ArrayList<TrainingListeningBot> botList){
        botCollection = botList;
    }

    public void addBot(TrainingListeningBot bot){
        botCollection.add(bot);
    }

    public ArrayList<TrainingListeningBot> getBots(){
        return botCollection;
    }

    public void deleteBot(TrainingListeningBot bot){
        botCollection.remove(bot);
    }

}
