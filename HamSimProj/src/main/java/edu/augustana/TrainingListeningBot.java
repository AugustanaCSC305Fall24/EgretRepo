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

    private final String name;

    //this count is just for the names
    private static int count = 1;

    private static final double frequencyRange;


    public TrainingListeningBot(int band) throws InterruptedException {
        this.band = band; //temporary. Setting the band to 10

        this.name = "bot" + count;
        count++;

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


        switch (band){
            case 10:
                double frequency10 = 28000 + randomGen.nextInt(1701); //This is because I was getting errors for trying to get a random value between two values, so I made it an integer and am getting a random integer in the range and then adding the lower bound to it
                this.outputFrequency =  frequency10 / 1000;
                this.frequencyRange = 1.7;
                break;

            case 17:
                double frequency17 = 18068 + randomGen.nextInt(101); //This is because I was getting errors for trying to get a random value between two values, so I made it an integer and am getting a random integer in the range and then adding the lower bound to it
                this.outputFrequency = frequency17 / 1000;
                this.frequencyRange = .1;
                break;

            case 20:
                double frequency20 = 14000 + randomGen.nextInt(351);
                this.outputFrequency = frequency20 / 1000;
                this.frequencyRange = .35;
                break;

            case 30:
                double frequency30 = 10100 + randomGen.nextInt(51);
                this.outputFrequency = frequency30 / 1000;
                this.frequencyRange = .05;
                break;

            case 40:
                double frequency40 = 7000 + randomGen.nextInt(301);
                this.outputFrequency = frequency40 / 1000;
                this.frequencyRange = .3;
                break;

            case 80:
                double frequency80 = 3500 + randomGen.nextInt(501);
                this.outputFrequency = frequency80 / 1000;
                this.frequencyRange = .5;
                break;
        }

        //testing
        System.out.println(this.textCallSign + " " + this.textBotPhrase + " " + outputFrequency);

    }

    //temporary constructor method. Will create a bot interface later.
    public TrainingListeningBot(int band, String name, String callSign, String message) {
        this.band = band;

        this.name = name;

        this.textBotPhrase = message;
        this.morseBotPhrase = TextToMorseConverter.textToMorse(message);

        this.textCallSign = callSign;
        this.morseCallSign = TextToMorseConverter.textToMorse(callSign);

        this.playSound = false;


        switch (band){
            case 10:
                double frequency10 = 28000 + randomGen.nextInt(1701); //This is because I was getting errors for trying to get a random value between two values, so I made it an integer and am getting a random integer in the range and then adding the lower bound to it
                this.outputFrequency =  frequency10 / 1000;
                this.frequencyRange = 1.7;
                break;

            case 17:
                double frequency17 = 18068 + randomGen.nextInt(101); //This is because I was getting errors for trying to get a random value between two values, so I made it an integer and am getting a random integer in the range and then adding the lower bound to it
                this.outputFrequency = frequency17 / 1000;
                this.frequencyRange = .1;
                break;

            case 20:
                double frequency20 = 14000 + randomGen.nextInt(351);
                this.outputFrequency = frequency20 / 1000;
                this.frequencyRange = .35;
                break;

            case 30:
                double frequency30 = 10100 + randomGen.nextInt(51);
                this.outputFrequency = frequency30 / 1000;
                this.frequencyRange = .05;
                break;

            case 40:
                double frequency40 = 7000 + randomGen.nextInt(301);
                this.outputFrequency = frequency40 / 1000;
                this.frequencyRange = .3;
                break;

            case 80:
                double frequency80 = 3500 + randomGen.nextInt(501);
                this.outputFrequency = frequency80 / 1000;
                this.frequencyRange = .5;
                break;
        }

        //testing
        System.out.println(this.textCallSign + " " + this.textBotPhrase + " " + outputFrequency);

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

    public String getName() {
        return name;
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
        System.out.println(outputFrequency);


        new Thread(() -> { //Need to continuously check "playSound" throughout this loop, and then break out of it if it is false
            while (playSound) {
                try {
                    MorsePlayer.playBotMorseString(morseCallSign, outputFrequency, frequencyRange);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                if (!playSound) {break;} //This is so that we can stop the sound sooner, rather than waiting till the end

                try { // Use WPM to calculate how long each thing takes
                    Thread.sleep(MorsePlayer.getMessagePlayDuration(morseCallSign) + 2000); //waiting the duration of the message, plus 2 seconds
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                if (!playSound) {break;}

                try {
                    MorsePlayer.playBotMorseString(morseBotPhrase, outputFrequency, frequencyRange);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                if (!playSound) {break;}

                try {
                    Thread.sleep(MorsePlayer.getMessagePlayDuration(morseBotPhrase) + 2000); //Same thing as other sleep
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();

    }

    //returns string representation of bot
    public String toString() {
        return name + ", " + textCallSign + ", " + outputFrequency;
    }
}
