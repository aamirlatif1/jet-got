package com.jet.common.event;

public record GameEvent(
        int newNumber,
        String playerId
){ }
