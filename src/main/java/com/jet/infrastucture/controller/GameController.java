package com.jet.infrastucture.controller;

import com.jet.game.entity.Game;
import com.jet.game.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/games")
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;

    @GetMapping("/player/{playerId}/number/{startNumber}")
    public Game startGame(
            @PathVariable("playerId") String playerId,
            @PathVariable("startNumber") int startNumber) {
        return gameService.startGame(startNumber, playerId);
    }

    @GetMapping("/game/{gameId}/player/{playerId}")
    public Game makeMove(
            @PathVariable("gameId") String gameId,
            @PathVariable("playerId") String playerId
    ) {
        return gameService.makeMove(gameId, playerId);
    }

}
