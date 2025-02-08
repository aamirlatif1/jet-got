package com.jet.game.service;

import com.jet.connon.exception.DomainException;
import com.jet.connon.dto.GameMoveRequest;
import com.jet.connon.dto.GameStartRequest;
import com.jet.game.entity.Game;
import com.jet.game.repository.GameRepository;
import com.jet.player.entity.Player;
import com.jet.player.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GameService {

    private final GameRepository gameRepository;
    private final PlayerRepository playerRepository;

    public Game startGame(GameStartRequest request) {
        Optional<Player> startingPlayOp = playerRepository.findById(request.getStartingPlayer());
        if (startingPlayOp.isEmpty()) {
            throw new DomainException("starting player dose not exist: " + request.getStartingPlayer());
        }
        Optional<Player> againstPlayOp = playerRepository.findById(request.getAgainstPlayer());
        if (againstPlayOp.isEmpty()) {
            throw new DomainException("against player dose not exist: " + request.getAgainstPlayer());
        }

        Game game = new Game(request.getStartingPlayer(), request.getAgainstPlayer(), request.getStartingNumber());
        gameRepository.save(game);
        return game;
    }

    public Game makeMove(GameMoveRequest request) {
        Optional<Player> startingPlayOp = playerRepository.findById(request.getPlayerId());
        if (startingPlayOp.isEmpty()) {
            throw new DomainException("player dose not exist");
        }

        Optional<Game> gameOpt = gameRepository.findById(request.getGameId());
        if (gameOpt.isEmpty()) {
            throw new DomainException("Game not found");
        }

        Game game = gameOpt.get();
        game.makeMove(request.getPlayerId());
        gameRepository.save(game);
        return game;
    }

}
