package com.jet.player;

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
    public Player createPlayer(@RequestBody Player player) {
        playerService.savePlayer(player);
        return player;
    }

    @GetMapping("")
    public List<Player> getPlayers() {
        return playerService.getAllPlayer();
    }

}
