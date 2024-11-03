package edu.augustana;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class TrainingListeningBot {

    private double outputFrequency;
    private final String morseBotPhrase;
    private final String morseCallSign;

    private final String textBotPhrase;
    private final String textCallSign;

    private boolean playSound;
    private final int band;


    public static ArrayList<String> botPhraseArray = new ArrayList<>(Arrays.asList("Hello", "SOS", "Pizza is good", "You are cool", "Good Morning", "Good Afternoon", "Good night", "Weather is good", "My CW is bad", "Help me", "The wilderness needs to be explored")); //Add the phrases to this list
    public static ArrayList<String> botCallSignArray = new ArrayList<>(Arrays.asList("K8X", "K5AA", "B2AA", "N2ASD", "K8ABC", "KB9VBR", "A22A", "K7LQ", "N6Y", "W3TRO", "W8IA", "N9NA")); //Add list of call signs
    public static ArrayList<String> usedBotPhrases = new ArrayList<>();
    public static ArrayList<String> usedCallSigns = new ArrayList<>();

    private static final Random randomGen = new Random();


    public TrainingListeningBot(int band) throws InterruptedException {
        this.band = 10; //temporary. Setting the band to 10

        String selection = botPhraseArray.get(randomGen.nextInt(botPhraseArray.size()));
        botPhraseArray.remove(selection);
        usedBotPhrases.add(selection); //This is so that we can add them back into the array once we stop the sim
        this.textBotPhrase = selection;
        this.morseBotPhrase = TextToMorseConverter.textToMorse(selection); //Morse string of their phrase


        selection = botCallSignArray.get(randomGen.nextInt(botCallSignArray.size()));
        botCallSignArray.remove(selection);
        usedCallSigns.add(selection);
        this.textCallSign = selection;
        this.morseCallSign = TextToMorseConverter.textToMorse(selection); //Morse string of their callSign


        this.playSound = false;

        if (band == 10) { //need to add more if else statements to account for each band option
            int integerFrequency = randomGen.nextInt(1701) + 28000; //This is because I was getting errors for trying to get a random value between two values, so I made it an integer and am getting a random integer in the range and then adding the lower bound to it
            this.outputFrequency = (double) integerFrequency / 1000;
        }


    }

    /**
     * Accessor method for botPhrase
     * @return botPhrase
     */
    public String getTextBotPhrase() {
        return textBotPhrase;
    }

    /**
     * Accessor method for callSign
     * @return callSign
     */
    public String getTextCallSign() {return textCallSign;}

    /**
     * Accessor method for outputFrequency
     * @return outputFrequency
     */
    public double getOutputFrequency() {
        return outputFrequency;

        //this method can be used if we want to add a hint button
    }

    public void playSound() throws InterruptedException {
        playSound = true;
        playContinuousMessage();
    }

    public void stopSound() {
        playSound = false;
    }

    private void playContinuousMessage() {

        //Can probably have this just play the whole message, but when I do I need to add more space between the call sign
        //and the message. So just append an * between the two I think.

        new Thread(() -> { //Need to continuously check "playSound" throughout this loop, and then break out of it if it is false
            while (playSound) {
                try {
                    MorsePlayer.playBotMorseString(morseCallSign, outputFrequency);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                if (!playSound) {break;} //This is so that we can stop the sound sooner, rather than waiting till the end

                try {
                    Thread.sleep(morseCallSign.length() * 500L); //This gets the amount of characters played, and then waits a certain amount of time. Currently, we are waiting half a second for every character. This may need to be shorter
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                if (!playSound) {break;}

                try {
                    MorsePlayer.playBotMorseString(morseBotPhrase, outputFrequency);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                if (!playSound) {break;}

                try {
                    Thread.sleep(morseBotPhrase.length() * 500L); //Same thing as other sleep
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();

    }
}
