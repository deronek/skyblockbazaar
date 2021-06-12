package com.mattdion.skyblockbazaar.player;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.mattdion.skyblockbazaar.Utils;
import com.mattdion.skyblockbazaar.exceptions.NoPlayerFoundException;
import com.mattdion.skyblockbazaar.hypixelapi.PlayerReplyService;
import com.mattdion.skyblockbazaar.minions.MinionID;
import net.hypixel.api.reply.skyblock.SkyBlockProfileReply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

@Service
public class PlayerMapService {
    private final PlayerMap playerMap;
    private final PlayerReplyService playerReplyService;

    @Autowired
    public PlayerMapService(PlayerMap playerMap, PlayerReplyService playerReplyService) {
        this.playerMap = playerMap;
        this.playerReplyService = playerReplyService;
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

    public void updateMinionMap(Player player) {
        Map<MinionID,Integer> minionLevels = playerReplyService.getMinionLevelsMap(player);

        player.updateMinionMap(minionLevels);
    }
}
