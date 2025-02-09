package com.jet.player.service;

import com.jet.common.dto.PlayerRequest;
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

    public Player savePlayer(PlayerRequest request) {
        Player player = new Player();
        player.setId(request.getUsername());
        player.setFullName(request.getFullName());
        player.setStatus(Status.ONLINE);
        return repository.save(player);
    }

    public void disconnectPlayer(Player player) {
        var storedPlayer = repository.findById(player.getId()).orElse(null);
        if (storedPlayer != null) {
            storedPlayer.setStatus(Status.OFFLINE);
            repository.save(storedPlayer);
        }
    }

    public List<Player> getAllPlayer() {
        return repository.findAll();
    }
}
