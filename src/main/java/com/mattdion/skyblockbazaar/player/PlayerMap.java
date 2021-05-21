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

    public void addPlayer(String name) {
        Player player = mojangAPIClient
                .get()
                .uri(name)
                .retrieve()
                .bodyToMono(Player.class)
                .block(REQUEST_TIMEOUT);
        playerMap.put(player.getName(), player);
    }
}
