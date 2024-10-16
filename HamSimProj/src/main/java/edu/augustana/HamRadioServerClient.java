package edu.augustana;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HamRadioServerClient {

    // Method to create a server by sending a POST request
    public void createServer(float noiseLevel, float signalStrength) throws Exception {
        URL url = new URL("http://localhost:8000/server?noise_level=" + noiseLevel + "&signal_strength=" + signalStrength);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        // You can send additional data if needed
        OutputStream os = connection.getOutputStream();
        os.write("{}".getBytes()); // Empty JSON body
        os.flush();
        os.close();

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            System.out.println("Server created successfully.");
        } else {
            System.out.println("Failed to create server. Response code: " + responseCode);
        }
    }

    // Method to retrieve server conditions
    public void getServerConditions() throws Exception {
        URL url = new URL("http://localhost:8000/server/conditions");
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

