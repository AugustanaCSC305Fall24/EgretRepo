package edu.augustana;
import java.util.Scanner;

public class HamRadioClientMain {



    public static void main(String[] args) {
        try {
            // Create the server and set conditions
//            HamRadioServerClient serverClient = new HamRadioServerClient();
//            serverClient.createServer("serv1", 0.5f, 1.0f); // noiseLevel = 0.5, signalStrength = 1.0

            // Connect to WebSocket and send/receive messages
            HamRadioWebSocketClient webSocketClient = new HamRadioWebSocketClient();
            webSocketClient.connectWebSocket("serv1");

            // Retrieve and display the server conditions
//            serverClient.getServerConditions("serv1");

            // Initialize scanner to capture user input
            Scanner scanner = new Scanner(System.in);
            String input;

            System.out.println("Type messages to send to the WebSocket server (type 'exit' to quit):");

            // Keep the connection alive and allow sending multiple messages
            while (true) {
                System.out.print("Message: ");
                input = scanner.nextLine();

                // Exit the loop if user types "exit"
                if (input.equalsIgnoreCase("exit")) {
                    webSocketClient.disconnectWebSocket();
                    break;
                }

                // Send the user input as a message
                webSocketClient.sendMessage(input);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
