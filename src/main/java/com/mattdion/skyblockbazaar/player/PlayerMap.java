package com.mattdion.skyblockbazaar.player;

import com.mattdion.skyblockbazaar.apiutils.MojangAPIUtil;
import com.mattdion.skyblockbazaar.exceptions.NoPlayerFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

@Repository
public class PlayerMap {
    private final MojangAPIUtil mojangAPIUtil;
    private final Map<String, Player> playerMap;

    @Autowired
    public PlayerMap(MojangAPIUtil mojangAPIUtil) {
        this.mojangAPIUtil = mojangAPIUtil;
        this.playerMap = new HashMap<>();
    }

    /**
     * @param name {@link Player} name
     * @return {@link Player} added to {@link PlayerMap}
     * @throws NoPlayerFoundException when no player is found in Mojang API database
     * @throws TimeoutException       when request to Mojang API times out
     */
    public Player addPlayer(String name) throws NoPlayerFoundException, TimeoutException {
        Player player = mojangAPIUtil.getPlayer(name);
        playerMap.put(player.getName(), player);
        return player;
    }

    public Map<String, Player> getPlayers() {
        return new HashMap<>(playerMap);
    }

    public Player getPlayer(String name) {
        return playerMap.get(name);
    }

    public Player removePlayer(String name) {
        return playerMap.remove(name);
    }
}
