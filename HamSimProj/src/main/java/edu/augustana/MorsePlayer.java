package edu.augustana;

import java.util.Random;

import static edu.augustana.CWHandler.startTimer;
import static edu.augustana.CWHandler.stopTimer;
import static edu.augustana.Radio.*;
import static edu.augustana.Radio.stopTone;

public class MorsePlayer {

    private static Random randGen2;





    public static void playMorseString(String morseString) throws InterruptedException {



        new Thread(() -> {

        int beatLength = 80;
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



}
