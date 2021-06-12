package com.mattdion.skyblockbazaar.player;

import com.mattdion.skyblockbazaar.exceptions.NoPlayerFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.concurrent.TimeoutException;

@RestController
public class PlayerMapController {
    private final PlayerMapService playerMapService;

    @Autowired
    public PlayerMapController(PlayerMapService playerMapService) {
        this.playerMapService = playerMapService;
    }

    @PostMapping(
            path = "api/v1/adduser",
            consumes = MediaType.ALL_VALUE
    )
    public Player addUser(@RequestParam("name") String name) {
        try {
            return playerMapService.addPlayer(name);
        } catch (NoPlayerFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No player found", e);
        } catch (TimeoutException e) {
            throw new ResponseStatusException(HttpStatus.GATEWAY_TIMEOUT, "Can't connect to Mojang API", e);
        }
    }
}
