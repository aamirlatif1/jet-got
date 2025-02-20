package com.jet.player.service;

import com.jet.common.dto.PlayerRequest;
import com.jet.common.event.PlayerChangedEvent;
import com.jet.infrastucture.kafka.publisher.PlayerChangeMessagePublisher;
import com.jet.player.entity.Player;
import com.jet.player.valueobject.Status;
import com.jet.player.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlayerService {

    private final PlayerRepository repository;
    private final PlayerChangeMessagePublisher messagePublisher;

    public Player connectPlayer(PlayerRequest request) {
        Player player = new Player();
        player.setId(request.username());
        player.setFullName(request.fullName());
        player.setStatus(Status.ONLINE);
        Player savedPlayer = repository.save(player);

        PlayerChangedEvent event = new PlayerChangedEvent(savedPlayer, messagePublisher);
        event.fire();

        return savedPlayer;
    }

    public void disconnectPlayer(String username) {
        var storedPlayer = repository.findById(username).orElse(null);
        if (storedPlayer != null) {
            storedPlayer.setStatus(Status.OFFLINE);
            repository.save(storedPlayer);
            PlayerChangedEvent event = new PlayerChangedEvent(storedPlayer, messagePublisher);
            event.fire();

        }
    }

    public List<Player> getConnectedPlayers() {
        return repository.findAllByStatus(Status.ONLINE);
    }

    public List<Player> getAllPlayer() {
        return repository.findAll();
    }
}
