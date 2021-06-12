package com.mattdion.skyblockbazaar.controllers;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.mattdion.skyblockbazaar.hypixelapi.BazaarReplyService;
import com.mattdion.skyblockbazaar.hypixelapi.PlayerReplyService;
import com.mattdion.skyblockbazaar.minions.MinionID;
import com.mattdion.skyblockbazaar.exceptions.NoPlayerFoundException;
import com.mattdion.skyblockbazaar.player.Player;
import com.mattdion.skyblockbazaar.player.PlayerMapService;
import net.hypixel.api.reply.skyblock.SkyBlockProfileReply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.concurrent.TimeoutException;

@RestController
@RequestMapping("/api/v1")
public class MainController {
    private final BazaarReplyService bazaarReplyService;
    private final PlayerReplyService playerReplyService;
    private final PlayerMapService playerMapService;

    @Autowired
    public MainController(BazaarReplyService bazaarReplyService, PlayerReplyService playerReplyService, PlayerMapService playerMapService) {
        this.bazaarReplyService = bazaarReplyService;
        this.playerReplyService = playerReplyService;
        this.playerMapService = playerMapService;
    }

    @GetMapping("/main/{name}")
    public Object getReply(@PathVariable String name) throws TimeoutException, NoPlayerFoundException {
        Player player = playerMapService.addPlayer(name);
        String fixed_uuid = player.getId().toString().replace("-", "");

        SkyBlockProfileReply skyBlockProfileReply = playerReplyService.getSkyblockProfileReply(fixed_uuid);

        JsonArray craftedGenerators =
                skyBlockProfileReply.getProfile()
                        .getAsJsonObject("members")
                        .getAsJsonObject(fixed_uuid)
                        .getAsJsonArray("crafted_generators");

        HashMap<MinionID, Integer> minionLevels = new HashMap<>();

        // initialize minionLevels map
        for(MinionID minionID : MinionID.values()) {
            minionLevels.put(minionID, 0);
        }

        for(int i = 0; i < craftedGenerators.size(); ++i) {
            JsonElement element = craftedGenerators.get(i);
            String generator = element.getAsString();
            int delimiter = generator.lastIndexOf("_");

            String generatorName = generator.substring(0, delimiter);
            int generatorLevel = Integer.parseInt(generator.substring(delimiter + 1));

            MinionID minionID = MinionID.valueOf(generatorName);
            int actualGeneratorLevel = minionLevels.get(minionID);
            if(generatorLevel > actualGeneratorLevel) {
                minionLevels.put(minionID, generatorLevel);
            }
        }

        player.updateMinionMap(minionLevels);

        return player;
    }

}
