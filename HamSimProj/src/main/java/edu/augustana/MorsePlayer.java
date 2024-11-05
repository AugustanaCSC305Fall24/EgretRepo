package edu.augustana;

import java.util.Random;

import static edu.augustana.CWHandler.startTimer;
import static edu.augustana.CWHandler.stopTimer;
import static edu.augustana.Radio.*;
import static edu.augustana.Radio.stopTone;

public class MorsePlayer {

    private static Random randGen2;
    private static int beatLength;
    private static int maxToneHz = 3000;





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
    public static void playBotMorseString(String morseString, double botFrequency) throws InterruptedException {

        new Thread(() -> {

            double variation = 0.3;
            char[] morse = morseString.toCharArray();

            randGen2 = new Random();

            for(int i = 0; i < morse.length ; i++){
                if(morse[i] == '.'){
                    double freq = Math.min(Math.abs(getSelectedTuneFreq() -  botFrequency) *1000000, Radio.getCwToneFreq());
                    if(freq >= 7000){
                        freq = 0;
                    }
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
                    double freq = Math.min(Math.abs(getSelectedTuneFreq() -  botFrequency) *1000000, Radio.getCwToneFreq());
                    if(freq >= 7000){
                        freq = 0;
                    }
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

    public static void setBeatLength(int newValue){
        //System.out.println(newValue);
        beatLength = newValue;
    }



}
