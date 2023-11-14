const io = require("socket.io-client");

const socket = io("http://localhost:8800", {
    reconnection: true,
});

socket.on("connect", () => {
    console.log("Connected to the server");
});

socket.on("disconnect", () => {
    console.log("Disconnected from the server");
});

// Emitir el evento 'new-user-add' al servidor
socket.emit("new-user-add", "userID123");

// Escuchar el evento 'get-users' del servidor
socket.on("get-users", (activeUsers) => {
    console.log("Active Users:", activeUsers);
});

// Emitir el evento 'send-message' al servidor
socket.emit("send-message", {
    receiverId: "targetUserID",
    message: "Hello, this is a test message",
});

// Escuchar el evento 'recieve-message' del servidor
socket.on("recieve-message", (data) => {
    console.log("Received message:", data);
});
