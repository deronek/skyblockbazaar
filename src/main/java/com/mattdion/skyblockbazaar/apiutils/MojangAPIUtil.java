package com.mattdion.skyblockbazaar.apiutils;

import com.mattdion.skyblockbazaar.exceptions.NoPlayerFoundException;
import com.mattdion.skyblockbazaar.player.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ResponseStatusException;

import java.time.Duration;
import java.util.concurrent.TimeoutException;

@Repository
public class MojangAPIUtil {
    private static final Duration REQUEST_TIMEOUT = Duration.ofSeconds(5);
    private static final String PLAYER_NOT_FOUND_MOJANG = "Player not found in Mojang API database";
    private final WebClient mojangAPIClient;

    @Autowired
    public MojangAPIUtil(WebClient.Builder builder) {
        this.mojangAPIClient = builder
                .baseUrl("https://api.mojang.com/users/profiles/minecraft/")
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

            if (player == null) throw new NoPlayerFoundException(PLAYER_NOT_FOUND_MOJANG);
            return player;
        } catch (IllegalStateException e) {
            throw new TimeoutException("Mojang API timeout");
        } catch (WebClientResponseException e) {
            throw new ResponseStatusException(e.getStatusCode());
        }
    }
}
