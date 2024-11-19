package edu.augustana;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

public class HamRadioServerClient {

    private static ArrayList<String> serverList;

    private static final String API_URL = "http://127.0.0.1:8000"; // Replace with your FastAPI server's URL
    private static final HttpClient httpClient = HttpClient.newHttpClient();

    // Method to create a server by sending a POST request
    private static void createServer(String serverId, double noiseLevel, double signalStrength) throws Exception {
        String url = String.format("%s/server/%s?noise_level=%.2f&signal_strength=%.2f",
                API_URL, serverId, noiseLevel, signalStrength);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            throw new RuntimeException("Failed to create server: " + response.body());
        }
        System.out.println("Server created successfully: " + response.body());
    }

    private static String getAvailableServers() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL + "/servers"))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    // Method to retrieve server conditions
    public void getServerConditions(String serverId) throws Exception {
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
}
