package edu.augustana;

import java.util.Random;

import static edu.augustana.CWHandler.startTimer;
import static edu.augustana.CWHandler.stopTimer;
import static edu.augustana.Radio.*;
import static edu.augustana.Radio.stopTone;



public class MorseTester {



    private static Random randGen2;

    public static void morseTester() throws InterruptedException {

        randGen2 = new Random();

        int Nchars = 10;
        int beatLength = 200;
        double variation = 0.3;

        //Print out N dots
        for(int i = 0; i < Nchars; i++){
            playTone(Math.abs(getSelectedTuneFreq() -  getSelectedOutFrequency()) * 1000000); startTimer();
            Thread.sleep(beatLength + randGen2.nextInt( (int) (beatLength * variation)));
            stopTone(); stopTimer();
            Thread.sleep(beatLength + randGen2.nextInt( (int) (beatLength * variation)));
        }

        //Print out N dashes
        for(int i = 0; i < Nchars; i++){
            playTone(Math.abs(getSelectedTuneFreq() -  getSelectedOutFrequency()) * 1000000); startTimer();
            Thread.sleep((beatLength * 3) + randGen2.nextInt((int) (beatLength * variation)));
            stopTone(); stopTimer();
            Thread.sleep(beatLength + randGen2.nextInt((int) (beatLength * variation)));
        }

    }


}
