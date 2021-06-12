package com.mattdion.skyblockbazaar.apiutils;

import com.mattdion.skyblockbazaar.exceptions.NoPlayerFoundException;
import com.mattdion.skyblockbazaar.player.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;
import java.util.concurrent.TimeoutException;

@Repository
public class MojangAPIUtil {
    private final WebClient mojangAPIClient;
    private static final Duration REQUEST_TIMEOUT = Duration.ofSeconds(5);

    @Autowired
    public MojangAPIUtil(WebClient.Builder builder) {
        this.mojangAPIClient = builder
                .baseUrl("https://api.mojang.com/users/profiles/minecraft/")
                //.baseUrl("http://10.255.255.1")
                .build();
    }

    public Player getPlayer(String name) throws NoPlayerFoundException, TimeoutException {
        try {
            Player player = mojangAPIClient
                    .get()
                    .uri(name)
                    .retrieve()
                    .bodyToMono(Player.class)
                    .block(REQUEST_TIMEOUT);

            if (player == null) throw new NoPlayerFoundException("Player not found");
            return player;
        } catch (IllegalStateException e) {
            throw new TimeoutException();
        }
    }
}
