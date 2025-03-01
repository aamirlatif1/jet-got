package com.jet.infrastructure.kafka.model;

public record GameChangedModel(
        int newNumber,
        String playerId
) {
}
