package com.jet.common.event;


import com.jet.common.event.publisher.DomainEventPublisher;
import com.jet.player.entity.Player;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PlayerChangedEvent implements DomainEvent<Player> {
    private final Player player;
    private final DomainEventPublisher<PlayerChangedEvent> publisher;

    @Override
    public void fire() {
        publisher.publish(this);
    }
}

