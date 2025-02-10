'use strict';

class Player {
    username;

    constructor(username) {
        this.username = username;
    }
}

class Message {
    gameId;
    playerId;
    againstPlayerId;
    number;
    action;

    constructor(gameId, playerId, againstPlayerId, number, action) {
        this.gameId = gameId;
        this.playerId = playerId
        this.againstPlayerId = againstPlayerId;
        this.number = number;
        this.action = action;
    }
}
let player = null;
let stompClient = null;
let userName = null;
let playersList = null;
let buttonConnect = null;
let buttonDisConnect = null;
let gameDisplay = null;
let buttonSend = null;
let online= null;
let messagesList = null;
let formInput = null;
let sendMessage = null;
let send = null;
let formSendMessage = null;
let inputSendMessage = null;
let messageList = null;
let membersList = null;
let messageLabel = null;
let membersListSelected = null;
let selectedMember = null;
let game = null;

document.addEventListener("DOMContentLoaded", function() {
    userName = document.getElementById("username");
    playersList = document.getElementById("playerslist");
    buttonConnect = document.getElementById("connect");
    buttonDisConnect = document.getElementById("disconnect");
    gameDisplay = document.getElementById("gameDisplay");
    buttonSend = document.getElementById("send");
    online = document.getElementById("online");
    messagesList = document.getElementById("messagesList");
    formInput = document.getElementById("form");
    sendMessage = document.getElementById("sendmessage");
    send = document.getElementById("send");
    formSendMessage = document.getElementById("formsendmessage");
    inputSendMessage = document.getElementById("inputsendmessage");
    messageList = document.getElementById("messagelist");
    membersList = document.getElementById("memberslist")
    messageLabel = document.getElementById("messagelabel");

    buttonConnect.addEventListener("click", (e) => {
        connect();
        e.preventDefault();
    });

    buttonDisConnect.addEventListener("click", (e) => {
        disconnect();
        e.preventDefault();
    });

    send.addEventListener("click", (e) => {
        sendGameMessages(selectedMember);
        e.preventDefault();
    });

    userName.addEventListener("keyup", () => {
        const userNameValue = userName.value;
        if(userNameValue.length == 0) {
            buttonConnect.disabled = true;
        } else {
            buttonConnect.disabled = false;
        }
    });

    formInput.addEventListener("submit", (e) => {
        setConnected(false);
        e.preventDefault()
    });

    formSendMessage.addEventListener("submit", (e) => {
        e.preventDefault()
    });
});

window.addEventListener("beforeunload" , () => {
    disconnect();
});

function connect () {
    const socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, onConnected, onError);

    buttonConnect.disabled = true;
    userName.disabled = true;
    console.log('Connected');
}

function disconnect() {
    stompClient.disconnect();

    buttonConnect.disabled = false;
    userName.disabled = false;
    setConnected(false);
    online.innerHTML = "";
    console.log('Disconnected');
}

function sendGameMessages(receiverId) {
    console.log('Send game message');
    let message = new Message(null, player.username, membersListSelected.textContent, inputSendMessage.value, 'NEW')
    stompClient.send(`/app/gamemessage`,
        {},
        JSON.stringify(message)
    );
}

function setConnected(connected) {
    buttonDisConnect.disabled = !connected;
    if (connected) {
        gameDisplay.style.display = "block";
        sendMessage.style.display = "block";
        sendMessage.style.visibility = "visible";
    }
    else {
        gameDisplay.style.display = "none";
        sendMessage.style.display = "none";
        sendMessage.style.visibility = "hidden";
    }
    messagesList.innerHTML = "";
    playersList.innerHTML = "";
}

function onError(error) {
    console.error('Error with websocket', error);
}


function onConnected() {
    setConnected(true);
    player = new Player(userName.value);
    online.innerHTML = "<p>" + player.username + " you are online!</p>";

    stompClient.subscribe('/player/' + player.username + '/topic/players', (playersList) => {
        showUsers(JSON.parse(playersList.body));
    });

    stompClient.subscribe('/player/' + player.username + '/topic/gamemessages', (gameMessage) => {
        showGameMovies(JSON.parse(gameMessage.body));
    });

    stompClient.subscribe('/topic/messages', (message) => {
        showMessagesList(JSON.parse(message.body));
    });

    stompClient.send(`/app/player`,
        {},
        JSON.stringify(player)
    );
}

function showMessagesList(message) {
    console.log(message)
    const date = new Date(message.timestamp);
    // if(message.action == 'NEW_MESSAGE' || message.action == 'COMMENTED') {
    //     messagesList.innerHTML += "<tr><td><div><h3>" + message.user.username + "</h3> " + message.action + " " +  date.toLocaleString("nl-BE") +  " - " + message.comment + "</div></td></tr>";
    // };
    if(message.action == 'JOIN' || message.action == 'LEFT') {
        messagesList.innerHTML += "<tr><td><div>" + message.username + " " + message.action + " " +  date.toLocaleString("nl-BE") + "</div></td></tr>";
    }
    updateScroll(messageList);
}

function showGameMovies(message) {
    console.log(message)
    game = message;
    // messagesList.innerHTML += "<tr><td><div class='private'><h3>" + message. + "</h3> " + message.action + " " +   " - " + message.comment + "</div></td></tr>";
    // updateScroll(messageList);
}

function showUsers(users) {
    console.log(users);
    playersList.innerHTML = "<li class='red' id='memberslistitem0'>All members</li>";
    messageLabel.innerHtml = "Send a public message:";
    users.forEach( connectedUser => {
        playersList.innerHTML += "<li class='black' id='memberslistitem" + connectedUser.id + "' >" + connectedUser.id + "</li>";
    });
    updateScroll(membersList);
    membersListSelected = document.getElementById('memberslistitem0');
    playersList.addEventListener("click", (e) => {
        membersListSelected.className = 'black';
        membersListSelected  = document.getElementById(e.target.id);
        if(membersListSelected.id === 'memberslistitem0') {
            messageLabel.innerHTML = "Send a public message:";
        } else {
            messageLabel.innerHTML = "Send a private message to: " + membersListSelected.textContent;
        }
        membersListSelected.className = 'red';
        selectedMember = membersListSelected.id.charAt(membersListSelected.id.length - 1);
    });

}

function updateScroll(element) {
    element.scrollTop = element.scrollHeight;
}

