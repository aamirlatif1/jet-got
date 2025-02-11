package com.jet.game.entity;

import com.jet.common.exception.DomainException;
import com.jet.game.valueobject.Move;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "games")
public class Game {
    @Id
    private String id;
    private String playerTurn;
    private int currentNumber;
    private int startNumber;
    private boolean finished;
    private String winner;
    private List<String> players;
    private List<Move> moveHistory;

    public Game(String startingPlayer, String againstPlayer, int startNumber) {
        this.id = UUID.randomUUID().toString();
        this.playerTurn = againstPlayer;
        this.currentNumber = startNumber;
        this.moveHistory = new ArrayList<>();
        this.players = List.of(startingPlayer, againstPlayer);
        this.startNumber = startNumber;
        this.finished = false;
    }

    public void makeMove(String playerId) {
        if (finished) {
            throw new DomainException("Game is already finished");
        }

        if (!playerTurn.equals(playerId)) {
            throw new DomainException("turn is of different player");
        }

        int addedValue = calculateBestMove();
        int newNumber = (currentNumber + addedValue) / 3;

        moveHistory.add(new Move(currentNumber, addedValue, newNumber, playerId));
        this.currentNumber = newNumber;
        int index = players.indexOf(playerId);

        this.playerTurn = players.get((index + 1) % 2);

        if (newNumber == 1) {
            finished = true;
            winner = playerId;
        }
    }

    private int calculateBestMove() {
        if (currentNumber % 3 == 0) return 0;
        return (currentNumber % 3 == 1) ? -1 : 1;
    }
}
