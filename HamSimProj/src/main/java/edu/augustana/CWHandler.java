package edu.augustana;

import java.util.ArrayList;

public class CWHandler {

    private static ArrayList<String> cwArray = new ArrayList<String>();
    private static long startTime;


    /**
     * creates a starting point for the timer
     */
    public static void startTimer() {
        startTime = System.nanoTime();

    }

    /**
     * takes the starting point of the timer and subtracts it from the current time to get the time pressed
     */
    public static void stopTimer() {
        long timePressed = System.nanoTime() - startTime;
        convertToCW(timePressed);
    }

    /**
     * converts the time pressed to a dot or a dash
     * @param timePressed the time that the button was pressed for
     */
    public static void convertToCW(long timePressed) {
        String cwCharacter = "";
        if (timePressed < 200000000) {
            cwCharacter = ".";
        } else {
            cwCharacter = "-";
        }
        cwArray.add(cwCharacter);
        System.out.println(cwArray);
    }

}
