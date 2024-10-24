package edu.augustana;

import java.util.Random;

public class TrainingListeningBot {

    private double outputFrequency;
    private final String botPhrase;
    private final String callSign;
    private boolean playSound;
    private final int band;


    public static final String[] botPhraseArray = {"Hello", "Hello2"}; //Add the phrases to this list
    public static final String[] botCallSignArray = {"HHTO", "PTOY"}; //Add list of call signs
    private static final Random randomGen = new Random();


    public TrainingListeningBot(int band) throws InterruptedException {
        this.band = 10; //temporary. Setting the band to 10
        this.botPhrase = TextToMorseConverter.textToMorse(botPhraseArray[randomGen.nextInt(botPhraseArray.length)]); //Morse string of their phrase
        this.callSign = TextToMorseConverter.textToMorse(botCallSignArray[randomGen.nextInt(botCallSignArray.length)]); //Morse string of their callSign
        this.playSound = true;

        if (band == 10) { //need to add more if else statements to account for each band option
            int integerFrequency = randomGen.nextInt(1701) + 28000; //This is because I was getting errors for trying to get a random value between two values, so I made it an integer and am getting a random integer in the range and then adding the lower bound to it
            this.outputFrequency = (double) integerFrequency / 1000;
        }

        playContinuousMessage();

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

    public void playSound() throws InterruptedException {
        playSound = true;
        playContinuousMessage();
    }

    public void stopSound() {
        playSound = false;
    }

    private void playContinuousMessage() throws InterruptedException {

        while (playSound) {
            MorsePlayer.playBotMorseString(callSign, outputFrequency);
            Thread.sleep(100000000); //Need to adjust this to wait the right amount of time
            MorsePlayer.playBotMorseString(botPhrase, outputFrequency);
            Thread.sleep(1000000000); //Need to adjust this too. Needs to be long enough to wait for the entire message to play before starting the while loop again
        }
    }
}
