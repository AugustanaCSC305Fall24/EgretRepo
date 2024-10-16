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

    // When a message is received
    @OnMessage
    public void onMessage(String message) {
        System.out.println("Received message: " + message);
    }

    // Send a message to the WebSocket
    public void sendMessage(String message) throws Exception {
        session.getBasicRemote().sendText(message);
    }

    // Method to connect to the WebSocket server using Tyrus explicitly
    public void connectWebSocket() throws Exception {
        ClientManager client = ClientManager.createClient();

        // Create a default ClientEndpointConfig
        ClientEndpointConfig config = ClientEndpointConfig.Builder.create()
                .encoders(Collections.emptyList())  // Add your encoders here if any
                .decoders(Collections.emptyList())  // Add your decoders here if any
                .build();


        URI uri = URI.create("ws://127.0.0.1:8000/ws");
        client.connectToServer(this, uri);
    }
}
