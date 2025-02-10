package com.jet.common.event;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@Getter
public class GameEvent {
    private final int newNumber;
    private final String playerId;

    @JsonCreator
    public GameEvent(
            @JsonProperty("newNumber") int newNumber,
            @JsonProperty("playerId") String playerId) {
        this.newNumber = newNumber;
        this.playerId = playerId;
    }
}
