from fastapi import FastAPI, WebSocket, WebSocketDisconnect, Query, HTTPException
from typing import List, Dict

app = FastAPI()

servers: Dict[str, Dict] = {}  # Dictionary to keep track of multiple servers


# Create a new server
@app.post("/server/{server_id}")
async def create_server(server_id: str, 
                        noise_level: float = Query(0.0, description="Noise level for simulation"),
                        signal_strength: float = Query(1.0, description="Signal strength for simulation")):
    if server_id in servers:
        raise HTTPException(status_code=400, detail="Server ID already exists")
    
    servers[server_id] = {
        "conditions": {
            "noise_level": noise_level,
            "signal_strength": signal_strength
        },
        "clients": []  # Each server will have its own list of WebSocket clients
    }
    return {"message": "Server created", "server_id": server_id, "conditions": servers[server_id]["conditions"]}


# Retrieve conditions for a specific server
@app.get("/server/{server_id}/conditions")
async def get_server_conditions(server_id: str):
    if server_id not in servers:
        raise HTTPException(status_code=404, detail="Server not found")
    
    return {"conditions": servers[server_id]["conditions"]}


# WebSocket endpoint for a specific server
@app.websocket("/ws/{server_id}")
@app.websocket("/ws/{server_id}")
async def websocket_endpoint(websocket: WebSocket, server_id: str):
    if server_id not in servers:
        await websocket.close(code=1008)
        return
    
    await websocket.accept()
    servers[server_id]["clients"].append(websocket)
    print(f"Client connected to server {server_id}")
    
    try:
        while True:
            data = await websocket.receive_text()
            print(f"Received message: {data}")  # Log received messages
            for client in servers[server_id]["clients"]:
                await client.send_text(data)
    except WebSocketDisconnect:
        servers[server_id]["clients"].remove(websocket)



# Optional: Endpoint to list all active servers
@app.get("/servers")
async def list_servers():
    return {"active_servers": list(servers.keys())}
