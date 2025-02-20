package com.jet.infrastucture.kafka.listener;

import com.jet.common.event.GameEvent;
import com.jet.common.event.PlayerChangedEvent;
import com.jet.player.entity.Player;
import com.jet.player.service.PlayerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class GameEventListener {

    private final SimpMessagingTemplate messagingTemplate;
    private final PlayerService playerService;

    @KafkaListener(topics = "game-events", groupId = "game-of-three-group")
    public void handleGameStarted(GameEvent event) {
        log.info("Game Started: " + event.playerId());
        messagingTemplate.convertAndSend("/topic/game-status",  event);
    }

    @KafkaListener(topics = "${player.player-changed-topic-name}", groupId = "game-of-three-group")
    public void handlePlayerConnected(PlayerChangedEvent event) {
        log.info("Player changed: " + event.getPlayer().getId());
        sendMembersList();
    }

    private void sendMembersList() {
        List<Player> memberList = playerService.getConnectedPlayers();
        for (Player player : memberList) {
            messagingTemplate.convertAndSendToUser(player.getId(), "/topic/players", filterMemberListByUser(memberList, player));
        }
    }

    List<Player> filterMemberListByUser(List<Player> players, Player player) {
        return players.stream().filter(filterUser -> !filterUser.getId().equals(player.getId()))
                .toList();
    }

}
