package com.jet.common.event;

public interface DomainEvent<T> {
    void fire();
}