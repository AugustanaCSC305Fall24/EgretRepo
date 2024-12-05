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
    public void onMessage(String message) throws InterruptedException {
        System.out.println("Received message: " + message);
        HamRadioServerClient.handleReceivedMessage(message);
    }

    public void connect(String serverId, String userId) {
        try {
            // Construct the WebSocket URI using the server ID and user ID as a query parameter
            String wsUri = HamRadioServerClient.getServerURL() + serverId;
            String fullUri = String.format("ws://"  + HamRadioServerClient.getServerURL() + ":8000/ws/%s?user_id=%s", serverId, userId);

            // Create a WebSocket container
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();

            // Connect to the WebSocket server
            session = container.connectToServer(this, new URI(fullUri));
            System.out.println("Connected to WebSocket server: " + wsUri + " as user " + userId);

        } catch (Exception e) {
            System.err.println("Failed to connect to WebSocket server");
            e.printStackTrace();
        }
    }

    // Send a message to the WebSocket
    public void sendMessage(String message) throws Exception {
        if (HamRadioServerClient.isConnected) {
            if (session != null && session.isOpen()) {
                session.getBasicRemote().sendText(message);
            } else {
                System.out.println("No open WebSocket session to send message.");
            }
        }
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
