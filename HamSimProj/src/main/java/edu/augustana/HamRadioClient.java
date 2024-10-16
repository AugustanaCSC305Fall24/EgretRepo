package edu.augustana;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import javax.websocket.*;

@ClientEndpoint
public class HamRadioClient {

    private Session session;

    // Retrieve the server conditions
    public static void getServerConditions() throws Exception {
        URL url = new URL("http://localhost:8000/server/conditions");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            System.out.println("Server conditions: " + response.toString());
        } else {
            System.out.println("Failed to retrieve server conditions. Response code: " + responseCode);
        }
    }

    // Connect to the WebSocket server
    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        System.out.println("Connected to WebSocket server.");
    }

    // Receive messages from the server
    @OnMessage
    public void onMessage(String message) {
        // Convert text message to Morse code (CW)
        System.out.println("Received message: " + message);
    }

    // Send a message to the server
    public void sendMessage(String message) throws Exception {
        session.getBasicRemote().sendText(message);
    }

    // Create and join a server
    public static void main(String[] args) {
        try {
            // Get the current server conditions
            getServerConditions();

            // Connect to the WebSocket server
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            URI uri = URI.create("ws://localhost:8000/ws");
            container.connectToServer(HamRadioClient.class, uri);

            // Now the client can send and receive messages over the WebSocket
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

