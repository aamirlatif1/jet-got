package com.jet.player.repository;

import com.jet.player.entity.Player;
import com.jet.player.valueobject.Status;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface PlayerRepository extends MongoRepository<Player, String> {

    List<Player> findAll();
    List<Player> findAllByStatus(Status status);
    Optional<Player> findById(String id);


}
