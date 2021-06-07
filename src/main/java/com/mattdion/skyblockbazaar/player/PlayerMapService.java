package com.mattdion.skyblockbazaar.player;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;

@Service
public class PlayerMapService {
    private final PlayerMap playerMap;

    @Autowired
    public PlayerMapService(PlayerMap playerMap) {
        this.playerMap = playerMap;
    }

    public Map<String, Player> addPlayer(String name) throws NoPlayerFoundException {
        Player player = playerMap.getPlayer(name);
        if (player != null) {
            if (Duration.between(player.getLastUpdated(), Instant.now())
                    .compareTo(Duration.ofMinutes(Player.UPDATE_INTERVAL_MINUTES)) <= 0) {
                return playerMap.getPlayers();
            }
            playerMap.removePlayer(name);
        }
        playerMap.addPlayer(name);
        return playerMap.getPlayers();
    }
}
