package edu.augustana;

import java.util.*;

public class CWFlashcards {

    private static HashMap<String, String> morseToTextMap = TextToMorseConverter.getMorseToTextMap();
    private static Map<String, String> textToMorseMap = TextToMorseConverter.getTextToMorseMap();
    private static List<String> alphabetList = new ArrayList<>();
    private static Random randGen = new Random();
    private static String currentLetter = "";
    private static String currentMorse;

    public static Boolean handleGuess(String guess) {
        if (Objects.equals(textToMorseMap.get(guess), currentMorse)) {
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
            letter = selectRandomLetter();
            currentLetter = letter;
        } else {
            letter = currentLetter;
        }
        currentMorse = TextToMorseConverter.textToMorse(letter);
        currentMorse = currentMorse.replace(' ', '/');
        System.out.println("Current Letter: " + currentLetter);
        System.out.println("Current morse string: " + currentMorse);
        MorsePlayer.playMorseString(currentMorse);
    }

    public static void generateAlphabetList() {
        addAbbrevToMap();
        alphabetList.addAll(textToMorseMap.keySet());
        alphabetList.remove(26);
      //  System.out.println(alphabetList.toString());
    }

    private static String selectRandomLetter() {
        if (alphabetList.isEmpty()) {
            generateAlphabetList();
        }
        String letter = alphabetList.get(randGen.nextInt(alphabetList.size()));
        for (int i = 0; i < alphabetList.size(); i ++) {
            if (alphabetList.get(i).equals(letter)) {
                alphabetList.remove(i);
            }
        }
        return letter;
    }

    private static void addAbbrevToMap() {
        textToMorseMap.put("AGN", ".-/--./-.");// A .-
        textToMorseMap.put("ANT", ".-/-./-");// B -...
        textToMorseMap.put("AS", ".-/...");// C -.-.
        textToMorseMap.put("BK", "-.../-.-");//D -..
        textToMorseMap.put("BTU", "-.../-/..-");//E .
        textToMorseMap.put("B4", "-.../....-");// "F", "..-."
        textToMorseMap.put("CFM", "-.-./..-./--");// "G", "--."
        textToMorseMap.put("CK", "-.-./-.-");// "H", "...."
        textToMorseMap.put("CL", "-.-./.-..");// "I", ".."
        textToMorseMap.put("CLG", "-.-./.-../--.");// "J", ".---"
        textToMorseMap.put("CX", "-.-./-..-");// "K", "-.-"
        textToMorseMap.put("COS", "-.-./---/..."); // "L", ".-.."
        textToMorseMap.put("CQ", "-.-./--.-"); // "M", "--"
        textToMorseMap.put("CS", "-.-./..."); // "N", "-."
        textToMorseMap.put("CUL", "-.-./..-/.-..");// "O", "---"
        textToMorseMap.put("CW", "-.-./.--"); // "P", ".--."
        textToMorseMap.put("DE", "-../."); //  "Q", "--.-"
        textToMorseMap.put("DX", "-../-..-");// "R", ".-."
        textToMorseMap.put("EMRG", "./--/.-./--."); // "S", "..."
        textToMorseMap.put("ES", "./..."); // "T", "-"
        textToMorseMap.put("FB", "..-./-..."); // "U", "..-"
        textToMorseMap.put("FREQ", "..-./.-././--.-"); // "V", "...-"
        textToMorseMap.put("GA", "--./.-");// "W", ".--"
        textToMorseMap.put("GG", "--./--.");// "X", "-..-"
        textToMorseMap.put("GM", "--./--"); // "Y", "-.--"
        textToMorseMap.put("GN", "--./-."); // "Z", "--.."
        textToMorseMap.put("GUD", "--./..-/-..");
        textToMorseMap.put("HEE", "...././.");
        textToMorseMap.put("II", "../..");
        textToMorseMap.put("KN", "-.-/-.");
        textToMorseMap.put("NIL", "-./../.-..");
        textToMorseMap.put("OK", "---/-.-");
        textToMorseMap.put("PLS", ".--./.-../...");
        textToMorseMap.put("PWR", ".--./.--/.-.");
        textToMorseMap.put("RFI", ".-./..-./..");
        textToMorseMap.put("RIG", ".-./../--.");
        textToMorseMap.put("RPT", ".-./.--./-");
        textToMorseMap.put("SIG", ".../../--.");
        textToMorseMap.put("SRI", ".../.-./..");
        textToMorseMap.put("TFC", "-/..-./-.-.");
        textToMorseMap.put("TKS", "-/-.-/...");
        textToMorseMap.put("SOS", ".../---/...");
    }
}
