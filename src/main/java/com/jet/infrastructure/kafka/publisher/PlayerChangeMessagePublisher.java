package com.jet.infrastructure.kafka.publisher;

import com.jet.common.event.PlayerChangedEvent;
import com.jet.common.event.publisher.DomainEventPublisher;
import com.jet.infrastructure.kafka.model.PlayerChangeModel;
import com.jet.infrastructure.kafka.producer.KafkaMessageHelper;
import com.jet.infrastructure.kafka.producer.KafkaProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PlayerChangeMessagePublisher implements DomainEventPublisher<PlayerChangedEvent> {

    private final KafkaProducer<String, PlayerChangeModel> kafkaTemplate;
    private final KafkaMessageHelper orderKafkaMessageHelper;

    @Override
    public void publish(PlayerChangedEvent domainEvent) {
        var playerId = domainEvent.getPlayer().getId();
        kafkaTemplate.send("player-events", playerId, new PlayerChangeModel(domainEvent.getPlayer().getId()), orderKafkaMessageHelper.callback());
    }
}
