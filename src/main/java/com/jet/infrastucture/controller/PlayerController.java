package com.jet.infrastucture.controller;

import com.jet.common.dto.GameMessage;
import com.jet.common.dto.MessageType;
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
    public void addPlayer(PlayerRequest request, SimpMessageHeaderAccessor headerAccessor) throws Exception {
        Player newPlayer = playerService.savePlayer(request);
        headerAccessor.getSessionAttributes().put("user", newPlayer);
        sendMembersList();
        var newMessage = new GameMessage(newPlayer.getId(), 0, MessageType.JOIN);
        simpMessagingTemplate.convertAndSend("/topic/messages", newMessage);
    }

    @PostMapping("/players")
    @ResponseStatus(HttpStatus.CREATED)
    public Player createPlayer(@RequestBody PlayerRequest request) {
        return playerService.savePlayer(request);
    }

    @GetMapping("/players")
    public List<Player> getPlayers() {
        return playerService.getAllPlayer();
    }

    private void sendMembersList() {
        List<Player> memberList = playerService.getAllPlayer();
        memberList.forEach(
                sendUser -> simpMessagingTemplate.convertAndSendToUser(sendUser.getId(), "/topic/players", filterMemberListByUser(memberList, sendUser)));
    }

    List<Player> filterMemberListByUser(List<Player> players, Player player) {
        return players.stream().filter(filterUser -> !filterUser.getId().equals(player.getId()))
                .toList();
    }

}
