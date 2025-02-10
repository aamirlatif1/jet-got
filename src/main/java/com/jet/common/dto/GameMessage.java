package com.jet.common.dto;

public record GameMessage(
        String sender,
        int number,
        MessageType type
) {}
