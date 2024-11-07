package edu.augustana;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import static edu.augustana.CWHandler.startTimer;
import static edu.augustana.CWHandler.stopTimer;
import static edu.augustana.Radio.*;
import static edu.augustana.Radio.stopTone;

public class MorsePlayer {

    private static Random randGen2;
    private static final long beatLength = TimeUnit.NANOSECONDS.toMillis((1200000000L / 20));
    private static int maxToneHz = 3000;
    private static int wordsPerMinute;
    private static double multiplier;





    public static void playMorseString(String morseString) throws InterruptedException {

        new Thread(() -> {

        double variation = 0.3;
        char[] morse = morseString.toCharArray();

        randGen2 = new Random();

        for(int i = 0; i < morse.length ; i++){
            if(morse[i] == '.'){
                playTone(Math.abs(getSelectedTuneFreq() -  getSelectedOutFrequency()) * 1000000);
                startTimer();
                try {
                    Thread.sleep(beatLength + randGen2.nextInt( (int) (beatLength * variation)));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                stopTone();
                stopTimer();
            }else if(morse[i] == '-'){
                playTone(Math.abs(getSelectedTuneFreq() -  getSelectedOutFrequency()) * 1000000); startTimer();
                try {
                    Thread.sleep((beatLength * 3) + randGen2.nextInt( (int) (beatLength * variation)));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                stopTone(); stopTimer();
            }else{
                try {
                    Thread.sleep((beatLength * 7) + randGen2.nextInt( (int) (beatLength * variation)));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            try {
                Thread.sleep(beatLength + randGen2.nextInt( (int) (beatLength * variation)));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        }).start();

    }
    
    //Temporary method to get the bots to play their sound
    //Not DRY code at all, but I didn't want to break anything
    public static void playBotMorseString(String morseString, double botFrequency, double frequencyRange) throws InterruptedException {

        new Thread(() -> {

            double variation = 0.3;
            char[] morse = morseString.toCharArray();

            randGen2 = new Random();


            for(int i = 0; i < morse.length ; i++){

                double freq = Math.abs(getSelectedTuneFreq() -  botFrequency) * 1000000 + 400;

                //testing
                System.out.println(freq);

                freq = 1000 * Math.log10(freq); //if you are about on, it puts out freq of 2500
                freq = freq - 2100; //so now I am bringing it down to 400

                //testing
                System.out.println(freq);
                //3950

                if (freq < 400) {
                    freq = 400;
                }





                if(morse[i] == '.'){

                    playTone(freq);
                    startTimer();
                    try {
                        Thread.sleep(beatLength + randGen2.nextInt( (int) (beatLength * variation)));
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    stopTone();
                    stopTimer();
                }else if(morse[i] == '-'){

                    playTone(freq);
                    startTimer();
                    try {
                        Thread.sleep((beatLength * 3) + randGen2.nextInt( (int) (beatLength * variation)));
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    stopTone(); stopTimer();
                }else{
                    try {
                        Thread.sleep((long) ((beatLength * 7) * (multiplier) + randGen2.nextInt( (int) (beatLength * (multiplier) * variation))));
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                try {
                    Thread.sleep(((beatLength) + randGen2.nextInt( (int) (beatLength * multiplier * variation))));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

        }).start();




    }

    //returns the amount of time that this message will take to play
    public static long getMessagePlayDuration(String morseMessage) {
        double variation = 0.3;
        char[] morse = morseMessage.toCharArray();

        randGen2 = new Random();

        long millisTimeWaited = 0;

        for(int i = 0; i < morse.length ; i++){
            if(morse[i] == '.'){
                millisTimeWaited = millisTimeWaited + beatLength + randGen2.nextInt( (int) (beatLength * variation));
            }else if(morse[i] == '-'){
                millisTimeWaited = millisTimeWaited + (beatLength * 3) + randGen2.nextInt( (int) (beatLength * variation));
            }else{
                millisTimeWaited = millisTimeWaited + (long) ((beatLength * 7) * (multiplier) + randGen2.nextInt( (int) (beatLength * (multiplier) * variation)));

            }

            millisTimeWaited = millisTimeWaited + (beatLength + randGen2.nextInt( (int) (beatLength * variation)));

        }

        return millisTimeWaited;
    }


    public static void setWordsPerMinuteMultiplier(int wpm) {
        wordsPerMinute = wpm;
        multiplier = (double) 20 / wpm;
    }



}
