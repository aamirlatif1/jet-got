package com.jet.infrastucture.controller;

import com.jet.connon.dto.GameMoveRequest;
import com.jet.connon.dto.GameStartRequest;
import com.jet.game.entity.Game;
import com.jet.game.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/games")
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;

    @PostMapping("/start")
    public Game startGame(
            @RequestBody GameStartRequest request) {
        return gameService.startGame(request);
    }

    @PostMapping("/turn")
    public Game makeMove(
            @RequestBody GameMoveRequest request
    ) {
        return gameService.makeMove(request);
    }

}
