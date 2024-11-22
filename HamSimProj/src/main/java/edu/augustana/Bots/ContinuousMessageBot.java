package edu.augustana.Bots;


import edu.augustana.TextToMorseConverter;

import java.util.ArrayList;
import java.util.Arrays;

public class ContinuousMessageBot extends Bot{


    private final String morseBotPhrase;
    private final String morseCallSign;

    private final String textBotPhrase;
    private final String textCallSign;

    private boolean playSound;


    public static ArrayList<String> botPhraseArray = new ArrayList<>(Arrays.asList("Hello", "SOS", "Pizza is good", "You are cool", "Good Morning", "Good Afternoon", "Good night", "Weather is good", "My CW is bad", "Help me", "The wilderness needs to be explored")); //Add the phrases to this list
    public static ArrayList<String> botCallSignArray = new ArrayList<>(Arrays.asList("K8X", "K5AA", "B2AA", "N2ASD", "K8ABC", "KB9VBR", "A22A", "K7LQ", "N6Y", "W3TRO", "W8IA", "N9NA")); //Add list of call signs
    public static ArrayList<String> usedBotPhrases = new ArrayList<>();
    public static ArrayList<String> usedCallSigns = new ArrayList<>();


    //this count is just for the names
    private static int count = 1;




    //bot constructor for training listening sim
    public ContinuousMessageBot(int band) {
        super(band);

        setBehaviorType(new ContinuousPlaying(this));

        setName("bot" + count);

        count++;

        String selection = botPhraseArray.get(randomGen.nextInt(botPhraseArray.size()));
        botPhraseArray.remove(selection);
        usedBotPhrases.add(selection); //This is so that we can add them back into the array once we stop the sim
        this.textBotPhrase = selection;
        this.morseBotPhrase = TextToMorseConverter.textToMorse(selection); //Morse string of their phrase


        selection = botCallSignArray.get(randomGen.nextInt(botCallSignArray.size()));
        botCallSignArray.remove(selection);
        usedCallSigns.add(selection); //This is so that we can add them back into the array once we stop the sim
        this.textCallSign = selection;
        this.morseCallSign = TextToMorseConverter.textToMorse(selection); //Morse string of their callSign
        this.playSound = false;



        //testing
        System.out.println(this.textCallSign + " " + this.textBotPhrase + " " + getOutputFrequency());

    }

    //bot constructor for scenario bots
    public ContinuousMessageBot(int band, String name, String callSign, String message) {

        super(band);

        setBehaviorType(new ContinuousPlaying(this));

        setName(name);

        this.textBotPhrase = message;
        this.morseBotPhrase = TextToMorseConverter.textToMorse(message);

        this.textCallSign = callSign;
        this.morseCallSign = TextToMorseConverter.textToMorse(callSign);

        this.playSound = false;

        //testing
        System.out.println(this.textCallSign + " " + this.textBotPhrase + " " + getOutputFrequency());

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
     * Accessor method for morseBotPhrase
     * @return morseBotPhrase
     */
    public String getMorseBotPhrase() {
        return morseBotPhrase;
    }

    /**
     * Accessor method for morseCallSign
     * @return morseCallSign
     */
    public String getMorseCallSign() {
        return morseCallSign;
    }


    @Override
    public void playSound() {

        playSound = true;
        playBehavior(); //calls the super class method which calls the playingBehavior object
    }

    @Override
    public void stopSound() {
        playSound = false;
    }

    //accessor method for playSound
    public boolean getPlaySound() {
        return playSound;
    }



    //returns string representation of bot
    public String toString() {
        return getName() + ", " + textCallSign + ", " + getOutputFrequency();
    }
}
