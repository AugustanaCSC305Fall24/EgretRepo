package edu.augustana;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static java.lang.Double.valueOf;

public class Radio {

    private static SourceDataLine line1;
    private static SourceDataLine line2;
    private static AudioFormat format;

    private static double selectedRF;
    private static double tunningRF;
    private static boolean radioOn;
    private static double noiseAmplitud;
    private static Random randGen;
    private static double soundAmplitud;


    private static final int SAMPLE_RATE = 44100;
    private static final int BUFFER_SIZE = 256;
    private static final int BUFFER_SIZE2 = 4096;
    private static SourceDataLine line;
    private static boolean isPlaying = false;
    private static byte[] buffer = new byte[BUFFER_SIZE2];
    private static byte[] buffer2 = new byte[BUFFER_SIZE2];
    private static boolean isRadioOn;
    private static int filterVal;
    private static BiquadLowPassFilter8Bit biquadFilter;
    private static BiquadLowPassFilter8Bit biquadFilter2;
    private static int lowerNoiseFreqBoundary = 250;
    private static int upperNoiseFreqBoundary = 550;




    public static void initializeRadio() throws LineUnavailableException{

        isRadioOn = true;

        noiseAmplitud = 0.0;
        randGen = new Random();
        soundAmplitud = 1;
        filterVal = 6379;
        biquadFilter = new BiquadLowPassFilter8Bit(SAMPLE_RATE, filterVal);
        biquadFilter2 = new BiquadLowPassFilter8Bit(SAMPLE_RATE, filterVal);


        format = new AudioFormat(SAMPLE_RATE, 8, 1, true, false);
        line = AudioSystem.getSourceDataLine(format);
        line.open(format, BUFFER_SIZE2);
        line.start();

        line2 = AudioSystem.getSourceDataLine(format);
        line2.open(format, BUFFER_SIZE2);
        line2.start();

        playNoise();

    }


    // Method to generate a tone and play it on the given line
    static void playTone(double frequency) {
        if (isPlaying) {
            return;  // Prevent starting multiple threads for the same tone
        }

        isPlaying = true;
        new Thread(() -> {


            double[] angles = new double[10];
            angles[0] = 0;

            while (isPlaying) {// Only play while isPlaying is true

                playSound(angles, 0, BUFFER_SIZE2, biquadFilter, buffer, line, frequency);


            }
        }).start();
    }

    // Method to stop the tone
    static void stopTone() {
        isPlaying = false;

        line.flush();  // Clear the buffer to stop sound
    }


    private static double generateGaussianNoise(double stdDev, Random random) {
        double u1 = random.nextDouble();
        double u2 = random.nextDouble();
        return Math.sqrt(-2.0 * Math.log(u1)) * Math.cos(2.0 * Math.PI * u2) * stdDev;
    }

    public static void  setSelectedRF(double newSelectedRF){

        selectedRF = newSelectedRF;

    }

    public static void  setTunningRF(double newTunningRF){

        tunningRF = newTunningRF;

    }

    public static void updateGain(double newAmplitud){

        soundAmplitud = newAmplitud;

    }

    public static void updateNoiseGain(double newAmplitud){

        noiseAmplitud = newAmplitud;

    }

    public static double getSelectedOutFrequency(){
        return selectedRF;
    }

    public static double getSelectedTuneFreq(){
        return tunningRF;
    }



    private static void playNoise(){

        new Thread(() -> {


            double[] angles = new double[10];
            angles[0] = 0;


            while (isRadioOn) {

                int frequency = randGen.nextInt(upperNoiseFreqBoundary - lowerNoiseFreqBoundary) + lowerNoiseFreqBoundary;

                playSound(angles, 0, BUFFER_SIZE2, biquadFilter2, buffer2, line2, 0);


            }
        }).start();
    }

    private void stopNoise(){
        line2.flush();
    }


    public static void changeFilterValue(int newFilterVal){

        biquadFilter2.setFilterValue(newFilterVal);
        biquadFilter.setFilterValue(newFilterVal);
    }

    static void playSound(double[] angles, int angleIndex, int bufferSize, BiquadLowPassFilter8Bit filter, byte[] buffer,  SourceDataLine line, double frequency){
        double angleIncrement  = 2.0 * Math.PI * frequency / format.getSampleRate();

        // Only play while isPlaying is true
        for (int i = 0; i < bufferSize; i++) {
            double sineSample = ( soundAmplitud * ((Math.sin(angles[angleIndex])) + generateGaussianNoise(noiseAmplitud , randGen)));

            // Clip and normalize the sample to fit into 8-bit range [-128, 127]
            sineSample = Math.max(-1.0, Math.min(1.0, sineSample));  // Clip sample to [-1.0, 1.0]


            double filteredSample = filter.processSample(sineSample);

            // Scale sample to 8-bit range [-128 to 127]
            byte sample = (byte) (filteredSample * 127);
            buffer[i] = sample;  // Write sample to buffer

            angles[angleIndex] += angleIncrement;
            if (angles[angleIndex] > 2.0 * Math.PI) {
                angles[angleIndex] -= 2.0 * Math.PI;
            }
        }

        line.write(buffer, 0, buffer.length);


    }






}
