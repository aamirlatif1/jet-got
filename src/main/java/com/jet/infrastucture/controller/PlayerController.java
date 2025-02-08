package com.jet.infrastucture.controller;

import com.jet.connon.dto.PlayerRequest;
import com.jet.player.entity.Player;
import com.jet.player.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/players")
@RequiredArgsConstructor
public class PlayerController {

    private final PlayerService playerService;

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
