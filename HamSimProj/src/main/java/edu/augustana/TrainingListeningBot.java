package edu.augustana;

import java.util.Random;

public class TrainingListeningBot {

    private double outputFrequency;
    private final String botPhrase;
    private final String callSign;
    private final int band;


    public static final String[] botPhraseArray = {}; //Add the phrases to this list
    public static final String[] botCallSignArray = {}; //Add list of call signs
    private static final Random randomGen = new Random();


    public TrainingListeningBot(int band) {
        this.band = 10; //temporary. Setting the band to 10
        this.botPhrase = botPhraseArray[randomGen.nextInt(0, botPhraseArray.length)];
        this.callSign = botCallSignArray[randomGen.nextInt(0, botCallSignArray.length)];

        if (band == 10) { //need to add more if else statements to account for each band option
            this.outputFrequency = randomGen.nextDouble(28.000, 29.700);
        }

    }

    /**
     * Accessor method for botPhrase
     * @return botPhrase
     */
    public String getBotPhrase() {
        return botPhrase;
    }

    /**
     * Accessor method for callSign
     * @return callSign
     */
    public String getCallSign() {
        return callSign;
    }

    /**
     * Accessor method for outputFrequency
     * @return outputFrequency
     */
    public double getOutputFrequency() {
        return outputFrequency;
    }
}
