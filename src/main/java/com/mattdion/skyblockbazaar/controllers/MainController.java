package com.mattdion.skyblockbazaar.controllers;

import com.mattdion.skyblockbazaar.exceptions.NoPlayerFoundException;
import com.mattdion.skyblockbazaar.player.Player;
import com.mattdion.skyblockbazaar.player.PlayerProductionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.concurrent.TimeoutException;

@RestController
@CrossOrigin
@RequestMapping("/api/v1")
public class MainController {
    private final PlayerProductionService playerProductionService;

    @Autowired
    public MainController(PlayerProductionService playerProductionService) {
        this.playerProductionService = playerProductionService;
    }

    @GetMapping("/main/{name}")
    public Object getReply(@PathVariable String name) throws NoPlayerFoundException {
        try {
            Player player = playerProductionService.addPlayer(name);
            playerProductionService.updateMinionMap(player);
            playerProductionService.getPlayerProducts(player);
            return player;
        } catch (TimeoutException e) {
            throw new ResponseStatusException(HttpStatus.GATEWAY_TIMEOUT, "External API timeout", e);
        }
    }
}
