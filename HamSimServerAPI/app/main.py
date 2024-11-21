from fastapi import FastAPI, WebSocket, WebSocketDisconnect, Query, HTTPException
from typing import List, Dict
import uuid  # For generating unique client IDs
app = FastAPI()

# Dictionary to keep track of multiple servers
servers: Dict[str, Dict] = {}


# Endpoint to create a new server
@app.post("/server/{server_id}")
async def create_server(
    server_id: str,
    noise_level: float = Query(0.0, description="Noise level for simulation"),
    signal_strength: float = Query(1.0, description="Signal strength for simulation")
):
    if server_id in servers:
        raise HTTPException(status_code=400, detail="Server ID already exists")
    
    servers[server_id] = {
        "conditions": {
            "noise_level": noise_level,
            "signal_strength": signal_strength,
        },
        "clients": []  # WebSocket clients connected to this server
    }
    return {"message": "Server created", "server_id": server_id, "conditions": servers[server_id]["conditions"]}


# Endpoint to list all available servers
@app.get("/servers")
async def list_servers():
    if not servers:
        return {"message": "No servers available. You can create one.", "active_servers": []}
    return {"active_servers": list(servers.keys())}

@app.get("/server/{server_id}/clients")
async def get_server_clients(server_id: str):
    if server_id not in servers:
        raise HTTPException(status_code=404, detail="Server not found")
    
    # Return a list of client IDs
    client_ids = [client["id"] for client in servers[server_id]["clients"]]
    return {"clients": client_ids}

# WebSocket endpoint for clients to connect to a specific server
@app.websocket("/ws/{server_id}")
async def websocket_endpoint(websocket: WebSocket, server_id: str):
    if server_id not in servers:
        await websocket.close(code=1008)
        return

    await websocket.accept()
    client_id = str(uuid.uuid4())  # Generate a unique ID for the client
    servers[server_id]["clients"].append({"id": client_id, "websocket": websocket})
    print(f"Client {client_id} connected to server {server_id}")

    try:
        while True:
            data = await websocket.receive_text()
            print(f"Received message: {data}")  # Log received messages
            # Broadcast the message to all connected clients
            for client in servers[server_id]["clients"]:
                if client["websocket"] != websocket:
                    await client["websocket"].send_text(data)
    except WebSocketDisconnect:
        servers[server_id]["clients"] = [
            client for client in servers[server_id]["clients"] if client["websocket"] != websocket
        ]
        print(f"Client {client_id} disconnected from server {server_id}")

# Optional: Helper endpoint to retrieve conditions for a specific server
@app.get("/server/{server_id}/conditions")
async def get_server_conditions(server_id: str):
    if server_id not in servers:
        raise HTTPException(status_code=404, detail="Server not found")
    return {"conditions": servers[server_id]["conditions"]}
