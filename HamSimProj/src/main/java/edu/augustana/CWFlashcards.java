package edu.augustana;

import java.util.*;

public class CWFlashcards {

    private static HashMap<String, String> morseToTextMap = TextToMorseConverter.getMorseToTextMap();
    private static Map<Character, String> textToMorseMap = TextToMorseConverter.getTextToMorseMap();
    private static List<Character> alphabetList = new ArrayList<Character>();
    private static Random randGen = new Random();
    private static String currentLetter = "";
    private static String currentMorse;

    public static Boolean handleGuess(String guess) {
        if (Objects.equals(textToMorseMap.get(guess.charAt(0)), currentMorse)) {
            currentLetter = "";
            currentMorse = "";
            return true;
        } else {
            return false;
        }
    }

    public static void playLetter() throws InterruptedException {
        String letter;

        if (currentLetter.isEmpty()) {
            letter = Character.toString(selectRandomLetter());
            currentLetter = letter;
        } else {
            letter = currentLetter;
        }
        currentMorse = TextToMorseConverter.textToMorse(letter);
        System.out.println("Current Letter: " + currentLetter);
        System.out.println("Current morse string: " + currentMorse);
        MorsePlayer.playMorseString(currentMorse);
    }

    public static void generateAlphabetList() {
        alphabetList.addAll(textToMorseMap.keySet());
        alphabetList.remove(26);
      //  System.out.println(alphabetList.toString());
    }

    private static Character selectRandomLetter() {
        if (alphabetList.isEmpty()) {
            generateAlphabetList();
        }
        char letter = alphabetList.get(randGen.nextInt(alphabetList.size()));
        for (int i = 0; i < alphabetList.size(); i ++) {
            if (alphabetList.get(i).equals(letter)) {
                alphabetList.remove(i);
            }
        }
        return letter;
    }
}
