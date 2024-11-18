package edu.augustana.Bots;

public interface Bot {

    /**
     * Accessor method for botPhrase
     * @return botPhrase
     */
    String getTextBotPhrase();

    /**
     * Accessor method for callSign
     * @return callSign
     */
    String getTextCallSign();

    /**
     * Accessor method for outputFrequency
     * @return outputFrequency
     */
    double getOutputFrequency();

    /**
     * Accessor method for name
     * @return name
     */
    String getName();

    /**
     * Accessor method for morseBotPhrase
     * @return morseBotPhrase
     */
    String getMorseBotPhrase();

    /**
     * Accessor method for morseCallSign
     * @return morseCallSign
     */
    String getMorseCallSign();

    /**
     * returns a string representation of the bot
     * @return String
     */
    String toString();



}
