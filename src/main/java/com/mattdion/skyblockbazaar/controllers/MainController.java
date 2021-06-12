package com.mattdion.skyblockbazaar.controllers;

import com.mattdion.skyblockbazaar.exceptions.NoPlayerFoundException;
import com.mattdion.skyblockbazaar.hypixelapi.BazaarReplyService;
import com.mattdion.skyblockbazaar.player.Player;
import com.mattdion.skyblockbazaar.player.PlayerMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeoutException;

@RestController
@RequestMapping("/api/v1")
public class MainController {
    private final BazaarReplyService bazaarReplyService;
    private final PlayerMapService playerMapService;

    @Autowired
    public MainController(BazaarReplyService bazaarReplyService, PlayerMapService playerMapService) {
        this.bazaarReplyService = bazaarReplyService;
        this.playerMapService = playerMapService;
    }

    @GetMapping("/main/{name}")
    public Object getReply(@PathVariable String name) throws TimeoutException, NoPlayerFoundException {
        Player player = playerMapService.addPlayer(name);
        playerMapService.updateMinionMap(player);

        return player;
    }

}
