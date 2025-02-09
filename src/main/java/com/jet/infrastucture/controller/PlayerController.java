package com.jet.infrastucture.controller;

import com.jet.common.dto.PlayerRequest;
import com.jet.player.entity.Player;
import com.jet.player.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

@RestController
@RequestMapping("/players")
@RequiredArgsConstructor
public class PlayerController {

    public record Greeting(String content) {}
    public record Message(String name) {}

    private final PlayerService playerService;

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(Message message) {
        return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.name()) + "!");
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Player createPlayer(@RequestBody PlayerRequest request) {
        return playerService.savePlayer(request);
    }

    @GetMapping("")
    public List<Player> getPlayers() {
        return playerService.getAllPlayer();
    }

}
