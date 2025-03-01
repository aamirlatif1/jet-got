package com.jet.infrastructure.kafka.consumer;


import com.jet.infrastructure.kafka.model.GameChangedModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class GameChangedKafkaListener implements KafkaConsumer<GameChangedModel>{

    private final SimpMessagingTemplate messagingTemplate;

    @Override
    @KafkaListener(groupId = "game-of-three-group",
            topics = "${game.game-changed-topic-name}")
    public void receive(
            @Payload List<GameChangedModel> messages,
            @Header(KafkaHeaders.RECEIVED_KEY) List<String> keys,
            @Header(KafkaHeaders.RECEIVED_PARTITION) List<Integer> partitions,
            @Header(KafkaHeaders.OFFSET) List<Long> offsets
    ) {
        log.info("{} number of game changed request received with keys:{}, partitions:{} and offsets: {}",
                messages.size(),
                keys.toString(),
                partitions.toString(),
                offsets.toString());

        messages.forEach(message -> {
            log.info("Game changed: " + message.playerId());
            messagingTemplate.convertAndSend("/topic/game-status",  message);
        });

    }
}
