package com.mattdion.skyblockbazaar.player;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

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
    public Map<String, Player> addUser(@RequestParam("name") String name) throws NoPlayerFoundException {
        return playerMapService.addPlayer(name);
    }

    @ExceptionHandler(NoPlayerFoundException.class)
    public ResponseEntity<String> noPlayerFoundExceptionHandler(Exception e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
