'use strict';

const app = "/app/hello";
let stompClient = null;

let buttonConnect = null;
let buttonDisConnect = null;
let buttonSend = null;
let greetings = null;
let formInput = null;
let nameInput = null;
let conversationDisplay = null;

function connect (event) {
    setConnected(true);
    console.log('Connected: ' + event);
    const socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, onConnected, onError);
    console.log('Connected');
}

function setConnected(connected) {
    buttonConnect.disabled = connected;
    buttonDisConnect.disabled = !connected;
    if (connected) {
        conversationDisplay.style.display = "block";
    }
    else {
        conversationDisplay.style.display = "none";
    }
    greetings.innerHTML = "";
}

function onError(error) {
    console.error('Error with websocket', error);
}


function onConnected(connected) {
    stompClient.subscribe(`/topic/greetings`, showGreeting);
    buttonConnect.disabled = connected;
    buttonDisConnect.disabled = !connected;
    if (connected) {
        conversationDisplay.style.display = "block";
    }
    else {
        conversationDisplay.style.display = "none";
    }
    greetings.innerHTML = "";
}

function disconnect() {
    stompClient.disconnect();
    setConnected(false);
    console.log("Disconnected");
}

function sendName() {
    stompClient.send(app,
        {},
        JSON.stringify({'name': nameInput.value})
    );
}

function showGreeting(payload) {
    console.log(payload)
    const message = JSON.parse(payload.body);
    greetings.innerHTML += "<tr><td>" + message.content + "</td></tr>";
}

document.addEventListener("DOMContentLoaded", function() {
    buttonConnect = document.getElementById("connect");
    buttonDisConnect = document.getElementById("disconnect");
    buttonSend = document.getElementById("send");
    conversationDisplay = document.getElementById("conversation");
    greetings = document.getElementById("greetings");
    formInput = document.getElementById("form");
    nameInput = document.getElementById("name");
    buttonConnect.addEventListener("click", (e) => {
        connect();
        e.preventDefault();});
    buttonDisConnect.addEventListener("click", (e) => {
        disconnect();
        e.preventDefault();});
    buttonSend.addEventListener("click", (e) => {
        sendName();
        e.preventDefault();});
    formInput.addEventListener("submit", (e) => e.preventDefault());
    setConnected(false);
});