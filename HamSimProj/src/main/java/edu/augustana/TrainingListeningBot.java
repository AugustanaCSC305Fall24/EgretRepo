package edu.augustana;

import java.util.Random;

public class TrainingListeningBot {

    private double outputFrequency;
    private final String morseBotPhrase;
    private final String morseCallSign;

    private final String textBotPhrase;
    private final String textCallSign;

    private boolean playSound;
    private final int band;


    public static final String[] botPhraseArray = {"Hello", "Hello2"}; //Add the phrases to this list
    public static final String[] botCallSignArray = {"1", "2"}; //Add list of call signs
    private static final Random randomGen = new Random();


    public TrainingListeningBot(int band) throws InterruptedException {
        this.band = 10; //temporary. Setting the band to 10

        //will have to remove the selection from the list so that two bots don't have the same stuff
        String selection = botPhraseArray[randomGen.nextInt(botPhraseArray.length)];
        this.textBotPhrase = selection;
        this.morseBotPhrase = TextToMorseConverter.textToMorse(selection); //Morse string of their phrase

        //remove selection from the list here

        selection = botCallSignArray[randomGen.nextInt(botCallSignArray.length)];
        this.textCallSign = selection;
        this.morseCallSign = TextToMorseConverter.textToMorse(selection); //Morse string of their callSign

        //remove selection from the list here

        this.playSound = false;

        if (band == 10) { //need to add more if else statements to account for each band option

        }

        switch (band){
            case 10:
                double frequency10 = 28 + (1.7 * randomGen.nextDouble()); //This is because I was getting errors for trying to get a random value between two values, so I made it an integer and am getting a random integer in the range and then adding the lower bound to it
                this.outputFrequency =  frequency10;
                break;

            case 17:
                double frequency17 = 18.068 + (18.168 - 18.068) * randomGen.nextDouble(); //This is because I was getting errors for trying to get a random value between two values, so I made it an integer and am getting a random integer in the range and then adding the lower bound to it
                this.outputFrequency = frequency17;
                break;

            case 20:
                double frequency20 = 14.000 + (14.350 - 14.000) * randomGen.nextDouble();
                this.outputFrequency = frequency20;
                break;

            case 30:
                double frequency30 = 10.1 + (10.15 - 10.1) * randomGen.nextDouble();
                this.outputFrequency = frequency30;
                break;

            case 40:
                double frequency40 = 7.000 + (7.300 - 7.000) * randomGen.nextDouble();
                this.outputFrequency = frequency40;
                break;

            case 80:
                double frequency80 = 3.5 + (4.0 - 3.5) * randomGen.nextDouble();
                this.outputFrequency = frequency80;
                break;
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
                    MorsePlayer.playBotMorseString(morseCallSign, outputFrequency);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                try {
                    Thread.sleep(4000); //Need to adjust this to wait the right amount of time. Needs to change to match the beat length etc.
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                try {
                    MorsePlayer.playBotMorseString(morseBotPhrase, outputFrequency);
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
