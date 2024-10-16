from fastapi import FastAPI, WebSocket, WebSocketDisconnect
from typing import List

app = FastAPI()

# A class to manage WebSocket connections
class ConnectionManager:
    def __init__(self):
        self.active_connections: List[WebSocket] = []
        #This is a list that stores all active WebSocket connections.

    async def connect(self, websocket: WebSocket):
        await websocket.accept()  # Accept the WebSocket connection
        self.active_connections.append(websocket)
        #Purpose: When a client connects, this method adds their WebSocket connection to the active_connections list.
        #Code: await websocket.accept() is a FastAPI method to accept a new WebSocket connection, followed by appending the connection to the active list.

    def disconnect(self, websocket: WebSocket):
        self.active_connections.remove(websocket)
        #Removes a WebSocket connection from the active_connections list when a client disconnects.

    async def broadcast(self, message: str):
        for connection in self.active_connections:
            await connection.send_text(message)

manager = ConnectionManager()

# Define the WebSocket endpoint
@app.websocket("/ws")
async def websocket_endpoint(websocket: WebSocket):
    await manager.connect(websocket)
    try:
        while True:
            # Wait for a message from the client
            data = await websocket.receive_text()
            # Broadcast the message to all connected clients
            await manager.broadcast(f"Client: {data}")
    except WebSocketDisconnect:
        manager.disconnect(websocket)
        await manager.broadcast("A client disconnected")
