package com.jet.player.repository;

import com.jet.player.entity.Player;
import com.jet.player.entity.Status;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PlayerRepository extends MongoRepository<Player, String> {

    List<Player> findAll();

}
