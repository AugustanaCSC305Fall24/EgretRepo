package edu.augustana;

import java.util.Random;

public class TrainingListeningBot {

    private double outputFrequency;
    private final String botPhrase;
    private final String callSign;
    private boolean playSound;
    private final int band;


    public static final String[] botPhraseArray = {"Hello", "Hello2"}; //Add the phrases to this list
    public static final String[] botCallSignArray = {"1", "2"}; //Add list of call signs
    private static final Random randomGen = new Random();


    public TrainingListeningBot(int band) throws InterruptedException {
        this.band = 10; //temporary. Setting the band to 10
        //will have to remove the selection from the list so that two bots don't have the same stuff
        this.botPhrase = TextToMorseConverter.textToMorse(botPhraseArray[randomGen.nextInt(botPhraseArray.length) - 1]); //Morse string of their phrase
        this.callSign = TextToMorseConverter.textToMorse(botCallSignArray[randomGen.nextInt(botCallSignArray.length) - 1]); //Morse string of their callSign
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

    private void playContinuousMessage() {

        //Can probably have this just play the whole message, but when I do I need to add more space between the call sign
        //and the message. So just append an * between the two I think.

        new Thread(() -> {
            while (playSound) {
                try {
                    MorsePlayer.playBotMorseString(callSign, outputFrequency);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                try {
                    Thread.sleep(4000); //Need to adjust this to wait the right amount of time. Needs to change to match the beat length etc.
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                try {
                    MorsePlayer.playBotMorseString(botPhrase, outputFrequency);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                try {
                    Thread.sleep(4000); //Need to adjust this too. Needs to be long enough to wait for the entire message to play before starting the while loop again
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();

    }
}
