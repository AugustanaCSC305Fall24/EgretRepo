package edu.augustana;

import java.util.ArrayList;

public class CWHandler {

    private static ArrayList<String> cwArray = new ArrayList<String>();
    private static long startTime;

    public static void startTimer() {
        startTime = System.nanoTime();

    }

    public static void stopTimer() {
        long timePressed = System.nanoTime() - startTime;
        convertToCW(timePressed);
    }

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
