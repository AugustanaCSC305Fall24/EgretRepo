package edu.augustana;


import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

public class HamRadioServerClient {

    private static ArrayList<String> serverList;

    private static final String API_URL = "http://127.0.0.1:8000"; // Replace with your FastAPI server's URL
    private static final HttpClient httpClient = HttpClient.newHttpClient();
    private static HamRadioWebSocketClient socketClient = new HamRadioWebSocketClient();
    public static boolean isConnected = false;

    // Method to create a server by sending a POST request
    public static void createServer(String serverId, double noiseLevel, double signalStrength) throws Exception {
        // Construct the URL with query parameters
        String url = String.format("%s/server/%s?noise_level=%.2f&signal_strength=%.2f",
                API_URL, serverId, noiseLevel, signalStrength);

        // Create an HTTP POST request
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .POST(HttpRequest.BodyPublishers.noBody()) // No request body required for this API
                .build();

        // Send the request and capture the response
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        // Handle the response
        if (response.statusCode() != 200) {
            throw new RuntimeException("Failed to create server: " + response.body());
        }else{
            connectToSever(serverId);
        }
    }

    public static  Map<String, List<String>> getAvailableServers() {
        Map<String, List<String>> serverClientsMap = new HashMap<>();
        Gson gson = new Gson();

        try {
            // Step 1: Get the list of active servers
            HttpRequest serverRequest = HttpRequest.newBuilder()
                    .uri(new URI(API_URL + "/servers")) // Correct the URL for the /servers endpoint
                    .GET()
                    .build();
            HttpResponse<String> serverResponse = httpClient.send(serverRequest, HttpResponse.BodyHandlers.ofString());

            // Parse the response from FastAPI
            JsonObject jsonResponse = gson.fromJson(serverResponse.body(), JsonObject.class);
            JsonArray activeServersArray = jsonResponse.getAsJsonArray("active_servers");

            System.out.println(activeServersArray.toString());

            // Step 2: If there are no active servers, return an empty map
            if (activeServersArray == null || activeServersArray.size() == 0) {
                System.out.println("No servers available");
                return serverClientsMap;
            }

            // Step 3: Fetch clients for each active server
            for (int i = 0; i < activeServersArray.size(); i++) {
                String serverId = activeServersArray.get(i).getAsString();

                // Fetch the list of clients for the server
                HttpRequest clientRequest = HttpRequest.newBuilder()
                        .uri(new URI(API_URL + "/server/" + serverId + "/clients"))
                        .GET()
                        .build();
                HttpResponse<String> clientResponse = httpClient.send(clientRequest, HttpResponse.BodyHandlers.ofString());

                // Assuming the response returns a JSON object with the "clients" field containing the list of client IDs
                JsonObject clientResponseJson = gson.fromJson(clientResponse.body(), JsonObject.class);


                //It crashes here because it receives a one
               JsonArray clientsArray = clientResponseJson.getAsJsonArray("clients");

                // Convert the client list to a List<String>
                List<String> clients = gson.fromJson(clientsArray, new TypeToken<List<String>>() {}.getType());

                // Map the server ID to its list of clients
                serverClientsMap.put(serverId, clients);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return serverClientsMap;
    }

    // Method to retrieve server conditions
    public static void getServerConditions(String serverId) throws Exception {
        URL url = new URL(API_URL + serverId + "/conditions");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/json");

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }

        in.close();
        connection.disconnect();

        System.out.println("Server conditions: " + content.toString());
    }

    public static String getServerURL(){
        return API_URL;
    }

    public static void connectToSever(String serverId) throws Exception {
        isConnected = true;
       socketClient.connect(serverId);
    }

    public static void sendMessage(String message) throws Exception {
        String formattedMessage = String.valueOf(Radio.getSelectedTuneFreq()) + "," + String.valueOf(Radio.generateFrequencyRange(Radio.getBand())) + "," + message;
        socketClient.sendMessage(formattedMessage);
    }

    public static void disconnectServer() throws Exception {
        isConnected = false;
        socketClient.disconnectWebSocket();
    }

    public static void playOutMessage(String message) throws InterruptedException {
        String[] messageParts = message.split(",", 3);
        double frequency = Double.valueOf(messageParts[0]);
        double range = Double.valueOf(messageParts[1]);
        System.out.println(Arrays.toString(messageParts));
        String morseMessage = messageParts[2];

        MorsePlayer.playBotMorseString(morseMessage,frequency,range);

    }

    public static void handleMessageSend(){
        long lastTimePressed = System.currentTimeMillis();


    }

}
