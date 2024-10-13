package edu.augustana;

import java.util.ArrayList;

public class CWHandler {

    private static ArrayList<String> cwArray = new ArrayList<String>();
    private static ArrayList<Long> cwBufferArray = new ArrayList<Long>();
    private static long startTime;
    private static long releasedStartTime;
    private static long totalTimePressed = 0;
    private static int pressCount = 0;

    // Dynamic timing variables
    private static long dotDuration;
    private static long dashDuration;
    private static long letterSpaceDuration;
    private static long wordSpaceDuration;

    /**
     * Starts the timer when the key is pressed
     */
    public static void startTimer() {
        spaceTimerStop();
        startTime = System.nanoTime();
    }

    /**
     * Stops the timer when the key is released and calculates the time pressed
     */
    public static void stopTimer() {
        long timePressed = System.nanoTime() - startTime;
        pressCount++;
        totalTimePressed += timePressed;

        // Auto-detect WPM based on the average time of the presses
        autoDetectWPM();

        // Convert the time pressed to Morse code (dot or dash)
        convertToCW(timePressed);

        spaceTimerStart();


    }


    public static void spaceTimerStart(){
        releasedStartTime = System.nanoTime();

    }

    public static void spaceTimerStop() {

        long timeSinceReleased = System.nanoTime() - releasedStartTime;

        if(timeSinceReleased > dotDuration * 7 - 1 ){
        
            cwArray.add("/");
            cwArray.add("*");
            cwArray.add("/");


        } else if (timeSinceReleased > dotDuration * 3) {
            cwArray.add("/");
        }
    }




    /**
     * Dynamically calculates WPM based on user's input speed
     */
    public static void autoDetectWPM() {
        // Calculate the average time per keypress
        long avgTimePressed = totalTimePressed / pressCount;

        // Calculate the WPM dynamically
        double avgTimePressedMs = avgTimePressed / 1000000.0; // Convert to milliseconds
        int wpm = (int) (1200 / avgTimePressedMs); // 1200 ms for a 1-dot unit at 1 WPM

        // Set dynamic Morse timings based on the auto-detected WPM
        setDynamicDurations(wpm);
    }

    public static void printOutArray(){
        System.out.println(cwArray);
    }

    /**
     * Sets dynamic Morse timings based on the WPM
     */
    public static void setDynamicDurations(int wpm) {
        // Calculate the durations based on the auto-detected WPM
        dotDuration = 1200000000L / wpm;  // 1.2 billion nanoseconds = 1200 ms for 1 WPM dot
        dashDuration = 3 * dotDuration;   // Dash is 3 times a dot
        letterSpaceDuration = 3 * dotDuration;  // Letter space is 3 dots
        wordSpaceDuration = 7 * dotDuration;    // Word space is 7 dots

//        System.out.println("Auto-detected WPM: " + wpm);
//        System.out.println("Dot duration (ns): " + dotDuration);
//        System.out.println("Dash duration (ns): " + dashDuration);
    }

    public static ArrayList<String> getCwArray(){
        return cwArray;
    }


    /**
     * Converts the time pressed into Morse code symbols (dot, dash) based on the dynamic WPM
     * @param timePressed the time that the key was pressed for
     */
    public static void convertToCW(long timePressed) {
        String cwCharacter = "";

        // Differentiate between dot and dash based on the dynamically adjusted WPM timing
        if (timePressed < dotDuration) {
            cwCharacter = "."; // Dot
        } else if (timePressed < dashDuration) {
            cwCharacter = "-"; // Dash
        } else {
            cwCharacter = "-"; // Handle for invalid long press (optional)
        }

        cwArray.add(cwCharacter);
        cwBufferArray.add(timePressed / 1000); // Store in microseconds for logging

        printOutArray();

    }


}
