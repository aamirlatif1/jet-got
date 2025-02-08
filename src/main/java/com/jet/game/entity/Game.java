package com.jet.game.entity;

import com.jet.connon.exception.DomainException;
import com.jet.game.valueobject.Move;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "games")
public class Game {
    @Id
    private String id;
    private String currentPlayer;
    private int currentNumber;
    private boolean finished;
    private final List<Move> moveHistory = new ArrayList<>();

    public Game(String id, String player, int startNumber) {
        this.id = id;
        this.currentPlayer = player;
        this.currentNumber = startNumber;
        this.finished = false;
    }

    public void makeMove(String playerId) {
        if (finished) {
            throw new DomainException("Game is already finished");
        }

        int addedValue = calculateBestMove();
        int newNumber = (currentNumber + addedValue) / 3;

        moveHistory.add(new Move(currentNumber, addedValue, newNumber, playerId));
        this.currentNumber = newNumber;
        this.currentPlayer = playerId;

        if (newNumber == 1) {
            finished = true;
        }
    }

    private int calculateBestMove() {
        if (currentNumber % 3 == 0) return 0;
        return (currentNumber % 3 == 1) ? -1 : 1;
    }
}
