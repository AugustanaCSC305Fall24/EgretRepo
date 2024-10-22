package edu.augustana;


import java.util.ArrayList;

public class HandleListeningSim {

    public static ArrayList<TrainingListeningBot> botList = new ArrayList<TrainingListeningBot>();
    private static boolean simActive = false;


    //Creates the bots and adds them to the list, and then makes them call the method in MorsePlayer to have them continuously play their sound
    public static void startSim() throws InterruptedException {

        int numBots = TrainingScreen2Controller.getNumBots();
        simActive = true;

        for (int i = 0; i < numBots; i++) {
            botList.add(new TrainingListeningBot(10)); //Need to change this to whatever band we are listening to once we get the bands set up
        }
    }

    //Takes the value from the two text boxes and check to see if they match the bot they are listening to
    public static void checkGuess() {

    }

    public static void stopSim() {
        simActive = false;
    }

    public static boolean getSimActive() {
        return simActive;
    }



}
