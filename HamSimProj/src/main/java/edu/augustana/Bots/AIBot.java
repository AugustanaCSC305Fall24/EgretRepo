package edu.augustana.Bots;

import swiss.ameri.gemini.api.*;
import swiss.ameri.gemini.spi.JsonParser;
import swiss.ameri.gemini.gson.GsonJsonParser;


public class AIBot extends Bot{

    private String systemPromptText;
    private JsonParser parser;
    GenAi genAi;

    public AIBot(int band, String name, String callSign, String systemPromptText) {
        super(band);
        setName(name);
        setTextCallSign(callSign);
        this.systemPromptText = systemPromptText;
        this.genAi = new GenAi("AIzaSyBdtDb5rL_wP8aXegcVpRo-bZIWjalQNQw", new GsonJsonParser());
    }

    public void requestMessage(String message){
        String fullPrompt = systemPromptText + "\n" +
                "Your name is: " + getName() + "\n" +
                "You are the chat bot. Act like a person talking through a HAM radio. Respond to this message.\n"
                + message;

        var model = createBotModel(fullPrompt);

        genAi.generateContent(model)
                .thenAccept(gcr -> {
                    String geminiResponse = gcr.text();
                    System.out.println("Debug: AIBot received response: " + geminiResponse);
                });

    }
    private GenerativeModel createBotModel(String fullPrompt) {
        return GenerativeModel.builder()
                .modelName(ModelVariant.GEMINI_1_5_FLASH)
                .addContent(Content.textContent(
                        Content.Role.USER,
                        fullPrompt
                ))
                .addSafetySetting(SafetySetting.of(
                        SafetySetting.HarmCategory.HARM_CATEGORY_DANGEROUS_CONTENT,
                        SafetySetting.HarmBlockThreshold.BLOCK_ONLY_HIGH
                ))
                .generationConfig(new GenerationConfig(
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null
                ))
                .build();
    }

    @Override
    public void playSound() {

    }

    @Override
    public void stopSound() {

    }

    @Override
    public String toString() {
        return "";
    }

    public static void main(String[] args) {
        AIBot aiBot = new AIBot(1, "AI Bot", "AI", "System Prompt");
        aiBot.requestMessage("");
        try {
            // Wait for the asynchronous task to complete
            Thread.sleep(5000); // Adjust the duration as needed
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
