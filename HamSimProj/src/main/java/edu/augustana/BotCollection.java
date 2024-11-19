package edu.augustana;

import edu.augustana.Bots.ContinuousMessageBot;

import java.util.ArrayList;

public class BotCollection {


    //Replace this with appropriate classes
    private ArrayList<ContinuousMessageBot> botCollection;

    public BotCollection(ArrayList<ContinuousMessageBot> botList){
        botCollection = botList;
    }

    public void addBot(ContinuousMessageBot bot){
        botCollection.add(bot);
    }

    public ArrayList<ContinuousMessageBot> getBots(){
        return botCollection;
    }

    public void deleteBot(ContinuousMessageBot bot){
        botCollection.remove(bot);
    }

}
