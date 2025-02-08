package com.jet.player.service;

import com.jet.player.entity.Player;
import com.jet.player.entity.Status;
import com.jet.player.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlayerService {

    private final PlayerRepository repository;

    public void savePlayer(Player player) {
        player.setStatus(Status.ONLINE);
        repository.save(player);
    }

    public void disconnectPlayer(Player player) {
        var storedPlayer = repository.findById(player.getUsername()).orElse(null);
        if (storedPlayer != null) {
            storedPlayer.setStatus(Status.OFFLINE);
            repository.save(storedPlayer);
        }
    }

    public List<Player> getAllPlayer() {
        return repository.findAll();
    }
}
