package com.mattdion.skyblockbazaar.player;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public void addUser(@RequestParam("name") String name) {
        playerMapService.addPlayer(name);
    }
}
