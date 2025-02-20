package com.jet.infrastructure.kafka.consumer;

import com.jet.common.event.GameEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class GameEventListener {

    private final SimpMessagingTemplate messagingTemplate;


    @KafkaListener(topics = "game-events", groupId = "game-of-three-group")
    public void handleGameStarted(GameEvent event) {
        log.info("Game Started: " + event.playerId());
        messagingTemplate.convertAndSend("/topic/game-status",  event);
    }

}
