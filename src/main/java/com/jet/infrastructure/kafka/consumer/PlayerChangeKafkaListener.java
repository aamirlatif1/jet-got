package com.jet.infrastructure.kafka.consumer;

import com.jet.infrastructure.kafka.model.PlayerChangeModel;
import com.jet.player.entity.Player;
import com.jet.player.service.PlayerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class PlayerChangeKafkaListener implements KafkaConsumer<PlayerChangeModel>{

    private final PlayerService playerService;
    private final SimpMessagingTemplate messagingTemplate;

    @Override
    @KafkaListener(id = "game-of-three-group",
            topics = "${player.player-changed-topic-name}")
    public void receive(@Payload List<PlayerChangeModel> messages,
                        @Header(KafkaHeaders.RECEIVED_KEY) List<String> keys,
                        @Header(KafkaHeaders.RECEIVED_PARTITION) List<Integer> partitions,
                        @Header(KafkaHeaders.OFFSET) List<Long> offsets) {
        log.info("{} number of payment requests received with keys:{}, partitions:{} and offsets: {}",
                messages.size(),
                keys.toString(),
                partitions.toString(),
                offsets.toString());

        messages.forEach(message -> {
            log.info("Player changed: " + message.playerId());
            sendMembersList();
        });
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
