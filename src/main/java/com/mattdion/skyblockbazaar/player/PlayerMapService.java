package com.mattdion.skyblockbazaar.player;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlayerMapService {
    private final PlayerMap playerMap;

    @Autowired
    public PlayerMapService(PlayerMap playerMap) {
        this.playerMap = playerMap;
    }

    public void addPlayer(String name) {
        playerMap.addPlayer(name);
    }
}
