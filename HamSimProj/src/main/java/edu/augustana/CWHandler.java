package edu.augustana;

import javafx.scene.input.KeyCode;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import static edu.augustana.Radio.playTone;
import static edu.augustana.Radio.stopTone;

public class CWHandler {

    private static ArrayList<String> cwArray = new ArrayList<String>();
    private static StringBuilder cwString = new StringBuilder();
    private static ArrayList<Long> cwBufferArray = new ArrayList<Long>();
    private static long startTime;
    private static long releasedStartTime;
    private static long totalTimePressed = 0;
    private static int pressCount = 0;
    private static Boolean alreadyPressed = false;
    private int wpm = 18;



    // Dynamic timing variables
    private static long dotDuration;
    private static long dashDuration;
    private static long letterSpaceDuration;
    private static long wordSpaceDuration;
    private static double marginOfError;

    /**
     * Starts the timer when the key is pressed
     */
    public static void startTimer() {
        if (!alreadyPressed) {
            playTone(Radio.getCwToneFreq());
            marginOfError = 0.2;
            spaceTimerStop();
            startTime = System.nanoTime();
            alreadyPressed = true;
        }
    }

    /**
     * Stops the timer when the key is released and calculates the time pressed
     */
    public static void stopTimer() {
        stopTone();
        long timePressed = System.nanoTime() - startTime;
        pressCount++;
        totalTimePressed += timePressed;

        // Auto-detect WPM based on the average time of the presses
        autoDetectWPM();

        // Convert the time pressed to Morse code (dot or dash)
        convertToCW(timePressed);

        spaceTimerStart();

        alreadyPressed = false;
    }


    public static void spaceTimerStart(){
        releasedStartTime = System.nanoTime();

    }

    public static void spaceTimerStop() {
       // int multiplier = 20 / wpm;
        long timeSinceReleased = System.nanoTime() - releasedStartTime;

        if(timeSinceReleased > dotDuration * 7 - 1 ){
            cwString.append("/*/");
        } else if (timeSinceReleased > dotDuration * 3) {
            cwString.append("/");
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
        System.out.println(cwString.toString());
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
    }

    public static ArrayList<String> getCwArray(){
        return cwArray;
    }

    public static String getCwString () {return cwString.toString();}


    /**
     * Converts the time pressed into Morse code symbols (dot, dash) based on the dynamic WPM
     * @param timePressed the time that the key was pressed for
     */
    //THIS IS WHERE THE MARGIN OF ERROR SHOULD BE ADDED TO ACCOUNT FOR HUMAN ERROR IN TYPING CW


    public static void convertToCW(long timePressed) {
        String cwCharacter = "";

        // Differentiate between dot and dash based on the dynamically adjusted WPM timing
        if (timePressed < dotDuration + (dotDuration * marginOfError)) {
            cwCharacter = "."; // Dot
        } else if (timePressed < dashDuration + (dashDuration * marginOfError)) {
            cwCharacter = "-"; // Dash
        } else {
            cwCharacter = "-"; // Handle for invalid long press (optional)
        }
        cwString.append(cwCharacter);
        cwBufferArray.add(timePressed / 1000); // Store in microseconds for logging
        printOutArray();
    }

    public static void addToArray(String toBeAdded) {
        cwArray.add(toBeAdded);
    }


    public static void setErrorMargin(double newMargin){
        marginOfError = newMargin;
    }


}
