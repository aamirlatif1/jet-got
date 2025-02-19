package com.jet.infrastucture.controller;

import com.jet.common.dto.GameMessage;
import com.jet.common.dto.PlayerAction;
import com.jet.common.dto.PlayerRequest;
import com.jet.player.entity.Player;
import com.jet.player.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PlayerController {

    private final PlayerService playerService;
    private final SimpMessagingTemplate simpMessagingTemplate;


    @MessageMapping("/player")
    public void addPlayer(PlayerRequest request, SimpMessageHeaderAccessor headerAccessor)  {
        Player newPlayer = playerService.connectPlayer(request);
        headerAccessor.getSessionAttributes().put("username", newPlayer.getId());

        var newMessage = new GameMessage(newPlayer.getId(), 0, PlayerAction.JOIN);
        simpMessagingTemplate.convertAndSend("/topic/messages", newMessage);
    }

    @PostMapping("/players")
    @ResponseStatus(HttpStatus.CREATED)
    public Player createPlayer(@RequestBody PlayerRequest request) {
        return playerService.connectPlayer(request);
    }

    @GetMapping("/players")
    public List<Player> getPlayers() {
        return playerService.getAllPlayer();
    }



}
