package com.jet.infrastucture.kafka;

import com.jet.game.event.GameEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GameEventListener {


    @KafkaListener(topics = "game-events", groupId = "game-of-three-group")
    public void handleGameStarted(GameEvent event) {
        System.out.println("Game Started: " + event);
    }

}
