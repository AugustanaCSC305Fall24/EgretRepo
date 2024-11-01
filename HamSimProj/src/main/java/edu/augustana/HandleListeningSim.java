package edu.augustana;


import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;


import java.util.ArrayList;

public class HandleListeningSim {

    public static ArrayList<TrainingListeningBot> botList = new ArrayList<TrainingListeningBot>();
    private static boolean simActive = false;


    //Creates the bots and adds them to the list, and then makes them call the method in MorsePlayer to have them continuously play their sound
    public static void startSim(double numBots, double playbackSpeed) throws InterruptedException {

        numBots = (int) numBots;
        simActive = true;

        for (int i = 0; i < numBots; i++) {
            botList.add(new TrainingListeningBot(10));//Need to change this to whatever band we are listening to once we get the bands set up
            botList.get(i).playSound();
        }
    }

    //Takes the value from the two text boxes and check to see if they match the bot they are listening to
    public static void checkGuess(String guessedCallSign, String guessedMessage, VBox guessedMessagesVBox) {

        guessedCallSign = guessedCallSign.trim().toUpperCase();
        guessedMessage = guessedMessage.trim().toUpperCase();

        //testing
        System.out.println("Guess: " + guessedCallSign + " " + guessedMessage);

        boolean guessedCorrectly = false; //Make this variable so that you can check if you have to add the guess as red text into the listview

        for (TrainingListeningBot bot : botList) {

            //testing
            System.out.println("bot: " +  bot.getTextCallSign() + " " + bot.getTextBotPhrase());

            if (bot.getTextCallSign().toUpperCase().equals(guessedCallSign)) {
                if (bot.getTextBotPhrase().toUpperCase().equals(guessedMessage)) {

                    //need to add a counter of how many you get right here and put it on screen
                    //Also can put the message into the list view with green text and update the counter
                    bot.stopSound();
                    botList.remove(bot);
                    guessedCorrectly = true;

                }

            }
        }

        //Will have to change this to a listview. So all of this code will have to change potentially
        String fullGuess = "(" + guessedCallSign + ") " + guessedMessage;
        Label label = new Label(fullGuess); //Created a label to add to the VBox. I saw this was how it was done on the Chatter code
        label.setWrapText(true);
        label.setFont(Font.font("System", FontWeight.NORMAL, 11)); //Maybe need to play around with this to get a font we like

        if (!guessedCorrectly) {
            //Add guess as red into the listview
            label.setTextFill(Color.RED);

        } else { //Guessed correctly
            label.setTextFill(Color.GREEN);

        }

        guessedMessagesVBox.getChildren().add(label); //May have to put this statement into each if else because it might not change the color
        //Maybe need to add a condition for the first message because it might
        //not be able to call getChildren on a null vbox. So I might have to instantiate it in the trainingscreen2controller
        //to have a message in it already


    }

    public static void stopSim(VBox guessedMessagesVBox) {
        simActive = false;
        for (TrainingListeningBot bot : botList) {
            bot.stopSound();
        }
        botList.clear();

        guessedMessagesVBox.getChildren().clear(); //Clearing the messages log vbox

        //Need to make the back button call this method as well, but I haven't instantiated that yet
    }

    public static boolean getSimActive() {
        return simActive;
    }



}
