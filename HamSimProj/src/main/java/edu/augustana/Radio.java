package edu.augustana;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import java.util.Random;

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



    public static void initializeRadio() throws LineUnavailableException{

        isRadioOn = true;
        noiseAmplitud = 0.0;
        randGen = new Random();
        soundAmplitud = 1;

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

            double angle = 0;


            while (isPlaying) {// Only play while isPlaying is true

                double angleIncrement = angleIncrement = 2.0 * Math.PI * frequency / format.getSampleRate();


                for (int i = 0; i < BUFFER_SIZE2; i++) {

                    double sineSample = soundAmplitud * (Math.sin(angle) +  (generateGaussianNoise(noiseAmplitud, randGen)));

                    // Clip and normalize the sample to fit into 8-bit range [-128, 127]
                    sineSample = Math.max(-1.0, Math.min(1.0, sineSample));  // Clip sample to [-1.0, 1.0]


                    // Scale sample to 8-bit range [-128 to 127]
                    byte sample = (byte) (sineSample * 127);
                    buffer[i] = sample;  // Write sample to buffer

                    angle += angleIncrement;
                    if (angle > 2.0 * Math.PI) {
                        angle -= 2.0 * Math.PI;
                    }
                }
                line.write(buffer, 0, buffer.length);
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

            double angle = 0;


            while (isRadioOn) {

                double angleIncrement  = 2.0 * Math.PI * (randGen.nextInt(700) / format.getSampleRate());

                // Only play while isPlaying is true
                for (int i = 0; i < BUFFER_SIZE2; i++) {
                    double sineSample = (Math.sin(0) + generateGaussianNoise(0.1, randGen)) * noiseAmplitud;

                    // Clip and normalize the sample to fit into 8-bit range [-128, 127]
                    sineSample = Math.max(-1.0, Math.min(1.0, sineSample));  // Clip sample to [-1.0, 1.0]
                    //System.out.println(sineSample);
                    //for (int j = 0; j <  sineSample)

                    // Scale sample to 8-bit range [-128 to 127]
                    byte sample = (byte) (sineSample * 127);
                    buffer2[i] = sample;  // Write sample to buffer

                    angle += angleIncrement;
                    if (angle > 2.0 * Math.PI) {
                        angle -= 2.0 * Math.PI;
                    }
                }
                line2.write(buffer2, 0, buffer2.length);
            }
        }).start();
    }

    private void stopNoise(){
        line2.flush();
    }



}
