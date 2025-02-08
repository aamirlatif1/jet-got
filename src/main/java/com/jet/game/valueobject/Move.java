package com.jet.game.valueobject;

public record Move(
        int previousNumber,
        int addedValue,
        int newNumber,
        String playerId
) {
}
