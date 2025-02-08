package com.jet.connon.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameMoveRequest {
    private String gameId;
    private String playerId;
    private String number;
}
