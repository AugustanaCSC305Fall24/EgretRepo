package edu.augustana;

import javafx.scene.input.KeyCode;

import java.util.concurrent.TimeUnit;

import static edu.augustana.CWHandler.startTimer;
import static edu.augustana.CWHandler.stopTimer;
import static edu.augustana.Radio.*;
import static edu.augustana.Radio.stopTone;

public class PaddleHandler {
    private static int wordsPerMinute = 10;
    private static long dotDurationPaddle = 1200000000L / 20;
    private static long dashDurationPaddle = dotDurationPaddle * 3;
    private static boolean dotPaddlePressed;
    private static boolean dashPaddlePressed;
    private static StringBuilder cwString = new StringBuilder();
    private static long paddleReleaseTime;


    public static void playContinuousDot() {
        if (!dotPaddlePressed) {
            // System.out.println("tone and dot loop start");
            stopSpaceTimer();
            dotPaddlePressed = true;

            while (dotPaddlePressed) {
                playTone(Radio.getCwToneFreq());
                try {
                    Thread.sleep(TimeUnit.NANOSECONDS.toMillis(dotDurationPaddle));
                    cwString.append('.');
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                stopTone();
                try {
                    Thread.sleep(TimeUnit.NANOSECONDS.toMillis(dotDurationPaddle));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                //  System.out.println("tone and dot loop end");
            }
        }
    }

    public static void playContinuousDash() {
        if (!dashPaddlePressed) {
            stopSpaceTimer();
            dashPaddlePressed = true;
            while (dashPaddlePressed) {
                playTone(Radio.getCwToneFreq());
                try {
                    Thread.sleep(TimeUnit.NANOSECONDS.toMillis(dashDurationPaddle));
                    cwString.append('-');
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                stopTone();
                try {
                    Thread.sleep(TimeUnit.NANOSECONDS.toMillis(dotDurationPaddle));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public static void stopPaddlePress() {
        dotPaddlePressed = false;
        dashPaddlePressed = false;
        startSpaceTimer();
    }

    public static void startSpaceTimer() {
        paddleReleaseTime = System.nanoTime();
    }

    public static void stopSpaceTimer() {
        int multiplier = 20 / wordsPerMinute;
        long timeSinceReleased = System.nanoTime() - paddleReleaseTime;
        if (timeSinceReleased > (dotDurationPaddle * 7 - 1) * multiplier) {
            cwString.append("/*/");
            System.out.println(cwString.toString());
        } else if (timeSinceReleased > ((dotDurationPaddle * 3) + ((dotDurationPaddle * 3) * 0.2)) * multiplier) {
            cwString.append("/");
        }
    }


}
