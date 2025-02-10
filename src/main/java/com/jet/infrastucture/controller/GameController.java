package com.jet.infrastucture.controller;

import com.jet.common.dto.GameMoveRequest;
import com.jet.common.dto.GameStartRequest;
import com.jet.game.entity.Game;
import com.jet.game.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/games")
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;
    private final SimpMessagingTemplate simpMessagingTemplate;


    @MessageMapping("/gamemessage")
    public void gameMove(GameMoveRequest request) {
        GameStartRequest gameStartRequest = new GameStartRequest();
        gameStartRequest.setStartingPlayer(request.getPlayerId());
        gameStartRequest.setAgainstPlayer(request.getAgainstPlayerId());
        gameStartRequest.setStartingNumber(request.getNumber());
        Game game = gameService.startGame(gameStartRequest);
        simpMessagingTemplate.convertAndSendToUser(request.getPlayerId(), "/topic/gamemessages", game);
        simpMessagingTemplate.convertAndSendToUser(request.getAgainstPlayerId(), "/topic/gamemessages", game);

    }

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
