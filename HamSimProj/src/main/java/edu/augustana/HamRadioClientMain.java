package edu.augustana;

public class HamRadioClientMain {

    public static void main(String[] args) {
        try {
            // Create the server and set conditions
            HamRadioServerClient serverClient = new HamRadioServerClient();
            serverClient.createServer(0.5f, 1.0f); // noiseLevel = 0.5, signalStrength = 1.0

            // Connect to WebSocket and send/receive messages
            HamRadioWebSocketClient webSocketClient = new HamRadioWebSocketClient();
            webSocketClient.connectWebSocket();

            // Retrieve and display the server conditions
            serverClient.getServerConditions();

            // Send a message to the WebSocket server
            webSocketClient.sendMessage("Hello, this is a ham radio message!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
