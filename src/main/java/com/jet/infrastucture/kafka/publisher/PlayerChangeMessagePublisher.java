package com.jet.infrastucture.kafka.publisher;

import com.jet.common.event.PlayerChangedEvent;
import com.jet.common.event.publisher.DomainEventPublisher;
import com.jet.infrastucture.kafka.producer.KafkaMessageHelper;
import com.jet.infrastucture.kafka.producer.KafkaProducer;
import com.jet.player.entity.Player;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PlayerChangeMessagePublisher implements DomainEventPublisher<PlayerChangedEvent> {

    private final KafkaProducer<String, PlayerChangedEvent> kafkaTemplate;
    private final KafkaMessageHelper orderKafkaMessageHelper;

    @Override
    public void publish(PlayerChangedEvent domainEvent) {
        var playerId = domainEvent.getPlayer().getId();
        kafkaTemplate.send("player-events", playerId, domainEvent, orderKafkaMessageHelper.callback());
    }
}
