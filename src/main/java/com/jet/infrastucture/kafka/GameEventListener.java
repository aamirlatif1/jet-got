package com.jet.infrastucture.kafka;

import com.jet.common.event.GameEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GameEventListener {

    private final SimpMessagingTemplate messagingTemplate;

    @KafkaListener(topics = "game-events", groupId = "game-of-three-group")
    public void handleGameStarted(GameEvent event) {
        System.out.println("Game Started: " + event);
        messagingTemplate.convertAndSend("/topic/game-status",  event);
    }

}
