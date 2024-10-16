from fastapi import FastAPI, WebSocket, WebSocketDisconnect, Query
from typing import List, Dict

app = FastAPI()

clients = []  # List to keep track of connected clients
server_conditions = {}  # Dictionary to store server conditions

@app.post("/server")
async def create_server(noise_level: float = Query(0.0, description="Noise level for simulation"),
                        signal_strength: float = Query(1.0, description="Signal strength for simulation")):
    """Create a ham radio server with user-specified conditions."""
    server_conditions["noise_level"] = noise_level
    server_conditions["signal_strength"] = signal_strength
    return {"message": "Server created", "conditions": server_conditions}

@app.get("/server/conditions")
async def get_server_conditions():
    """Retrieve the current server conditions."""
    if server_conditions:
        return {"conditions": server_conditions}
    else:
        return {"error": "No server conditions set"}

@app.websocket("/ws")
async def websocket_endpoint(websocket: WebSocket):
    await websocket.accept()
    clients.append(websocket)
    
    try:
        while True:
            data = await websocket.receive_text()
            # Broadcast received message to all clients
            for client in clients:
                if client != websocket:
                    await client.send_text(data)
    except WebSocketDisconnect:
        clients.remove(websocket)
