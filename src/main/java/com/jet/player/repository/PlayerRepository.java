package com.jet.player.repository;

import com.jet.player.entity.Player;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface PlayerRepository extends MongoRepository<Player, String> {

    List<Player> findAll();
    Optional<Player> findById(String id);

}
