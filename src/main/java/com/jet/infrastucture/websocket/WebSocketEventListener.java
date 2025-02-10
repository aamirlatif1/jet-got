package com.jet.infrastucture.websocket;

import com.jet.common.dto.GameMessage;
import com.jet.common.dto.PlayerAction;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketEventListener {

    private final SimpMessageSendingOperations messageTemplate;

    public void handleWebSocketDisconnect(SessionDisconnectEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        String username = (String) accessor.getSessionAttributes().get("username");
        if (username != null) {
            log.info("player disconnected: {}", username);
            var gameMessage = new GameMessage(username, 0, PlayerAction.LEAVE);
            messageTemplate.convertAndSend("/topic/messages", gameMessage);
        }
    }

    public void handleSessionConnectEvent(SessionConnectEvent event) {
        System.out.println("session connect event");
    }

}