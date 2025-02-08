package com.jet.game.service;

import com.jet.connon.exception.DomainException;
import com.jet.game.entity.Game;
import com.jet.game.repository.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GameService {

    private final GameRepository gameRepository;

    public Game startGame(int startNumber, String playerId) {
        Game game = new Game(UUID.randomUUID().toString(), playerId, startNumber);
        gameRepository.save(game);
        return game;
    }

    public Game makeMove(String gameId, String playerId) {
        Optional<Game> gameOpt = gameRepository.findById(gameId);
        if (gameOpt.isEmpty()) {
            throw new DomainException("Game not found");
        }

        Game game = gameOpt.get();
        game.makeMove(playerId);
        gameRepository.save(game);
        return game;
    }

}
