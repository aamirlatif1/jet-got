package com.jet.infrastucture.websocket;

import com.jet.common.dto.GameMessage;
import com.jet.common.dto.PlayerAction;
import com.jet.player.service.PlayerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.context.event.EventListener;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketEventListener {

    private final PlayerService playerService;
    private final SimpMessageSendingOperations messageTemplate;

    @EventListener
    public void handleWebSocketDisconnect(SessionDisconnectEvent event) {
        log.info("Received session disconnect event: {}", event);
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        String username = (String) accessor.getSessionAttributes().get("username");
        if (username != null) {
            log.info("player disconnected: {}", username);
            var gameMessage = new GameMessage(username, 0, PlayerAction.LEAVE);
            playerService.disconnectPlayer(username);
            messageTemplate.convertAndSend("/topic/messages", gameMessage);
        }
    }

    @EventListener
    public void handleSessionConnectEvent(SessionConnectEvent event) {
        System.out.println("session connect event");
    }

}