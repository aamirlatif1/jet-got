package com.jet.common.dto;

public record GameMessage(
        String username,
        int number,
        PlayerAction action
) {}
