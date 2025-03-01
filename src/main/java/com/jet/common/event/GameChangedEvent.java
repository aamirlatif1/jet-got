package com.jet.common.event;

import com.jet.common.event.publisher.DomainEventPublisher;
import com.jet.game.entity.Game;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class GameChangedEvent implements DomainEvent<Game>{
    private final Game game;
    private final DomainEventPublisher<GameChangedEvent> publisher;

    @Override
    public void fire() {
        publisher.publish(this);
    }
}
