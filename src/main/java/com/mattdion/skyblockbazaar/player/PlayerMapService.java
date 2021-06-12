package com.mattdion.skyblockbazaar.player;

import com.mattdion.skyblockbazaar.exceptions.NoPlayerFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.TimeoutException;

@Service
public class PlayerMapService {
    private final PlayerMap playerMap;

    @Autowired
    public PlayerMapService(PlayerMap playerMap) {
        this.playerMap = playerMap;
    }

    public Player addPlayer(String name) throws NoPlayerFoundException, TimeoutException {
        Player player = playerMap.getPlayer(name);
        if (player == null) return playerMap.addPlayer(name);

        if (Duration.between(player.getLastUpdated(), Instant.now())
                .compareTo(Duration.ofMinutes(Player.UUID_UPDATE_INTERVAL_MINUTES)) <= 0) {
            return playerMap.getPlayer(name);
        }
        playerMap.removePlayer(name);
        return playerMap.addPlayer(name);
    }
}
