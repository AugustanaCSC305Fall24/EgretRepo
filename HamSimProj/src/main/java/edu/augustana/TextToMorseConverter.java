package edu.augustana;

import java.util.HashMap;
import java.util.Map;



public class TextToMorseConverter {
    // Map to hold the Morse code mappings
    private static final Map<Character, String> morseCodeMap = new HashMap<>();

    static {
        morseCodeMap.put('A', ".-");
        morseCodeMap.put('B', "-...");
        morseCodeMap.put('C', "-.-.");
        morseCodeMap.put('D', "-..");
        morseCodeMap.put('E', ".");
        morseCodeMap.put('F', "..-.");
        morseCodeMap.put('G', "--.");
        morseCodeMap.put('H', "....");
        morseCodeMap.put('I', "..");
        morseCodeMap.put('J', ".---");
        morseCodeMap.put('K', "-.-");
        morseCodeMap.put('L', ".-..");
        morseCodeMap.put('M', "--");
        morseCodeMap.put('N', "-.");
        morseCodeMap.put('O', "---");
        morseCodeMap.put('P', ".--.");
        morseCodeMap.put('Q', "--.-");
        morseCodeMap.put('R', ".-.");
        morseCodeMap.put('S', "...");
        morseCodeMap.put('T', "-");
        morseCodeMap.put('U', "..-");
        morseCodeMap.put('V', "...-");
        morseCodeMap.put('W', ".--");
        morseCodeMap.put('X', "-..-");
        morseCodeMap.put('Y', "-.--");
        morseCodeMap.put('Z', "--..");
        morseCodeMap.put('1', ".----");
        morseCodeMap.put('2', "..---");
        morseCodeMap.put('3', "...--");
        morseCodeMap.put('4', "....-");
        morseCodeMap.put('5', ".....");
        morseCodeMap.put('6', "-....");
        morseCodeMap.put('7', "--...");
        morseCodeMap.put('8', "---..");
        morseCodeMap.put('9', "----.");
        morseCodeMap.put('0', "-----");
        morseCodeMap.put(' ', "/"); // Space separator in Morse code
    }

    // Method to convert text to Morse code
    public static String textToMorse(String text) {
        StringBuilder morseCode = new StringBuilder();

        for (char c : text.toUpperCase().toCharArray()) {
            String morseChar = morseCodeMap.get(c);
            if (morseChar != null) {
                morseCode.append(morseChar).append(" ");
            } else {
                // Handle characters not in Morse code map
                morseCode.append("? ");
            }
        }

        return morseCode.toString().trim();
    }

    // Main method for testing
    public static void main(String[] args) {
        String text = "HELLO WORLD";
        String morse = textToMorse(text);
        System.out.println("Text: " + text);
        System.out.println("Morse Code: " + morse);
    }
}
