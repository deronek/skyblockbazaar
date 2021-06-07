package com.mattdion.skyblockbazaar.player;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Repository
public class PlayerMap {
    private static final Duration REQUEST_TIMEOUT = Duration.ofSeconds(5);
    private final WebClient mojangAPIClient;
    private final Map<String, Player> playerMap;

    @Autowired
    public PlayerMap(WebClient.Builder builder) {
        this.mojangAPIClient = builder.baseUrl("https://api.mojang.com/users/profiles/minecraft/").build();
        this.playerMap = new HashMap<>();
    }

    /**
     * @param name player name
     * @throws NoPlayerFoundException when no player is found in Mojang API database
     */
    public void addPlayer(String name) throws NoPlayerFoundException {
        Player player = mojangAPIClient
                .get()
                .uri(name)
                .retrieve()
                .bodyToMono(Player.class)
                .block(REQUEST_TIMEOUT);

        if (player == null) throw new NoPlayerFoundException("Player not found");
        playerMap.put(player.getName(), player);
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
