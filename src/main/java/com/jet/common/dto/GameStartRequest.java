package com.jet.common.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameStartRequest {
    private String startingPlayer;
    private String againstPlayer;
    private int startingNumber;
}
