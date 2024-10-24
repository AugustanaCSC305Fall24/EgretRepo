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
            botList.add(new TrainingListeningBot(10));//Need to change this to whatever band we are listening to once we get the bands set up
            botList.get(i).playSound();
        }
    }

    //Takes the value from the two text boxes and check to see if they match the bot they are listening to
    public static void checkGuess() {


        String guessedCallSign = TrainingScreen2Controller.getGuessedCallSign().trim().toUpperCase();
        String guessedMessage = TrainingScreen2Controller.getGuessedMessage().trim().toUpperCase();
        boolean guessedCorrectly = false; //Make this variable so that you can check if you have to add the guess as red text into the listview

        for (TrainingListeningBot bot : botList) {
            if (bot.getCallSign().equals(guessedCallSign)) {
                if (bot.getBotPhrase().equals(guessedMessage)) {

                    //need to add a counter of how many you get right here and put it on screen
                    //Also can put the message into the list view with green text and update the counter
                    bot.stopSound();
                    botList.remove(bot);
                    guessedCorrectly = true;

                }

            }
        }

        if (!guessedCorrectly) {
            //Add guess as red into the listview
        }

        //Clear the data in both guessing text boxes



    }

    public static void stopSim() {
        simActive = false;
        for (TrainingListeningBot bot : botList) {
            bot.stopSound();
        }
        botList.clear();
    }

    public static boolean getSimActive() {
        return simActive;
    }



}
