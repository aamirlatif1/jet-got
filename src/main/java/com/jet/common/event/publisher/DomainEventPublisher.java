package com.jet.common.event.publisher;

import com.jet.common.event.DomainEvent;

public interface DomainEventPublisher<T extends DomainEvent> {

    void publish(T domainEvent);
}
