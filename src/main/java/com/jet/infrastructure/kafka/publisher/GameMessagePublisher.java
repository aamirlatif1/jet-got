package com.jet.infrastructure.kafka.publisher;

import com.jet.common.event.GameChangedEvent;
import com.jet.common.event.publisher.DomainEventPublisher;
import com.jet.infrastructure.kafka.model.GameChangedModel;
import com.jet.infrastructure.kafka.producer.KafkaMessageHelper;
import com.jet.infrastructure.kafka.producer.KafkaProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class GameMessagePublisher implements DomainEventPublisher<GameChangedEvent> {

    private final KafkaProducer<String, GameChangedModel> kafkaTemplate;
    private final KafkaMessageHelper kafkaMessageHelper;

    @Override
    public void publish(GameChangedEvent domainEvent) {
        var gameId = domainEvent.getGame().getId();
        var game = domainEvent.getGame();
        kafkaTemplate.send("game-events", gameId, new GameChangedModel(
                game.getCurrentNumber(), game.getPlayerTurn()
        ), kafkaMessageHelper.callback());

    }
}
