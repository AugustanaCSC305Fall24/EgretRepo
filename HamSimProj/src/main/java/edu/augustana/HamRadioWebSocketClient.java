package edu.augustana;

import org.glassfish.tyrus.client.ClientManager;
import javax.websocket.*;
import java.net.URI;
import java.util.Collections;

@ClientEndpoint
public class HamRadioWebSocketClient {

    private Session session;

    // When WebSocket connection is opened
    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        System.out.println("Connected to WebSocket server.");
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        System.out.println("WebSocket closed: " + closeReason);
    }

    // When a message is received
    @OnMessage
    public void onMessage(String message) {
        System.out.println("Received message: " + message);
    }

    // Send a message to the WebSocket
    public void sendMessage(String message) throws Exception {
        if (session != null && session.isOpen()) {
            session.getBasicRemote().sendText(message);
        } else {
            System.out.println("No open WebSocket session to send message.");
        }
    }

    // Method to connect to the WebSocket server using Tyrus explicitly
    public void connectWebSocket(String serverId) throws Exception {
        ClientManager client = ClientManager.createClient();

        // Create a default ClientEndpointConfig
        ClientEndpointConfig config = ClientEndpointConfig.Builder.create()
                .encoders(Collections.emptyList())  // Add your encoders here if any
                .decoders(Collections.emptyList())  // Add your decoders here if any
                .build();

        // Pass the serverId as part of the WebSocket URI
        URI uri = URI.create("ws://127.0.0.1:8000/ws/" + serverId);
        client.connectToServer(this, uri);
    }


    // Method to disconnect from the WebSocket server
    public void disconnectWebSocket() throws Exception {
        if (session != null && session.isOpen()) {
            session.close(new CloseReason(CloseReason.CloseCodes.NORMAL_CLOSURE, "Client disconnect"));
            System.out.println("Disconnected from WebSocket server.");
        } else {
            System.out.println("No open WebSocket session to close.");
        }
    }

}
